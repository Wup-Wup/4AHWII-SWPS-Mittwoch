package sorts;

public class SelectionSortStable {
	
	static long zaehlerVSSSort;
	static long zaehlerSSSort;


	public static long getZaehlerVSSSort() {
		return zaehlerVSSSort;
	}


	public static long getZaehlerSSSort() {
		return zaehlerSSSort;
	}


	public static void sort(int[] arr) {

		for (int i = 0; i < arr.length - 1; i++) {
			int min = i;
			for (int j = i + 1; j < arr.length; j++) {
				zaehlerVSSSort++;
				if (arr[min] > arr[j]) {
					zaehlerSSSort++;
					min = j;
				}
			}
			int key = arr[min];
			while (min > i) {
				arr[min] = arr[min - 1];
				min--;
				zaehlerSSSort++;
			}
			arr[i] = key;
		}
	}
}
