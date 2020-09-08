// Find first and last occurrence of 
// an elements in given sorted array 
import java.io.*; 

class SortedArray { 

	public static int search(int arr[], int low, int high, int target, String mode) { 
		int length = arr.length;
		
		while(low <= high) {
		    int mid = low + (high-low)/2;
		    int midElement = arr[mid];
		    if(midElement == target){
		        if(mode.equals("first")) {
		            if((mid-1)>=0 && arr[mid-1] == target)
		                high = mid-1;
		            else
		                return mid;
		        } else {
		            if((mid+1)<length && arr[mid+1] == target)
		                low = mid+1;
		            else
		                return mid;
		        }
		    }else if(midElement > target) {
		        high = mid-1;
		    } else {
		        low = mid+1;
		    }
		}
		return -1;
	} 

	public static void main(String[] args) { 

		int arr[] = { 1, 2, 2, 2, 2, 3, 4, 7, 8, 8 }; 
		int target = 8; 
		int n = arr.length;
		System.out.println("First Occurrence = " + search(arr, 0, n - 1, target, "first")); 
		System.out.println("Last Occurrence = " + search(arr, 0, n - 1, target, "last")); 
	} 
} 
