
import java.io.*;

class GFG {
    private int[] createSegmentTree(int[] input){
        int size = input.length;
        int segmentTreeSize = (int)(Math.pow(2, Math.ceil(this.log(size, 2))));
        int[] segmentTree = new int[segmentTreeSize*2 -1];
        
        this.buildSegmentTree(segmentTree, input, 0, 0, input.length-1);
        return segmentTree;
    }
    
    private void buildSegmentTree(int[] segmentTree, int[] input, int pos, int low, int high){
        if(low == high){
            segmentTree[pos] = input[low];
            return;
        }
        int mid = (low+high)/2;
        buildSegmentTree(segmentTree, input, 2*pos + 1, low, mid);
        buildSegmentTree(segmentTree, input, 2*pos + 2, mid+1, high);
        segmentTree[pos] = Math.min(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
    }

    private double log(int x, int base){
        return  (Math.log(x) / Math.log(base));
    }
    
    private void display_segment_tree(int[] tree){
        for(int i: tree)
            System.out.print(""+i+"-->");
    }
    
    private int rangeMinimumQuery(int[] segmentTree, int qlow, int qhigh, int len){
        return this.getRangeMinimumQuery(segmentTree, qlow, qhigh, 0, len-1, 0);
    }
    
    private int getRangeMinimumQuery(int[] segmentTree, int qlow, int qhigh, int low, int high, int pos){
        // Complete Overlap
        if(qlow <= low && qhigh >= high)
            return segmentTree[pos];
        
        // No Overlap
        if(qlow > high || qhigh < low)
            return Integer.MAX_VALUE;
            
        int mid = (low+high)/2;
        return Math.min(getRangeMinimumQuery(segmentTree, qlow, qhigh, low, mid, 2*pos + 1), getRangeMinimumQuery(segmentTree, qlow, qhigh, mid+1, high, 2*pos + 2));
    }
    
    private void updateSegmentTree(int[] input, int[] segmentTree, int index, int delta){
        input[index] += delta;
        this.updateSegmentTreeRange(segmentTree, index, delta, 0, input.length - 1, 0);
    }
    
    private void updateSegmentTreeRange(int[] segmentTree, int index, int delta, int low, int high, int pos){
        // Nothing to update - no overlap
        if(index < low || index > high )    
            return;
        
        // Updating the leaf node
        if(low == high) {
            segmentTree[pos] += delta;
            return;
        }
        
        int mid = (low + high)/2;
        updateSegmentTreeRange(segmentTree, index, delta, low, mid, 2*pos + 1);
        updateSegmentTreeRange(segmentTree, index, delta, mid+1, high, 2*pos + 2);
        segmentTree[pos] = Math.min(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
        return;
    }
    
    private void updateSegmentTreeRange(int[] input, int[] segmentTree, int lrange, int hrange, int delta) {
        for(int i=lrange; i<=hrange; i++)
            input[i] += delta;
        this.updateSegmentTreeRangeRecr(segmentTree, lrange, hrange, delta, 0, input.length - 1, 0);
    }
    
    private void updateSegmentTreeRangeRecr(int[] segmentTree, int lrange, int hrange, int delta, int low, int high, int pos) {
        if(low > high || lrange > high || hrange < low)
            return;
        
        if(low==high) {
            segmentTree[pos] += delta;
            return;
        }
        
        int mid = (low+high)/2;
        updateSegmentTreeRangeRecr(segmentTree, lrange, hrange, delta, low, mid, 2*pos +1);
        updateSegmentTreeRangeRecr(segmentTree, lrange, hrange, delta, mid+1, high, 2*pos +2);
        segmentTree[pos] = Math.min(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
    }
    
    private void updateSegmentTreeRangeLazy(int[] input, int[] segmentTree, int[] lazy, int startRange, int endRange, int delta){
        for(int i=startRange; i<=endRange; i++)
            input[i] += delta;
        this.updateSegmentTreeRangeLazyRec(segmentTree, lazy, startRange, endRange, delta, 0, input.length - 1, 0);
    }
    
    private void updateSegmentTreeRangeLazyRec(int[] segmentTree, int[] lazy, int startRange, int endRange, int delta, int low, int high, int pos){
        if(low > high)
            return;
        
        if(lazy[pos] != 0){
            segmentTree[pos] += lazy[pos];
            if(low!=high){ // they are not leaf nodes
                lazy[2*pos + 1] += lazy[pos];
                lazy[2*pos + 2] += lazy[pos];
            }
            lazy[pos] = 0;
        }
        
        // no overlap
        if(startRange > high || endRange < low) {
            return;
        }
        
        // total overlap
        if(startRange <= low && endRange >= high){
            segmentTree[pos] += delta;
            if(low != high){
                lazy[2*pos + 1] += delta;
                lazy[2*pos + 2] += delta;
            }
            return;
        }
        
        // partial overlap
        int mid = (low + high)/2;
        updateSegmentTreeRangeLazyRec(segmentTree, lazy, startRange, endRange, delta, low, mid, 2*pos +1);
        updateSegmentTreeRangeLazyRec(segmentTree, lazy, startRange, endRange, delta, mid+1, high, 2*pos +2);
        
        segmentTree[pos] = Math.min(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
        return;
        
    }
    
    private int rangeMinimumQueryLazy(int[] segmentTree, int[] lazy, int sRange, int eRange, int len ){
        return this.rangeMinimumQueryLazyRec(segmentTree, lazy, sRange, eRange, 0, len-1, 0);
    }
    
    private int rangeMinimumQueryLazyRec(int[] segmentTree, int[] lazy, int sRange, int eRange, int low, int high, int pos){
        if(low > high)
            return Integer.MAX_VALUE;
        
        if(lazy[pos] != 0){
            segmentTree[pos] += lazy[pos];
            if(low != high) { // not a leaf node
                lazy[2*pos + 1] = lazy[pos];
                lazy[2*pos + 2] = lazy[pos];
            }
            lazy[pos] = 0;
        }
        
        // no overlap
        if(sRange > high || eRange < low)
            return Integer.MAX_VALUE;
        
        // total overlap
        if(sRange <= low && eRange >= high)
            return segmentTree[pos];
            
        // partial overlap
        int mid = (low + high)/2;
        return Math.min(
            rangeMinimumQueryLazyRec(segmentTree, lazy, sRange, eRange, low, mid, 2*pos + 1),
            rangeMinimumQueryLazyRec(segmentTree, lazy, sRange, eRange, mid+1, high, 2*pos + 2)
            );
    }
    
    
	public static void main (String[] args) {
		GFG gfg = new GFG();
		int input[] = {0,3,4,2,1,6,-1};
		int segmentTree[] = gfg.createSegmentTree(input);
		// `gfg.display_segment_tree(segmentTree);
		System.out.println(gfg.rangeMinimumQuery(segmentTree, 0, 3, input.length));
		System.out.println(gfg.rangeMinimumQuery(segmentTree, 1, 5, input.length));
		System.out.println(gfg.rangeMinimumQuery(segmentTree, 1, 6, input.length));
		gfg.updateSegmentTree(input, segmentTree, 2, 1);
		System.out.println(gfg.rangeMinimumQuery(segmentTree, 1, 3, input.length));
		gfg.updateSegmentTreeRange(input, segmentTree, 3, 5, -2);
		System.out.println(gfg.rangeMinimumQuery(segmentTree, 5, 6, input.length));
		System.out.println(gfg.rangeMinimumQuery(segmentTree, 0, 3, input.length));
		
		
		int input1[] = {-1,2,4,1,7,1,3,2};
        int segTree1[] = gfg.createSegmentTree(input1);
        int lazy1[] =  new int[segTree1.length];
        gfg.updateSegmentTreeRangeLazy(input1, segTree1, lazy1, 0, 3, 1);
        gfg.updateSegmentTreeRangeLazy(input1, segTree1, lazy1, 0, 0, 2);
        System.out.println(gfg.rangeMinimumQueryLazy(segTree1, lazy1, 5, 6, input1.length));
	}
}
