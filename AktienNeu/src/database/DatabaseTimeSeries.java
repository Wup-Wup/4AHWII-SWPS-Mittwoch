package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class DatabaseTimeSeries {

	private String sqlUsername;
	private String sqlPassword;
	private String connectionUrl;
	private Connection connection;

	public DatabaseTimeSeries(String sqlUsername, String sqlPassword, String databaseName, String connectionUrl) {
		this.sqlUsername = sqlUsername;
		this.sqlPassword = sqlPassword;
		this.connectionUrl = connectionUrl;
	}
	
	public void openConnection() {
		if (this.connection != null) {
			return;
		}
		try {
			this.connection = DriverManager.getConnection(this.connectionUrl, this.sqlUsername, this.sqlPassword);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void closeConnection() {
		if (this.connection == null) {
			return;
		}
		try {
			this.connection.close();
			this.connection = null;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void createTimeSeriesTable(String tableName) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("create table if not exists " + tableName
					+ " (" + "day date not null primary key, close decimal(11,2) not null)");
			pstatement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void insertIntoSeriesTable(String tableName, Date date, BigDecimal closeValue) {
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("replace into " + tableName + " (day, close) values (?,?) ");
			pstatement.setDate(1, date);
			pstatement.setBigDecimal(2, closeValue);
			pstatement.executeUpdate();

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void create200AvgTable(String tableName) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("create table if not exists " + tableName
					+ " (day date not null primary key, avgclose decimal(11,2) not null)");
			pstatement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public BigDecimal calculate200avg(String tableName, Date date) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("with temp as ( "
					+ "	select close from "+tableName+" where day <= '"+date+"' order by day desc limit 200)"
					+ " select avg(close) from temp");
			ResultSet rset = pstatement.executeQuery();
			rset.next();
			return rset.getBigDecimal(1);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
			return null;
	}
	
	public void insertInto200avg(String tableName, Date date, BigDecimal avg200) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("replace into "+tableName+" (day, avgclose) values (?,?)");
			pstatement.setDate(1,date);
			pstatement.setBigDecimal(2, avg200);
			pstatement.executeUpdate();
			
		}catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	public void calculateAll200Avg(String tableName, String avgTableName) {
		
		try{
			PreparedStatement pstatement = this.connection.prepareStatement("select count(day) from "+tableName);
			ResultSet countSet = pstatement.executeQuery();
			countSet.next();
			int count = countSet.getInt(1);
			PreparedStatement p2statement = this.connection.prepareStatement("select day from "+tableName);
			ResultSet dateSet = p2statement.executeQuery(); 
			for (int i = 0; i < count; i++) {
				dateSet.next();
				Date date = dateSet.getDate(1);
				insertInto200avg(avgTableName, date, calculate200avg(tableName, date));
			}
		}catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	public TreeMap<Date,BigDecimal> getAll200Avg(String tableName) {
		try {
			TreeMap<Date,BigDecimal> data200Avg = new TreeMap<Date,BigDecimal>();
			PreparedStatement p2statement = this.connection.prepareStatement("select * from "+tableName);
			ResultSet dataSet = p2statement.executeQuery();
			while(dataSet.next()) {
				data200Avg.put(dataSet.getDate(1), dataSet.getBigDecimal(2));
			}
			
	/*		for(Date day : data200Avg.keySet()) {
				System.out.println(day.toString()+" "+data200Avg.get(day));
			}*/
			return data200Avg;
		}catch(SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
	
	public int getBackground(String tableName) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("select close from "+tableName+" order by day desc limit 1");
			PreparedStatement p2statement = this.connection.prepareStatement("select avgclose from "+tableName+"200avg order by day desc limit 1");
			ResultSet close = pstatement.executeQuery();
			ResultSet avgClose = p2statement.executeQuery();
			close.next();
			avgClose.next();
			int res = close.getBigDecimal(1).compareTo(avgClose.getBigDecimal(1));
			return res;
		}catch(SQLException throwables) {
			throwables.printStackTrace();
		}
		return 0;
	}
}
