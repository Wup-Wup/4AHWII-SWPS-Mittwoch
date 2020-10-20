import java.io.IOException;
import java.net.MalformedURLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.json.JSONException;

public class LookAtDates {
	
		static int monh2;
		static int tueh2;
		static int wedh2;
		static int thuh2;
		static int frih2;
		
		static int monh;
		static int tueh;
		static int wedh;
		static int thuh;
		static int frih;
		
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
		
		monh = GetDays.week(today, tYears, DayOfWeek.MONDAY);
		tueh = GetDays.week(today, tYears, DayOfWeek.TUESDAY);
		wedh = GetDays.week(today, tYears, DayOfWeek.WEDNESDAY);
		thuh = GetDays.week(today, tYears, DayOfWeek.TUESDAY);
		frih = GetDays.week(today, tYears, DayOfWeek.FRIDAY);
		
		FreeDays.fillArrayList(today,tYears);
		
		for(int i=0;i<FreeDays.freeDays.size();i++) {
			switch (FreeDays.freeDays.get(i).getDayOfWeek()) {
			case MONDAY:
				mon++;
				break;
			case TUESDAY:
				tue++;
				break;
			case WEDNESDAY:
				wed++;
				break;
			case THURSDAY:
				thu++;
				break;
			case FRIDAY:
				fri++;
				break;
			default:
				break;

			}
		}

		for(int i=0;i<FreeDays.freeDaysHolidays.size();i++) {
			switch (FreeDays.freeDaysHolidays.get(i).getDayOfWeek()) {
			case MONDAY:
				monh--;
				monh2++;
				break;
			case TUESDAY:
				tueh--;
				tueh2++;
				break;
			case WEDNESDAY:
				wedh--;
				wedh2++;
				break;
			case THURSDAY:
				thuh--;
				thuh2++;
				break;
			case FRIDAY:
				frih--;
				frih2++;
				break;
			default:
				break;

			}
		}
	}
	
	public static void ausgabe() {
		if(LookAtDates.positive) {
			System.out.println("Mit Ferien:");
			System.out.println("Monday: "+monh);
			System.out.println("Tuesday: "+tueh);
			System.out.println("Wednesday: "+wedh);
			System.out.println("Thursday: "+thuh);
			System.out.println("Friday: "+frih+"\n");
			
			System.out.println("Ohen Ferien:");
			System.out.println("Monday: "+mon);
			System.out.println("Tuesday: "+tue);
			System.out.println("Wednesday: "+wed);
			System.out.println("Thursday: "+thu);
			System.out.println("Friday: "+fri);
		}
	}
}