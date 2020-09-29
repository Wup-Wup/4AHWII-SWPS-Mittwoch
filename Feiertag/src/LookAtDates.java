import java.time.LocalDate;

public class LookAtDates {
	
		static int mon;
		static int tue;
		static int wed;
		static int thu;
		static int fri;
		static boolean positive =true;
		
	public static void finalDays(LocalDate today, int tYears) {

		if(tYears<0) {
			System.out.println("Please use a positive number");
			positive=false;
			return;
		}
		mon = getDays.monday(today, tYears);
		tue = getDays.tuesday(today, tYears);
		wed = getDays.wednesday(today, tYears);
		thu = getDays.thursday(today, tYears);
		fri = getDays.friday(today, tYears);
		
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
		//Subtract the holidays which always fall on the same day
		mon=mon-(2*tYears);
		thu=thu-(2*tYears);
	}
}
