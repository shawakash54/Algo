class Main {
	// If searchFirst is true, we return the first occurrence of a number
	// in sorted array of integers; else we return its last occurrence
	public static int binarySearch(int[] A, int x, boolean searchFirst) {
		int result = -1;
		
		int start = 0, end = A.length-1, mid;
		
		while(start <= end) {
		    mid = start + (end-start)/2;
		    
		    if(A[mid] == x) {
		        if(searchFirst) {
		            if(mid-1 >= 0 && A[mid-1] == x) {
		                end = mid-1;
		            }else {
		                result = mid;
		                break;
		            }
		        }else {
		            if(mid+1 < A.length && A[mid+1] == x) {
		                start = mid+1;
		            }else {
		                result = mid;
		                break;
		            }
		        }
		    }else {
		        if(A[mid] > x) {
		            end = mid-1;
		        }else {
		            start = mid+1;
		        }
		    }
		}
		
		return result;
	}

	public static void main(String[] args) {
		int[] A = {2, 5, 5, 5, 6, 6, 8, 9, 9, 9};
		int key = 5;

		// pass true for first occurrence
		int first = binarySearch(A, key, true);

		// pass false for last occurrence
		int last = binarySearch(A, key, false);

		int c = last - first + 1;

		if (first != -1) {
			System.out.println("Element " + key + " occurs " + c + " times");
		} else {
			System.out.println("Element not found in the array");
		}
	}
}
