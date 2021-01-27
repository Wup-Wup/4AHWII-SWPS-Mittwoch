import java.util.Scanner;

public class AbbreviationScanner {

	
	public static String abbreviationScanner() {
		
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter an Abbreviation (e.g. TSLA, IBM, AAPL,...): ");
		String abbreviation = s.next();
		
		s.close();
		
		return abbreviation;
	}
}
