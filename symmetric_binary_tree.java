import java.io.*;
import java.util.*;

class Node {
    Node left, right;
    int data;
    
    public Node(int key) {
        data = key;
        left = right = null;
    }
}

class BinaryTree {
    Node root;
    
    private boolean isMirror(Node left, Node right) {
        boolean selfBalanced;
        if((left == null && right != null) || (left != null && right == null))
            return false;
        if(left == null && right == null)
            return true;
        if(left.data == right.data)
            selfBalanced = true;
        else
            return false;
        
        return selfBalanced && isMirror(left.right, right.left) && isMirror(left.left, right.right);
    }
    
    private boolean isSymmetric(Node node)  
    { 
        return isMirror(node.left, node.right); 
    } 

	public static void main(String[] args) 
    { 
        BinaryTree tree = new BinaryTree(); 
        tree.root = new Node(1); 
        tree.root.left = new Node(2); 
        tree.root.right = new Node(2); 
        tree.root.left.left = new Node(3); 
        tree.root.left.right = new Node(4); 
        tree.root.right.left = new Node(4); 
        tree.root.right.right = new Node(3); 
        boolean output = tree.isSymmetric(tree.root); 
        if (output == true) 
            System.out.println("1"); 
        else
            System.out.println("0"); 
    } 
}
