
/*
    Permutations of a given String.
    abc
    O/P:
        abc
        acb
        bac
        bca
        cab
        cba

    Why backtracking?
        Our choice: The character we choose, place and recur on
        Our constraints: None here, but at each point we have less characters to work with
        Our goal: Once we have done n placements, we have a possible permuted string.

    Time complexity - O(n*n!)
        n denotes the size of the string, we might be traversing it, cloning it, printing it
        n! - total number of possible permutations
    Space complexity - 
        O(n*n!)
            If we store each permutation in an array
        O(n)
            If we only print, as the recursion call stack will be n deep(total number of frames in the stack). 
            Tree will be n frames deep.
*/

List<String> getPermutations(String str) {
    int len = str.length();
    List<String> permutations1 = new ArrayList();
    List<String> permutations2 = new ArrayList();
    // Approach 1 - Swapping characters
    getPermutationsSwapping(str, permutations1, 0, len-1);

    // Approach 2 - Calculating left and right sequence
    getPermutationsLeftAndRightSequence(str, permutations2, "");
}

void getPermutationsSwapping(String str, ArrayList<String> permuatations, int l, int r) {
    if(l==r) {
        // End of string reached, atleast a n characters combination traversed
        permuatations.add(str);
    }else {
        for(int i=l; i<=r; i++) {
            swap(str, l, i);
            getPermutationsSwapping(str, permuatations, l+1, r);
            swap(str, l, i);
        }
    }
}

void getPermutationsLeftAndRightSequence(String str, ArrayList<String> permuatations, String answer) {
    // Here str is the pending string.
    if(str.length() == 0)
        permuatations.add(answer);
    else {
        for(int i=0; i<str.length(); i++) {
            char ch = str.charAt(i);
            String leftStr = str.substring(0, i);
            String rightStr = str.substring(i+1);
            getPermutationsLeftAndRightSequence(leftStr + rightStr, permuatations, answer + ch);
        }
    }
}


String swap(String str, int i, int j) {
    // Swapping i with jth character
    
    // Way 1
    /*
        char[] chars = str.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        
        return String.valueOf(chars);
    */

    // Way 2
    StringBuilder strBuilder = new StringBuilder(str);
    str.setCharAt(str.charAt(i));
    str.setCharAt(str.charAt(j));
    return strBuilder.toString();
}



/*
    Given a collection of numbers, return all possible permutations.
    Example: [1,2,3]
    Solution:
        [1,2,3]
        [1,3,2]
        [2,1,3]
        [2,3,1]
        [3,1,2]
        [3,2,1]
    
    There are a total of n! permutations. At the first spot we will have n choice, at the second spot (n-1) choices.. and so on
    Hence, total permuatations = n * (n-1) * (n-2) * (n-3) ... 1 = n!
*/

List<List<Integer>> getPermutations(List<Integer> collection) {
    List<List<Integer>> permutations = new ArrayList();
    int len = collection.size();
    getPermutationsSwapping(collection, 0, len, permuatations);
}

void getPermutationsSwapping(List<Integer> collection, int start, int end, List<List<Integer>> permutations) {
    if(start == end) {
        // Cloning it as array list are mutable. 
        // String were immutable hence the cloning was not required in the above case
        permutations.add(new ArrayList<Integer>(collection));
    }else {
        for(int i=start, i<=end; i++) {
            swap(collection, i, start);
            getPermutationsSwapping(collection, start+1, end, permutations);
            swap(collection, i, start);
        }
    }
}




// ===========================================================================================================================


// Palindrome permutations


/*
    Number of palindrome permutations
    Input : str = "gfgf"
    Output : 2
    There are two palindromic 
    permutations fggf and gffg

    Input : str = "abc"
    Output : 0

    Pointers:
    - A string is palindromic if there are even number of occurence of each character and there
        might be an odd occurence of a single character.
    - One occurence of the odd character will always go the middle.
    - Half of the characters in the string can decide the result.

    For a string like aabbccd, the total count of palindromic strings are 3! since there are 3 characters with even occurence
    and are atmost 2.

    For a string like aaaaaabbbb, total count of palindromic strings are 5!/(3! * 2!)
*/

long factorial(int num) {
    int res = 1;
    for(int i=2; i<=num; i++)
        res *= i;
    return res;
}

int countPalinPermutations(String str) {
    int len = str.length();
    int[] freq = new int[256];

    // Count frequencies of all characters
    for(int i=0; i<len; i++) 
        freq[str.charAt(i)]++;
    
    // Since half of the characters
    // decide count of palindromic
    // permutations, we take (n/2)!
    long res = factorial(n/2);

    // flag to detect second odd frequency if present
    boolean oddFreq = false;
    for(int i=0; i<256; i++) {

        int presentFreq = freq[i];
        if(presentFreq % 2 != 0) {
            if(oddFreq) {
                // second occurence of odd frequence found
                return 0;
            }
            oddFreq = true;
        }
        res /= factorial(presentFreq/2);
    }
    return (int)res;
}




/*

    Print all palindrome permutations of a string
    Input:  str = "aabcb"
    Output: abcba bacab

    nput:  str = "aabbcadad"
    Output: aabdcdbaa aadbcbdaa abadcdaba
            abdacadba adabcbada adbacabda
            baadcdaab badacadab bdaacaadb
            daabcbaad dabacabad dbaacaabd


    Generation of palindrome can be done by following steps : 
        First we need to check whether letters of string can make a palindrome or not, if not then return.
        After above checking we can make half part of first palindrome string (lexicographically smallest) 
            by taking half frequency of each letter of the given string.
        Now traverse through all possible permutation of this half string and each time add reverse of 
            this part at the end and add odd frequency character in mid between if string is of odd length, 
            for making the palindrome.

*/

void printAllPossiblePalindromes(String str) {
    int[] frequencyArray = createArrayUnicode();
    findFrequencyOfEachCharacter(frequencyArray, str);
    
    int oddFrequencyOccurence = 0;
    for(int i=0; i<frequencyArray.length; i++) {
        if(frequencyArray[i] % 2 != 0)
            oddFrequencyOccurence++;
    }

    // If more than 1, odd frequency character is present, no possible palindrome can be formed
    if(oddFrequencyOccurence > 1) {
        System.out.println("No possible palindromes");
        return;
    }

    Pair<String, char> halfStringResults = findHalfString(frequencyArray);
    String halfStr = halfStringResults.getFirst();
    char oddCharacter = halfStringResults.getSecond();

    printPossiblePalindromes(halfStr, oddCharacter, 0, halfStr.length()-1);
}

int[] createArrayUnicode(){
    int[] arr = new int[256];
    return arr;
}

void findFrequencyOfEachCharacter(int[] unicodeArray, String str) {
    for(int i=0; i<str.length(); i++) {
        unicodeArray[str.charAt(i)]++;
    }
}

Pair<String, char> findHalfString(int[] frequencyArray) {
    String halfStr = "";
    char oddCharacter = '';
    for(int i=0; i<frequencyArray.length; i++) {
        if(frequencyArray[i] % 2 == 0) {
            String temp = Character.toString((char)i);
            temp.repeat((frequencyArray[i]/2) - 1);
            halfStr += temp;
        }
        oddCharacter = Character.toString((char)i);
    }
    return Pair.of(halfStr, oddCharacter);
}

void printPossiblePalindromes(String str, char oddCharacter, int start, int end) {
    if(start == end) {
        // one permutation found
        // Priting the respective palindromic permutation
        System.out.println(str + Character.toString(oddCharacter) + new StringBuilder(str).reverse().toString());
    }

    for(int i=start, i<=end; i++) {
        swap(str, i, start);
        printPossiblePalindromes(str, oddCharacter, start+1, end);
        swap(str, i, start);
    }
}


/*
    Next higher palindromic number using the same set of digits
    Given a palindromic number num having n number of digits. 
    The problem is to find the smallest palindromic number greater than num using the same set of digits as in num. 
    If no such number can be formed then print “Not Possible”. 
    The number could be very large and may or may not even fit into long long int.

    Input : 4697557964
    Output :  4756996574

    Input : 543212345
    Output : Not Possible
*/

void nextHighestPalindrome(String str) {
    // Since there will be many swapping happening in the string, owing to it's immutability, it
    // is better to use char[] array

    char[] chars = str.toCharArray();
    printNextHighestPalindrome(chars);
}

void printNextHighestPalindrome(char[] str) {
    int len = str.length;
    // If length < 3, no next highest palindrome possible
    if(len <= 3) {
        System.out.println("Not possible");
        return;
    }

    // mid is the last digit in the first half of the palindrome string
    int mid = str.length/2 - 1;

    // when traversing from mid to start of the string, nextLowestNumberIndex denotes the element which broke the ascending order
    // when traversing from nextLowestNumberIndex + 1 to mid, nextLowestNumberIndexGreaterThanNextLowestNumberIndex denotes the element
    // which is higher in valud than nextLowestNumberIndex's element but lowest in value
    int nextLowestNumberIndex, nextLowestNumberIndexGreaterThanNextLowestNumberIndex;

    for(nextLowestNumberIndex=mid-1; nextLowestNumberIndex>=0; nextLowestNumberIndex-- ) {
        // Start from the (mid-1)th digit and find the first digit that is smaller than the digit next to it.
        if(str[nextLowestNumberIndex] > str[nextLowestNumberIndex+1])
            break;
    }

    if(nextLowestNumberIndex < 0) {
        // if no such digit found, then all the characters are in descending order and hence no next number is possible and hence
        // there cannot be a greater palindromic number with same set of digits
        System.out.println("Not possible");
        return;
    }

    // Find the smallest digit on right side of nextLowestNumberIndexth digit which is greater than str[nextLowestNumberIndex] up to index 'mid'
    nextLowestNumberIndexGreaterThanNextLowestNumberIndex = nextLowestNumberIndex + 1;
    for(int j=nextLowestNumberIndex+2; j <= mid; j++) {
        if(str[j] > str[nextLowestNumberIndex] && str[j] < str[nextLowestNumberIndexGreaterThanNextLowestNumberIndex])
            nextLowestNumberIndexGreaterThanNextLowestNumberIndex = j;
    }

    swap(str, nextLowestNumberIndex, nextLowestNumberIndexGreaterThanNextLowestNumberIndex);
    // as the number is a palindrome, the same swap of digits should be performed in the 2nd half of the string
    swap(str, len - nextLowestNumberIndex - 1, len - nextLowestNumberIndexGreaterThanNextLowestNumberIndex - 1);

    // reversing the string in the range of nextLowestNumberIndex + 1 to mid
    reverse(chars, nextLowestNumberIndex + 1, mid);

    if(len % 2 == 0) {
        // if the string has even characters, then reverse characters in the range of 
        // mid+1 to (len - nextLowestNumberIndex - 1)
        reverse(chars, mid+1, len-nextLowestNumberIndex-2);
    }else {
        // reverse the characters in string in the range of mid+2 to (len - nextLowestNumberIndex - 1)
        reverse(chars, mid+2, len-nextLowestNumberIndex-2);
    }
    
    // Next higher palindrome
    System.out.println(String.valueOf(chars));
}

void reverse(char[] chars, int i, int j) {
    while( i < j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        i++; j--;
    }
}

void swap(char[] chars, int i, int j) {
    char temp = chars[i];
    chars[i] = chars[j];
    chars[j] = temp;
}



/*
    Permute a string by changing case
    Input : ab
    Output : AB Ab ab aB

    Input : ABC
    Output : abc Abc aBc ABc abC AbC aBC ABC

    At each position, we have two options, either select the lower / capital version of the character. Hence,
    we have a total of 2^n permutations.

    This can be solved using bit wise operation.
    i >> j means shifting j bits right in i's binary representation.
*/


void permute(String input) {
    int len = input.length();
    String lowerCase = input.toLowerCase();

    for(int i=0; i < 1<<len; i++) {
        StringBuilder strBuilder = new StringBuilder();
        for(int j=0; j<len; j++) {
            // If j-th bit is set, we convert it to upper case
            if((i>>j & 1) == 1)
                strBuilder.append(lowerCase.substring(j, j+1).toUpperCase());
            else
                strBuilder.append(lowerCase.charAt(j));
        }
        System.out.println(strBuilder.toString());
    }
}


// ===========================================================================================================================
