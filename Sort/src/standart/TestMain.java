package standart;

import java.util.ArrayList;

import sorts.BubbleSort;
import sorts.InsertionSort;
import sorts.QuickSort;
import sorts.SelectionSort;
import sorts.SelectionSortStable;

public class TestMain {

	static int tries = 5;
	static int length = 20000;
	static int size = 1000;

	static long timeStart;
	static long timeEnd = System.currentTimeMillis();

	static ArrayList<Long> timeSelectionSort = new ArrayList<Long>();
	static ArrayList<Long> timeSelectionSortStable = new ArrayList<Long>();
	static ArrayList<Long> timeBubbleSort = new ArrayList<Long>();
	static ArrayList<Long> timeInsertionSort = new ArrayList<Long>();
	static ArrayList<Long> timeQuickSort = new ArrayList<Long>();

	public static void main(String[] args) {

		int[] arr = Array.createArray(length);
		int[] arrSelection = Array.createArray(length);
		int[] arrSelectionStable = Array.createArray(length);
		int[] arrBubble = Array.createArray(length);
		int[] arrInsertion = Array.createArray(length);
		int[] arrQuick = Array.createArray(length);

		for (int i = 0; i < tries; i++) {

			arr = Array.fillArray(arr, size);
			arrSelection = arr.clone();
			arrSelectionStable = arr.clone();
			arrBubble = arr.clone();
			arrInsertion = arr.clone();
			arrQuick = arr.clone();

			timeStart = System.currentTimeMillis();
			SelectionSort.sort(arrSelection);
			timeEnd = System.currentTimeMillis();
			timeSelectionSort.add(timeEnd - timeStart);

			timeStart = System.currentTimeMillis();
			BubbleSort.sort(arrBubble);
			timeEnd = System.currentTimeMillis();
			timeBubbleSort.add(timeEnd - timeStart);

			timeStart = System.currentTimeMillis();
			SelectionSortStable.sort(arrSelectionStable);
			timeEnd = System.currentTimeMillis();
			timeSelectionSortStable.add(timeEnd - timeStart);

			timeStart = System.currentTimeMillis();
			InsertionSort.sort(arrInsertion);
			timeEnd = System.currentTimeMillis();
			timeInsertionSort.add(timeEnd - timeStart);

			timeStart = System.currentTimeMillis();
			QuickSort.sort(arrQuick, 0, arrQuick.length - 1);
			timeEnd = System.currentTimeMillis();
			timeQuickSort.add(timeEnd - timeStart);
		}
		Prints.printout(tries, CalculateAverage.calculateAllZaehler(tries),
				CalculateAverage.calculateAllVZaehler(tries), Times.timer(timeSelectionSort),
				Times.timer(timeSelectionSortStable), Times.timer(timeBubbleSort), Times.timer(timeInsertionSort),
				Times.timer(timeQuickSort));
	}
}
