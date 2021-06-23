package Main;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;

public class Database {

	public static Connection connection;
	static  NumberFormat n = NumberFormat.getInstance();
	


	public static void openConnection(String connectionUrl, String sqlUsername, String sqlPassword) {
		if (connection != null) {
			return;
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, sqlUsername, sqlPassword);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public static void closeConnection() {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
			connection = null;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public static void createTable(String ticker) {
		try {
			PreparedStatement pstatement = connection.prepareStatement("create table if not exists " + ticker
					+ " (day varchar(100) not null primary key, ticker varchar(10) not null, flag boolean, count int, konto decimal(11,4))");
			pstatement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public static void dropTable(String ticker) {
		try {
			PreparedStatement pstatement = connection.prepareStatement("drop table if exists " + ticker + "");
			pstatement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public static double print(String ticker, String method, double startMoney) {
		
		n.setMaximumFractionDigits(2);

		System.out.println("Strategie " + method + ":");

		PreparedStatement pstatement;
		try {
			pstatement = Database.connection
					.prepareStatement("select day, konto from " + ticker + "" + method + " order by day desc");
			ResultSet countSet = pstatement.executeQuery();
			countSet.next();
			String day = countSet.getString(1);
			double konto = countSet.getDouble(2);
			System.out.println("Enddatum: " + day + "\nEndgeld: " + n.format(konto) + "€");
			System.out.println("Prozentuelle Abweichung: " + (konto / startMoney) * 100 + "%\n");

			return konto;
		} catch (SQLException e) {
			return 0;
		}
	}

	public static BigDecimal getClose(LocalDate date, String ticker, boolean buyAndHold) {
		BigDecimal close = null;
		if (buyAndHold == true) {
			try {
				PreparedStatement pstatement = Database.connection
						.prepareStatement("select adjustedClose from " + ticker + " where day >= '" + date + "' order by day asc limit 1");
				ResultSet result = pstatement.executeQuery();
				result.next();
				close = result.getBigDecimal(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				PreparedStatement pstatement = Database.connection
						.prepareStatement("select adjustedClose from " + ticker + " where day = '" + date + "'");
				ResultSet result = pstatement.executeQuery();
				result.next();
				close = result.getBigDecimal(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return close;
	}

	public static BigDecimal get200avg(LocalDate date, String ticker) {
		BigDecimal avg200 = null;
		try {
			PreparedStatement pstatement = Database.connection
					.prepareStatement("select avgcloseAdjusted from " + ticker + "200avg where day = '" + date + "'");
			ResultSet result = pstatement.executeQuery();
			result.next();
			avg200 = result.getBigDecimal(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return avg200;
	}
}
