/* Merge Sort */
class MergeSort { 
	// Merges two subarrays of arr[]. 
	// First subarray is arr[l..m] 
	// Second subarray is arr[m+1..r] 
	void merge(int arr[], int l, int m, int r) { 
		int size1 = m-l+1;
		int size2 = r-m;
		
		int[] arr1 = new int[size1];
		int[] arr2 = new int[size2];
		
		int i=0, j=0, k=l;
		
		for(i=0; i<size1; i++) {
		    arr1[i] = arr[i+l];
		}
		
		for(j=0; j<size2; j++) {
		    arr2[j] = arr[j+m+1];
		}
		
		// merge
		i = j = 0;
		while(i<size1 && j<size2) {
		    int elem1 = arr1[i];
		    int elem2 = arr2[j];
		    
		    if(elem1 < elem2) {
		        arr[k++] = elem1;
		        i++;
		    }else {
		        arr[k++] = elem2;
		        j++;
		    }
		}
		
		while(i<size1) {
		    arr[k] = arr1[i];
		    k++;
		    i++;
		}
		
		while(j<size2) {
		    arr[k] = arr2[j];
		    k++;
		    j++;
		}
	} 

	// Main function that sorts arr[l..r] using 
	// merge() 
	void sort(int arr[], int l, int r) { 
		if(l < r) {
		    int m = l + (r-l)/2;
		    sort(arr, l, m);
		    sort(arr, m+1, r);
		    merge(arr, l, m, r);
		}
	} 

	/* A utility function to print array of size n */
	static void printArray(int arr[]) { 
		int n = arr.length; 
		for (int i = 0; i < n; ++i) 
			System.out.print(arr[i] + " "); 
		System.out.println(); 
	} 

	public static void main(String args[]) { 
		int arr[] = { 12, 11, 13, 5, 6, 7 }; 

		System.out.println("Given Array"); 
		printArray(arr); 

		MergeSort ob = new MergeSort(); 
		ob.sort(arr, 0, arr.length - 1); 

		System.out.println("\nSorted array"); 
		printArray(arr); 
	} 
} 
