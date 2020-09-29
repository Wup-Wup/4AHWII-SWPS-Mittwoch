import java.time.LocalDate;

public class TestMain {
	
	public static void main(String[] args) {
		LookAtDates.finalDays(LocalDate.now(), 10);
		ausgabe();
	}
	
	
	
	
	
	public static void ausgabe() {
		if(LookAtDates.positive) {
			System.out.println("Monday: "+LookAtDates.mon);
			System.out.println("Tuesday: "+LookAtDates.tue);
			System.out.println("Wednesday: "+LookAtDates.wed);
			System.out.println("Thursday: "+LookAtDates.thu);
			System.out.println("Friday: "+LookAtDates.fri);
		}
	}
}
