
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.TreeMap;

import org.json.JSONException;

import alphaVantage.AlphaVantage;
import database.DatabaseTimeSeries;
import javaSwing.MyJFrame;

public class AktienNeu {

	public static void main(String[] args) throws MalformedURLException, JSONException, IOException {
		
		String stonks = AbbreviationScanner.abbreviationScanner();
		
		TreeMap<Date,BigDecimal> timeSeries = AlphaVantage.getAdjustedTimeSeries(stonks, KeyReader.reader("Key.txt"));
		if(timeSeries!=null) {
			DatabaseTimeSeries kull = new DatabaseTimeSeries("java","java","timeseriesdatabase","jdbc:mysql://localhost:3306/timeseriesdatabase");
			kull.openConnection();
			kull.createTimeSeriesTable(stonks);
			for(Date date : timeSeries.keySet()) {
				kull.insertIntoSeriesTable(stonks, date, timeSeries.get(date));
			}
			kull.create200AvgTable(stonks+"200avg");
			kull.calculateAll200Avg(stonks, stonks+"200avg");
			
			MyJFrame swung = new MyJFrame("Aktienkurs");
			swung.changeChart(stonks, kull.getAll200Avg(stonks+"200avg"), timeSeries, kull.getBackground(stonks));
			kull.closeConnection();
		}
	}
}
