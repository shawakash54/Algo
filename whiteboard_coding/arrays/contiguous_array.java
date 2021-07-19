// Max contiguous sum array
/*
    Input 2:
        A = [-2, 1, -3, 4, -1, 2, 1, -5, 4]

    Output 2:
        6
*/

int maxSubArray(final List<Integer> A) {
    int maxSum = Integer.MIN_VALUE;;
    int maxTillHere = 0;

    for(int i=0; i<A.size(); i++) {
        maxTillHere += A.get(i);
        if(maxSum < maxTillHere) {
            maxSum = maxTillHere;
        }
        if(maxTillHere < 0)
            maxTillHere = 0;
    }
    return maxSum;
}

// ===========================================================================================================================
