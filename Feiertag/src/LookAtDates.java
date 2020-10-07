import java.io.IOException;
import java.net.MalformedURLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.json.JSONException;

public class LookAtDates {
	
		static int mon;
		static int tue;
		static int wed;
		static int thu;
		static int fri;
		static boolean positive =true;
		
	public static void finalDays(LocalDate today, int tYears) throws MalformedURLException, JSONException, IOException {

		if(tYears<0) {
			System.out.println("Please use a positive number");
			positive=false;
			return;
		}
		mon = GetDays.week(today, tYears, DayOfWeek.MONDAY);
		tue = GetDays.week(today, tYears, DayOfWeek.TUESDAY);
		wed = GetDays.week(today, tYears, DayOfWeek.WEDNESDAY);
		thu = GetDays.week(today, tYears, DayOfWeek.TUESDAY);
		fri = GetDays.week(today, tYears, DayOfWeek.FRIDAY);
		
		FreeDays.fillArrayList(today,tYears);

		for(int i=0;i<FreeDays.freeDays.size();i++) {
			switch (FreeDays.freeDays.get(i).getDayOfWeek()) {
			case MONDAY:
				mon--;
				break;
			case TUESDAY:
				tue--;
				break;
			case WEDNESDAY:
				wed--;
				break;
			case THURSDAY:
				thu--;
				break;
			case FRIDAY:
				fri--;
				break;
			default:
				break;

			}
		}
	}
	
	public static void ausgabe() {
		if(LookAtDates.positive) {
			System.out.println("Monday: "+mon);
			System.out.println("Tuesday: "+tue);
			System.out.println("Wednesday: "+wed);
			System.out.println("Thursday: "+thu);
			System.out.println("Friday: "+fri);
		}
	}
}