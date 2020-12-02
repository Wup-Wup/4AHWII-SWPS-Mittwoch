import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class TestMain {

	public static void main(String[] args) throws MalformedURLException, JSONException, IOException {
		
		System.out.println();
		test();
	}
	
	static ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
	static ArrayList <Double> wert = new ArrayList <Double>();
	static String s;
	
	
	public static void test() throws MalformedURLException, JSONException, IOException {
		 
		System.out.println("Bitte geben sie eine der folgenden Aktienkurse ein: Tsla(tesla), Ap(Apple)");
		
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

}


	

