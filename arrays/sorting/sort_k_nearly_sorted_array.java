// A java program to sort a nearly sorted array 
// O(nlogk)
import java.util.Iterator; 
import java.util.PriorityQueue; 

class GFG 
{ 
	private static void kSort(int[] arr, int n, int k) 
	{ 
	    
	    if(k < 0 || k >arr.length)
	        return;

		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		
		for(int i=0; i<k + 1; i++) {
		    pq.add(arr[i]);
		}
		
		int start = 0;
		
		for(int i=k + 1; i<arr.length; i++) {
		    arr[start++] = pq.poll();
		    pq.add(arr[i]);
		}
		
		Iterator<Integer> iq = pq.iterator();
		while(iq.hasNext()){
		    arr[start++] = pq.poll();
		}

	} 

	private static void printArray(int[] arr, int n) 
	{ 
		for(int i = 0; i < n; i++) 
			System.out.print(arr[i] + " "); 
	} 

	public static void main(String[] args) 
	{ 
		int k = 3; 
		int arr[] = { 2, 6, 3, 12, 56, 8 }; 
		int n = arr.length; 
		kSort(arr, n, k); 
		System.out.println("Following is sorted array"); 
		printArray(arr, n); 
	} 
} 

