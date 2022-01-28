
// Egg dropping problem

/*
    Given a certain number of floors, and given a certain number of eggs,
    what is the least amount of egg drops that we need to perform to find the pivotal floor.
        Pivotal floor is the floor where if we drop the egg from there, the egg wont break, and 
        if we drop the egg from the floor right above it, the egg will break.
    We will be given the number of floors and the number of eggs, we have to tell the least number of eggs we
    have to drop to find the pivotal floor.


    For example, if we have 1 egg, and n floors, then we have to drop the egg n times from each floor to find the pivotal floor,
    hence, the number of drops we have to do in the worst case scenario is n times.

        Base case 1: If we have 1 egg, then the minimum number of egg drops equals to the number of floors we have in the worst case(we are talking about
        guarantee here, so we have to consider the worst case scenario)

        Base case 2: If we have n eggs and 1 floor, then the number of egg drops we have to do is only 1.

        Base case 3: If we have n eggs and 0 floor, then the number of egg drops we have to do is 0 drops.

*/


int totalDropsBottomUp(int totalEggs, int totalFloors) {
    int[][] totalDrops = new int[totalEggs+1][totalFloors+1];

    for(int j=0; j<=totalFloors; j++) {
        // We have 0 eggs
        totalDrops[0][j] = 0;

        if(totalEggs > 1) {
            // We have 1 egg only
            totalDrops[1][j] = j;
        }
    }

    for(int i=0; i<=totalEggs; i++) {
        // We have only 0 floor
        totalDrops[i][0] = 0;

        if(totalFloors > 1) {
            totalDrops[i][1] = 1;
        }
    }

    for(int egg = 2 ; egg <= totalEggs; egg++) {
        for(int floor = 2; floor <= totalFloors; floor++) {
            totalDrops[egg][floor] = Integer.MAX_VALUE;

            for(int attemptFloor = 1; attemptFloor <= floor; attemptFloor++) {
                /*
                    We want to know the cost of the 2 outcomes:
                        1.) We drop an egg and it breaks.
                    We move 1 floor down. We have 1 less egg.
                        2.) We drop an egg and it doesn't break.
                    We have this many floors left: the difference between the total floors and our current
                    floor. We have the same number of eggs.
                */
                int costOfWorstOutcome = Math.max(
                    totalDrops[egg-1][attemptFloor-1],
                    totalDrops[egg][floor-attemptFloor]
                );
                /*
                    After we get the cost of the WORST outcome we add 1 to that worst outcome
                    to simulate the fact that we are going to do a test from THIS subproblem.
                    The answer to this problem is 1 PLUS the cost of the WORST SITUATION that
                    happens after our action at this subproblem.
                */
                int accountingThePresentDrop = 1 + costOfWorstOutcome;
                /*
                    Did we reach a BETTER (lower) amount of drops that guarantee that we can
                    find the pivotal floor where eggs begin to break?
                */
                totalDrops[egg][floor] = Math.min(accountingThePresentDrop, totalDrops[egg][floor]);
            }
        }
    }

    return totalDrops[totalEggs][totalFloors];
}


int totalDropsTopDown(int totalEggs, int totalFloors) {
    Map<String> cache = new HashMap<>();
    // Checking my input sanity
    if(totalEggs <= 0) 
        return -1;
    return findSolutionTopDown(totalEggs, totalFloors, cache);
}

int findSolutionTopDown(int eggsRemaining, int floorsRemaining, Map<String> cache) {
    if(eggsRemaining == 1) {
        return floorsRemaining;
    }
    if(floorsRemaining == 1 ||  floorsRemaining == 0) {
        return floorsRemaining;
    }
    // Cache is being checked to verify a similar subproblem was solved before since it goes DFS
    String cacheStr = floorsRemaining + "|" + eggsRemaining;
    if(cache.contains(cacheStr) && cache.get(cacheStr) != Integer.MAX_VALUE)
        return cache.get(cacheStr);
    else
        cache.set(Intger.MAX_VALUE);
    
    // If cache is a miss, then we need to find the minimum egg drops we have to do.
    // We have to still keep in mind that we have maximise our search space because we want to guarantee
    // that the returned answer will provide a solution
    
    // Try all the floors we have to minimise the answer
    for (int floor=1; floor<=floorsRemaining; floor++) {
        // There are 2 possiblities, on dropping the egg, the egg might either break or might not break.
        // Since we need a guarantee to our solution, we have to maximise the either of the branching
        int costOfWorstOutcome = Math.max(
            findSolutionTopDown(eggsRemaining, floorsRemaining-floor, cache),
            findSolutionTopDown(eggsRemaining-1, floor-1, cache)
        );
        int accountingThePresentDrop = 1 + costOfWorstOutcome;
        cache.put(cacheStr, Math.min(costOfWorstOutcome, cache.get(cacheStr)));
    }

    return cache.get(cacheStr);
}


// Range sum querying
/*
    Return the running sum of elements between two given indices(inclusive)
    For range sum, let's say we want the sum from i to j, we would first get the range sum from 0 to j and then get the sum from 0 to i-1
    and then the answer is the difference of both the sum.

    [1,2,3]
    i=1, j=2
    ans = 5
*/

int[] runningSumCache;

int numArray(int[] nums, int i, int j) {
    initialiseRangeSum(nums);
    return runningSumCache[j+1] - runningSumCache[i];
}

void initialiseRangeSum(int[] nums) {
    runningSumCache = new int[nums.length+1];
    runningSumCache[0] = 0;
    for(int i=0; i<nums.length; i++) {
        runningSumCache[i + 1] += nums[i] + runningSumCache[i];
    }
}


// Maximum contiguous sub array sum - Kadane's algorightm

/*
    Finding the running sum range between two indices is okay.
    But, for finding the maximum contiguous sub array sum is a problem where we have to generate all such sub arrays and
    then maximise them. Generating all such sub arrays is O(n^2).
    
    We can use Kadane's algorithm to solve it in O(n) in one traversal.
*/

int maxSubArraySum(int[] nums) {
    int max = nums[0];
    int maxSoFar = max;

    for(int i=1; i<nums.length; i++) {
        /*
            We are inspecting the item at index i.
            We want to answer the question:
            "What is the Max Contiguous Subarray Sum we can achieve ending at index i?"
            We have 2 choices:
            maxEndingHere + nums[i] (extend the previous subarray best whatever it was)
                1.) Let the item we are sitting at contribute to this best max we achieved
                    ending at index i - 1.
                    nums[i] (start and end at this index)
                2.) Just take the item we are sitting at's value.
                    The max of these 2 choices will be the best answer to the Max Contiguous
                    Subarray Sum we can achieve for subarrays ending at index i.
        */


        /*
            int presentElem = nums[i];
            maxSoFar += presentElem;
            if(maxSoFar > max) 
                max = maxSoFar;
            if(maxSoFar < 0)
                maxSoFar = 0;

            OR
        */

        maxSoFar = Math.max(maxSoFar + nums[i], nums[i]);

        // Did we beat the 'max' with the 'maxSoFar'?
        max = Math.max(max, maxSoFar);  
    }
    return max;
}



// Maximum sum rectangle in a 2D matrix - Kadane's algorithm application

/*
    It can be solved in O(n*n) time. We can utilise Kadanes algorithm to find the upper and lower boundary for the rectangle
    by moving the left and right boundaries of it.

    Trivial approach will be to generate all the possible rectangles - O(n^4) and 
        then find the maximum sum in the particular rectangle - O(n^2). Hence, time complexity for this is - O(n^6)

    For a rectangle, we have left, right, top and bottom. 
    We can move left to right and for any left and right, we can try finding the maximum top bottom of the running sum 
    using Kadane's algo. This region of top bottom on the running sum will give us the view of the maximum sub array sum 
    for the particular left and right selected.
    Now, if we explore all the possible left and right , we will be able to maximise the rectangle region.
    With this approach, time complexity is O(n^3)
*/

class Rectangle {
    int sum;
    int leftBorder;
    int rightBorder;
    int topBorder;
    int bottomBorder;
}

class KadaneResult {
    int sum;
    int startIdx;
    int endIdx;
}

KadaneResult getKadaneResult(int[] arr) {
    int maxSoFar = arr[0];
    int maxEndingHere = arr[0];

    int startIdxMaxEndingHere = 0;
    int startIdxMaxSoFar = 0;
    int endIdxMaxSoFar = 0;

    for(int i = 1; i<arr.length; i++) {
        maxEndingHere += arr[i];
        if(maxEndingHere < 0) {
            startIdxMaxEndingHere = i + 1;
            maxEndingHere = 0;
        }

        if(maxEndingHere > maxSoFar) {
            maxSoFar = maxEndingHere;
            startIdxMaxSoFar = startIdxMaxEndingHere;
            endIdxMaxSoFar = i;
        }
    }

    KadaneResult kadaneResult = new KadaneResult(maxSoFar, startIdxMaxSoFar, endIdxMaxSoFar);
    return kadaneResult;
}

Rectangle maxSum(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    int[] runningSum = new int[rows];
    Rectangle rectangle = new Rectangle();


    for(int left=0; left<cols; left++) {
        // reset the running sum
        resetRunningSum(runningSum);

        for(int right=left; right<cols; right++) {
            // add the particular right boundary column to running sum
            addRunningSum(runningSum, right, matrix);

            KadaneResult kadaneResult = getKadaneResult(runningSum);
            if(kadaneResult.sum > rectangle.sum) {
                // Found a new maximum sum rectangle
                rectangle.sum = kadaneResult.sum;
                rectangle.leftBorder = left;
                rectangle.rightBorder = right;
                rectangle.topBorder = kadaneResult.startIdx;
                rectangle.bottomBorder = kadaneResult.endIdx;
            }
        }
    }
    
    return rectangle;
}   

void resetRunningSum(int[] arr) {
    for(int i=0; i<arr.length; i++)
        arr[i] = 0;
}

void addRunningSum(int[] arr, int idx, int[][] matrix) {
    int rows = matrix.length;
    for(int i=0; i<rows; i++) {
        arr[i] += matrix[i][idx];
    }
}


// Total unique way to make a change (Coin change)

/*
    How many unique ways are there to make change for the amount
    Input:
        amount = 5
        coins = [1,2,5]
        Output = 4
            There are 4 unique ways. We can do the following:
            1 1 1 1 1
            2 1 1 1
            2 2 1
            5

        amount = 3
        coins = [2]
        Output = 0
            There are no ways of making 3 with a coin of value 2

        amount = 0
        coins = [x, y, z]
        Output = 1
            If the amount is 0, no matter what coins we have, we will be able to make the change by not considering any coins at all.
*/


int change(int amount, int[] coins) {
    int[][] numberOfWays = new int[coins.length + 1][amount + 1];

    // Base conditions
    // If amount is zero, we can always make change by selecting no coin, hence one way
    for(int row=0; row<=coins.length; row++) {
        numberOfWays[row][0] = 1;
    }

    // If there are no coins, then there are no ways of making any change
    for(int col=1; col<=amount; col++) {
        numberOfWays[0][col] = 0;
    }

    for(int row=1; row<=coins.length; row++) {
        for(int col=1; col<=amount; col++) {

            /*
            * numberOfWays[row][col] will be the sum of the ways to make change not considering this coin
            * (numberOfWays[row-1][col]) and the ways to make change considering this coin (numberOfWays[row][col] -
            * currentCoinValue] ONLY if currentCoinValue <= col, otherwise this coin can not
            * contribute to the total # of ways to make change at this sub problem target
            * amount)
            */
            int currentCoinValue = coins[row-1];
            int withPresentCoin = currentCoinValue < col ? numberOfWays[row][col-currentCoinValue] : 0;
            int withoutPresentCoin = numberOfWays[row-1][col];
            numberOfWays[row][col] = withoutPresentCoin + withPresentCoin;
        }
    }

    return numberOfWays[coins.length][amount];
}



// Recursive staircase

/*
    If a person can only take 1/ 2 steps, how many unique possible ways are there for
    the person to climb the stairs of given step length?


    Input:   Output:    Possible ways:
    2        2          1,1 | 2
    3        3          1,1,1 | 1,2 | 2,1
    4        5          1,1,1,1,1 | 2,1,1 | 1,2,1 | 1,1,2 | 2,2

*/

int climbStairsHelper(int n]) {
    // TOP DOWN approach
    // int[] memo = new int[n+1];
    // return climbStairsTopDown(n, memo);

    // BOTTOM UP approach
    return climbStairsBottomUp(n);
}

int climbStairsTopDown(int n, int[] memo) {
    if(n < 0) {
        // No possible way of climbing negative stairs
        return 0;
    }

    if(n == 0) {
        // Possible solution
        return 1;
    }

    /*
        Do we already have an answer to this question (subproblem)?
        If not fall through and compute, BUT if we already know it
        just return it from the cache
    */
    if(memo[n] > 0)
        return memo[n];

    /*
        The answer to this subproblem is the sum of the answer to the
        subproblems n - 1 and n - 2
        This drills us towards our base cases that bring us back up with
        an answer
        We cache the answer before returning it so we have it later
    */
    memo[n] = climbStairsTopDown(n-1, memo) + climbStairsTopDown(n-2, memo);
    return memo[n];
}

int climbStairsBottomUp(int n) {
    int[] numberOfWays = new int[n+1];
    
    // If there are 0 stairs, we do nothing, one possible way
    numberOfWays[0] = 1;

    // If there are 1 stair, one possible way
    numberOfWays[1] = 1;

    for(int i=2; i<=n; i++) {
        numberOfWays[i] = numberOfWays[i-1] + numberOfWays[i-2];
    }

    return numberOfWays[n];
}


// Count total unique binary search trees

/*
    We are given a number n. How many strucuturally unique binary search trees can be construct from the set of
    numbers from 1..to..n

    Binary search tree - Everything on the left subtree has to be less than the node we are sitting at. Everything
    on the right subtree have to be greater than the node we are sitting at.

    The answer we would produce will form an important combinatorial sequence - Catalan number sequence.
*/

int numTrees(int n) {
    // int[] memo = new int[n+1];
    // return numTreesTopDown(n, memo);
}

int numTreesTopDown(int n, int[] memo) {
    // Base conditions
    if(n==0 || n==1) {
        return 1;
        // If 0 nodes, then 1 bst is possible.
        // if 1 node, then only one bst is possible.
    }

    if(memo[n] > 0) {
        return memo[n];
    }

    for(int i=1; i<=n; i++) {
        /*
            We attain this value by taking the Cartesian Product (fancy word meaning all
            possible cross products) between all possible unique left BST's and unique
            right BST's.
        */
        int ways = 0;
        ways += numTreesTopDown(i-1, memo) * numTreesTopDown(n-i, memo);
        memo[n] = ways;
    }

    return memo[n];
}

int numTreesBottomUp(int n) {
    int[] memo = new int[n+1];
    /*
        The answer to the subproblem when n = 0 is 1.
        The answer to the subproblem when n = 1 (we can only place a 1 as a value and
        have a single node) is 1. A single node can only make 1 unique tree.
    */
    memo[0] = memo[1] = 1;

    for(int i=2; i<=n; i++) {
        for(int j=1; j<=i; j++) {
            /*
            * The answer to the ith subproblem will be the summation of F(i, n) for i = 0
            * to i = n (we plant every number we have available at the root)
            * 
            * Remember that we expressed: F(i, n) = G(i - 1) * G(n - i)
            * 
            * The answer to the total unique BST's we can construct with values from 1...n
            * with i representing what is rooted at the root of the tree is F(i, n).
            * 
            * We attain this value by taking the Cartesian Product (fancy word meaning all
            * possible cross products) between all possible unique left BST's and unique
            * right BST's.
            * 
            * All possible unique left BST's count is G[j - 1] if we plant at i.
            * 
            * All possible unique right BST's count is G[i - j] if we plant at i.
            * 
            * Taking a product is the same as taking all pairing between the two sets of
            * possibilites.
            */
            memo[i] += memo[j-1] * memo[i-j];
        }
    }

    return memo[n];
}



// Change making problem

/*
    Minimum coins to make change for a given amount.
*/

int coinChange(int[] coins, int amount) {
    // TOP DOWN approach
    // return coinChangeTopDown(coins, amount, new int[amount+1]);

    // BOTTOM UP approach
    return coinChangeBottomUp(coins, amount);
}

int coinChangeTopDown(int[] coins, int remainingAmount, int[] memo) {
    if(remainingAmount < 0) {
        // not a valid possibility as the considered coin in the last step violates the condition
        return -1;
    }

    if(remainingAmount == 0) {
        // The minimum coins needed to make change for 0 is always 0
        // coins no matter what coins we have.
        return 0;
    }
    
    if(memo[remainingAmount] != 0) 
        return memo[remainingAmount];

    int coinsRequired = Integer.MAX_VALUE;
    for(int coin: coins) {
        int changeResult = coinChangeTopDown(coins, remainingAmount - coin, memo);
        
        if(changeResult >=0 && changeResult < coinsRequired) {
            coinsRequired = 1 + changeResult;
        }
    }
    memo[remainingAmount] = coinsRequired == Integer.MAX_VALUE ? -1 : coinsRequired;
    return memo[remainingAmount];
}

int coinChangeBottomUp(int[] coins, int amount) {
    int[] minimumCoinsRequired = new int[amount+1];

    // Base conditions
    // If amount is 0, then number of coins required are also zero
    minimumCoinsRequired[0] = 0;

    Arrays.fill(minimumCoinsRequired, Integer.MAX_VALUE);

    for(int i=1; i<=amount; i++) {
        for(int coin: coins) {
            if(coin <= i) {
                minimumCoinsRequired[i] = Math.min(minimumCoinsRequired[i], minimumCoinsRequired[i-coin] + 1);
            }
        }
    }

    // If we don't have any possible solution, then we will have Integer.MAX_VALUE as the value and we can return -1 for that case
    return minimumCoinsRequired[amount] == Integer.MAX_VALUE ? -1 : minimumCoinsRequired[amount];
}


// Total ways to decode a string

/*
    Input:      Output:     Possible decoding:
    12          2           "1","2" -> "AB" |  "12" -> L
    226         3           "2","2","6" -> "BBF" | "22", "6" -> VF | "2", "26" -> BZ


    1 -> A
    2 -> B
    .
    .
    .
    Z -> 26


*/

int numDecodings(String s) {
    int[] dp = new int[s.length() + 1];
    Arrays.fill(dp, -1);

    return numDecodings(s, 0, dp);
}

int numDecodings(String s, int decodePointer, int[] dp) {
    int len = s.length();
    if(decodePointer >= len) {
    // Valid decoding
        return 1; 
    }
    
    // if already evaluated
    if(dp[decodePointer] != -1)
        return dp[decodePointer];

    int totalDecompositions = 0;
    for(int i=1; i<=2; i++) {
        if(decodePointer + i <= len) {
            String snippetUnderObservation = s.substring(decodePointer, decodePointer + i);
            if(validSnippet(snippetUnderObservation)) {
                totalDecompositions += numDecodings(s, decodePointer+i, dp);
            }
        }
    }
    dp[decodePointer] = totalDecompositions;
    return dp[decodePointer];
}

boolean validSnippet(String snippet) {
    // If a snippet starts with 0, it is a invalid snippet
    if(snippet.length() == 0 || snippet.charAt(0) == '0') 
        return false;
    
    int value = Integer.parseInt(snippet);
    return value >= 1 && value <=26;
}


// Edit distance between 2 strings - Levenshtein distance

/*
    Calculate the edit distance between two strings.
    For calculating the edit distance, we have 3 operations that we can do:
        1. replace a character
        2. delete a character
        3. insert a character
    
    Input:            Output:         Reasoning:
    horse, ros        3               horse -> rorse (replace h with r) , rorse -> rose (remove r) , rose -> ros (remove 3)


        At each position we have 3 options:
        1. Replace
            Lets say we have to find the minimum edit distance between A[0..3] and B[0..2]
            then, it is the same as replace character and findind minimum edit distance between A[0..2] and B[0..1]
        2. Delete
            Lets say we have to find the minimum edit distance between A[0..3] and B[0..2]
            then, it is the same as deleting the last character from A and findind minimum edit distance between A[0..2] and B[0..2]
        3. Insert
            Lets say we have to find the minimum edit distance between A[0..3] and B[0..2]
            then, it is the same as inserting character in B and findind minimum edit distance between A[0..3] and B[0..1]

    DP Table:
    For finding minimum edit distance between BENYAM and EPHREM
    Key:
            Replace     |   Insert
            Delete      |   Our position
        
        - If the characters are the same, then it is the same as ignoring that character and solving for the rest.
        - If the characters are different, then we need to take the minimum of the 3 possibilies.


            ""  B   E   N   Y   A   M
        ""  0   1   2   3   4   5   6  
        E   1   1   1   2   3   4   5
        P   2   2   2   2   3   4   5
        H   3   3   3   3   3   4   5
        R   4   4   4   4   4   4   5
        E   5   5   4   5   5   5   5
        M   6   6   5   5   6   6   5

*/

int levenshteinDistance(String s1, String s2) {
    // int[][] cache = new int[s1.length()+1][s2.length()+1];
    // for(int[] row: cache) {
    //     Arrays.fill(row, -1);
    // }
    // return editDistanceTopDown(s1, s1.length()-1, s2, s2.length()-1, cache);
}

int editDistanceTopDown(String s1, int s1Index, String s2, int s2Index, int[][] cache) {
    if(s1Index < 0) {
        // If s1 is "", it is all insertions to get s1 to s2
        return s2Index + 1;
    }
    if(s2Index < 0) {
        // If s2 is "", it is all deletions to get s1 to s2
        return s1Index + 1;
    }

    if(cache[s1Index][s2Index] != -1) {
        return cache[s1Index][s2Index];
    }

    // Present sub case has never been evaluated before
    if(s1.charAt(s1Index) == s2.charAt(s2Index)) {
        return editDistanceTopDown(s1, s1Index-1, s2, s2Index-1, cache);
    }else {
        /*
            We have a character mismatch. Remember we want to transform s1 into s2 and
            we hold the i'th character of s1 and the j'th character of s2:

            Deletion:
                Find levenshtein distance of s1[0...(i-1)] => s2[0...j]
                i'th character of s1 is deleted

            Insertion:
                Find levenshtein distance of s1[0...i] => s2[0...(j-1)]
                We then insert s2[j] into s2 to regain s2[0...j]
            
            Substitution:
                Find levenshtein distance of s1[0...(i-1)] => s2[0...(j-1)]
                We then insert s2[j] as i'th character of s1 effectively substituting it
        */
        // Check for the minimum of all the three use cases
        int insertCase = editDistanceTopDown(s1, s1Index, s2, s2Index-1, cache);
        int deleteCase = editDistanceTopDown(s1, s1Index-1, s2, s2Index, cache);
        int replaceCase = editDistanceTopDown(s1, s1Index-1, s2, s2Index-1, cache);

        /*
            We want to take the minimum of these 3 options to fix the problem (we add
            1 to the min cost action to symbolize performing the operation)
        */
        cache[s1Index][s2Index] = 1 + Math.min(insertCase, Math.min(deleteCase, replaceCase));
    }
    return cache[s1Index][s2Index];
}


// Partition to k equal sum subsets from an array of integers

/*
    arr = [4,3,2,3,5,2,1]
    total sum = 20
    k = 4
    Subset sum = 20/4 = 5
    Subsets are - [5], [1,4], [2,3], [2,3]

    We have to consider all the elements and we can only consider a element once in any subset.

    The question is NP-hard as there are total 2^n subsets where n is the size of the array.

*/

boolean canPartitionKSubsets(int[] arr, int k) {
    int sumOfAllElements = 0;
    for(int i: arr)
        sumOfAllElements += i;
    
    if(sumOfAllElements % k != 0 || k > arr.length || k <= 0)
        return false;

    return canPartitionBacktracking(
        arr,
        0,
        new boolean[arr.length],
        0,
        sumOfAllElements/k,
        k
    );
}

boolean canPartitionBacktracking(int[] arr, int startPosition, boolean[] usedElements, int presentSubsetSum, int targetSubsetSum, int k) {
    if(k == 1) {
        /*
            If we have filled all k - 1 buckets up to this point and we are now on
            our last bucket, we can stop and be finished.
            
            Example:
            arr = [4, 3, 2, 3, 5, 2, 1]
            k = 4
            targetBucketSum = 5
            If we get to the point in our recursion that k = 1 that means we have filled
            k - 1 buckets (4 - 1 = 3). 3 buckets have been filled, each a value of 5 meaning
            we have "eaten" 15 "points" of value from an array that sums to 20.
            This means we have 5 "points" to extract from the array and that for sure will fill
            the last bucket. So at the point there is 1 bucket left, we know we can complete the
            partitioning (we don't have to though, we just want to know whether we can or not).
        */
        return true;
    }
    
    /*
        Bucket full. continue the recursion with k - 1 as the new k value, BUT the
        targetBucketSum stays the same. We just have 1 less bucket to fill.
    */
    if(presentSubsetSum == targetSubsetSum) {
        return canPartitionBacktracking(arr, 0, usedElements, 0, targetSubsetSum, k-1);
    }

    for(int i=startPosition; i<arr.length; i++) {
        /*
            Try all values from 'iterationStart' to the end of the array ONLY if:
            
            1.) They have not been used up to this point in the recursion's path
            2.) They do not overflow the current bucket we are filling
        */
        if(!usedElements[i] && presentSubsetSum + arr[i] > targetSubsetSum) {
            usedElements[i] = true;

            /*
                See if we can partition from this point with the item added
                to the current bucket progress
            */
            if(canPartitionBacktracking(arr, i+1, usedElements, presentSubsetSum + arr[i], targetSubsetSum, k)) {
                return true;
            }
            used[i] = false;
        }
    }
    /*
        Partitioning from this point is impossible. Whether we are at the
        top level of the recursion or deeper into it.
    */
    return false;
}


// Implement a sudoku solver

/*
    9*9 board
    Constraints:
        - Have number 1-9 in every row and column
        - Every subgrid should also have number 1-9


    Brute force:
        - Generate every possible board (exponential number of boards)
        - Validate all boards
        - Return the valid board where the pre existing numbers matches

    Backtracking
        Our choice
            Place 1-9 in an empty cell
        Our constraint
            Placement should not break board
        Our goal
            Fill the board

    Sudoku is  NP Complete problem for 9*9 board.
    Time Complexity - It is going to be exponential if not super exponential
*/

char EMPTY_ENTRY = '.';

boolean canSolveSudoku(char[][] board) {
    // All the empty entries in the board are defined using EMPTY_ENTRY
    return solveSudokuPossible(board, 0, 0);
}

boolean solveSudokuPossible(char[][] board, int row, int col) {
    if(col == board[row].length) {
        // The particular row placements are completed, we can now proceed to next row
        col = 0;
        row++;
        if(row == board.length) {
            // If all the rows placements are completed, then we have a possible solve
            return true;
        }
    }

    // If the position to be filled is not empty, we can skip it
    if(board[row][col] != EMPTY_ENTRY) {
        solveSudokuPossible(board, row, col+1);
    }

    for(int i=1; i<=9; i++) {
        /*
            Here, we used '0' because chars are actually represented by ASCII values. '0' is a char and represented by the value of 48.
            We typed (a + '0') and in order to add these up, Java converted '0' to its ASCII value which is 48 and a is 1 so the sum is 49. 
            Then what we did is: (char)(49)
        */
        char toBePlaced = (char) (i + '0');

        if(possiblePlacement(toBePlaced, board, row, col)) {
            board[row][col] = toBePlaced;
            if(solveSudokuPossible(board, row, col+1)) {
                return true;
            }
            // backtracking
            board[row][col] = EMPTY_ENTRY;
        }
    }
    return false;
}

boolean possiblePlacement(char toBePlaced, char[][] board, int row, int col) {
    // Check if the same character exists in the particular row
    for(int j=0; j<board[row].length; j++) {
        if(toBePlaced == board[row][j])
            return false;
    }

    // Check if the same character exists in the particular column
    for(int i=0; i<board.length; i++) {
        if(toBePlaced == board[i][col])
            return false;
    }
    
    // Check if the same character exists in the particular sub region
    int regionSize = (int) Math.sqrt(board.length);
    int rowBoxIndexInWhichCharacterIsToBePlaced = row/regionSize;
    int columnBoxIndexInWhichCharacterIsToBePlaced = col/regionSize;

    int startOfRowBox = rowBoxIndexInWhichCharacterIsToBePlaced * regionSize;
    int startOfColumnBox = columnBoxIndexInWhichCharacterIsToBePlaced * regionSize;

    for(int i=0; i<regionSize; i++) {
        for(int j=0; j<regionSize; j++) {
            if(toBePlaced == board[startOfRowBox+i][startOfColumnBox+j]) {
                return false;
            }
        }
    }

    return true;
}

// IP address decomposition

/*
    Given a string with only numbers, restore it by returning all possible valid IP combinations.

    Input:                      Output:
    "25525511135"               ["255.255.11.135", "255.255.111.35"]

    Valid IP Address conditions:
     - Value should be between 1 and 255(every sinppet) and no leading 0s allowed
     

    Choice:
        Taking snippets 1-3 digits long and validate it along
    Constratins:
        We can't have a snippet more than 3 digits, value should be between 1 and 255 and no leading 0's
    Goal:
        4 valid subsections, and build pointer is at the end. We have exhausted all the characters as a part of the 4 subsections.

    Time complexity:
        O(1) - Since there are limited number of ip addresses(2^32). 
            Big O is about upper bounding and it is going to be constant eventually.
    Space complexity:
        O(1) - Same logic.
    
*/

List<String> restoreIpAddresses(String rawIpString) {
    int len = rawIpString.length();
    List<String> answers = new ArrayList<String>();
    getIPAddresses(
        rawIpString,
        0
        1,
        new int[4],
        answers
    );
    return answers;
}

void getIPAddresses(String rawIPString, int presentIndex, int presentSegment, int[] ipAddressSegments, List<String> validIPAddress) {
    if(presentSegment == 4 && presentIndex == rawIPString.length()) {
        // we have considered all the characters from the given string and we have a possible answer
        validIPAddress.add(buildIPAddressFromSegments(ipAddressSegments));
    }else
    if(presentSegment == 4) {
        // Segment found but all the characters not explored
        return;
    }

    // We have to explore 1-3 digits for each segment
    for(int segmentLength=0; segmentLength<=2; segmentLength++) {
        if(presentIndex + segmentLength < rawIPString.length()) {
            int segmentEnd = presentIndex + segmentLength;
            String segment = rawIPString.substring(presentIndex, segmentEnd + 1);
            int segmentValue = Integer.parseInt(segment);

            if(segmentValue < 1 || segmentValue > 255 || (segmentLength >= 1 && segment.charAt(0) == '0')) {
                // If a segment is starting with 0s, or segment is more than 255 or less than 1, then not a valid segment
                break;
            }
            
            ipAddressSegments[presentSegment] = segment;
            getIPAddresses(rawIPString, presentIndex+segmentLength, presentSegment+1, ipAddressSegments, validIPAddress);
        }
    }
}

String buildIPAddressFromSegments(int[] ipAddressSegments) {
    return ipAddressSegments[0] + "." + ipAddressSegments[1] + "." + ipAddressSegments[2] + "." + ipAddressSegments[3];
}


// compute all mnemonics for a phone number

/*
    1       2       3
          A,B,C   D,E,F
    4       5       6
  G,H,I   J,K,L   M,N,O
    7       8       9
 P,Q,R,S  T,U,V  W,X,Y,Z
    *       0       #


    Input:              Output:
    "23"                ["AD", "AE", "AF", "BD", "BE", "BF", "CD", "CE", "CF"]
    "89"                ["TW", "TX", "TY", "TZ", "UW", "UX", "UY", "UZ", "VW", "VX", "VY", "VZ"]

    Time complexity: O(4^n) * O(n) = O(n*4^n) | at each level, we take at most 4 choices.

    Space complexity: 
        O(n) (stack space) | n levels
        If we store the possibilies then, O(n) + O(4^n) = O(4^n)

    Choice:
        For a certain digit, we have certain choices to choose from, like for digit 2, we have the choice range of A,B,C
    Constratints:
        No constraints really
    Goals:
        Convert the digits given to their corresponding possibilites of letters that can take their place.
*/

List<String> letterCombinations(String digits) {
    List<String> digitToPossibleLetters = Arrays.asList(
        "",     // 0
        "",     // 1
        "ABC",  // 2
        "DEF",  // 3
        "GHI",  // 4
        "JKL",  // 5
        "MNO",  // 6
        "PQRS", // 7
        "TUV",  // 8
        "WXYZ"  // 9
    );

    List<String> possibleCombinations = new ArrayList<>();
    int numberOfDigits = digits.length();

    exploreCombination(
        digits,
        0,
        digitToPossibleLetters,
        numberOfDigits,
        possibleCombinations
    );

    return possibleCombinations;
}

void exploreCombination(String digits, int presentIndex, 
    List<String> digitToPossibleLetters, int numberOfDigits, List<String> possibleCombinations,
    StringBuilder partialMnemonic) {

    if(presentIndex == numberOfDigits) {
        // One possible combination
        possibleCombinations.add(partialMnemonic.build().toString());
        return;
    }

    char presentCharacter = digits.charAt(presentIndex);
    int presentDigitAsInt = presentCharacter - '0';

    String possibleLetters = digitToPossibleLetters[presentDigitAsInt];

    for(char possibleLetter : possibleLetters.toCharArray()) {
        // Choose the possible letter
        partialMnemonic.append(possibleLetter);
        // Explore the other possiblities
        exploreCombination(digits, presentIndex+1, digitToPossibleLetters, numberOfDigits, possibleCombinations, partialMnemonic);
        // Backtrack - unchoose the possible letter
        partialMnemonic.deleteCharAt(partialMnemonic.length() - 1);
    }
}


// N Queens placement problem

/*
    Return all distinct non-attacking placements of n Queens on an nxn chess board. n -> input.
    Place the queens in such a way that the queens are not attacking each other.
*/

List<List<String>> solveNQueens(int n) {
    
}


