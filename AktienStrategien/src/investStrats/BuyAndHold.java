package investStrats;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Main.Database;

public class BuyAndHold {

	public static void insertIntoTable(LocalDate date, String ticker, boolean flag, int count, BigDecimal konto) {
		try {
			PreparedStatement pstatement = Database.connection
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
	
	public static void firstAndLastInsert(LocalDate startDate, String ticker, double startMoney) {
		
		double close = Database.getClose(startDate, ticker, true).doubleValue();
		
		int roundedNumber = (int) (startMoney / close);
		double nmoney = startMoney % close;

		insertIntoTable(startDate, ticker, true, roundedNumber, BigDecimal.valueOf(nmoney));
		
		nmoney = nmoney + roundedNumber*getLastClose(ticker).doubleValue();
		
		insertIntoTable(LocalDate.now(), ticker, false, roundedNumber, BigDecimal.valueOf(nmoney));
	}
	
	public static BigDecimal getLastClose(String ticker) {
		BigDecimal close = null;
		try {
			PreparedStatement pstatement = Database.connection
					.prepareStatement("select adjustedClose from " + ticker + " order by day desc limit 1");
			ResultSet result = pstatement.executeQuery();
			result.next();
			close = result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return close;
	}
}
