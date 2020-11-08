
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FreeDays {

	static ArrayList<LocalDate> freeDaysHolidays = new ArrayList<LocalDate>();
	static ArrayList<LocalDate> freeDays = new ArrayList<LocalDate>();
	static ArrayList<LocalDate> holidays = new ArrayList<LocalDate>();
	static JSONArray jsonArray = new JSONArray();
	static JSONObject jsonO;
	static LocalDate start;
	static LocalDate end;

	public static void fillArrayList(LocalDate today, int tYears)
			throws MalformedURLException, JSONException, IOException {
		for (int i = 0; i < tYears; i++) {
			LocalDate freeDateYear = today.plusYears(i);

			holidayDay(freeDateYear.getYear());

			addDays(freeDateYear.getYear(), "BY");

		}
	}

	private static void addDays(int year, String land) throws MalformedURLException, JSONException, IOException {
		JSONObject allFreeDays = new JSONObject(IOUtils.toString(
				new URL("https://feiertage-api.de/api/?jahr=" + year + "&nur_land=" + land + "&nur_daten"),
				Charset.forName("UTF-8")));
		String[] keys = JSONObject.getNames(allFreeDays);
		for (String key : keys) {
			String date = (String) allFreeDays.get(key);
			
			if(!holidays.contains(date)) {
			freeDaysHolidays.add(LocalDate.parse(date));  
			 }
			freeDays.add(LocalDate.parse(date));
		}
	}

	public static void holidayDay(int year) throws MalformedURLException, JSONException, IOException {

		jsonArray = new JSONArray(
				IOUtils.toString(new URL("https://ferien-api.de/api/v1/holidays/BY/" + year), StandardCharsets.UTF_8));

		for (int i = 0; i < jsonArray.length(); i++) {
			jsonO = jsonArray.getJSONObject(i);
			start = LocalDate.parse(jsonO.get("start").toString().substring(0, 10));
			end = LocalDate.parse(jsonO.get("end").toString().substring(0, 10));

			for (LocalDate hDate = start; hDate.isBefore(end); hDate = hDate.plusDays(1)) {
				holidays.add(hDate);
			}
		}
	}

}