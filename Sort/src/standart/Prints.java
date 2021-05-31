package standart;

import java.util.HashMap;

public class Prints {

	public static void printout(int tries, HashMap<String, Long> zaehler, HashMap<String, Long> vZaehler,
			long timeSelection, long timeSelectionStable, long timeBubble, long timeInsertion, long timeQuick) {

		System.out.println("Anzahl Durchläufe: " + tries + "\n");

		System.out.println("Durschnitt Vergleichsverfahren:");
		for (String sort : vZaehler.keySet()) {
			System.out.println(sort + ": " + vZaehler.get(sort));
		}

		System.out.println("\nDurschnitt Vertauschoperationen:");
		for (String sort : zaehler.keySet()) {
			System.out.println(sort + ": " + zaehler.get(sort));
		}
		
		System.out.println("\nDurchschnittliche Zeit in ms:");
		System.out.println("SelectionSort: "+timeSelection);
		System.out.println("SelectionSortStable: "+timeSelectionStable);
		System.out.println("BubbleSort: "+timeBubble);
		System.out.println("InsertionSort: "+timeInsertion);
		System.out.println("QuickSort: "+timeQuick);

	}
}
