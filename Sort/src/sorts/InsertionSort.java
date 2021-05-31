package sorts;

public class InsertionSort {
	
	static long zaehlerISort;
	static long zaehlerVISort;
	
	public static long getZaehlerISort() {
		return zaehlerISort;
	}
	
	public static long getZaehlerVISort() {
		return zaehlerVISort;
	}

	public static void sort(int arr[]) {
		
		for (int i = 1; i < arr.length; ++i) {
			zaehlerVISort++;
			int key = arr[i];
			int j = i - 1;
			while (j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j = j - 1;
				zaehlerISort++;
			}
			arr[j + 1] = key;
		}
	}
}
