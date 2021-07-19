// Maximum absolute difference

/*
    f(i, j) is defined as |A[i] - A[j]| + |i - j|, where |x| denotes absolute value of x
    A=[1, 3, -1]

    f(1, 1) = f(2, 2) = f(3, 3) = 0
    f(1, 2) = f(2, 1) = |1 - 3| + |1 - 2| = 3
    f(1, 3) = f(3, 1) = |1 - (-1)| + |1 - 3| = 4
    f(2, 3) = f(3, 2) = |3 - (-1)| + |2 - 3| = 5

    So, we return 5.


    Case 1: A[i] < A[j] and i < j : -(A[i] - A[j]) + (-)(i-j) =    (A[j] + j) - (A[i] + i)
    Case 2: A[i] > A[j] and i > j : (A[i] - A[j]) + (i-j)     =    (A[i] + i) - (A[j] + j)
    Case 3: A[i] < A[j] and i > j : -(A[i] - A[j]) + (i-j)    =    (i-A[i]) - (j-A[j])
    Case 4: A[i] > A[j] and i < j : (A[i] - A[j]) + (j-i)     =    (j-A[j]) - (i-A[i])

    Case 1 and 2 are the same, while 3 and 4 are the same.
    We have to maximise the left expression and miniminse the right expression to get our answer.
*/

int maxArr(ArrayList<Integer> A) {
    int max1, min1, max2, min2;
    max1 = max2 = Integer.MIN_VALUE;
    min1 = min2 = Integer.MAX_VALUE;
    for(int i=0; i<A.size(); i++) {
        int pres = A.get(i);
        max1 = Math.max(pres + i, max1);
        min1 = Math.min(pres + i, min1);
        max2 = Math.max(i-pres, max2);
        min2 = Math.min(i-pres, min2);
    }
    return Math.max((max1-min1), (max2-min2));
}


// ===========================================================================================================================

