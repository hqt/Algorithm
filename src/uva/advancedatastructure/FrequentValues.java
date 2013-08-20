package uva.advancedatastructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FrequentValues {
	
	public static void main(String[] args) throws Exception {
		FrequentValues main = new FrequentValues();
		main.run();
	}

	public static final int SIZE = 100000;
	public static int[] input = new int[SIZE + 10];			// input array
	// Using Direct Adress Table
	public static int[] freq = new int[2 * SIZE + 100];	// count the frequency of this numbers in list
	public static int[] start = new int[2 * SIZE + 100];	// first starting location of number in list
	
	public void run() throws Exception {
		
		/*Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));*/
		
		InputReader sc = null;
		
		sc = new InputReader(System.in);
		sc = new InputReader(new FileInputStream(new File("input.txt")));
		StringBuilder out = new StringBuilder();
	     
	     while (true) {
	    	 int n = sc.nextInt();
	    	 if (n == 0) break;
	    	 int query = sc.nextInt();
	    	 Arrays.fill(freq, 0);
	    	 Arrays.fill(start, 0);
	    	 input[0] = SIZE*2 + 10000;
	    	 for (int i = 1; i <= n; i++) {
	    		 input[i] = sc.nextInt() + SIZE;
	    		 if (input[i-1] != input[i]) start[input[i]] = i;
	    		 if (start[input[i]] != 0) freq[input[i]]++;
	    	 }
	    	 
	    	 // building segment tree
	    	 SegmentTree tree = new SegmentTree(n);
	    	 
	    	 for (int i = 0; i < query; i++) {
	    		 int u = sc.nextInt();
	    		 int v = sc.nextInt();
	    		 
	    		 // normal case
	    		 if (input[u] == input[v]) {
	    			// System.out.println(v - u + 1);
	    			 out.append(v-u+1 + "\n");
	    			 continue;
	    		 }
	    		 
	    		 // input[u] != input[v]
	    		 // we filter into three segment
	    		 // first segment : from u to the end of sequence "type u"
	    		 // second segment : from start of element "type v" to type v
	    		 // third segment : all elements again in range (u, v) that "whole" range.
	    		 // third segment we can use segment tree to query it
	    		 int val1 = freq[input[u]] - (u - start[input[u]]);
	    		 int val2 = v - start[input[v]] + 1;
	    		 val2 = Math.max(val1, val2);
	    		 int left = start[input[u]] + freq[input[u]];
	    		 int right = start[input[v]]-1;
	    		 // from left to right is whole range 
	    		 int val3 = (left <= right) ? tree.rmq(1, 1, n, left, right) : 0;
	    		 val3 = Math.max(val2, val3);
	    		 //System.out.println(val3);
	    		 out.append(val3 + "\n");
	    	 }
	     }
	     
	     System.out.print(out);

	}
	
	public static class SegmentTree {         // the segment tree is stored like a heap array

		private int[] st;
		private int n;
		private int left (int p) { return p << 1; } // same as binary heap operations
		private int right(int p) { return (p << 1) + 1; }

		private void build(int p, int L, int R) {
			if (L == R) {                        // as L == R, either one is fine
				st[p] = freq[input[L]];                                        
			}
			else {                                // recursively compute the values
				build(left(p), L, (L + R) / 2);
				build(right(p), (L + R) / 2 + 1, R);
				int p1 = st[left(p)], p2 = st[right(p)];
				st[p] = Math.max(p1, p2);
			}
		}

		// O(log n) for each query
		public int rmq(int p, int L, int R, int i, int j) {          
			// current segment outside query range
			if (i >  R || j <  L) {
				return -1; 
			}
			
			// inside query range
			if (L >= i && R <= j) {
				return st[p];               
			}

			// compute the min position in the left and right part of the interval
			int p1 = rmq(left(p), L, (L+R) / 2, i, j);
			int p2 = rmq(right(p), (L+R) / 2 + 1, R, i, j);

			// if we try to access segment outside query
			if (p1 == -1) return p2;   
			if (p2 == -1) return p1;

			return Math.max(p1, p2);
		}          

		public int rmq(int i, int j)
		{ return rmq(1, 0, n - 1, i, j); } // overloading
		
		public SegmentTree(int n) {
			st =  new int[input.length * 4];
			// p must be 1. (1,n) : must be same with rmq
			build(1, 1, n);
		}

	}

	class InputReader {
	    public BufferedReader reader;
	    public StringTokenizer tokenizer;

	    public InputReader(InputStream stream) {
	        reader = new BufferedReader(new InputStreamReader(stream));
	        tokenizer = null;
	    }

	    public String next() {
	        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
	            try {
	                tokenizer = new StringTokenizer(reader.readLine());
	            } catch (IOException e) {
	                throw new RuntimeException(e);
	            }
	        }
	        return tokenizer.nextToken();
	    }

	    public int nextInt() {
	        return Integer.parseInt(next());
	    }
	    
	    public double nextDouble() {
	    	return Double.parseDouble(next());
	    }
	    
	    public float nextFloat() {
	    	return Float.parseFloat(next());
	    }
	    
	    public long nextLong() {
	    	return Long.parseLong(next());
	    }
	    
	}
}


