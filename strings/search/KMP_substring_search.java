// Implementation of KMP pattern 
// searching algorithm 

class KMP_String_Matching { 
	void KMPSearch(String pat, String txt) { 
		int M = pat.length();
		int N = txt.length();
		// O(M+N)
		
		int[] lps = new int[M];
		computeLPS(pat, M, lps);
		
		// O(N)
		int i=0;
		int j=0;
		while(i < N) {
		    if(txt.charAt(i) == pat.charAt(j)) {
		        // there's a match
		        i++;
		        j++;
		    }
		    if(j == M) {
		        // a pattern found
		        System.out.println("Pattern found at index: " + (i-j));
		        j = lps[j-1];
		    }else 
		    if(i < N && txt.charAt(i) != pat.charAt(j)) {
		        // Mismatch after j matches
		        if(j == 0)
		            i++;
		        else
		            j=lps[j-1];
		    }
		}
	} 

	void computeLPS(String pat, int M, int lps[]) { 
	    
	    // O(M)
		lps[0] = 0;
		int length = 0;
		int i = 1;
		
		while(i < M) {
		    if(pat.charAt(i) == pat.charAt(length)) {
		        // there's a match
		        length++;
		        lps[i] = length;
		        i++;
		    }else {
		        if(length == 0) {
		            lps[i] = 0;
		            i++;
		        }else {
		            length = lps[length-1];
		        }
		    }
		}
	} 

	public static void main(String args[]) { 
		String txt = "ABABDABACDABABCABAB"; 
		String pat = "ABABCABAB"; 
		new KMP_String_Matching().KMPSearch(pat, txt); 
	} 
} 
