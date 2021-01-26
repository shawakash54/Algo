
import java.io.*;
import java.util.Arrays;

/*
SQRT Decomposition
Original array size = n
Buckets(b) = ceil(sqrt(n))
Bucket size(c) = n/b
Last Bucket size = n%c

Time Complexity - O(b + c) 
        [Since bucket size and number of buckets are almost same]
        = O(b) 
        = O(sqrt(n))
*/

class SQRTDecomposition {
    private final int a[];
    private final long blockSums[];
    private final int sqrt;
    
    public SQRTDecomposition(int[] input) {
        this.sqrt = (int)Math.ceil(Math.sqrt(input.length));
        a = new int[this.sqrt * this.sqrt];
        System.arraycopy(input, 0, a, 0, input.length);
        this.blockSums = new long[this.sqrt];
        for(int i=0; i<this.blockSums.length; i++) {
            final int startIndex = i*this.sqrt;
            for(int j=0; j<this.sqrt; j++) {
                this.blockSums[i] += a[startIndex + j];
            }
        }
    }
    
    public void update(int index, int value) {
        final int blockIndex = index / this.sqrt;
        this.blockSums[blockIndex] += value - this.a[index];
        this.a[index] = value;
    }
    
    public long query(final int left, final int right) {
        final int startBlockIndex = left/this.sqrt;
        final int endBlockIndex = right/this.sqrt;
        
        long sum = 0;
        for(int i=startBlockIndex + 1 ; i< endBlockIndex; i++) {
            sum += this.blockSums[i];
        }
        
        final int startIndex = left % this.sqrt;
        final int endIndex = right % this.sqrt;
        
        for(int i=startIndex; i<sqrt; i++) {
            sum += this.a[startBlockIndex * this.sqrt + i];
        }
        
        for(int i=0; i <= endIndex; i++) {
            sum += this.a[endBlockIndex * this.sqrt + i];
        }
        
        return sum;
    }
    
    @Override
    public String toString() {
        return "SqrtDecomposition{\n" +
                "a=" + Arrays.toString(a) +
                ",\n blockSums=" + Arrays.toString(blockSums) +
                '}';
    }
    
	public static void main(String[] args) {
        final SQRTDecomposition sqrtDecomposition = new SQRTDecomposition(new int[]{1, 2, 6, 7, 9, 3, 1, 9});
        System.out.println(sqrtDecomposition);
        System.out.println(sqrtDecomposition.query(2, 6));
        sqrtDecomposition.update(5, 7);
        System.out.println(sqrtDecomposition);
        System.out.println(sqrtDecomposition.query(2, 6));
    }
}
