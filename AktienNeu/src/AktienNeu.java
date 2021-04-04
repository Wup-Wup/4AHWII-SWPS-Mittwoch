
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.TreeMap;

import org.json.JSONException;

import alphaVantage.AlphaVantage;
import database.DatabaseTimeSeries;
import javaSwing.MyJFrame;

public class AktienNeu {

	public static void main(String[] args) throws MalformedURLException, JSONException, IOException {

		ArrayList<String> aktien = new ArrayList<String>();
		String aktie;

		File file = new File("Aktienkurse.txt");
		if (!file.canRead() || !file.isFile())
			System.exit(0);

		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("Aktienkurse.txt"));
			while ((aktie = in.readLine()) != null) {

				TreeMap<Date, BigDecimal> timeSeries = AlphaVantage.getAdjustedTimeSeries(aktie,
						TextReader.reader("Key.txt"));

				if (timeSeries != null) {
					DatabaseTimeSeries dataB1 = new DatabaseTimeSeries("java", "java", "timeseriesdatabase",
							"jdbc:mysql://localhost:3306/timeseriesdatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
					dataB1.openConnection();
					dataB1.createTimeSeriesTable(aktie);
					for (Date date : timeSeries.keySet()) {
						dataB1.insertIntoSeriesTable(aktie, date, timeSeries.get(date));
					}

					dataB1.create200AvgTable(aktie + "200avg");
					dataB1.calculateAll200Avg(aktie, aktie + "200avg");

					MyJFrame chart1 = new MyJFrame("Aktienkurs");
					chart1.changeChart(aktie, dataB1.getAll200Avg(aktie + "200avg"), timeSeries,
							dataB1.getBackground(aktie), TextReader.reader("Path.txt"));
					dataB1.closeConnection();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
	}
}
