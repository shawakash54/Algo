
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
boolean isBalanced(String str) {
    // Considering that the string is valid and will have legal characters only.
    Deque<Character> stack = new ArrayDeque<Character>();

    for(int i=0; i<str.length(); i++) {
        char c = str.get(i);
        if(c == '(' || c == '{' || c == '[') {
            stack.push(c);
            continue;
        }

        // If the character is a closing bracket and the stack is empty
        if(stack.isEmpty())
            return false;

        char check = stack.pop();

        switch(c) {
            case ')':
                if(check == '}' || check == ']')
                    return false;
                break;
            case '}':
                if(check == ')' || check == ']')
                    return false;
                break;
            case ']':
                if(check == ')' || check == '}')
                    return false;
                break;
        }
    }
    return stack.isEmpty();
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
    List<String> parantheses = new ArrayList<>();
    generateParanthesesBacktracking(n, n, "", parantheses);
    return parantheses;
}

void generateParanthesesBacktracking(int openBracketCount, int closeBracketCount, String target, List<String> parantheses) {
    // Valid parantheses spotted
    /*
      The recursion has bottomed out.
      We have used all left and right parens necessary within constraints up
      to this point. Therefore, the answer we add will be a valid paren string.
      
      We can add this answer and then backtrack so the previous call can exhaust
      more possibilities and express more answers...and then return to its caller,
      etc. etc.
    */
    if(openBracketCount == 0 && closeBracketCount == 0) {
        parantheses.add(target);
        return;
    }

    /*
      At each frame of the recursion we have 2 things we can do:
        1.) Insert a left parenthesis
        2.) Insert a right parenthesis
      These represent all of the possibilities of paths we can take from this
      respective call. The path that we can take all depends on the state coming
      into this call.
    */


    // Going depth first
    if(openBracketCount > 0) {
        generateParanthesesBacktracking(openBracketCount--, closeBracketCount, target+"(", parantheses);
    }

    // Backtracking
    if(closeBracketCount > 0) {
        generateParanthesesBacktracking(openBracketCount, closeBracketCount--, target+")", parantheses);
    }

}


// ===========================================================================================================================


