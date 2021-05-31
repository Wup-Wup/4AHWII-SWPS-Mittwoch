package standart;

import java.util.Scanner;

public class AbbreviationScanner {

	
	public static String abbreviationScanner(String message) {
		
		Scanner s = new Scanner(System.in);
		System.out.println(message);
		String abbreviation = s.next();
		
		s.close();
		
		return abbreviation;
	}
}

