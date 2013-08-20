package uva.suffixarray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/* 760
 * 1. if read string line by line, take care if can accept blank string
   if true --> readLine() rather than readNext()
   
2. in String Processing, take care when duplicate result !!!!!
  */
public class DNASequence {
	
	public static void main(String[] args) throws Exception {
		DNASequence main = new DNASequence();
		main.run();
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     int[] res = new int[100010];
	     
	     while (sc.hasNext()) {
	    	 String a = sc.nextLine();
	    	 String b = sc.nextLine();
	    	 if (sc.hasNext()) sc.nextLine();
	    	 
	    	 String con = a + "." + b;
	    	 SuffixArray s = new SuffixArray(con);
	    	 int[] SA = s.SA;
	    	 int[] LCP = s.LCP;
	    	 int max = 0;
	    	 int seg1, seg2;
	    	 int count = 0;
	    	
	    	 for (int i = 1; i < LCP.length; i++) {
	    		 if (SA[i-1] < a.length()) seg1 = 1;	// this suffix contains both string
	    		 else seg1 = 2;							// this suffix contains only second string
	    		 if (SA[i] < a.length()) seg2 = 1;
	    		 else seg2 = 2;
	    		 if (seg1 == seg2) continue;		// so, prefix will contains chars in one string
	    		 if (LCP[i] == max && LCP[i] > 0) res[count++] = i;
	    		 else if (LCP[i] > max) {
	    			 res[0] = i;
	    			 count = 1;
	    			 max = LCP[i];
	    		 }
	    	 }
	    	 
	    	 if (count == 0) System.out.println("No common sequence.");
	    	 else {
	    		 // print all results
	    		 // use those variables to avoid duplicate
	    		 String str = "";
	    		 StringBuilder buff;
	    		 for (int i = 0; i < count; i++) {
	    			 buff = new StringBuilder();
	    			 int loc = SA[res[i]];
	    			 int length = LCP[res[i]];
	    			 //System.out.println("loc: " + loc + "\tlength " + LCP[loc]);
	    			 for (int j = 0; j < length; j++) {
	    				 buff.append(con.charAt(loc + j));
	    			 }
	    			 if (buff.toString().equals(str)) continue;
	    			 else str = buff.toString();
	    			 System.out.println(str);
	    		 }
	    	 }
	    	 if (sc.hasNextLine()) System.out.println();
	    	 
	     }
	     
	     pr.close();
	     sc.close();
	}
	
	static class SuffixArray {
		  Scanner scan;
		  char T[];                        // the input string, up to 100K characters
		  int n;                                             // the length of input string

		  int[] RA, tempRA;             // rank array and temporary rank array
		  int[] SA, tempSA;         // suffix array and temporary suffix array
		  int[] c;                                         // for counting/radix sort

		  char P[];     // the pattern string (for string matching)
		  int m;             // the length of pattern string

		  int[] Phi;    // for computing longest common prefix
		  int[] PLCP;
		  int[] LCP;    // LCP[i] stores the LCP between previous suffix "T + SA[i-1]" and current suffix "T + SA[i]"

		  public SuffixArray(String s) {
			  T = s.toCharArray();
			  n = s.length();
			  int MAX_N = 100010;
			  c = new int[MAX_N];
			  RA = new int[MAX_N];
			  tempRA = new int[MAX_N];
			  SA = new int[MAX_N];
			  tempSA = new int[MAX_N];
			  Phi = new int[MAX_N];
			  PLCP = new int[MAX_N];
			  LCP = new int[MAX_N];

			  constructSA();   
			  computeLCP();  
			  
		  }
		  void countingSort(int k) {
		    int i, sum, maxi = Math.max(300, n);   // up to 255 ASCII chars or length of n
		    for (i = 0; i < 100010; i++) c[i] = 0;                // clear frequency table
		    for (i = 0; i < n; i++)                    // count the frequency of each rank
		      c[i + k < n ? RA[i + k] : 0]++;
		    for (i = sum = 0; i < maxi; i++) {
		      int t = c[i]; c[i] = sum; sum += t;
		    }
		    for (i = 0; i < n; i++)               // shuffle the suffix array if necessary
		      tempSA[c[SA[i] + k < n ? RA[SA[i] + k] : 0]++] = SA[i];
		    for (i = 0; i < n; i++)                          // update the suffix array SA
		      SA[i] = tempSA[i];
		  }

		  void constructSA() {              // this version can go up to 100000 characters
		    int i, k, r;
		    for (i = 0; i < n; i++) RA[i] = T[i];                      // initial rankings
		    for (i = 0; i < n; i++) SA[i] = i;          // initial SA: {0, 1, 2, ..., n-1}
		    for (k = 1; k < n; k <<= 1) {            // repeat sorting process log n times
		      countingSort(k);       // actually radix sort: sort based on the second item
		      countingSort(0);               // then (stable) sort based on the first item
		      tempRA[SA[0]] = r = 0;                  // re-ranking; start from rank r = 0
		      for (i = 1; i < n; i++)                         // compare adjacent suffices
		        tempRA[SA[i]] =      // if same pair => same rank r; otherwise, increase r
		          (RA[SA[i]] == RA[SA[i-1]] && RA[SA[i]+k] == RA[SA[i-1]+k]) ? r : ++r;
		      for (i = 0; i < n; i++)                          // update the rank array RA
		        RA[i] = tempRA[i];
		  } }

		  void computeLCP() {
		    int i, L;
		    Phi[SA[0]] = -1;                                              // default value
		    for (i = 1; i < n; i++)                                 // compute Phi in O(n)
		      Phi[SA[i]] = SA[i-1];         // remember which suffix is behind this suffix
		    for (i = L = 0; i < n; i++) {                  // compute Permuted LCP in O(n)
		      if (Phi[i] == -1) { PLCP[i] = 0; continue; }                 // special case
		      while (i + L < T.length && Phi[i] + L < T.length && T[i + L] == T[Phi[i] + L]) L++; // L will be increased max n times
		      PLCP[i] = L;
		      L = Math.max(L-1, 0);                          // L will be decreased max n times
		    }
		    for (i = 1; i < n; i++)                                 // compute LCP in O(n)
		      LCP[i] = PLCP[SA[i]];   // put the permuted LCP back to the correct position
		  }

		  int strncmp(char[] a, int i, char[] b, int j, int n){
		    for (int k=0; i+k < a.length && j+k < b.length; k++){
		      if (a[i+k] != b[j+k]) return a[i+k] - b[j+k];
		    }
		    return 0;
		  }

		  int owner(int idx) { return (idx < n-m-1) ? 1 : 2; }

		}

}

