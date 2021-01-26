// Java program to sort an array of 0, 1 and 2 
import java.io.*; 

class countzot { 

	// Sort the input array, the array is assumed to 
	// have values in {0, 1, 2} 
	static void sort012(int a[], int arr_size) { 
		int low=0, mid=0, high=a.length-1;
		
		while(mid<=high) {
		    int elem = a[mid];
		    if(elem == 0) {
		        swap(a, low, mid);
		        low++;
		        mid++;
		    }else
		    if(elem == 1) {
		        mid++;
		    }else
		    if(elem == 2) {
		        swap(a, mid, high);
		        high--;
		    }
		}
	} 
	
	static void swap(int a[], int x, int y) {
	    int temp = a[x];
	    a[x] = a[y];
	    a[y] = temp;
	}

	/* Utility function to print array arr[] */
	static void printArray(int arr[], int arr_size) { 
		int i; 
		for (i = 0; i < arr_size; i++) 
			System.out.print(arr[i] + " "); 
		System.out.println(""); 
	} 

	/*Driver function to check for above functions*/
	public static void main(String[] args) { 
		int arr[] = { 0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1 }; 
		int arr_size = arr.length; 
		sort012(arr, arr_size); 
		System.out.println("Array after seggregation "); 
		printArray(arr, arr_size); 
	} 
} 
