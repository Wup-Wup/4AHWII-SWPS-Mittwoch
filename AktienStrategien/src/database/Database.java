package database;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Database {

	private String sqlUsername;
	private String sqlPassword;
	private String connectionUrl;
	private Connection connection;

	public Database(String sqlUsername, String sqlPassword, String databaseName, String connectionUrl) {
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
					"create table if not exists "+ticker+"200strat (day varchar(100) not null primary key, ticker varchar(10) not null, flag boolean, count int, konto decimal(11,2))");
			pstatement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void firstEntry(String ticker, BigDecimal start) {
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("replace into "+ticker+"200strat (day, ticker, flag, count, konto) values (?,?,?,?,?)");
			pstatement.setString(1, "1000-11-11");
			pstatement.setString(2, ticker);
			pstatement.setBoolean(3, false);
			pstatement.setInt(4, 0);
			pstatement.setBigDecimal(5, start);
			pstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertIntoTable(LocalDate date, String ticker, boolean flag, int count, BigDecimal konto) {
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("replace into "+ticker+"200strat(day, ticker, flag, count, konto) values (?,?,?,?,?)");
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

	public void getInsertData(LocalDate date, String ticker) {
		LocalDate lastDate = null;
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("select count(day) from " + ticker + " where day > '" + date + "'");
			ResultSet countSet = pstatement.executeQuery();
			countSet.next();
			int count = countSet.getInt(1);
			PreparedStatement p2statement = this.connection
					.prepareStatement("select day from " + ticker + " where day > '" + date + "'");
			ResultSet dateSet = p2statement.executeQuery();
			for (int i = 0; i < count; i++) {
				dateSet.next();
				String dates = dateSet.getString(1);
				updateCount(date,getSplit(ticker,date), ticker);
				sellOrBuy(LocalDate.parse(dates), ticker);
				lastDate = date;
			}
			sellOut(lastDate, ticker);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public boolean boughtOrSold(String ticker) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("select flag from "+ticker+"200strat order by day desc limit 1");
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			return rs.getBoolean(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void sellOrBuy(LocalDate date, String ticker) {
		
		if (boughtOrSold(ticker)) {
			if (getClose(date, ticker).compareTo(get200avg(date, ticker)) == 1) {
				
				
				double mbefore = moneyBefore(date, ticker).doubleValue();
				int count = getCount(date, ticker).intValue();
				double nmoney = mbefore + count*getClose(date, ticker).doubleValue();
				
				insertIntoTable(date, ticker, false, 0, BigDecimal.valueOf(nmoney));
			}
		} else {
			if (getClose(date, ticker).compareTo(get200avg(date, ticker)) == -1) {

				double mbefore = moneyBefore(date, ticker).doubleValue();
				int roundedNumber = (int) (mbefore / getClose(date, ticker).doubleValue());
				double nmoney = mbefore % getClose(date, ticker).doubleValue();

				insertIntoTable(date, ticker, true, roundedNumber, BigDecimal.valueOf(nmoney));
			}
		}
	}

	public BigDecimal moneyBefore(LocalDate date, String ticker) {

		PreparedStatement pstatement;
		try {
			pstatement = this.connection.prepareStatement("select konto from "+ticker+"200strat order by day desc limit 1");
			ResultSet result = pstatement.executeQuery();
			result.next();
			return result.getBigDecimal(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public BigDecimal getCount(LocalDate date, String ticker) {
		
		PreparedStatement pstatement;
		try {
			pstatement = this.connection.prepareStatement("select count from "+ticker+"200strat order by day desc limit 1");
			ResultSet result = pstatement.executeQuery();
			result.next();
			return result.getBigDecimal(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public BigDecimal getClose(LocalDate date, String ticker) {
		BigDecimal close = null;
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("select Close from " + ticker + " where day = '" + date + "'");
			ResultSet result = pstatement.executeQuery();
			result.next();
			close = result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return close;
	}

	public BigDecimal get200avg(LocalDate date, String ticker) {
		BigDecimal avg200 = null;
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("select avgclose from " + ticker + "200avg where day = '" + date + "'");
			ResultSet result = pstatement.executeQuery();
			result.next();
			avg200 = result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return avg200;
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
	public double getSplit(String ticker, LocalDate date) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("select split from " + ticker + " where day = '" + date + "'");
			ResultSet result = pstatement.executeQuery();
			result.next();
			double split = result.getDouble(1);
			return split;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Double) null;
	}
	
	public void updateCount(LocalDate date, double split, String ticker) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("update "+ticker+"200strat set count = count * "+split);
			pstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sellOut(LocalDate date, String ticker) {
		if(getCount(date, ticker).doubleValue()>0) {
			double mbefore = moneyBefore(date, ticker).doubleValue();
			int count = getCount(date, ticker).intValue();
			double nmoney = mbefore + count*getClose(date, ticker).doubleValue();
			
			insertIntoTable(LocalDate.now(), ticker, false, 0, BigDecimal.valueOf(nmoney));
		}
	}
}
