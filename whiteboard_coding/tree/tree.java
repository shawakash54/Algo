
/*
    Fundamentals - Binary Tree
    
    Types of trees
        Full Binary tree
            A tree where a leaf nodes have no children or a node has both the children , it is a full tree.

                        *
                      /   \
                     *     *
                          / \
                         *   *
                        / \
                       *   *

        Complete Binary tree (Binary Heaps)
            When we fill the nodes, we go top to bottom and left to right.
                        *
                      /   \
                     *     *
                    / \   / \
                   *   * *   *
                  / \
                 *   *
        
        Perfect Binary tree
            Where all the leaves are at the same level, and all the nodes that decided to have descendants have
            both the children nodes. It is both complete and also full.
                        *
                      /   \
                     *     *
                    / \   / \
                   *   * *   *
*/  


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) {
        val = x;
    }
}


// Symmetric tree LC-101
/*
    Check if a binary tree is a mirror of itself.
    For example, a binary tree: [1, 2, 2, 3, 4, 4, 3] is symmetric
              1
           /     \
          2       2
         / \     / \
        3   4   4   3

    Binary tree [1, 2, 2, null, 3, null, 3] is not symmetric.
              1
            /   \
           2     2
            \     \
             3     3

*/

boolean isSymmetric(TreeNode root) {
    if(root == null) {
        return true;
    }
    return areSubTreesSymmetric(root.left, root.right);
}

boolean areSubTreesSymmetric(TreeNode left, TreeNode right) {
    if(left == null || right == null) // left and right both should be null or else they are not symmetric
        return left == right;
    
    return left.val == right.val && areSubTreesSymmetric(left.left, right.right) && areSubTreesSymmetric(left.right, right.left);
}


// Same tree LC-100
/*
    Given two binary trees, check if they are the same or not.
    Two binary trees are considered identical if they are structurally identical and the nodes have the same value

    Input:
        1            1
       / \          / \
      2   3        2   3
    [1, 2, 3]  &  [1, 2, 3]

    Both of the above are identical.

       1          1
      /            \
     2              2
    [1, 2]       [1, null, 2]
    Not identical since they are not structurually identical


       1          1
      / \        / \
     3   2      2   3
    Not identical. Though they are structurually identical but node values are different.
*/

boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null || q == null)
        return p == q;
    return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
}

// Contains duplicate I LC 217

/*
    Given an array of integers, check if the array contains any duplicates.

    Input                   Output
    [1,2,3,1]               true
    [1,2,3,4]               false
*/

boolean containsDuplicate(int[] nums) {
    if(nums == null || nums.length == 0)
        return false;
    Set<Integer> set = new HashSet<Integer>();
    for(int i : nums) {
        if(!set.add(i))
            return true;
    }
    return false;
}


// Contains duplicate II LC 219
/*
    What if the distance between the indices of the duplicates in the array allowed to be at most k. (Extend algo for this use case)
    Given an array of integers and an integer k, find out whether there are 2 distinct indices i and j in the array
    such that nums[i] = nums[j] and the absolute difference between i and j is at most k.

    Input                   Output
    [1,2,3,1]               true
    k=3

    [1,0,1,1]               true
    k=1

    [1,2,3,1,2,3]           false
    k=2
*/

boolean containsNearbyDuplicate(int[] nums, int k) {
    Set<Integer> set = new HashSet<>();
    for(int i=0; i<nums.length; i++) {
        int presElem = nums[i];
        if(!set.add(presElem)) {
            // Not able to add to set, hence already present
            return true;
        }
        if(set.size() > k) {
            set.remove(nums[i-k]);
        }
    }
    return false;
}


// Contains duplicate III - LC 220
/*
    Given an array of integers, find out whether there are two distinct indices i and j in the array
    such that absolute difference between nums[i] and nums[j] is at most t and the absolute difference between
    i and j is at most k.

    Input:
        nums = [1, 2, 3, 1] , k = 3, t = 0
        Output: false

        nums = [1,0,1,1] , k = 1, t = 2
        Output: true

        nums = [1,5,9,1,5,9] , k = 2, t = 3
        Output: false

    We need to solve it using Binary search trees. We need a self-balanced BST to keep the time complexity as 
    O(log(k)). For a given node, we need to find the node which is lower than it but is the highest and the node which is higher than it but the lowest
    and then take the absolute difference of both the combinations and see if it at most t.

    We can use TreeSet in Java for implementing it. TreeSet implements self balancing BST - Red-Black tree.
    Check for overflows of integers if one value in nums tends to Integer.MAX_VALUE.
    Use ceiling(), floor() API of the treeset.
*/

boolean containsNearbyAlmostDuplicates(int[] nums, int k, int t) {
    TreeSet<Long> set = new TreeSet<>();
    for(int i=0; i<nums.length; i++) {
        int presElem = nums[i];
        
        Long ceilingNumber = set.ceiling((long) presElem);
        if(ceilingNumber != null && ceilingNumber-presElem <= t)
            return true;
        
        Long floorNumber = set.floor((long) presElem);
        if(floorNumber != null && presElem-floorNumber <= t)
            return true;
        
        set.add((long) presElem);
        if(set.size() > k) 
            set.remove((long) nums[i-k]);
    }
    return false;
}


// Maximum depth of binary tree - LC 104
/*
    Given binary tree, find it's maximum depth.
    The maximum depth is the number of nodes along the longest path from the root node down to the farthest 
    leaf node.

    A leaf is a node with no children.
    
    Input:
    [3,9,20, null, null, 15, 7]
                3
              /   \
             9     20
                  /  \
                 15   7
                  
    Output:
    3
*/

int maxDepth(TreeNode root) {
    // Solve using recursion and iteratively
    maxDepthRecursive(root);
    maxDepthIterative(root);
}

int maxDepthRecursive(TreeNode node) {
    if(node == null)
        return 0;
    
    int leftSubtreeMaxDepth = maxDepthRecursive(node.left);
    int rightSubtreeMaxDepth = maxDepthRecursive(node.right);

    return 1 + Math.max(leftSubtreeMaxDepth, rightSubtreeMaxDepth);
}

int maxDepthIterative(TreeNode node) {
    if(node == null)
        return 0;

    LinkedList<TreeNode> stack = new LinkedList<>();
    LinkedList<Integer> depths = new LinkedList<>();

    stack.add(node);
    depths.add(1);

    int maxDepth = 0;

    while(!stack.isEmpty()) {
        TreeNode presentNode = stack.pollLast();
        int currentDepth = depths.pollLast();

        if(presentNode != null) {
            maxDepth = Math.max(maxDepth, currentDepth);
            stack.add(presentNode.left);
            depths.add(currentDepth+1);
            stack.add(presentNode.right);
            depths.add(currentDepth+1)
        }
    }
    return maxDepth
}


// Inorder traversal LC - 94
/*
    Input: [1, null, 2, 3]
    Output: [1, 3, 2]

    1
     \
      2
     /
    3

    Time Complexity - O(n)
    Space complexity - O(n) 
    
    Do with recursively and iteratively.
*/

List<Integer> list = new ArrayList<>();

List<Integer> inorderTraversal(TreeNode root) {
    inorderRecursively(root);
}

void inorderRecursively(TreeNode node) {
    if(node == null)
        return;
    inorderRecursively(node.left, list);
    list.add(node.val);
    inorderRecursively(node.right, list);
    return;
}

void inorderIterative(TreeNode node) {
    Stack<TreeNode> stk= new Stack<>();
    TreeNode temp = node;
    
    while(temp.left != null) {
        stk.push(temp);
        temp = temp.left;
    }

    while(stk.isEmpty()) {
        temp = stk.pop();
        list.add(temp);

        // I am the extreme end right now
        // Exploring the extreme end of the right one if present
        TreeNode tempRight = temp.right;
        while(tempRight != null) {
            stk.add(tempRight);
            tempRight = tempRight.left;
        }
    }
}

// Preorder traversal - LC 144
/*
    Input: [1, null, 2, 3]
    Output: [1, 2, 3]

    1
     \
      2
     /
    3

    Time Complexity - O(n)
    Space complexity - O(n) 

    Do with recursively and iteratively.
*/
List<Integer> list = new ArrayList<>();

List<Integer> preorderTraversal(TreeNode root) {
    preOrderTraversalRecursive(root);
    preOrderIterative(root);
}

void preOrderTraversalRecursive(TreeNode node) {
    if(node == null)
        return;
    list.add(node.val);
    preOrderTraversalRecursive(node.left);
    preOrderTraversalRecursive(node.right);
}

void preOrderIterative(TreeNode node) {
    Stack<TreeNode> stk = new Stack<>();
    stk.push(node);
    while(!stk.isEmpty()) {
        TreeNode present = stk.pop();
        if(present != null) {
            list.add(present.val);
            stk.add(present.right);
            stk.add(present.left);
        }
    }
} 

// Postorder traversal - LC 145
/*
    Input: [1, null, 2, 3]
    Output: [3, 2, 1]

    1
     \
      2
     /
    3

    Time Complexity - O(n)
    Space complexity - O(n) 

    Do with recursively and iteratively.
*/

List<Integer> list = new ArrayList<>();

List<Integer> postorderTraversal(TreeNode root) {
    postorderTraversalRecursive(root);
    postorderTraversalIterative(root);
}

void postorderTraversalRecursive(TreeNode node) {
    if(node == null)
        return;

    postorderTraversalRecursive(node.left);
    postorderTraversalRecursive(node.right);
    list.add(node.val);
}

// https://www.programcreek.com/2012/12/leetcode-solution-of-iterative-binary-tree-postorder-traversal-in-java/
void postorderTraversalIterative(TreeNode node) {
    Stack<TreeNode> stk = new Stack<>();

    if(node == null)
        return stk;

    stk.push(node);
    TreeNode prev = null;

    while(!stk.isEmpty()) {
        TreeNode current = stk.peek();
        if(prev==null || prev.left==current || prev.right==current) {
            if(current.left != null) 
                stk.push(current.left);
            else
            if(current.right != null)
                stk.push(current.right);
            else {
                stk.pop();
                list.add(current.val);
            }
        } else
        if(current.left == prev) { 
            if(current.right != null) 
                stk.push(current.right);
            else {
                stk.pop();
                list.add(current.val);
            }   
        } else 
        if(current.right == prev) {
            stk.pop();
            list.add(current.val);
        }
        prev = current;
    }
}


// Validate BST - LC 98
/*
    Given a Binary tree, determine if it is a valid Binary Search Tree.

    Input: [2,1,3]
        2
       / \
      1   3
    Output: true

    Input: [5, 1, 4, null, null, 3, 6]
                5
              /   \ 
             1     4
                  / \
                 3   6
    Outpu: false 
*/

boolean isValidBST(TreeNode root) {
    isValidBSTRecursive(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    isValidBSTBFS(root);
}

boolean isValidBSTRecursive(TreeNode node, int min, int max) {
    if(node == null)
        return true;
    else
    if(node.val <= min || node.val >= max)
        return false;
    
    return isValidBSTRecursive(node.left, min, node.val) && isValidBSTRecursive(node.right, node.val, max);
}

class AugmentedTreeNode {
    TreeNode node;
    long min, max;

    public AugmentedTreeNode(TreeNode node, long min, long max) {
        this.node = node;
        this.min = min;
        this.max = max;
    }
}

boolean isValidBSTBFS(TreeNode node) {
    // We create a DS on top of the TreeNode so as to hold the min-max range while traversing down the tree.
    AugmentedTreeNode augmentedNode = new AugmentedTreeNode(node, Long.MIN_VALUE, Long.MAX_VALUE);
    Queue<AugmentedTreeNode> queue = new LinkedList<>();

    queue.add(augmentedNode);

    while(!queue.isEmpty()) {
        augmentedNode = queue.poll();
        if(augmentedNode.node != null) {
            int nodeValue = augmentedNode.node.val;
            if(nodeValue <= augmentedNode.min || nodeValue >= augmentedNode.max)
                return false;
            queue.add(new AugmentedTreeNode(augmentedNode.node.left, augmentedNode.min, nodeValue));
            queue.add(new AugmentedTreeNode(augmentedNode.node.right, nodeValue, augmentedNode.max));
        }
    }

    return true;
}


// Binary tree - level order traversal - LC 102
/*
    Input: [3, 9, 20, null, null, 15, 7]
                3
              /   \
             9     20
                  /  \
                 15   7

    Output: [[3], [9, 20], [15, 7]]

    We need to go BFS - Hence queue is something we can turn to.
*/

List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> list = new ArrayList<>();

    // Reason to keep this check and not check for null node while traversing is otherwise we will have a list in the list which will be completely empty.
    if(root == null)
        return list;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while(!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> presentLevel = new ArrayList<>();

        while(size > 0) {
            TreeNode presentNode = queue.poll();
            
            presentLevel.add(presentLevel.val);

            if(presentNode.left != null)
                queue.offer(presentNode.left);
            if(presentNode.right != null)
                queue.offer(presentNode.right);
        }
        list.add(presentLevel);
    }
    return list;
}


// Convert sorted array to BST - LC 108
/*
    Elements are given in ascending order. Convert the list into a height balanced BST.
    Heigh balanced BST - A Binary tree in which the depth of the 
        two subtrees of every node never differ by more than 1.

    Input: 
    [-10, -3, 0, 5, 9]

    Possible answer:
    [0, -3, 9, -10, null, 5]
    
                0
              /   \
            -3     9
           /      /
         -10     5

    Since the given array is sorted, we can greedily select the middle element
        as the root node to ensure heigh balance and then continue it recursively
        to generate a possible solution.
        If the given array was not sorted, then we would need to use something like AVL, Red-black tree and
        do rotations to ensure it entering one element at a time in the BST.

*/

TreeNode sortedArrayToBST(int[] nums) {
    return greedilyCreateBST(nums, 0, nums.length-1);
}

TreeNode greedilyCreateBST(int[] nums, int start, int end) {
    if(start > end)
        return null;
    
    int middle = (start + end)/2;
    TreeNode node = new TreeNode(nums[middle]);
    node.left = greedilyCreateBST(nums, start, middle-1);
    node.right = greedilyCreateBST(nums, middel+1, end);

    return node;
}


// Implement trie - LC 208
/*
    Implement a Trie(Prefix tree) with insert, search, and startsWith method

    Trie trie = new Trie();
    trie.insert("apple");
    trie.search("apple");   // returns true
    trie.insert("app");     // return false
    trie.startsWith("app"); // returns true
    trie.insert("app");
    trie.search("app");     // returns true
*/

class TrieNode {
    int numberOfTerminatingWords;
    final TrieNode[] children = new TrieNode[26];

    public TrieNode next(final char c) {
        return children[c-'a'];
    }
}

class Trie {
    final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void update(final String old, final String newString) {
        delete(old);
        insert(newString);
    }

    public void delete(final String word) {
        TrieNode currentNode = this.root;
        for(int i=0; i<word.length(); i++) {
            char c = word.charAt(i);
            if(currentNode == null)
                return;

            currentNode = currentNode.next(c);
        }

        if(currentNode != null) {
            if(currentNode.numberOfTerminatingWords != 0)
                currentNode.numberOfTerminatingWords--;
        }
    }

    public void insert(String word) {
        TrieNode currentNode = this.root;
        
        if(word == null)
            return;

        for(int i=0; i<word.length(); i++) {
            char c = word.charAt(i);
            if(currentNode.next(c) == null)
                currentNode.children[c-'a'] = new TrieNode();

            currentNode = currentNode.next(c);
        }
        currentNode.numberOfTerminatingWords++;
    }

    // Returns number of words in the trie
    public int search(String word) {
        TrieNode currentNode = this.root;
        if(word == null)
            return 0;
        
        for(int i=0; i<word.length(); i++) {
            char c = word.charAt(i);
            if(currentNode == null || currentNode.next(c) == null)
                return 0;
            else
                currentNode = currentNode.next(c);
        }
        return currentNode.numberOfTerminatingWords;
    }
}


// Search suggestion system - LC 1268
/*
    Given a array of string products and a string searchWord. Design a system that suggests at most 3 products names from
        products after each character of searchWord is typed. Suggested products should have common prefix with the searchWord.
        If there are more than 3 products with a common prefix, return three lexicographically minimum products.

    Input:
        products = ["mobile", "mouse", "moneypot", "monitor", "mousepad"]
        searchWord = "mouse"

        Output:
            [
                ["mobile", "moneypot", "monitor"],
                ["mobile", "moneypot", "monitor"],
                ["mouse", "mousepad"],
                ["mouse", "mousepad"]
                ["mouse", "mousepad"]
            ]

    // Solve using Trie and other approaches.
*/

List<List<String>> suggestedProducts(String[] products, String searchWord) {
    
}


// Serialize and Deserialize binary tree
/*
    Given a binary tree, first serialize it into a string and then deserialize it back to the Binary tree DS.

            1
           / \
          2   3
             / \
            4   5
        
    We can do a pre-order traversal and serialize to a string. The reason to select pre-order is since the node will 
    come in front and it helps during the deserialization.

    Serialised string - "1, 2, X, X, 3, 4, X, X, 5, X, X"
*/


// Lowest common ancestor between 2 binary tree
/*
    For given X and Y nodes, find the lowest common ancestor.

    Example:
             A
           /   \
          B     C
               / \
              D   X
                 /
                Y

    For this tree, X is the LCA of X and Y.

             A
           /   \
          B     C
               / \
              X   D
                 /
                Y
    
    For this tree, C is the LCA of X and Y.

    Whenever we are doing a recursive problem for a tree, focus always on a SINGLE NODE.

    Time complexity - O(n)
    Space complexity - O(h) | O(n) for skewed trees
*/


// All nodes distance K in a binary tree
/*
    Find all nodes at a distance K from the given node in a binary tree.

            1
          /   \
         5*    2#
        / \     \
       12  7     5
          / \   /
         1# 8# 9

    For the above given tree, if the node is 5 , marked with *
    Then the nodes at a distance k(=2) are the ones marked with #

    This looks like a BFS(level order traversal), but wait, the issue is that we cannot go up.
    To solve for it, we can use a HashMap to keep track of the parent node. Now, we can do a regular level order
    traversal and try solving it. 

    Complexity
        m = no of edges
        n = no of nodes

        Time - O(m+n) . Since no of edges is always 1 less than the number of nodes. Hence, effectively O(n)
        Space - O(n)
        
*/




// Binary tree level order traversal
/*
            1
         /     \
        2       3
      /   \   /   \
     4     5 6     7
    /
   8

   Output: [1, 2, 3, 4, 5, 6, 7, 8]
*/


// Test if binary tree is height balanced
/* 
    Definition of being balanced:
        |LeftSubTreeHeight - RightSubTreeHeight| <= 1
    

    Height Balanced Tree examples
    1.     *
          / \
         *   *

    2.     *

    3. (null node)

    4.     *
         /   \
        *     *
            /   \
           *     *

    Not Height Balanced Tree examples

    1.     *
         /   \
        *     *
       / \
      *   *
         /
        *

    2.     *
            \
             *
              \
               *
    


    Approaches:
        1. At each node get left and right subtree's height and see if they are balanced.
        
        2. Drill down recursion, as we come up, we care about two things, 
            a node's height in the tree and if a node's left and right subtrees are balanced.

     
    
*/


// Count total unique binary search tree
/*
    Input       Output
    1           1
    2           2
    3           5
    4           14
    5           42

    Catalan numbers sequence.


    n=2
    Possibility set = {1, 2}

    Possibilities:
        1
         \
          2


        2
       /
      1

    Hence, the output is 2.


    F(i, n): With i at the root and n nodes [1, 2, 3, .., n]. How many structurally unique BST's can be build ?
    G(3) = F(1, 3) + F(2, 3) + F(3, 3)

    Cartesian product of left and right subtrees possibilites.
    F(i, n) = G(i-1) * G(n-i)

    Time - O(n^2)
    Space - O(n)
*/  


// Test if a binary tree is symmetric in structure and value
/*  
            Input:                              Output:

            null                                true

            1                                   true

            1                                   true
          /   \
         2     2

            1                                   false
          /   \
         2     x

            2                                   false
          /   \
         1     1
        / \   / \
       2   4 x   2

    
    
*/


// BT from Preorder and Inorder traversal
/*

*/



//  BT from Inorder and Postorder
/*

*/



// Postorder and Preorder - only a unique full binary tree can be constructed
/*

*/



// BT from Inorder and level order
/*

*/


// Construct BST given post order traversal
/*

*/


// Construct BST given pre order traversal
/*

*/


// Segment tree range minimum query
/*
    Time complexity for querying - O(log(n))
    Time complexity for building segment tree - O(n * log(n)^2)
*/


// Segment tree range minimum query with lazy updates
/*

*/


// Cartesian tree from inorder traversal
/*

*/



// N-ary tree - BFS
/*

*/


// N-ary tree - DFS
/*

*/


// N-ary tree - Mirror image Check
/*
    Store elements of one tree in a stack
    Store elements of another tree in a queue
    If all the elements are the same when popped out , then it is a mirror.
*/


// B+ Tree/ B Tree for database indexing for LLD


// Binary lifting
/*
    https://github.com/Errichto/youtube/blob/master/leetcode/1483-kth-ancestor.cpp
    Binary Lifting:
        There is a tree of size N, rooted at 0.
        Answer Q queries:
            Given v and k, find the k-th ancestor of v.

        Brute force:
            repeat k times:
                v = parent[v]
        
            If there are Q queries
            Time complexity = O(Q*N)
        
        Binary Lifting:
            How to improve from O(N) to O(log(N))
                - Divide by 2
                    - Binary search
                    - Divide and conquer
                - Powers of 2
                    - Segment tree
                    - Binary lifting
                    - Sparse tables 

            The problem boils down to, can we solve the problem quickly if k is a power of 2 ?
            And, can we devise a way to solve the problem for general k efficiently.

            Example:
                                        0
                                     /  | \
                                    /   \  \
                                   /     \  \
                                  1       3  5
                                /   \        /
                               2     4      6
                                    /
                                   7
                                 /   \
                               11      8
                                         \
                                          9
                                           \
                                            10

                        Let's say we have to find the k=(19th) ancestor
                        We can see that k in binary form can be represented as :
                            19 = 10011 = 16 + 2 + 1 (We have to represent it in powers of 2)
                        Hence, if we had pre calculated the parents for each power of 2 in the tree for every node,
                            we can take 3 jumps at most to find the 19th ancestor from a given node.
                            Hence, the time complexity reduced down to O(log(N))

                    Preprocessing required:
                        We have to find the ancestor at every powers of 2 for each node, we can define it as follows:
                            ancestor[N][log]
                            ancestor[v][j] defines 2^j-th ancestor of v

                        for v=0..N-1:
                            ancestor[v][0] = parent[v]
                            ancestor[v][1] = ancestor[ancestor[v][0]][0]
                            ancestor[v][2] = ancestor[ancestor[v][1]][1]
                            ancestor[v][3] = ancestor[ancestor[v][2]][2]
                            ... and so on ...
                        

                        It can be boiled down to:
                            for v = 0 .. N-1:
                                ancestor[v][0] = parent[v]
                                for j = 1 .. LOG - 1:
                                    ancestor[v][j] = ancestor[ancestor[v][j-1]][j-1]

                            The problem with the above pseudo code is that here, if the tree is always in a sorted order
                            moving down, then it will work. Because we are going bottom up (first calculating the smaller values) since
                            we are assuming that parent[i] < i.
                                It will work for trees like this:
                                            5
                                             \
                                              6
                                               \
                                                7
                                It will not work for trees like the follow:
                                            4
                                             \
                                              2
                                               \ 
                                                3
                                Because if we try to calculate for a tree like this using the above pseudocode, it will result in error
                                as we would try to calculate the ancestors for a node that has not been evaluated yet.
                                Example, let's say, we first calculate for node 2 since we are running from v = start .. end. Then, parent node of 2 is 4 but
                                since we are starting linearly, we still have not evaluated for 4th node and hence it will break.

                            
                            To sovle for it, we can use the below code:

                                for v = 0 .. N-1:
                                    ancestor[v][0] = parent[v]
                                for j = 1 .. LOG-1:
                                    for v = 0 .. N-1:
                                        ancestor[v][j] = ancestor[ancestor[v][j-1]][j-1]

                                In the above code, since we go level by level and calculate first the first ancestors and the second ancestors
                                for each node, the problem is solved which is poised by the above code.


                        Time and Space complexity for preprocessing - O(N * log(N))
                            if tree is balanced, then it is good otherwise brute force becomes a better choice.


                        
                                

            Time complexity for querying - O(Q * log(N))
            Time complexity for pre-processing - O()


            
*/



// LCA - Binary tree
/*
    Way 1: Find the path to both the given elements. Then find the point where both the element
        diverge from. That becomes the LCA. Time - O(n). Space - O(n)

    Way 2: It can be solved in linear time while finding the elements itself without the need to 
        store the path.

    Way 3: Binary lifting can be used to find the LCA in log(n) time.
        DP application.

        Recurrence relations:
            parent(node, h) = parent(parent(node, h/2), h/2) | parent[node][h] = parent[parent[node][h/2]][h/2]
            parent(node, 1) = precompute parent(node)
    
        Since, h is a large quantity, and to reduce the pre-computations we can only store
        parent at powers of 2.

        Hence,  h = 2^x
                => h/2 = 2^(x-1)
            Hence, parent at height = 2^x is defined as:
                parent[node][x] = parent[parent[node][x-1]][x-1]
            And, if x=0,
                parent[node][1] = precomputedparent[node]
        
*/


// LCA - Binary Search tree
/*  
    Time complexity - O(h) - h = height of the binary search tree
*/



// AVL tree implementation - Balanced BST
/*
    Self balancing BST.
    Balancing factor for every node in the tree should be either of the follow {-1, 0, 1}

    Balancing factor = Height of left subtree - Height of right subtree

    4 Problems:
        1. Left Left case (LL) 
            Tree:

                    20
                   / 
                 10

            Insert 5:

                    20
                   / 
                 10
                /
               5
            
            5 is inserted at it's position just as it would been inserted in a BST.
            Then traverse upwards until we find a node which is unbalanced.

            Balancing factor for 5 is 0.
            Balancing factor for 10 is 1.
            Balancing factor for 20 is 2.

            Node with value 20 is an unbalanced node as we see.
            Once we find the unbalanced node, start the traverse to our newly inserted node and note down the first 2 movements.
            Here, we see there are two Left movements for moving towards 5. 
            Hence, it is a LL problem.
            For LL problem, we do a single right rotation at node 10, the node at first movement towards node with value 5.

            After R rotation:
                    10
                   /  \ 
                  5    20


        2. Right Right case (RR)
            Tree:

                    20
                      \
                       30
            
            Insert 40:
                    
                    20
                      \
                       30
                         \
                          40

            After having inserted 40, we traverse upward to find the first unbalanced node.

            Balancing factor for 40 is 0
            Balancing factor for 30 is -1
            Balancing factor for 20 is -2

            Node with the value 20 is an unbalanced node.
            Now, we record the next two initial movements from this node towards the inserted node.
            Here the movements are two right movements.
            Hence, it is a RR problem.
            For RR problem, we do a single left rotation at node 30 to solve it.

                    30
                  /    \
                20      40

        3. Left Right case (LR)

            Tree:

                    30
                   /
                 10
            
            Insert 20:

                    30
                   /
                 10
                   \
                    20

            Balancing factor of 20 is 0
            Balancing factor of 10 is -1
            Balancing factor of 30 is 2

            Node with value 30 is an unbalanced node.
            The two initial movements towards the inserted node is Left and Right.
            Hence, it is a LR problem.
            For LR , we do Left rotatin for 10 and then Right rotation for 30

            After Left rotation for 10:
                    30
                   /
                  20
                 /
                10

            After Right rotation for 30:
                    20
                  /    \
                 10    30


        4. Right Left case (RL)

            Tree:

                    30
                       \
                        50
                    
            Insert 40:

                    30
                      \
                       50
                      /
                    40

            Balancing factor of 40 is 0
            Balancing factor of 50 is 1
            Balancing factor of 30 is -2

            Node with value 30 is an unbalanced node.
            The two initial movements towards the inserted node is Right and Left.
            Hence, it is a RL problem.
            For RL , we do Right rotatin for 50 and then Left rotation for 30

            After Right rotation for 50:
                    30
                      \
                       40
                         \
                          50

            After Left rotation for 30:
                    40
                  /    \
                 30    50


            c
          /   \
         b     d
        / \   /  \
       a  T3 T4  T5
      / \
     T1 T2

    Let's assume we inserted an item and the insertion disturbed the balancing factor of c. Then in order to identify
    the case we can consider the following:
        
    How to detect the cases:
        LL :   
            1. Balancing Factor > 1
            2. item < b.data

        LR :
            1. Balancing Factor > 1
            2. item > b.data

        RL :
            1. Balancing Factor < -1
            2. item < d.data
        
        RR : 
            1. Balancing Factor < -1
            2. item > d.data

*/


// Prime number sieves to find prime numbers in a given range
/* 
    In order to find all the prime number in the range of 1-N
        the time complexity is O(n * log(n) * log(log(n)))

    Now, if we want to find the prime numbers in a range of A-B.
        We have to first find the prime numbers in the range of 1-√B
        And, then use them to find the prime numbers in the range of A-B. Time complexity here will become:
            O((A-B) * log(B) * log(log(B)))
        Also the time complexity for finding the prime numbers from 1-√B will be:
            O(√B * log(√B) * log(log(√B)))
*/



// Sparse Table and Range minimum query
/*
    We have to answer queries asking for the minimum number in the given range.

    2, 3, 4, 5, 3, 1, 1, 3

    Brute force: O(Q*N)
    Sparse table: 
        Preprocessing: O(N*log(N))
        Query: O(Q) // Not even O(Q * log(N))

    One way can be to pre-compute the minimum for every possible range. There are N^2 range. Space and time complexity
    will be really bad for the preprocessing. Hence, not an efficient approach.

    Like in Binary lifting, we only do things for powers of 2.

    We compute a sparse matrix minima[][]
    This matrix has N rows and columns are exponents of 2 until 16(2^4)

    minima[N][log]

    Let's say for the following indices,
    0 1 2 3 4 5 6 7 8 9 10 11 12

    - If we want to compute the minima from indices 3,10 (8 elements starting from index 3)
        We can say that minima[3][3] = min( minima[3][2], minima[7][2] )        // 2^3 = 8

        Generalising the above experssion: 
            minima[i][j] = min( minima[i][j-1], minima[i+ (1<<(j-1)) ][j-1] )
            // j here is power of 2(exponent)

        We can calculate the preprocessed matrix like the following then:
            for(j=1 .. log-1):
                for(i=0; i+(1<<j)-1 < N; i++):            // This is not N-1 as we want to calculate until i .. i+some value < N (Possible present range)
                    minima[i][j] = min( minima[i][j-1], minima[i+ (1<<(j-1)) ][j-1] )
            
            We loop over j first so that we have j ready for each layer since we depend on i+(1<<(j-1)) while
                calculating for i
            Hence, preprocessing takes O(N*log(N))

        Now, query logic:
            One way is to take the range in binary representation and then find from the matrix. It takes O(logN) time.
                0 1 2 3 4 5 6 7 8 9 10 11 12
                For this let's say we want to find the minimum from 2..8

                We can say 7 is the length of the range and it's binary form is 4+2+1 - 111
                We can find minima of length 4(exponent=2) at index 2 and then find the minimum between itself and 
                    minima of length 2(exponent=1) at index 2+(1<<2) and then find the minimum between itself and 
                    minima of length 1(exponent=0) at index 6+(1<<1) and then return the minimum.
                
                This takes O(logN) time and is not the most efficient way of solving it.
            
            Second way, 
                Since we are searching for the interval, we don't really care about the overlapping sub intervals.
                For the above example, we could have taken the minima starting at 2 of length 4(exponent=2) and then
                again taken minima starting at 5 of length 4(exponent=2) and the minimum of both would have been the minimum so to speak.

                So, here lets say we have to find the minimum in the range of size 22. Then we first find the greatest power of 2 that fits 
                the range size viz 16. Hence exponent = 4
                Let's say we have been asked to give the minimum from i,j and the range size is len = j-i + 1
                    then the previous power of 2 to len is prevPow2 = log2(len)
                    then minimum = min( minima[i][prevPow2], minima[j-(1<<prevPow2)+1][prevPow2])


    Sparse tables are really useful for any range querying where we don't care if the intervals overlap.
        We can use it for minimum, maximum, gcd only if there are not any more updates.
            If there are more updates, then we need to use Segment tree.
        We cannot use it for sum and only.
    
*/



// Square root decomposition - Range querying
/*
    1. Update index i to k
    2. Query (L, R) -> sum

    Brute force for Q Queries:
        1. Update - O(1)
        2. Query - O( Q*N )
    
    For any array, the most optimised decomposition is to break the array into blocks equal to ceil(srt(N)).

    For array:
        1, 2, 6, 7, 9, 3, 1, 9
        We can create blocks of size ceil(sqrt(8)) = 3

        1, 2, 6     7, 9, 3    1, 9, x

        With each block we also need the sum of that block to be stored. For empty index, we can take sum as 0 as
        it doesn't affect the sum of any range.

        1, 2, 6     7, 9, 3     1, 9, x
        sum=9       sum=19      sum=10

        If we are asked to update index 6 to value 7 (present value is 3) Index is considered to start from 1.

        To get the block number the index lies in is 6/block size = 2
        To get the index inside the block : val = 6%3 = 0 if 0, it lies at the end of the block, or else val + (block_number-1)*block_size

        We also need to update the sum of the block which can be done in O(1)
        Hence update takes O(1)

        1, 2, 6     7, 9, 7     1, 9, x
        sum=9       sum=23      sum=10

        Query :
        Let's query from index 3 to index 7:
            Figure out which blocks contains L and R. Those 2 blocks are kept aside, and the blocks in between are summed together.
            
            Now, summing all the blocks in between is sqrt(N) time complexity since we have atmost N/(sqrt(N)) = sqrt(N) blocks.
            Now for L and R block, even if the index is at the extreme end, we would have to travel at most sqrt(N) elements inside the block.

            Hence total time complexity - O(sqrt(N) + sqrt(N) + sqrt(N))
                                        -  inbetween blocks + left extreme block + right extreme block
                                        - O(3*sqrt(N))
                                        - O(sqrt(N))


    A comparison:
        sqrt(N) > log2(N)

        An easy way to grab this, 
            is that log2(N) will be a value close to the number of (binary) digits of N, 
            while sqrt(N) will be a number that has itself half the number of digits that N has. 
                Or, to state that with an equality:
                    log2(N) = 2log2(sqrt(N))

        So you need to take the logarithm(!) of sqrt(N) to bring it down to the same order of complexity as log2(N).
        For example, for a binary number with 11 digits, 0b10000000000 (=210), the square root is 0b100000, but the logarithm is only 10.
*/
