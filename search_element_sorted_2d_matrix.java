// Find whether 
// a given element is present 
// in the given sorted 2-D matrix 

public class GFG { 
    
    public static int rowNumber(int n, int columns) {
        return n/columns;
    }
    
    public static int columnNumber(int n, int columns) {
        return n%columns;
    }

	  static boolean searchMatrix(int matrix[][], int target) { 
      int rows = matrix.length;
      int columns = matrix[0].length;

      int left = 0, right = rows*columns-1;
      while(left <= right) {
          int mid = left + (right-left)/2;
          int midElement = matrix[rowNumber(mid, columns)][columnNumber(mid, columns)];
          //  System.out.println("Mid: "+mid);
          //  System.out.println("Mid Element: "+midElement);
          if( midElement == target)
              return true;
          else if(midElement > target) {
              right = mid-1;
          }else {
              left = mid+1;
          }
      }
      return false;
    } 

    public static void main(String args[]) { 
      int matrix[][] = { { 1, 3, 5, 7 }, 
              { 10, 11, 16, 20 }, 
              { 23, 30, 34, 50 } }; 
      int target = 3; 
      if (searchMatrix(matrix, target)) { 
        System.out.println("Found"); 
      } 
      else { 
        System.out.println("Not found"); 
      } 
    } 
} 

