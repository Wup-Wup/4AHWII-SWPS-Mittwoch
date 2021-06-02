package investStrats;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BuyAndHold {


	private String sqlUsername;
	private String sqlPassword;
	private String connectionUrl;
	private Connection connection;

	public BuyAndHold(String sqlUsername, String sqlPassword, String databaseName, String connectionUrl) {
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
	
	public void createTable(String ticker) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement(
					"create table if not exists "+ticker+"BuyAndHold (day varchar(100) not null primary key, ticker varchar(10) not null, flag boolean, count int, konto decimal(11,2))");
			pstatement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	public void dropTable(String ticker) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement(
					"drop table if exists "+ticker+"");
			pstatement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	public void insertIntoTable(LocalDate date, String ticker, boolean flag, int count, BigDecimal konto) {
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("replace into "+ticker+"BuyAndHold(day, ticker, flag, count, konto) values (?,?,?,?,?)");
			pstatement.setString(1, date.toString());
			pstatement.setString(2, ticker);
			pstatement.setBoolean(3, flag);
			pstatement.setInt(4, count);
			pstatement.setBigDecimal(5, konto);
			pstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void firstAndLastInsert(LocalDate startDate, String ticker, BigDecimal startMoney) {
		
		int roundedNumber = (int) (startMoney.doubleValue() / getClose(startDate, ticker).doubleValue());
		double nmoney = startMoney.doubleValue() % getClose(startDate, ticker).doubleValue();

		insertIntoTable(startDate, ticker, true, roundedNumber, BigDecimal.valueOf(nmoney));
		
		nmoney = nmoney + roundedNumber*getLastClose(ticker).doubleValue();
		
		insertIntoTable(LocalDate.now(), ticker, false, roundedNumber, BigDecimal.valueOf(nmoney));
	}
	
	public BigDecimal getClose(LocalDate date, String ticker) {
		BigDecimal close = null;
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("select adjustedClose from " + ticker + " where day >= '" + date + "' order by day asc limit 1");
			ResultSet result = pstatement.executeQuery();
			result.next();
			close = result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return close;
	}
	
	public BigDecimal getLastClose(String ticker) {
		BigDecimal close = null;
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("select adjustedClose from " + ticker + " order by day desc limit 1");
			ResultSet result = pstatement.executeQuery();
			result.next();
			close = result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return close;
	}
	
	public void print(String ticker) {
		
		System.out.println("Strategie Buy and Hold:");
				
		PreparedStatement pstatement;
		try {
			pstatement = this.connection
					.prepareStatement("select day, konto from " + ticker+"BuyAndHold order by day desc");
			ResultSet countSet = pstatement.executeQuery();
			countSet.next();
			String day = countSet.getString(1);
			String konto = countSet.getString(2);
			System.out.println("Enddatum: "+day+"\nEndgeld: "+konto+"€\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
