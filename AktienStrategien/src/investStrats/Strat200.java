package investStrats;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Main.Database;

public class Strat200 {

	public static void firstEntry(String ticker, BigDecimal start) {
		try {
			PreparedStatement pstatement = Database.connection
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

	public static void insertIntoTable(LocalDate date, String ticker, boolean flag, int count, BigDecimal konto) {
		try {
			PreparedStatement pstatement = Database.connection
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

	public static boolean getInsertData(LocalDate date, String ticker) {
		LocalDate lastDate = null;
		try {
			PreparedStatement pstatement = Database.connection
					.prepareStatement("select count(day) from " + ticker + " where day > '" + date + "'");
			ResultSet countSet = pstatement.executeQuery();
			countSet.next();
			int count = countSet.getInt(1);
			PreparedStatement p2statement = Database.connection
					.prepareStatement("select day from " + ticker + " where day > '" + date + "'");
			ResultSet dateSet = p2statement.executeQuery();
			for (int i = 0; i < count; i++) {
				dateSet.next();
				LocalDate dates = LocalDate.parse(dateSet.getString(1));
				sellOrBuy(dates, ticker);
				lastDate = dates;
			}
			sellOut(lastDate, ticker);
			return false;
		} catch (SQLException throwables) {
			System.out.println(ticker+" is no valid share price in the dabase \n");
			Database.dropTable(ticker+"200strat");
			return true;
		}
	}

	public static boolean boughtOrSold(String ticker) {
		try {
			PreparedStatement pstatement = Database.connection.prepareStatement("select flag from "+ticker+"200strat order by day desc limit 1");
			ResultSet rs = pstatement.executeQuery();
			rs.next();
			return rs.getBoolean(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void sellOrBuy(LocalDate date, String ticker) {
		
		BigDecimal close = Database.getClose(date, ticker, false);
		BigDecimal get200avg = Database.get200avg(date, ticker);
		
		if (boughtOrSold(ticker)) {
			if (close.compareTo(get200avg) == -1) {
				
				
				double mbefore = moneyBefore(date, ticker).doubleValue();
				int count = getCount(date, ticker).intValue();
				double nmoney = mbefore + count*close.doubleValue();
				
				insertIntoTable(date, ticker, false, 0, BigDecimal.valueOf(nmoney));
			}
		} else {
			if (close.compareTo(get200avg) == 1 || close.compareTo(get200avg) == 0) {

				double mbefore = moneyBefore(date, ticker).doubleValue();
				int roundedNumber = (int) (mbefore / close.doubleValue());
				double nmoney = mbefore % close.doubleValue();

				insertIntoTable(date, ticker, true, roundedNumber, BigDecimal.valueOf(nmoney));
			}
		}
	}

	public static BigDecimal moneyBefore(LocalDate date, String ticker) {

		PreparedStatement pstatement;
		try {
			pstatement = Database.connection.prepareStatement("select konto from "+ticker+"200strat order by day desc limit 1");
			ResultSet result = pstatement.executeQuery();
			result.next();
			return result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BigDecimal getCount(LocalDate date, String ticker) {
		
		PreparedStatement pstatement;
		try {
			pstatement = Database.connection.prepareStatement("select count from "+ticker+"200strat order by day desc limit 1");
			ResultSet result = pstatement.executeQuery();
			result.next();
			return result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void sellOut(LocalDate date, String ticker) {
		if(getCount(date, ticker).doubleValue()>0) {
			double mbefore = moneyBefore(date, ticker).doubleValue();
			int count = getCount(date, ticker).intValue();
			double nmoney = mbefore + count*Database.getClose(date, ticker, false).doubleValue();
			
			insertIntoTable(LocalDate.now(), ticker, false, 0, BigDecimal.valueOf(nmoney));
		}
	}
}
