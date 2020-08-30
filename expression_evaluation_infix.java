/* Infix to Postfix using Shunting yard algorithm + evaluating Postfix expression combined together */

import java.io.*;
import java.util.Stack; 

class EvaluateString {
    public static int evaluate(String expr) {
        char tokens[] = expr.toCharArray();
        Stack<Integer> value = new Stack<Integer>();
        Stack<Character> opr = new Stack<Character>();
        
        for(int i=0; i<tokens.length; i++) {
            char token = tokens[i];
            if (token == ' ')
                continue;
            
            if (token >= '0' && token <= '9') {
                // there might be more than one digit
                StringBuffer sb = new StringBuffer();
                while(i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9'))
                    sb.append(tokens[i++]);
                value.push(Integer.parseInt(sb.toString()));
            }else
            if(token == '(') {
                opr.push(token);
            }else
            if(token == ')') {
                while(opr.peek() != '(') {
                    value.push(applyOpr(value.pop(), value.pop(), opr.pop()));
                }
                opr.pop();
            }else 
            if(token == '+' || token == '-' || token == '*' || token == '/'){
                while (!opr.empty() && hasPrecedence(token, opr.peek())) {
                    value.push(applyOpr(value.pop(), value.pop(), opr.pop()));
                }
                opr.push(token);
            }else {
                throw new IllegalArgumentException("Token not recognised");
            }
        }
        
        // Parsing the leftover expressions
        while (!opr.empty()) {
            value.push(applyOpr(value.pop(), value.pop(), opr.pop()));
        }
        
        return value.pop();
    }
    
    // Return true if op2 has higher precedence comapred to op1
    public static boolean hasPrecedence(char op1, char op2){
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
    
    public static int applyOpr(int a, int b, char opr){
        switch(opr){
            case '+': return a+b;
            case '-': return a-b;
            case '*': return a*b;
            case '/': 
                if (b==0)
                    throw new UnsupportedOperationException("Can not divide by zero");
                return a/b;
            default:
                throw new IllegalArgumentException("Operation not defined");
        }
    }
    
	public static void main (String[] args) {
		System.out.println(EvaluateString.evaluate("10 + 2 * 6")); 
        System.out.println(EvaluateString.evaluate("100 * 2 + 12")); 
        System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 )")); 
        System.out.println(EvaluateString.evaluate("100 * ( 2 + 12 ) / 14")); 
	}
}
