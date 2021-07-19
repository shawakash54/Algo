// Adding one to number
/*
    Example:

    If the vector has [1, 2, 3]

    the returned vector should be [1, 2, 4]
*/

ArrayList<Integer> plusOne(ArrayList<Integer> A) {
    // 0 in-front of the input array is a valid input
    // 0 in-front of the output array is not valid.

    int carry = 1;
    for(int i = A.size()-1; i>=0; i--) {
        int pres = A.get(i);
        int sum = pres + carry;
        A.set(i, sum%10);
        carry = sum/10;
    }
    if(carry > 0) {
        List<Integer> temp = new ArrayList<>(Arrays.asList(carry));
        A.addAll(0, temp);
    }
    // Getting rid of front zeroes
    for(int i=0; i<A.size();) {
        if(A.get(i) == 0)
            A.remove(i);
        else
            break;
    }
    return A;
}


// ===========================================================================================================================


