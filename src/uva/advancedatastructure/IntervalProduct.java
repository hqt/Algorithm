package uva.advancedatastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * UVA 12532
 * Using Fenwick Tree
 * 
 * To problem:
 *          In this problem, all we care about is the number
 *          of negative values and the number of 0 value.
 *          For a range [l..r], P[l..r] = 0 if it contains
 *          any 0 value, or else P[l..r] = '-' when the number
 *          of negative values is odd and vice versa. Hence this is
 *          a RSQ problem of finding the number of negative values
 *          and 0 in a given range.
 *          
 * We will using two tree for this problem : one tree is check product is zeros ?
 * and other, to check product is negative ?
 */
public class IntervalProduct {
	
	public static void main(String[] args) throws Exception {
		IntervalProduct main = new IntervalProduct();
		main.run();
	}

	public static String occurs;
	public void run() throws Exception {

		PrintWriter pr = null;
		pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(System.in));
		br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt"))));
		
	    String line = null;
	    String[] tokens = null;
		
	    while ((line = br.readLine()) != null && !line.trim().equals("")) {
			 tokens = line.split(" ");
			 int n = Integer.parseInt(tokens[0]);
	    	 int k = Integer.parseInt(tokens[1]);
	    	 
	    	 FenwickTree negativeTree = new FenwickTree(n);
	    	 FenwickTree zerosTree = new FenwickTree(n);
	    	 
	    	 tokens = br.readLine().split(" ");
	    	 for (int i = 1; i <= n; i++) {
	    		 int num = Integer.parseInt(tokens[i-1]);
	    		 if (num < 0) negativeTree.change(i, 1);
	    		 else if (num == 0) zerosTree.change(i, 1);
	    	 }
	    	 
	    	 for (int i = 0; i < k; i++) {
	    		 tokens = br.readLine().split(" ");
	    		 char type = tokens[0].charAt(0);
	    		 int u = Integer.parseInt(tokens[1]);
	    		 int v = Integer.parseInt(tokens[2]);
	    		 
	    		 // answer query
	    		 if (type == 'P') {
	    			 if (zerosTree.rsq(u, v) > 0) pr.print("0");
	    			 else if (negativeTree.rsq(u, v) % 2 == 1) pr.print("-");
	    			 else pr.print("+");
	    			 continue;
	    		 }
	    		 
	    		 // update query. change location u to v-value
	    		 if (v < 0) {
	    			 negativeTree.change(u, 1);
	    			 zerosTree.change(u, 0);
	    		 }
	    		 else if (v == 0) {
	    			 negativeTree.change(u, 0);
	    			 zerosTree.change(u, 1);
	    		 }
	    		 else {	// v > 0
	    			 negativeTree.change(u, 0);
	    			 zerosTree.change(u, 0);
	    		 }
	    	 }
	    	 pr.println();
	     }
	     
	    //  sc.close();
	     pr.close();
	     br.close();
	    
	}
	
	public class FenwickTree {
		private ArrayList<Integer> ft;

		// return index of last non-zero digit (from right)
		private int LSOne(int S) { return (S & (-S)); }

		public FenwickTree() {}

		// initialization: n + 1 zeroes, ignore index 0
		public FenwickTree(int n) {
			ft = new ArrayList<Integer>();
			for (int i = 0; i <= n; i++)
				ft.add(0);
		}

		// returns RSQ(1, b)
		public int rsq(int b) { 
			int sum = 0;
			for (; b > 0; b -= LSOne(b))
				sum += ft.get(b);
			return sum;
		}

		// returns RSQ(a, b)
		public int rsq(int a, int b) { 
			return rsq(b) - (a == 1 ? 0 : rsq(a - 1));
		}

		// adjusts value of the k-th element by v (v can be +ve/inc or -ve/dec)
		void adjust(int k, int v) { // note: n = ft.size() - 1
			for (; k < (int) ft.size(); k += LSOne(k))
				ft.set(k, ft.get(k) + v);
		}
		
		// change the frequency value at k-th element by v
		void change(int k, int v) {
			int val = readSingle(k); int adjustVal = v - val;
			adjust(k, adjustVal);
		}
		
		public int readSingle(int idx) {
			int sum = ft.get(idx);	// sum will be decrease
			if (idx > 0) {			// special case
				int z = idx - LSOne(idx);	// make z first
				idx--;	// idx is no important anymore. so. instead y, you can use idx
				while (idx != z) { // at some iteration idx (y) will become z
					sum -= ft.get(idx);
					// substruct tree frequency which is between y and "the same path"
					idx -= LSOne(idx);
				}
			}
			return sum;
		}
		
		public void scale(int c) {
			for (int i = 0; i <= ft.size(); i++) 
				ft.set(i, ft.get(i) / c);
		}
	}
}

