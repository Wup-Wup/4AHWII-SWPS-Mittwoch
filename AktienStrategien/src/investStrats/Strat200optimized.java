package investStrats;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Strat200optimized {

	private String sqlUsername;
	private String sqlPassword;
	private String connectionUrl;
	private Connection connection;

	public Strat200optimized(String sqlUsername, String sqlPassword, String databaseName, String connectionUrl) {
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
					"create table if not exists "+ticker+"200stratOptimized (day varchar(100) not null primary key, ticker varchar(10) not null, flag boolean, count int, konto decimal(11,2))");
			pstatement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void firstEntry(String ticker, BigDecimal start) {
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("replace into "+ticker+"200stratOptimized (day, ticker, flag, count, konto) values (?,?,?,?,?)");
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
					.prepareStatement("replace into "+ticker+"200stratOptimized(day, ticker, flag, count, konto) values (?,?,?,?,?)");
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
				LocalDate dates = LocalDate.parse(dateSet.getString(1));
				sellOrBuy(dates, ticker);
				lastDate = dates;
			}
			sellOut(lastDate, ticker);
		} catch (SQLException throwables) {
			System.out.println(ticker+" is no valid share price in the dabase");
			dropTable(ticker+"200stratOptimized");
		}
	}

	public boolean boughtOrSold(String ticker) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("select flag from "+ticker+"200stratOptimized order by day desc limit 1");
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			return rs.getBoolean(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void sellOrBuy(LocalDate date, String ticker) {
		
		if (boughtOrSold(ticker)) {
			if (compareBigger(getClose(date, ticker),get200avg(date, ticker))) {
				
				
				double mbefore = moneyBefore(date, ticker).doubleValue();
				int count = getCount(date, ticker).intValue();
				double nmoney = mbefore + count*getClose(date, ticker).doubleValue();
				
				insertIntoTable(date, ticker, false, 0, BigDecimal.valueOf(nmoney));
			}
		} else {
			if (compareSmaller(getClose(date, ticker),get200avg(date, ticker))) {

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
			pstatement = this.connection.prepareStatement("select konto from "+ticker+"200stratOptimized order by day desc limit 1");
			ResultSet result = pstatement.executeQuery();
			result.next();
			return result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BigDecimal getCount(LocalDate date, String ticker) {
		
		PreparedStatement pstatement;
		try {
			pstatement = this.connection.prepareStatement("select count from "+ticker+"200stratOptimized order by day desc limit 1");
			ResultSet result = pstatement.executeQuery();
			result.next();
			return result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BigDecimal getClose(LocalDate date, String ticker) {
		BigDecimal close = null;
		try {
			PreparedStatement pstatement = this.connection
					.prepareStatement("select adjustedClose from " + ticker + " where day = '" + date + "'");
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
					.prepareStatement("select avgcloseAdjusted from " + ticker + "200avg where day = '" + date + "'");
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
	
	public void updateCount(LocalDate date, double split, String ticker) {
		try {
			PreparedStatement pstatement = this.connection.prepareStatement("update "+ticker+"200stratOptimized set count = count * "+split+" where day= '"+ date +"'");
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
	
	public boolean compareBigger(BigDecimal close, BigDecimal avg200){
		
		BigDecimal avg200Plus3Percent;
		
		avg200Plus3Percent = avg200.multiply(BigDecimal.valueOf(1.03));
		
		if(close.compareTo(avg200Plus3Percent)==1) {
			return true;
		}
		return false;
	}
	
	public boolean compareSmaller(BigDecimal close, BigDecimal avg200){
		
		BigDecimal avg200Minus3Percent;
		
		avg200Minus3Percent = avg200.multiply(BigDecimal.valueOf(0.97));
		
		if(close.compareTo(avg200Minus3Percent)==-1) {
			return true;
		}
		return false;
	}
	
	public void print(String ticker) {
		
		System.out.println("Strategie 200StratOptimized:");
				
		PreparedStatement pstatement;
		try {
			pstatement = this.connection
					.prepareStatement("select day, konto from " + ticker+"200stratOptimized order by day desc");
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
