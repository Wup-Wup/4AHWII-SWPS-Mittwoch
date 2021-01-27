package alphaVantage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.TreeMap;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class AlphaVantage {
	
	public static TreeMap<Date,BigDecimal> getAdjustedTimeSeries(String nameSharePrice, String apiKey) throws JSONException, MalformedURLException, IOException{
		
		TreeMap<Date,BigDecimal> timeSeries = new TreeMap<Date,BigDecimal>();
	
		String url="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+nameSharePrice+"&outputsize=full&apikey="+apiKey;

		try {
			JSONObject json = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));
			json = json.getJSONObject("Time Series (Daily)");
		
			String[] dates = JSONObject.getNames(json); 
			
			for(String date : dates) {
				BigDecimal closeValue = new BigDecimal((String) ((JSONObject) json.get(date)).get("4. close"));
				timeSeries.put(Date.valueOf(date), closeValue);
			}
		}catch(org.json.JSONException e) {
			System.out.println(nameSharePrice+" is not a valid share price.");
			return null;
		}		
		return timeSeries;
	}
	
}
