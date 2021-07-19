
// Insertion sort

void insertionSort(int[] arr) {
    int len = arr.length;
    for (int i=1; i<len; i++) {
        int j = i-1;
        int presentElem = arr[i];
        // try to find the sorted position of ith element in the left group
        while(j >= 0 && arr[j] > presentElem) {
            arr[j+1] = arr[j];
            j=j-1;
        }
        arr[j+1] = temp;
    }
}


// ===========================================================================================================================


// Merge sort

mergeSort(arr, 0, arr.length-1);

void mergeSort(int[] arr, int start, int end) {
    if(start < end) {
        int mid = start + (end-start)/2; // to avoid overflow of int
        mergeSort(arr, start, mid);
        mergeSort(arr, mid+1, end);
        merge(arr, start, mid, end);
    }
}

void merge(int[] arr, int start, int mid, int end) {
    int[] temp = new int[end - start + 1];
    int i = start, j = mid + 1, k=0;
    
    while(i <= mid && j <= end) {
        if(arr[i] < arr[j]) {
            temp[k++] = arr[i];
            i += 1;
        }else {
            temp[k++] = arr[j];
            j += 1;
        }
    }

    // copying remaining elements from either group
    while (i <= mid) {
        temp[k++] = arr[i++];
    }
    while (j <= end) {
        temp[k++] = arr[j++];
    }

    for(i = start; i <= end; i++) {
        arr[i] = temp[i-start];
    }
}


// ===========================================================================================================================




// Quick sort
quickSort(arr, 0, arr.length-1);

void quickSort(int[] arr, int start, int end) {
    if(start < end) {
        int pivotIndex = findMovePivot(arr, start, end);
        quickSort(arr, start, pivotIndex-1);
        quickSort(arr, pivotIndex+1, end);
    }
}

void findMovePivot(int[] arr, int start, int end) {
    // Choosing a pivot - last element as the pivot
    int pivot = arr[end];
    int lastSmallestElementIndex = -1;
    for(int i=start; i<end; i++) {
        if(arr[i] < pivot) {
            lastSmallestElementIndex++;
            swap(arr, lastSmallestElementIndex, i);
        }
    }
    swap(arr, lastSmallestElementIndex+1, end);
    return lastSmallestElementIndex + 1;
}

void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}



// ===========================================================================================================================
