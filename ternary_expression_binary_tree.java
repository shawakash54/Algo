
import java.io.*;
import java.util.*;

class Node {
    Node left, right;
    char data;
    public Node(char data) {
        this.left=null;
        this.right=null;
        this.data = data;
    }
}

class BinaryTree {
    static Node root;
    static Stack<Node> stk;
    public BinaryTree(String expr) {
        this.stk = new Stack<Node>();
        this.root = createBinaryTree(expr);
    }
    
    private Node createBinaryTree(String expr) {
        for (int i=expr.length()-1; i>=0;) {
            // If the letter is the last letter of 
            // the string or is of the type :letter: or ?letter: 
            // we push the node pointer containing 
            // the letter to the stack 
            if (
                (i == expr.length()-1) ||
                (i != 0 && (
                    (expr.charAt(i-1) == '?' && expr.charAt(i+1) == ':') ||
                    (expr.charAt(i-1) == ':' && expr.charAt(i+1) == ':')
                ))
            ) {
                stk.push(new Node(expr.charAt(i)));
            } else {
                // If we do not push the current letter node to stack, 
                // it means the top 2 nodes in the stack currently are the 
                // left and the right children of the current node 
                // So pop these elements and assign them as the 
                // children of the current letter node and then 
                // push this node into the stack 
                Node left = stk.pop();
                Node right = stk.pop();
                Node temp = new Node(expr.charAt(i));
                temp.left = left;
                temp.right = right;
                stk.push(temp);
            }
            i-=2;
        }
        return stk.pop();
    }
    
    public void preOrderTraversal() {
        this.preorderTraverse(this.root);
    }
    
    private void preorderTraverse(Node node) {
        if ( node == null)
            return;
        System.out.print(node.data + "->");
        preorderTraverse(node.left);
        preorderTraverse(node.right);
    }
}
class GFG {
	public static void main (String[] args) {
		String str = "a?b?c:d:e";
		BinaryTree bt = new BinaryTree(str);
		bt.preOrderTraversal();
	}
}
