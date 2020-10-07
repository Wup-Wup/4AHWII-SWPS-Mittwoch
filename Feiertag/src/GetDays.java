import java.time.DayOfWeek;
import java.time.LocalDate;

public class GetDays {
	
	public static int week(LocalDate today, int tYears,DayOfWeek weekDay) {

		LocalDate workYear = today.plusYears(tYears);
		while (today.getDayOfWeek() != weekDay) {
			today = today.plusDays(1);
		}
		int day = 0;
		while (today.isBefore(workYear)) {
			day++;
			today = today.plusDays(7);
		}
		return day;
	}
}
