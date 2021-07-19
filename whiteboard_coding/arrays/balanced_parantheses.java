
// Balanced parantheses problem - Classic Stack Problem
/*
    Input: exp = “[()]{}{[()()]()}” 
    Output: true

    Input: exp = “[(])” 
    Output: false

    Time complexity - length of string  - n, hence 
        the worst case time complexity O(n)
        space complexity - O(n)

*/
boolean isBalanced(String s) {

}


// ===========================================================================================================================


// Generate all strings with n matched parantheses

/*
    Input       Output
    1           ["()"]
    2           ["(())", "()()"]
    3           ["((()))", "(()())", "(())()", "()(())", "()()()"]

    This is a backtracking problem.
    Our choice: Select "(" or ")"
    Our constratints: We cannot close until we have a open bracket to balance. Close bracket count can never be more than open bracket count.
    Our goals: It is to place n*2 characters.

    Time complexity - length of string  - n,
        we can say that for every element, we have 2 choices to make, hence the time complexity is O(2^n)
        space complexity - O(n) (to store the string)

    It can be solved using 
        - Backtracking
        - Stack
        - Dynamic Programming
*/

List<String> generateParantheses(int n) {

}

void generateParanthesesBacktracking(int n) {

}

void generateParanthesesDP(int n) {

}



// ===========================================================================================================================


