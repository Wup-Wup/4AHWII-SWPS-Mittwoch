import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

	static String url;

	public static void db() {
		connect();
		createNewTable();
		insert();
		selectAll();
	}

	public static void connect() {
		Connection conn = null;
		try {
			// db parameters
			url = "jdbc:sqlite:C:\\Users\\Alexander Bertoni\\Documents\\Schule\\3.Klasse\\Programmieren\\Programme\\Feiertag\\Datenbank.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public static void createNewTable() {
		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS Feiertage(" + "Montag integer,\n" + "Dienstag integer ,\n"
				+ "Mittwoch integer ,\n" + "Donnerstag integer,\n" + "Freitag integer);";

		try {
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

	private static Connection connection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public static void insert() {
		String sql = "INSERT INTO Feiertage(Montag,Dienstag,Mittwoch,Donnerstag,Freitag) VALUES(?,?,?,?,?)";

		try {

			Connection conn = connection();

			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, LookAtDates.monh2);
			pstmt.setInt(2, LookAtDates.tueh2);
			pstmt.setInt(3, LookAtDates.wedh2);
			pstmt.setInt(4, LookAtDates.thuh2);
			pstmt.setInt(5, LookAtDates.frih2);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Hier");
		}
	}

	public static void selectAll() {
		String sql = "SELECT * FROM Feiertage ";

		try {
			Connection conn = connection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println("Montag: " + rs.getInt("Montag") + "\nDienstag: " + rs.getInt("Dienstag")
						+ "\nMittwoch: " + rs.getInt("Mittwoch") + "\nDonnerstag: " + rs.getInt("Donnerstag")
						+ "\nFreitag: " + rs.getInt("Freitag")+"\n");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("proof");
		}
	}
}
