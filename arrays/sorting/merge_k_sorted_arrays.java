import java.util.*;
import java.lang.*;
import java.io.*;


class MinHeapNode {
    int value;
    int arrayIndex;
    int nextElementIndex;
    
    public MinHeapNode(int value, int arrayIdx, int nextidx) {
        this.value = value;
        this.arrayIndex = arrayIdx;
        this.nextElementIndex = nextidx;
    }
}

class MinHeap {
    MinHeapNode[] nodes;
    int heapSize;
    
    public MinHeap(MinHeapNode[] arr, int size) {
        this.nodes = arr;
        this.heapSize = size;
        
        // Heapify the nodes in the heap
        int nonLeafNodes = (this.heapSize-1)/2;
        while(nonLeafNodes >= 0) {
            minHeapify(nonLeafNodes);
            nonLeafNodes--;
        }
    }
    
    private void minHeapify(int idx) {
        int leftNodeIndex = this.leftNode(idx);
        int rightNodeIndex = this.rightNode(idx);
        int smallestElementIndex = idx;
        
        if(leftNodeIndex < this.heapSize && this.nodes[leftNodeIndex].value < this.nodes[smallestElementIndex].value)
            smallestElementIndex = leftNodeIndex;
        
        if(rightNodeIndex < this.heapSize && this.nodes[rightNodeIndex].value < this.nodes[smallestElementIndex].value)
            smallestElementIndex = rightNodeIndex;
            
        if(idx != smallestElementIndex) {
            swap(smallestElementIndex, idx);
            minHeapify(smallestElementIndex);
        }
    }
    
    private int leftNode(int idx) {
        return (idx*2)+1;
    }
    
    private int rightNode(int idx) {
        return (idx*2)+2;
    }
    
    private void swap(int a, int b) {
        MinHeapNode temp = nodes[a];
        nodes[a] = nodes[b];
        nodes[b] = temp;
    }
    
    public MinHeapNode getMin() {
        if (heapSize < 0) {
            // overflow 
            return null;
        }
        return nodes[0];
    }
    
    public void replaceMin(MinHeapNode node) {
        nodes[0] = node;
        minHeapify(0);
    }
    
    
}


class MergeKSortedArrays {
    public static void printArray(int[] arr) {
        for(int i: arr) {
            System.out.print(i+ " ,");
        }    
        System.out.println();
    }
    
    public static void mergeKSortedArrays(int[][] arr, int k) {
        MinHeapNode[] minHeapNodes = new MinHeapNode[k];
        int resultArraySize = 0;
        
        for(int i=0; i<arr.length; i++) {
            MinHeapNode temp = new MinHeapNode(arr[i][0], i, 1);
            minHeapNodes[i] = temp;
            resultArraySize += arr[0].length;
        }
        
        int[] resultArr = new int[resultArraySize];
        MinHeap mh = new MinHeap(minHeapNodes, k);
        
        for(int i=0; i<resultArraySize; i++) {
            MinHeapNode min = mh.getMin();
            resultArr[i] = min.value;
            
            int minArrayIndex = min.arrayIndex;
            int nextElementIndex = min.nextElementIndex;

            if(nextElementIndex < arr[minArrayIndex].length) {
                MinHeapNode next = new MinHeapNode(arr[minArrayIndex][nextElementIndex], minArrayIndex, nextElementIndex+1);
                mh.replaceMin(next);
            }else {
                MinHeapNode next = new MinHeapNode(Integer.MAX_VALUE, minArrayIndex, nextElementIndex+1);
                mh.replaceMin(next);
            }
        }
        
        printArray(resultArr);
    }
    
	public static void main (String[] args) throws java.lang.Exception {
		int[][] arr= {{2, 6, 12, 34}, 
                {1, 9, 20, 1000}, 
                {23, 34, 90, 2000}}; 
        System.out.println("Merged array is :"); 
        mergeKSortedArrays(arr, arr.length);
	}
}
