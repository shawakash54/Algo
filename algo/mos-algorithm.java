/*package whatever //do not write package name here */

import java.io.*;
import java.util.Arrays;

class Query implements Comparable<Query> {
    int start, end, index;
    public Query(int start, int end, int index){
        this.start = start;
        this.end = end;
        this.index = index;
    }
    @Override
    public int compareTo(final Query other) {
        int selfLeftBlock = this.start / MOsAlgorithm.BLOCK_SIZE;
        int otherLeftBlock = other.start / MOsAlgorithm.BLOCK_SIZE;
        return selfLeftBlock != otherLeftBlock ? (selfLeftBlock - otherLeftBlock) : (this.end - other.end);
    }
}

class MOsAlgorithm {
    public static int BLOCK_SIZE = 0;
	
	public static void main (String[] args) {
		int arr[] = {1, 1, 2, 1, 3};
		final Query queries[] = {
		    new Query(0, 4, 0),
		    new Query(1, 3, 1),
		    new Query(2, 4, 2),
		};
		BLOCK_SIZE = (int)Math.sqrt(arr.length);
		Arrays.parallelSort(queries);
        int[] results = new MOsAlgorithm().getQueriesResult(queries, arr);
        for(int res : results)
            System.out.println(res);
	}
	
	private int[] getQueriesResult(Query[] queries, int[] arr) {
	    int[] results = new int[queries.length];
	    int[] freq = new int[1000001];
	    int start = queries[0].start;
	    int end = start; int count = 0;
	    
	    for(Query query : queries) {
	        while(start < query.start) {
	            freq[arr[start]]--;
	            if(freq[arr[start]] == 0)
	                count--;
	            start++;
	        }
	        while(start > query.start) {
	            start--;
	            freq[arr[start]]++;
	            if(freq[arr[start]] == 1)
	                count++;
	        }
	        while(end < query.end) {
	            end++;
	            freq[arr[end]]++;
	            if(freq[arr[end]] == 1)
	                count++;
	        }
	        while(end > query.end) {
	            freq[arr[end]]--;
	            if(freq[arr[end]] == 0)
	                count--;
	            end--;
	        }
	        
	        results[query.index] = count;
	    }
	    return results;
	}
}






