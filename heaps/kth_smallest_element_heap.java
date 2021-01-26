import java.util.*;

class Main
{
	// Function to find the K'th smallest element in the
	// array using max-heap
	public static int FindKthSmallest(List<Integer> ints, int k) {
	    
	    // if k > ints.size()
	    if (k > ints.size() - 1 || k < 0) {
	        return -1;
	    }
	    
	    // 3 ways of overriding comparator class
		// PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b-a);
		// PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.reverseOrder());
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
		    @Override
		    public int compare(Integer a, Integer b) {
		        return b-a;
		    }
		});
		// Add first k elements to the max heap
	    for(int i=0; i<k; i++) {
	        pq.add(ints.get(i));
	    }

        for(int i=k; i<ints.size(); i++) {
            int incomingElement = ints.get(i);
            int presentTop = pq.peek();
            if(incomingElement < presentTop) {
                pq.poll();
                pq.add(incomingElement);
            }
        }
	    
	    return pq.peek();
	}


	public static void main(String[] args) {
		List<Integer> ints = Arrays.asList(7, 4, 6, 3, 9, 1);
		int k = 2;

		System.out.println("K'th smallest element in the array is " +
				FindKthSmallest(ints, k));
	}
}
