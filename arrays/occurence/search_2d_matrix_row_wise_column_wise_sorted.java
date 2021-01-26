// Search in a row wise and 
// column wise sorted matrix 

class GFG { 

	private static boolean search(int[][] mat, int target) { 
		int rows = mat.length;
		int columns = mat[0].length;
		
		int i=rows-1;
		int j=0;
		
		while(i>=0 && j<columns) {
		    int presentElement = mat[i][j];
		    if(presentElement == target)
		        return true;
		    else if(presentElement > target)
		        i=i-1;
		    else
		        j=j+1;
		}
		return false;
	} 

	public static void main(String[] args) { 
		int mat[][] = { { 10, 20, 30, 40 }, 
						{ 15, 25, 35, 45 }, 
						{ 27, 29, 37, 48 }, 
						{ 32, 33, 39, 50 } }; 

		System.out.println("Target Found: "+ search(mat, 29)); 
	} 
} 

