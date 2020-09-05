public class UniqueBST {

    public static void main(String[] args) {
        // Finding unique BSTs that can be formed using the set of nos - 10
        int n = 3;
        System.out.println(uniqueBST(n));
    }
    public static int uniqueBST(int n) {
        if(n < 2)
            return 1;
        int result[] = new int[n+1];
        result[0] = result[1] = 1;
        
        for(int i=2; i<=n; i++) {
            int res = 0;
            for(int j=0; j<i; j++) {
                int leftElements = j;
                int rightElements = i-j-1;
                res += result[leftElements] * result[rightElements];
            }
            result[i] = res;
        }
        
        return result[n];
    }
}
