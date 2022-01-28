
/*
    Heap
        It is a balanced binary tree i.e. the lowest level or the level above the lowest level must all have leaves.
        In other words, the tree is completely filled at all levels except possibly the lowest, 
            which is filled from the left upto a point.
        It's a nearly complete binary tree.

        In an array, given an index i,
            parent(i) = [i/2]
            left(i)   = 2*i
            right(i)  = 2*i + 1

        Types of heap:
            Min Heap: For every node i other than the root, A[parent(i)] <= A[i]
                Smallest element in a min heap is at the root.

            Max Heap: For every node i other than the root, A[parent(i)] >= A[i]
                Largest element in a max heap is at the root.
        
        Height of a heap:
            Height of a node: Number of edges on the longest simple downward path from the node to the leaf.
            Height of a heap is going to be the height of the root.

        Minimum number of nodes in a heap of height h is 2^h
        so, if h = 3, minimum number of nodes = 2^3 = 8 nodes

                            16
                        /       \
                       14       10
                     /   \     /  \
                    8     7   9    3
                   /
                  2
    
        Maximum number of nodes = Full binary tree = 2^(h+1) - 1
        so, if h=3, maximum number of nodes is 2^(3+1) - 1 = 15
                           15
                        /       \
                       14       7
                     /   \     /  \
                    13    10  6    3
                   /\     /\  /\   /\
                  11 12  8 9 4  5 1  2

        Number of nodes(n) in a binary heap is between the range of : 
            2^h <= n <= 2^(h+1) - 1
            OR,
            2^h <= n < 2^(h+1)
            OR,
            h <= log2(n) < h+1
        
        So, in Theta notation, h=theta(log2(n))
        This is why most operations in a heap run in proportion to the height , hence it is log(n) time complexity.


        Max Heapify:
            Procedure to maintain the max-heap property.
            It assumes that both the sub-trees rooted at left(i) and right(i) are max heaps already, only that A[i]
                might be smaller than its children, thus violating max heap property.

            Max heapify lets the value of A[i] float down in the max heap so that the sub-tree still obeys the max heap
                property.
            Time compexity - O(h)

        
        Time complexity of building a max heap:
            for i = length/2; i >= 1; i--
                MAX-HEAPIFT(i)
            O(n)
            The time complexity is not O(nlogn).

        
        Heap Sort
            HEAP-SORT(A)
                BUILD-MAX-HEAP(A)
                for i=length; i>1; i--
                    swap(A[1], A[i])
                    A.heap_size--;
                    BUILD-MAX-HEAP(A);

            Time complexity - O(nlog(n))
            


*/

// Implement a binary heap - priority queue
/*
    Heap is a data structure, not a abstract data type.
    Priority Queue is a abstract data type.
    Heap is an implementation of it.

    Just like, Stack and Queue are all abstract data types.
    Implementations of Stack and Queue are LinkedList, etc.

    Abstract data type
        It is a set of behavior that we define for a data type to have so that we can even call it a data type.
        Like for Stack, we need to support push and pop.
             for Queue, we need to support enqueue and dequeue.
            Abstract data types are when we support a core API. It can then be called data types.
        Implementation of this are the data structures.
    
    Priority Queue behaviors
        isEmpty()
        insertWithPriority()
        pullHighestPriorityItem()
        peek()
        
        Heaps are an implementation of the above.

    Types of heap:
        Min Heap - We will constant time access to the minimum element in the heap.
            5
          /   \
         7     6
        / \   / \
       10 15 17 12

        Max Heap - We will constant time access to the maximum element in the heap.
            9
          /   \
         8     6
        / \   /
       5   2 1 

       A Binary heap is always a complete Binary tree.
*/




// Heap Sort
/*
    Time complexity - O(nlogn)
    Space complexity - O(1)

    Heap sort is the like the best world combine of Merge sort and insertion sort.
    

*/



// Median of a number system
// Sliding window median
// Maximise capital - two heaps