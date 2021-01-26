import java.io.*;
import java.util.*;

class Node {
    Node left, right, parent;
    int data;
    
    public Node(int key) {
        data = key;
        left = right = parent = null;
    }
}

class BinarySearchTree {
    Node root, l1, l2, lca;
    
    // Takes a key and inserts into the BST at the respective position
    public static Node insert(Node node, int key) {
        if(node == null)
            return new Node(key);
        
        if(key <= node.data) {
            Node left = insert(node.left, key);
            node.left = left;
            left.parent = node;
        }else {
            Node right = insert(node.right, key);
            node.right = right;
            right.parent = node;
        }
        
        return node;
    }
    
    public static Node LCA(Node l1, Node l2) {
        Map<Node, Boolean> ancestors = new HashMap<Node, Boolean>();
        
        while(l1 != null) {
            ancestors.put(l1, Boolean.TRUE);
            l1 = l1.parent;
        }
        
        while(l2 != null) {
            if(ancestors.containsKey(l2)) {
                return l2;
            }
            l2 = l2.parent;
        }
        
        return null;
    }
    
    
	public static void main(String[] args)  
    { 
        BinarySearchTree tree = new BinarySearchTree(); 
        tree.root = tree.insert(tree.root, 20); 
        tree.root = tree.insert(tree.root, 8); 
        tree.root = tree.insert(tree.root, 22); 
        tree.root = tree.insert(tree.root, 4); 
        tree.root = tree.insert(tree.root, 12); 
        tree.root = tree.insert(tree.root, 10); 
        tree.root = tree.insert(tree.root, 14); 
  
        tree.l1 = tree.root.left.right.left; 
        tree.l2 = tree.root.left; 
        tree.lca = tree.LCA(tree.l1, tree.l2); 
  
        System.out.println("LCA of " + tree.l1.data + " and " + tree.l2.data 
                + " is " + tree.lca.data); 
    } 
}
