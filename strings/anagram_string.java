// Search all anagrams 
// of a pattern in a text 
public class Anagram { 
	static final int MAX = 256; 
	
	static boolean compare(int arr1[], int arr2[]) { 
		for (int i = 0; i < MAX; i++) 
			if (arr1[i] != arr2[i]) 
				return false; 
		return true; 
	} 

	// This function search for all permutations 
	// of pat[] in txt[] 
	static void search(String pat, String txt) { 
		int M = pat.length();
		int N = txt.length();
		
		int[] countP = new int[MAX];
		int[] countT = new int[MAX];
		
		for(int i=0; i<M; i++) {
		    countP[(int)(pat.charAt(i) - 'A')]++;
		}
		
		for(int i=0; i<N; i++) {
		    char current = txt.charAt(i);
		    countT[(int)(current-'A')]++;
		    if(i >= M) {
		        countT[(int)(txt.charAt(i-M)-'A')]--;
		    }
		    
		    if(compare(countP, countT)) {
		        System.out.println("Match found at: "+ (i-M+1));
		    }
		}
	} 

	public static void main(String args[]) { 
		String txt = "BACDGABCDA"; 
		String pat = "ABCD"; 
		search(pat, txt); 
	} 
} 
