import java.time.DayOfWeek;
import java.time.LocalDate;

public class getDays {
	
	public static int monday(LocalDate today, int tYears) {

		LocalDate workYear = today.plusYears(tYears);
		while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
			today = today.plusDays(1);
		}
		int day = 0;
		while (today.isBefore(workYear)) {
			day++;
			today = today.plusDays(7);
		}
		return day;
	}

	public static int tuesday(LocalDate today, int tYears) {

		LocalDate workYear = today.plusYears(tYears);
		while (today.getDayOfWeek() != DayOfWeek.TUESDAY) {
			today = today.plusDays(1);
		}
		int day = 0;
		while (today.isBefore(workYear)) {
			day++;
			today = today.plusDays(7);
		}
		return day;
	}

	public static int wednesday(LocalDate today, int tYears) {

		LocalDate workYear = today.plusYears(tYears);
		while (today.getDayOfWeek() != DayOfWeek.WEDNESDAY) {
			today = today.plusDays(1);
		}
		int day = 0;
		while (today.isBefore(workYear)) {
			day++;
			today = today.plusDays(7);
		}
		return day;
	}

	public static int thursday(LocalDate today, int tYears) {

		LocalDate workYear = today.plusYears(tYears);
		while (today.getDayOfWeek() != DayOfWeek.THURSDAY) {
			today = today.plusDays(1);
		}
		int day = 0;
		while (today.isBefore(workYear)) {
			day++;
			today = today.plusDays(7);
		}
		return day;
	}

	public static int friday(LocalDate today, int tYears) {

		LocalDate workYear = today.plusYears(tYears);
		while (today.getDayOfWeek() != DayOfWeek.FRIDAY) {
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
