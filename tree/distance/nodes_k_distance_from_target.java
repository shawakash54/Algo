
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
    Map<Node, Node> parentMap;
    
    private void populateParentMap(Node parent) {
        if(parent.left != null) {
            parentMap.put(parent.left, parent);
            populateParentMap(parent.left);
        }
        if(parent.right != null) {
            parentMap.put(parent.right, parent);
            populateParentMap(parent.right);
        }
    }
    
    private void printKDistanceNode(Node target, int distance) {
        List<Node> visited = new ArrayList<Node>();
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(target);
        visited.add(target);
        
        while(distance > 0) {
            int size = queue.size();
            
            while(size > 0) {
                target = queue.poll();
                Node left = target.left;
                Node right = target.right;
                Node parent = parentMap.get(target);
                
                if(left != null && !visited.contains(left)) {
                    queue.add(left);
                    visited.add(left);
                }
                if(right != null && !visited.contains(right)) {
                    queue.add(right);
                    visited.add(right);
                }
                if(parent != null && !visited.contains(parent)) {
                    queue.add(parent);
                    visited.add(parent);
                }
                size--;
            }
            distance--;
        }
        while(!queue.isEmpty())
            System.out.println(queue.poll().data);
    }
    
    private void printkdistanceNode(Node root, Node target, int distance) {
        parentMap = new HashMap<Node, Node>();
        if (root == null)
            return;
        parentMap.put(root, null);
        populateParentMap(root);
        printKDistanceNode(target, distance);
    }
    
    
	public static void main(String[] args) 
    { 
        BinaryTree tree = new BinaryTree(); 
        tree.root = new Node(20); 
        tree.root.left = new Node(8); 
        tree.root.right = new Node(22); 
        tree.root.left.left = new Node(4); 
        tree.root.left.right = new Node(12); 
        tree.root.left.right.left = new Node(10); 
        tree.root.left.right.right = new Node(14); 
        Node target = tree.root.left.right; 
        tree.printkdistanceNode(tree.root, target, 2); 
    } 
}
