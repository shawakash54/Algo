
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

class Height { 
    int height = 0; 
} 

class BinaryTree {
    Node root;
    Map<Node, Node> parentMap;
    
    private boolean isBalanced(Node root, Height height) {
        if (root == null) {
            height.height = 0;
            return true;
        }
        
        Height lh = new Height();
        Height rh = new Height();
        
        boolean leftBalanced = isBalanced(root.left, lh);
        boolean rightBalanced = isBalanced(root.right, rh);
        
        height.height = (lh.height > rh.height ? lh.height : rh.height) + 1;
        boolean selfBalanced = Math.abs(lh.height - rh.height) >= 2 ? false : true;
        
        return selfBalanced && leftBalanced && rightBalanced;
    }

	public static void main(String[] args) 
    { 
        Height height = new Height(); 
        /* Constructed binary tree is 
                   1 
                 /   \ 
                2      3 
              /  \    / 
            4     5  6 
            / 
           7         */
        BinaryTree tree = new BinaryTree(); 
        tree.root = new Node(1); 
        tree.root.left = new Node(2); 
        tree.root.right = new Node(3); 
        tree.root.left.left = new Node(4); 
        tree.root.left.right = new Node(5); 
        tree.root.right.right = new Node(6); 
        tree.root.left.left.left = new Node(7); 
  
        if (tree.isBalanced(tree.root, height)) 
            System.out.println("Tree is balanced"); 
        else
            System.out.println("Tree is not balanced");
    } 
}
