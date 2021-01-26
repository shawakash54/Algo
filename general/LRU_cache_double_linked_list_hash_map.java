import java.util.*;
import java.lang.*;
import java.io.*;

class Node {
    Node left, right;
    int value;
    int key;
    
    public Node(Node left, Node right, int key, int value) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

class LRUCache {
    HashMap<Integer, Node> map;
    int hashMapSize;
    Node start, end;
    
    public LRUCache(int size) {
        this.hashMapSize = size;
        this.map = new HashMap<Integer, Node>();
    }
    
    private void putEntry(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            removeNode(node);
            addAtTop(node);
        }else {
            Node node = new Node(null, null, key, value);
            if(map.size() >= hashMapSize) {
                map.remove(end.key);
                removeNode(end);
                addAtTop(node);
            }else {
                addAtTop(node);
            }
            map.put(key, node);
        }
    }
    
    private int getEntry(int key) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            removeNode(node);
            addAtTop(node);
            return node.value;
        }
        return -1;
    }
    
    private void removeNode(Node node) {
        if(node.right != null) {
            // not the last node
            node.right.left = node.left;
        }else {
            // last node
            end = node.left;
        }
        
        if(node.left != null) {
            // not the start node
            node.left.right = node.right;
        }else {
            // start node
            start = node.right;
        }
    }
    
    private void addAtTop(Node node) {
        node.right = start;
        node.left = null;
        if(start != null)
            start.left = node;
        start = node;
        if(end == null)
            end = start;
    }
    
	public static void main (String[] args) throws java.lang.Exception {
	    LRUCache lrucache = new LRUCache(4);
		lrucache.putEntry(1, 1);
		lrucache.putEntry(10, 15);
		lrucache.putEntry(15, 10);
		lrucache.putEntry(10, 16);
		lrucache.putEntry(12, 15);
		lrucache.putEntry(18, 10);
		lrucache.putEntry(13, 16);

		System.out.println(lrucache.getEntry(1));
		System.out.println(lrucache.getEntry(10));
		System.out.println(lrucache.getEntry(15));
	}
}
