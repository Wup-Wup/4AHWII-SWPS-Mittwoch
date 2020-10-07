import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class FreeDays {
	
	static ArrayList<LocalDate> freeDays = new ArrayList<LocalDate>();
	
	public static void fillArrayList(LocalDate today,int tYears) throws MalformedURLException, JSONException, IOException {
		for (int i = 0; i < tYears; i++) {
			LocalDate freeDateYear=today.plusYears(i);
			freeDays.add(LocalDate.of(freeDateYear.getYear(),1,1));
			freeDays.add(LocalDate.of(freeDateYear.getYear(),1,6));
			freeDays.add(LocalDate.of(freeDateYear.getYear(),5,1));
			freeDays.add(LocalDate.of(freeDateYear.getYear(),8,15));
			freeDays.add(LocalDate.of(freeDateYear.getYear(),10,26));
			freeDays.add(LocalDate.of(freeDateYear.getYear(),11,1));
			freeDays.add(LocalDate.of(freeDateYear.getYear(),12,8));
			freeDays.add(LocalDate.of(freeDateYear.getYear(),12,25));
			freeDays.add(LocalDate.of(freeDateYear.getYear(),12,26));
			addDays("Ostermontag",freeDateYear.getYear());
			addDays("Pfingstmontag",freeDateYear.getYear());
			addDays("Christi Himmelfahrt",freeDateYear.getYear());
			addDays("Fronleichnam",freeDateYear.getYear());
			
		}
			
	}
	private static void addDays(String name, int year) throws MalformedURLException, JSONException, IOException
    {
        JSONObject json = new JSONObject(IOUtils.toString(new URL("https://feiertage-api.de/api/?jahr=%22+year"), Charset.forName("UTF-8")));

        String date = json.getJSONObject("BY").getJSONObject(name).get("datum").toString();
        freeDays.add(LocalDate.parse(date));


    }

}
