// Java program to find med in 
// stream of running integers 
import java.util.Collections; 
import java.util.PriorityQueue; 

public class MedianMaintain { 
	
	// method to calculate med of stream 
	public static void printMedian(int[] a) { 
	    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
	    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
	    
		for(int i=0; i<a.length; i++) {
		    int current = a[i];
		    
		    if(minHeap.size() == 0 || minHeap.peek() > current) {
		        maxHeap.add(current);
		    }else {
		        minHeap.add(current);
		    }
		    
		    if(minHeap.size() > maxHeap.size() + 1) {
		        maxHeap.add(minHeap.remove());
		    }else
		    if(maxHeap.size() > minHeap.size() + 1) {
		        minHeap.add(maxHeap.remove());
		    }
		    if(minHeap.size() == maxHeap.size()) {
		        System.out.println((double)(minHeap.peek() + maxHeap.peek())/2.0);
		    }else {
		        if(minHeap.size() > maxHeap.size()) {
		            System.out.println(minHeap.peek());
		        }else {
		            System.out.println(maxHeap.peek());
		        }
		    }
		}
		
	} 
	
	// Driver code 
	public static void main(String []args) { 
		
		// stream of integers 
		int[] arr = new int[]{5, 15, 10, 20, 3}; 
		printMedian(arr); 
	} 
} 

