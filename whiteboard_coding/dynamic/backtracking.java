
// Egg dropping problem

/*
    Given a certain number of floors, and given a certain number of eggs,
    what is the least amount of egg drops that we need to perform the pivotal floor.
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

}


int totalDropsTopDown(int totalEggs, int totalFloors) {

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

public numArray(int[] nums) {
}


// Maximum contiguous sub array sum - Kadane's algorightm

/*

*/



// Maximum sum rectangle in a 2D matrix - Kadane's algorithm application

/*

*/



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





// Recursive staircase

/*
    If a person can only take 1/ 2 steps, how many unique possible ways are there for
    the person to climb the stairs of given step length?


    Input:   Output:    Possible ways:
    2        2          1,1 | 2
    3        3          1,1,1 | 1,2 | 2,1
    4        5          1,1,1,1,1 | 2,1,1 | 1,2,1 | 1,1,2 | 2,2

*/


// Count total unique binary search trees

/*
    We are given a number n. How many strucuturally unique binary search trees can be construct from the set of
    numbers from 1..to..n

    Binary search tree - Everything on the left subtree has to be less than the node we are sitting at. Everything
    on the right subtree have to be greater than the node we are sitting at.

    The answer we would produce will form an important combinatorial sequence - Catalan number sequence.



*/


// Change making problem

/*

*/


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
            then, it is the same as inserting character in B and findind minimum edit distance between A[0..2] and B[0..2]

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

/*
    // Solve it top down and bottom up
*/


// Partition to k equal sum subsets from an array of integers

/*
    arr = [4,3,2,3,5,2,1]
    total sum = 20
    k = 4
    Subset sum = 20/4 = 5
    Subsets are - [5], [1,4], [2,3], [2,3]

    We have to consider all the elements and we can only consider a element once in any subset.

*/

// Solve it using backtracking and DP approach


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


// N Queens placement problem

/*
    Return all distinct non-attacking placements of n Queens on an nxn chess board. n -> input.
    Place the queens in such a way that the queens are not attacking each other.

    
*/

