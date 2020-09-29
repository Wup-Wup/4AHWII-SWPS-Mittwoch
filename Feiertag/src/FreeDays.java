import java.time.LocalDate;
import java.util.ArrayList;

public class FreeDays {
	
	static ArrayList<LocalDate> freeDays = new ArrayList<LocalDate>();
	
	public static void fillArrayList(LocalDate today,int tYears) {
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
		}
			
	}

}
