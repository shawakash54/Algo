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
    List<Integer> path1 = new ArrayList<Integer>();
    List<Integer> path2 = new ArrayList<Integer>();
    
    
    public int findLCA(int v1, int v2) {
        path1.clear();
        path2.clear();
        return findPathInternal(v1, v2);
    }

    
    private int findPathInternal(int v1, int v2) {
        if(!findPath(root, v1, path1) || !findPath(root, v2, path2)) {
            return -1;
        }
        int i;
        for(i=0; i<path1.size() && i<path2.size(); i++) {
            if (!path1.get(i).equals(path2.get(i))) 
                break;
        }
        
        return path1.get(i-1);
        
    }
    
    private boolean findPath(Node node, int v, List<Integer> path) {
        if (node == null)
            return false;
        
        path.add(node.data);
        
        if(node.data == v)
            return true;
        
        if(findPath(node.left, v, path) || findPath(node.right, v, path))
            return true;
        
        path.remove(path.size() - 1);
        return false;
    }
    
    
	public static void main(String[] args) 
    { 
        BinaryTree tree = new BinaryTree(); 
        tree.root = new Node(1); 
        tree.root.left = new Node(2); 
        tree.root.right = new Node(3); 
        tree.root.left.left = new Node(4); 
        tree.root.left.right = new Node(5); 
        tree.root.right.left = new Node(6); 
        tree.root.right.right = new Node(7); 
  
        System.out.println("LCA(4, 5): " + tree.findLCA(4,5)); 
        System.out.println("LCA(4, 6): " + tree.findLCA(4,6)); 
        System.out.println("LCA(3, 4): " + tree.findLCA(3,4)); 
        System.out.println("LCA(2, 4): " + tree.findLCA(2,4)); 
      
    } 
}
