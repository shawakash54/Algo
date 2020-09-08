// Java program to find k'th smallest element in expected 
// linear time 
class KthSmallst 
{ 
    private int kthSmallestElement(int arr[], int left, int right, int k) {
        // if k is smaller than the numbers of element in the array
        if(k > 0 && k <= right-left+1) {
            int pivot = randomPartition(arr, left, right);
            
            // k-1 to translate k to zero index
            if (pivot-left == k-1) {
                // found the kth smallest element
                return arr[pivot];
            } 
            
            if(pivot-left > k-1) {
                return kthSmallestElement(arr, left, pivot-1, k);
            }else {
                return kthSmallestElement(arr, pivot+1, right, k-1-pivot);
            }
        }
        return -1;
    }
    
    private int partition(int arr[], int left, int right) {
        int i = left, j;
        int lastElement = arr[right];
        
        for(j=i; j<right; j++) {
            if(arr[j] <= lastElement) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }
    
    private void swap(int arr[], int src, int dest) {
        int temp = arr[dest];
        arr[dest] = arr[src];
        arr[src] = temp;
    }
    
    private int randomPartition(int arr[], int left, int right) {
        int numberOfElements = right-left+1;
        int pivot = (int)(Math.random() * (numberOfElements-1));
        swap(arr, left + pivot, right);
        return partition(arr, left, right);
    }
    
	public static void main(String args[]) { 
		KthSmallst ob = new KthSmallst(); 
		int arr[] = {12, 3, 5, 7, 4, 19, 26}; 
		int n = arr.length,k = 3; 
		System.out.println("K'th smallest element is "+ 
						ob.kthSmallestElement(arr, 0, n-1, k)); 
	} 
} 

