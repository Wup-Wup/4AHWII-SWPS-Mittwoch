import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class TestMain {

	public static void main(String[] args) throws MalformedURLException, JSONException, IOException {
		
		System.out.println();
		test();
		connect();
		createNewTable();
		
		for(int i=0;i<dates.size();i++) {
			insert(i);
		}
		selectAll();
	}
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
	static String urld;
	static ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
	static ArrayList <Double> wert = new ArrayList <Double>();
	static String s;
	
	
	public static void test() throws MalformedURLException, JSONException, IOException {
		 
		System.out.println("Bitte geben sie eine der folgenden Aktienkurse ein: Tsla(Tesla), Ap(Apple)");

		Scanner gustav = new Scanner(System.in);
		s=gustav.next();
		
		String url="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+s+"&interval=&apikey=X53EB9ALD4OMI2QH";
		JSONObject json = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));
		
		json = json.getJSONObject("Time Series (Daily)");
		
		for (int i=0; i<100; i++) {
	           dates.add(LocalDate.parse((CharSequence) json.names().get(i)));
	           wert.add(json.getJSONObject(LocalDate.parse((CharSequence)json.names().get(i)).toString()).getDouble("4. close"));
	           System.out.println(dates.get(i));
	           System.out.println(wert.get(i));
	       }
	}
	
	public static void connect() {
		Connection conn = null;
		try {
			// db parameters
			urld = "jdbc:sqlite:C:\\Users\\Alexander Bertoni\\Documents\\Schule\\3.Klasse\\Programmieren\\Programme\\Aktien\\Datenbank.db";
			// create a connection to the database
			conn = DriverManager.getConnection(urld);

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
			String sql = "CREATE TABLE IF NOT EXISTS "+s+"(" + "Datum date,\n" + "Wert decimal);";
			
			try {
				Connection conn = DriverManager.getConnection(urld);
				Statement stmt = conn.createStatement();
				stmt.execute(sql);
			} catch (SQLException e) {
				System.out.println(e.getMessage());

			}
		}
		
		private static Connection connection() {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(urld);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return conn;
		}
		
		public static void insert(int i) {
			String sql = "INSERT INTO "+s+"(Datum,Wert) VALUES(?,?)";

			try {

				Connection conn = connection();

				PreparedStatement pstmt = conn.prepareStatement(sql);

			    pstmt.setString(1, dates.get(i).format(formatter));
				pstmt.setDouble(2,wert.get(i));
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println("Hier");
			}
		}
		
		public static void selectAll() {
			String sql = "SELECT * FROM "+s;

			try {
				Connection conn = connection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {
					System.out.println("Datum: " + rs.getString("Datum")
							+ "\nWert: " + rs.getDouble("Wert")+"\n");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println("proof");
			}
		}
	}


	

