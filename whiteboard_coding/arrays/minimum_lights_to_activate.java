// Example Input
/*
    Input 1:

    A = [ 0, 0, 1, 1, 1, 0, 0, 1].
    B = 3
    Input 2:

    A = [ 0, 0, 0, 1, 0].
    B = 3

    Example Output
    Output 1:

    2
    Output 2:

    -1
*/


public int solve(ArrayList<Integer> A, int B) {
    // Edge case where the B is more than number of steps
    if(A.size() < B) {
        for(int i=0; i<A.size(); i++) {
            if(A.get(i) == 1)
                return 1;
        }
        return -1;
    }
    if (B == 1) {
        for(int i=0; i<A.size(); i++) {
            if(A.get(i) != 1)
                return -1;
        }
    }

    // Greedy approach - B is constant for all the light bulbs.
    // Try to light up the right most bulb
    int nextLitIndex = 0;
    int count = 0;
    
    while(nextLitIndex < A.size()) {
        int left = -1, right = -1;

        // Find the left near most bulb that can be lit up to light up the present step
        for(int i=0; i<B-1; i++) {
            if(nextLitIndex - i < 0) {
                continue;
            }
            if(A.get(nextLitIndex-i) == 1) {
                left = nextLitIndex-i;
                break;
            }
        }

        // Find the right farthest most bulb that can be lit up to light up the present step
        for(int i=B-1; i>=0; i--) {
            if(nextLitIndex + i >= A.size()) {
                continue;
            }
            if(A.get(nextLitIndex+i) == 1) {
                right = nextLitIndex + i;
                break;
            }
        }

        if(left == -1 && right == -1) 
            return -1;
        else
        if(right != -1) {
            count++;
            nextLitIndex = right + B;
        }else {
            count++;
            nextLitIndex = left + B;
        }
    }

    return count;
}


// ===========================================================================================================================
