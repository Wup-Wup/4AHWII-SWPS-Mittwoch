package standart;

public class Array {
	
	public static int[] createArray(int length) {
		
		int[] arr = new int[length];
		
		return arr;
	}
	
	public static int[] fillArray(int[] arr, int numberSize) {
		
		for(int i=0;i<arr.length;i++) {
			arr[i]=(int) (Math.random()*(numberSize+1));
		}
		return arr;
	}
}
