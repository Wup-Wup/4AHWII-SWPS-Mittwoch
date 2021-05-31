package sorts;

public class SelectionSort {
	
	static long zaehlerVSSort;
	static long zaehlerSSort;

	
	public static long getZaehlerVSSort() {
		return zaehlerVSSort;
	}

	public static long getZaehlerSSort() {
		return zaehlerSSort;
	}

	public static void sort(int arr[]) {

			for (int i = 0; i < arr.length - 1; i++) {
				int min_idx = i;
				for (int j = i + 1; j < arr.length; j++) {
					zaehlerVSSort++;
					if (arr[j] < arr[min_idx]) {
						min_idx = j;
						zaehlerSSort++;
					}
				}
				
				int temp = arr[min_idx];
				arr[min_idx] = arr[i];
				arr[i] = temp;
			}
		}
	
	}
