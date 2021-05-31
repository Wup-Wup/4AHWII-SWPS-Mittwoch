package standart;

import java.util.ArrayList;

public class Times {

	public static long timer(ArrayList<Long> time) {
		long summe=0;
		for (int i = 0; i < time.size(); i++) {
			summe=summe+time.get(i);
		}
		summe=summe/time.size();
		return summe;
	}
}
