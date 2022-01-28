
// Longest common substring

// Longest common subsequence (2 strings)

/*
    Subsequence are not contigious.
    Input                  Output
    ABCDGH                  ADH
    AEDFHR

    AGGTAB                  GTAB
    GXTXAYB

    It can be done in brute force generating all the possible subsets. O(2^n)
    Or, we can try to identify the subproblems and do it in O(n*n)

    - If the characters are a match , then the longest common subsequence is 1 + longest common subsequence of the remaining strings.
    - If the characters are not a match, then the longest common subsequence is either 
        longest common subsequence of the string a and b(- the last character under observation)
        OR
        longest common subsequence of the string a(- the last character under observation) and b
*/

// Minimum deletions and insertions to transform a string into another

// Longest non-decreasing subsequence
/*
    [-1, 3, 4, 5, 2, 2, 2, 2]
    Here, 2,2,2,2 is a longest non-decreasing sequence, though it is strictly not increasing as well.
    -1, 3, 4, 5 is the strict longest increasing sequence.
    Here, the answer though is [-1,2,2,2,2]. It is the longest non-decreasing subsequence.

    Approaches:
    1. Brute force: Generate all subsets 2^n and then check for longest non decreasing subsequence - O(2^n)
    2. DP approach - Time - O(n^2) , Space - O(n)
        Identifying the subproblems.
*/

// Longest increasing subsequence


// Longest decreasing subsequence

// maximum sum increasing subsequence
// Shortest common super-sequence
// Minimum deletions to make a sequence sorted
// Longest repeating subsequence
// Subsequence pattern matching
// Longest bitonic subsequence
// Longest alternating subsequence
// Edit distance
// String interleaving