// https://www.geeksforgeeks.org/find-maximum-sum-triplets-array-j-k-ai-aj-ak/

/*
    Given an array A containing N integers.

    You need to find the maximum sum of triplet ( Ai + Aj + Ak ) such that 0 <= i < j < k < N and Ai < Aj < Ak.

    If no such triplet exist return 0.

    Input 1:
    A = [2, 5, 3, 1, 4, 9]

    Input 2:
    A = [1, 2, 3]

    Output 1:
    16

    Output 2:
    6
*/

// Solution 1 - O(n3) time, O(1) space

public int solve(ArrayList<Integer> A) {
    int size = A.size();
    if(size < 3)
        return 0;
    
    int ans = 0;
    for(int i=0; i<size; i++) {
        for(int j=i+1; j<size; j++) {
            for(int k=j+1; k<size; k++) {
                if(A.get(i) < A.get(j) < A.get(k))
                    ans = Math.max(ans, A.get(i) + A.get(j) + A.get(k));
            }
        }
    }

    return ans;
}


// Solution 2 - O(n2), O(n) space
// Idea is to consider every element as the middle element and try to find the 
// maximum value on the right side and the maxium closest lower value on the left side.

public int solve(ArrayList<Integer> A) {
    int size = A.size();
    if(size < 3)
        return 0;

    int ans = 0;
    for(int i=1; i<size-1; i++) {
        int presentElem = A.get(i);
        int left=-1;
        int right=-1;

        // finding left
        for(int j=0; j<i; j++) {
            if(A.get(j) < presentElem)
                left = Math.max(left, A.get(j));
        }

        // finding right
        for(int j=i+1; j<size; j++) {
            if(A.get(j) > presentElem)
                right = Math.max(right, A.get(j));
        }

        if(left != -1 && right != -1) 
            ans = Math.max(ans, A.get(left) + presentElem + A.get(right));
    }
    return ans;
}


// Solution 3 - O(nlogn) time, O(n) space
// Idea is to pre compute max suffix array and use it inside the loop and sort the list while traversing and apply binary search
// to get the lowest maximum on the left side. For this, we can use treeset in Java.

public int solve(ArrayList<Integer> A) {
    int size = A.size();
    if(size < 3)
        return 0;

    int ans = 0;
    int[] maxSuffix = new int[size + 1];
    
    // calculating max suffix array
    maxSuffix[size] = 0;
    for(int i=size-1; i>=0; i--) {
        maxSuffix[i] = Math.max(maxSuffix[i+1], A.get(i));
    }

    TreeSet<Integer> sortedSet = new TreeSet<Integer>();
    sortedSet.add(A.get(0)); // inserting this value for first comparison otherwise NPE is to be expected
    for(int i=1; i<size-1; i++) {
        int presentElem = A.get(i);
        int left = -1;
        int right = -1;
        right = maxSuffix[i+1];
        left = sortedSet.lower(presentElem);
        sortedSet.add(presentElem);
        if(right > presentElem && left < presentElem && left != -1 && right != -1) {
            ans = Math.max(ans, left + presentElem + right);
        }
    }

    return ans;
}


// ===========================================================================================================================
