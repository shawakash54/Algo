// Partitions
/*
    You are given a 1D integer array B containing A integers.

    Count the number of ways to split all the elements of the array into 3 contiguous parts so that the sum of elements in each part is the same.

    Input 1:
    A = 5
    B = [1, 2, 3, 0, 3]
    Output 1:
    2
    There are no 2 ways to make partitions -
    1. (1,2)+(3)+(0,3).
    2. (1,2)+(3,0)+(3).
*/



/*
    Problem 1: Determine if array A can be split into three consecutive parts such that sum of each part is equal. 
    If yes then print any index pair(i, j) such that sum(arr[0..i]) = sum(arr[i+1..j]) = sum(arr[j+1..n-1]), otherwise print -1. 

    If the total sum of the elements in the array is not divisable by 3, I can't divide the numbers into three equal subsets.
    If it is divisible by 3, then sum of each sub-set = total sum(S)/3.

    i and j denotes such indices which satisfies the use case
    arr[0..i] = arr[i+1..j] = arr[j+1...n] = S/3
    If we have a prefix sum array where prefix[i] = arr[0..i]
    Then, 
    arr[0..i] = prefix[i]
    arr[i..j] = prefix[j] - prefix[i]
    Therefore,
    prefix[i] = prefix[j] - prefix[i] = S/3
    This gives,
        prefix[j] = 2*prefix[i] = 2*(S/3)
    For finding any such indices which satisfies, we will have to look for i and j in prefix sum array which will satisfy the above condition.
*/

// If no such possible indices, return 0 else return 1
int findSplit(int[] arr) {
    int n = arr.length;
    int S = 0; // denotes total sum of array

    int[] prefix = new int[n];
    // populate prefix sum
    prefix[0] = arr[0];
    for(int i=1; i<n; i++) {
        prefix[i] = prefix[i-1] + arr[i];
    }
    S = prefix[n-1];
    if(S % 3 != 0)
        return 0;
    int S1 = S/3;
    int S2 = 2*S1;
    int idx1=-1, idx2=-1;

    for(int i=0; i<n; i++) {
        if(prefix[i] == S1 && idx1==-1) {
            idx1 = i;
        }else
        if(prefix[i] == S2 && idx1 != -1) {
            idx2 = i;
            break;
        }
    }
    if(idx1 != -1 && idx2 != -1) {
        System.out.println("Possible solutuin: " + idx1 + " , " + idx2);
        return 1;
    }
    return 0;
}


/*
    Problem 2: It's not about finding one such possible solution, but about finding the total number of ways
    we can divide the array in.

    Prefix array gives me the i (first half where sum = S/3)
    We can create a suffix array as well.
    Now, wherever we get the i with the help of prefix array, we can search in the suffix array from i+2 to 
    get the positions where the suffix sum is S/3. We will increment counter for all such positiins.
    Why we start from i+2, the real reason is that we want to create 3 groups and having found the first group, we want to find the last group,
    such that middle group will automatically adjust.

    To solve for this suffix array, one possible way is to push the indices from suffix array where sum = S/3 to a vector/ array list and then 
    perform a binary search to find the index of the element greater than x. The index we get from binary search in the vector will give us the index of the
    element. We can subtract it with total size of vector to get the possible count. Here the time complexity is O(nlogn)

    Better solution: To solve for the suffix array, 
    what we can do is, we can maintain an array cnt[], where cnt[i] becomes 1, if the sum of cnt[i..n] = S/3, else 0.
    Then, we can calculate the cumulative sum of the cnt[] array and use it for finding the solution in O(n)
*/  


// Returns number of possible ways of doing it
int solve(int A, ArrayList<Integer> B) {
    int S=0;
    int len = B.size();

    for(int i=0; i<len; i++)
        S += B.get(i);

    if(S%3 != 0)
        return 0;
    
    // Populating my cnt[] array
    int[] cnt = new int[len];
    int sumTillNow = 0;
    for(int i=len-1; i>=0; i--) {
        sumTillNow += B.get(i);
        if(sumTillNow == (S/3)) {
            cnt[i] = 1;
        }else {
            cnt[i] = 0;
        }
    }

    // Finding cumulative cnt
    for(int i=cnt.length-2; i>=0; i--) {
        cnt[i] += cnt[i+1];
    }

    int ans = 0;
    sumTillNow = 0;
    for(int i=0; i+2<len; i++) {
        sumTillNow += B.get(i);
        if(sumTillNow == (S/3)) {
            ans += cnt[i+2];
        }
    }

    return ans;
}


// ===========================================================================================================================


// Set Refresher
/*
    Subsets of any set consists of all possible sets including elements and the null set.
    Subset of set A = {1,2,3,4}
    {}
    {1}, {2}, {3}, {4},
    {1,2}, {1,3}, {1,4}, {2,3},{2,4}, {3,4},
    {1,2,3}, {2,3,4}, {1,3,4}, {1,2,4}
    {1,2,3,4}

    Subsets can be 
    - Proper subset
    - Improper subset

    A proper subset is a set that contains a few elements of the original set.
    Improper set contains every element of the original set along with the null set.

    For example, if set A = {2, 4, 6}, then,
    Number of subsets: {2}, {4}, {6}, {2,4}, {4,6}, {2,6}, {2,4,6} and Φ or {}.
    Proper Subsets: {}, {2}, {4}, {6}, {2,4}, {4,6}, {2,6}
    Improper Subset: {2,4,6}

    If a set has n elements, then the number of subsets it can have is 2^n and the number of proper subsets are 2^n - 1
    Number of subsets that can be generated containing N elements from a set containint n elements are NCn number of ways.

    Example 1: How many number of subsets containing three elements can be formed from the set?
    S = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }
    Solution: Number of elements in the set = 10
    Number of elements in the subset = 3
    Therefore, the number of possible subsets containing 3 elements = 10C3
    = 10! / ((10-3)! * 3!)
    Therefore, the number of possible subsets containing 3 elements from the set S = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 } is 120.

    To be noted:
    X is a subset of Y means X is contained in Y.
*/



// Problem 1
// Generate all subsets/ find the power set of Set S
// A power set of S is the set of all subsets of S including empty set and S itself.

// Approach - Recursion

int[] S = { 1, 2, 3 };
findPowerSet(S);
void findPowerSet(int[] S) {
    Deque<Integer> set = new ArrayDeque<>();
    int n = S.length;
    printAllSets(S, set, n);
}

// Solution is similar to 0/1 Knapsack problem, where for each element either we consider them or don't consider them
void printAllSets(int[] S, Deque<Integer> set, int n) {
    if(n==0) {
        // One possible subset
        System.out.println(set);
        return;
    }
    // Consider the nth element
    set.addLast(S[n-1]);
    printAllSets(S, set, n-1);
    set.removeLast();
    // or, don't consider the nth element
    printAllSets(S, set, n-1);
}

/*
Output:
    [3,2,1]
    [3,2]
    [3,1]
    [3]
    [2,1]
    [2]
    [1]
    []
*/

// Approach - BitMasking approach

/*
    Representation using bits
    0000 - {}
    0001 - {1}
    0010 - {2}
    0011 - {1,2}
    0100 - {3}
    0101 - {1,3}
    0111 - {1,2,3}
    1000 - {4}
    1001 - {1,4}
    1010 - {2,4}
    1011 - {1,2,4}
    1100 - {3,4}
    1101 - {1,3,4}
    1110 - {2,3,4}
    1111 - {1,2,3,4}

    (1<<i) in Java is a number with ith bit set to 1.
*/

// Time complexity - O(n * 2^n)
int[] S = { 1, 2, 3 };
findPowerSet(S);
void findPowerSet(int[] S) {
    // possible subset count is 2^n
    int n = S.length;
    for(int i=0; i<(1<<n); i++) {
        System.out.print("{");
        for(int j=0; j<n; j++) {
            // (1<<j) is a number with jth bit set to 1, so when we 'and' them with the subset number, we get the numbers present in the current subset
            // and the ones which are not
            if(( i & (1<<j) ) > 0) {
                System.out.print(S[j] + " ");
            }
        }
        System.out.print("}");
    }
}

/*
    Output:
        {1}
        {2}
        {1 2}
        {3}
        {1 3}
        {2 3}
        {1 2 3}
*/


// ===========================================================================================================================


/*
    Knapsack problem
    Items are divisible and fraction of items can be taken as well. Hence, the greedy approach is applicable.
    Fraction of items can also be taken.

    We have to solve for it by sorting the value/ weight of each item and then picking them up in sorted order as per the ratio.
    Since fractional items can be taken, hence greedy approach is used here. Greedy method asks us to first decide on how to select the input
    and then just go on selecting it one by one. value/ weight ratio is how we have decided to select the input in order maximise the profits/ total value.

    Weight: [1,3,4,5]
    Value:  [1,4,5,7]
    Total Capaicty (capacity) = 7
*/

// Greedy Approach

double getMaxValue(int[] wt, int[] val, int capacity) {
    int len = wt.length;
    List<ItemValue> itemsPicked = new ArrayList();

    // Creating Item Values list
    ItemValue[] items = new ItemValue[len];
    for(int i=0; i<len; i++) {
        items[i] = new ItemValue(wt[i], val[i]);
    }

    // Sorting Item Value list
    Arrays.sort(items, new Comparator<ItemValue>() {
        @Override
        public int compare(ItemValue itv1, ItemValue itv2) {
            return itv2.cost - itv1.cost;
        }
    });

    double maxValue = 0;
    for(ItemValue item : items) {
        itemsPicked.add(item);
        if(item.weight <= capacity) {
            maxValue += item.value;
            capacity -= item.weight;
        }else {
            maxValue += capacity*item.cost;
            break;
        }
    }
    return maxValue;
}

class ItemValue {
    int weight;
    int value;
    double cost;

    public ItemValue(int w, int v) {
        this.weight = w;
        this.value = v;
        this.cost = v/w;
    }
}



/*
    0-1 Knapsack Problem
    We have items with their weights given to us. Each item has a value assign to it.
    We can only carry W weight , we have to find the maximum value we can carry.
    Also, only one quantity of each item can be picked. Also, items are not divisible and we either pick/ or don't pick the item.
    If the item could have been picked by spitting them, then it would have been a greedy problem, and we could have solved for it by sorting the
    value/ weight of each item and then picking them up in sorted order. But, since the items are not divisible, it is a 0/1 Knapsack problem.

    Weight: [1,3,4,5]
    Value:  [1,4,5,7]
    Total Weight (W) = 7
    It is like a container loading problem.

    Print set(objects that are being carried) for every approach below.
*/


// Recursion + Memoization solution

/*
    Recursion will generate 2^n possibilities subsets and hence time taken will be exponential.
    Also, there is an optimal substructure. Using memoization will reduce the time complexity from exponential to O(nW) where
    n = number of items, W = total capacity.
*/

int knapsack(int[] v, int[] w, int W) {
    Map<String, Integer> lookup = new HashMap<>();
    // return solveKnapsack(v, w, v.length-1, W);
    return solveKnapsack(v, w, v.length-1, W, lookup);
}

int solveKnapsack(int[] v, int[] w, int len, int W) {

    // Base case: negative capacity
    if(W < 0) {
        return Integer.MIN_VALUE;
    }

    // Base case: No items left/ no capacity left
    if(len < 0 || W == 0) {
        return 0;
    }

    // Case 1. Include current item `v[n]` in the knapsack and recur for remaining items `n-1` with decreased capacity `W-w[n]`
    int maxConsidering = v[len] + solveKnapsack(v, w, len-1, W-w[len]);
    
    // Case 2. Exclude current item `v[n]` from the knapsack and recur for remaining items `n-1`
    int maxWithoutConsidering = solveKnapsack(v, w, len-1, W);

    // Return maximum value we get by including or excluding the current item
    
    return Math.max(maxConsidering, maxWithoutConsidering);
}

int solveKnapsackWithMemoization(int[] v, int[] w, int len, int W, Map<String, Integer> lookup) {

    // Base case: negative capacity
    if(W < 0) {
        return Integer.MIN_VALUE;
    }

    // Base case: No items left/ no capacity left
    if(len < 0 || W == 0) {
        return 0;
    }

    // Construct a unique map key - for length n and capacity W, what is the maximised output which is already calculated
    String key = len + "|" + W;
    int max = 0;
    
    if(lookup.containsKey(key)) {
        max = lookup.get(key);
    }else {
        // If the subproblem is seen for the first time, solve it and store its result in a map

        // Case 1. Include current item `v[n]` in the knapsack and recur for remaining items `n-1` with decreased capacity `W-w[n]`
        int maxConsidering = v[len] + solveKnapsack(v, w, len-1, W-w[len], lookup);
        
        // Case 2. Exclude current item `v[n]` from the knapsack and recur for remaining items `n-1`
        int maxWithoutConsidering = solveKnapsack(v, w, len-1, W, lookup);

        // Return maximum value we get by including or excluding the current item
        
        max = Math.max(maxConsidering, maxWithoutConsidering);
        lookup.put(key, max);
    }
    return max;
}


// DP Solution
/*

    Why is it a DP problem ? If we look at the problem, it is about finding the possible combination and making a decision at each step
    of whether we want to include the particular element or not in the set. At the same time, we want to optimise the selection based upon the 
    weight we can carry. Hence, here we see there is scope for optimisations and there must be optimal substrucutres also present.



    DP Solution:
                                    Total weight --------->
    Value   |  Wt   |   0   |   1   |   2   |   3   |   4   |   5   |   6   |   7   |
      1     |  1    |   0   |   1   |   1   |   1   |   1   |   1   |   1   |   1   |
      4     |  3    |   0   |   1   |   1   |   4   |   5   |   5   |   5   |   5   |
      5     |  4    |   0   |   1   |   1   |   4   |   5   |   6   |   6   |   9   |
      7     |  5    |   0   |   1   |   1   |   4   |   5   |   7   |   8   |   9   |

    So, the highest value we get is 9 from the above 2D DP solution.

    If someones asks, which items are we picking ? 
    Then, pick the element at the bottom right from the matrix and then retrace back to 0.
    
    Time complexity will be O(nW) where n = number of items, W = total capacity.

*/


int knapsack(int[] v, int[] w, int W) {
    int len = v.length;
    int[][] maxValue = new int[W+1][len+1];

    // when no items are considered, then maxValue = 0
    for(int j=0; j<=W; j++) {
        maxValue[0][j] = 0;
    }

    // when total capacity is 0, maxValue = 0
    for(int i=0; i<=len; i++) {
        maxValue[i][0] = 0;
    }

    // do for i'th item
    for(int i=1; i<=len; i++) {
        // consider all weights from 0 to maximum capacity `W`
        for(int j=1; j<=W; j++) {
            if(w[i-1] > j) {
                // don't include the i'th element weight of ith element is more than the present capacity
                maxValue[i][j] = maxValue[i-1][j];
            }else {
                // find the maximum value we get by excluding or including the i'th item
                maxValue[i][j] = Math.max(v[i-1] + maxValue[i-1][j-w[i-1]] , maxValue[i-1][j]);
            }
        }
    }

    // Printing elements
    int totalProfit = maxValue[len][W];
    int tempWeight = W;
    for(int i=len; i>0; i--) {
        if(maxValue[i-1][tempWeight] != totalProfit) {
            System.out.println("Element " + i + " is present.");
            tempWeight -= w[i];
            totalProfit -= v[i];
        }
    }


    return maxValue[len][W];
}



// ===========================================================================================================================



// Subset Sum problem
/*
    Given a set of positive integers and an integer S, is there any non-empty subset who sum to S

    Input:
    set = { 7, 3, 2, 5, 8 }
    sum = 14
    
    Output: Subsequence with the given sum exists 
    subset { 7, 2, 5 } sums to 14

    It need not be a contiguous set
*/


boolean subsetSum(int[] A, int sum) {
    int len = A.length;
    boolean[][] present = new boolean[len+1][sum+1];

    // if no items are considered, then the sum can be made with a non empty subset
    for(int j=0; j<=sum; j++) {
        present[0][j] = false;
    }

    // since all the numbers are positive, sum 0 can't be made with any non empty subset
    for(int i=0; i<=len; i++) {
        present[i][0] = false;
    } 

    for(int i=1; i<=len; i++) {
        for(int j=1; j<=sum; j++) {
            // find the subset with sum `j` by excluding or including the i'th item
            present[i][j] = present[i-1][j] || present[i-1][j-A[i-1]];
        }
    }
    return present[len][sum];
}


// ===========================================================================================================================


/*
    Given a set of positive integers, find if it can be divided into two subsets of equal sum


    Consider S = {3, 1, 1, 2, 2, 1}
    We can partition S into two partitions, each having a sum of 5.    
    S1 = {1, 1, 1, 2}
    S2 = {2, 3}
    Note that this solution is not unique. Here’s another solution.
    S1 = {3, 1, 1}
    S2 = {2, 2, 1}
*/

boolean partition(int[] A) {
    int sum = Arrays.stream(A).sum();
    int len = A.length;
    if(sum % 2 != 0)
        return false;
    int sumToLookFor = sum/2;

    boolean[][] subsetPossibleWithSum = new boolean[len+1][sumToLookFor+1];

    // When no elements are considered
    for(int j=0; j<=sumToLookFor; j++) {
        subsetPossibleWithSum[0][j] = false;
    }

    // To form a subset with zero sum (since there are all positive integers, no such subsets are possible)
    for(int i=0; i<=len; i++) {
        subsetPossibleWithSum[i][0] = false;
    }

    for(int i=1; i<=len; i++) {
        for(int j=1; j<=sumToLookFor; j++) {
            // Checking if ith element can be conisderd to be a part of the subset
            if(A[i-1] > j) {
                subsetPossibleWithSum[i][j] = subsetPossibleWithSum[i-1][j];
            } else {
                subsetPossibleWithSum[i][j] = subsetPossibleWithSum[i-1][j] || subsetPossibleWithSum[i-1][j-A[i-1]];
            }
        }
    }

    return subsetPossibleWithSum[len][sumToLookFor];
}


/*
    3-Partition problem
    Given a set S of positive integers, determine if it can be partitioned into three disjoint subsets that all have
    the same sum, and they cover S.

    S = { 7, 3, 2, 1, 5, 4, 8 }
    S1 = { 7, 3 }
    S2 = { 5, 4, 1 }
    S3 = { 8, 2 }
*/

// Recursion solution
boolean partition(int[] S) {
    int sum = Arrays.stream(S).sum();
    int len = S.length;
    if(sum % 3 != 0) 
        return false;

    int sumToLookFor = sum/3;
    Map<String, Boolean> lookup = new HashMap<String, Boolean>();
    return isPartitionPossible(S, len-1, sumToLookFor, sumToLookFor, sumToLookFor lookup);
}

boolean isPartitionPossible(int[] S, int n, int sum1, int sum2, int sum3, Map<String, Boolean> lookup) {
    // return true if the subset is found
    if(a == 0 && b == 0 && c == 0) {
        return true;
    }

    // base case: no items left
    if(n < 0) {
        return false;
    }

    String key = sum1 + "|" + sum2 + "|" + sum3 + "|" + n;
    if(!lookup.containsKey(key)) {
        boolean set1Found = false;
        if(sum1 >= S[n])
            set1Found = isPartitionPossible(S, n-1, sum1 - S[n], sum2, sum3, lookup);

        boolean set2Found = false;
        if(!set1Found && sum2 >= S[n])
            set2Found = isPartitionPossible(S, n-1, sum1, sum2 - s[n], sum3, lookup);


        boolean set3Found = false;
        if(!set1Found && !set2Found && sum3 >= S[n])
            set3Found = isPartitionPossible(S, n-1, sum1, sum2, sum3 - s[n], lookup);
        
        // return true if we get a solution
        lookup.put(key, set1Found || set2Found || set3Found)
    }
    return lookup.get(key);
}


/*
    K-partition problem
    S = { 7, 3, 5, 12, 2, 1, 5, 3, 8, 4, 6, 4 }

    k=2
    S1 = { 5, 3, 8, 4, 6, 4 }
    S2 = { 7, 3, 5, 12, 2, 1 }

    k=3
    S1 = { 2, 1, 3, 4, 6, 4 }
    S2 = { 7, 5, 8 }
    S3 = { 3, 5, 12 }

    k=4
    S1 = { 1, 4, 6, 4 }
    S2 = { 2, 5, 8 }
    S3 = { 12, 3 }
    S4 = { 7, 3, 5 }

    k=5
    S1 = { 2, 6, 4 }
    S2 = { 8, 4 }
    S3 = { 3, 1, 5, 3 }
    S4 = { 12 }
    S5 = { 7, 5 }
*/

// Function for solving k–partition problem. It prints the subsets if set `S[0…n-1]` can be divided into `k` subsets with equal sum
void partition(int[] S, int k) {
    int sum = Arrays.stream(S).sum();
    int len = S.length;

    // Paritions more than number of elemnts available
    if(len < k)
        System.out.println("No Such partitons exist");
    int sumToLookFor = sum/k;

    int[] sumLeft = new int[k];
    Arrays.fill(sumLeft, sumToLookFor);

    // Array to store the partition in which the element is lying
    int[] partitionIndex = new int[len];

    if((sum % k != 0) || (!isPartitionPossible(S, k, sumToLookFor, partitionIndex, len-1)))
        System.out.println("No Such partitons exist");

    // printing the k-partitions
    for(int i=0; i<k; i++) {
        for(int j=0; j<len; j++) {
            if(partitionIndex[j] == i+1) {
                // It means the jth element is a part of ith partition
                System.out.print(S[j] + " ");
            }
        }
        System.out.println();
    }
}

boolean isPartitionPossible(int[] S, int k, int[] sum, int[] partitionIndex, int n) {
    if(allSumIsZero(sum, k)) {
        return true;
    }   

    // no items left to consider
    if(n < 0)
        return false;

    boolean result = false;
    for(int i=0; i<k; i++) {
        if(!result && sum[i] >= S[n]) {
            partitionIndex[n] = i+1;
            sum[i] -= S[n];
            result = isPartitionPossible(S, k, sum, partitionIndex, n-1);
            // backtrack: remove the current item from the i'th subset/ partition
            sum[i] += S[n];
        }
    }
    return result;
}

boolean allSumIsZero(int[] sum, int len) {
    boolean isZero = true;
    for(int i=0; i<len; i++) {
        if(sum[i] != 0)
            isZero = false;
    }
    return isZero;
}



// ===========================================================================================================================

// Unique generalised abbreviations

/*
    Given word ="word", return the following list (order does not matter):
    ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]

    If we try to solve for using it bit manipulation, it is basically the bits in one combination if is 0 means we consider the
    exact character, or else if it is 1 we increment count and then add it to the string when we encounter the next 0.

    Or, it can be solved using backtracking.
    Time complexity - O(2^n)
    Since, at each unit, we have two options, either consider the character as it is or else increment the count and then print the count in the 
    next combination
*/

List<String> generateAbbreviation(String word) {
    // Using recursion
    List<String> answers1 = new ArrayList<>();
    List<String> answers2 = new ArrayList<>();

    generateAbbreviationRecursion(answers1, word, "", 0, 0);
    generateAbbreviationBitManipulation(answers2, word);
} 

void generateAbbreviationRecursion(List<String> answers, String word, String current, int present, int count) {
    if(present == word.length()) {
        // One combination found
        if(count > 0) {
            // this is the consideration when the character was not selected in the previous stack choice
            current += count;
        }
        answers.add(current);
    } else {
        // incrementing the count
        generateAbbreviationRecursion(answers, word, current, present+1, count+1);
        // considering the present character
        generateAbbreviationRecursion(answers, word, current + (count > 0 ? count : "") + word.charAt(present), present+1, 0);
    }
}

void generateAbbreviationBitManipulation(List<String> answers, String word) {
    int len = word.length();
    for(int i=0; i< (1<<len); i++) {
        // check if the bit is set, then increase the count, or else consider the character in the string
        int count = 0;
        // instead of a string builder we can also use a stack (LIFO structure) as we have to insert at the front everytime 
        // and using string builder defeates the purpose since the time complexity will goto O(n)
        StringBuilder str = new StringBuilder("");
        for(int j=0; j<len; j++) {
            if( (1<<j) & i ) {
                count++;
            } else {
                if(count != 0) {
                    str.append(count);
                    count = 0;
                }
                // insert at the front
                str.insert(0, word.charAt(j));
            }
        }
    } 
}


// ===========================================================================================================================

// Minimum subset sum difference
    
/*
    Lets assume we have a set S whose sum is sum.
    In order to divide the set into 2 subsets and minimise the sum difference of both the sets,
    the best possible scenario is when there is a equal sum present in both the divided subsets,
    viz sum1 = sum2 = sum/2

    So, we establish that,
        sum = sum1 + sum2
        sum2 = sum - sum1
        We have to minimise the difference of sum2 - sum1,
        or , (sum - sum1) - sum1
        or m sum - 2*sum1
        So, here we have to minimise the above difference and if it tends to 0, then we have the equal subset sum distribution, and that is the minimal
        in any scenario
        Hence, we only should look to minimise abs(sum - 2*sum1)
        sum1 - sum of any possible subset

    Another solution is using the subset sum problem where we create a DP to see if the particular
        sum can be formed using a subset. Then having the DP in place, we can consider that sum1 <= sum2, 
        hence, sum1 <= sum/2(best case scenario when equal subset sum exists)
        We can consider the last row of the DP where we consider all the elements of the set and check the minimum value 
        we can obtain for sum - 2*sum1


    Input:  arr[] = {1, 6, 11, 5} 
    Output: 1
    Explanation:
        Subset1 = {1, 5, 6}, sum of Subset1 = 12 
        Subset2 = {11}, sum of Subset2 = 11        
*/

int findMin(int arr[], int n) {
    
    int len = arr.length;
    int sum = 0;
    // calculating complete sum of the set
    for(int i=0; i<len; i++)
        sum += arr[i];

    // 2D matrix to hold the solution to subset sum problem
    boolean[][] sumPossible = new boolean[len+1][sum+1];

    // Sum zero can be formed for every set with empty subset
    for(int i=0; i<=len; i++) {
        sumPossible[i][0] = true;
    }

    // If we consider no elements then no sum can be formed except for sum 0
    for(int j=1; j<=sum; j++) {
        sumPossible[0][j] = false;
    }

    // filling the matrix in bottom up manner
    for(int i=1; i<=len; i++) {
        for(int j=1; j<=sum; j++) {
            sumPossible[i][j] = sumPossible[i-1][j] || sumPossible[i-1][j-arr[i-1]];
        }
    }

    // to minimise the difference sum - 2*sum1
    int diff = Integer.MAX_VALUE;

    for(int j=sum/2; j>=0; j--) {
        if(sumPossible[len][j]) {
            diff = Math.min(diff, sum - 2*j);
        }
    }

    return diff;
}

