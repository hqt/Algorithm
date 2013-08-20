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
* UVA 12086
* Pure Fenwick Tree (Binary Indexed Tree)
* @author Huynh Quang Thao
*
*/
public class Potentiometers {
	
	public static void main(String[] args) throws Exception {
		Potentiometers main = new Potentiometers();
		main.run();
	}

	public static String occurs;
	public void run() throws Exception {

		PrintWriter pr = null;
		BufferedReader br = null;
		
		pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		br = new BufferedReader(new InputStreamReader(System.in));
		br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt"))));
		
	    String line = null;
	    String[] tokens = null;
		int ntestcase = 0;
		while(true) {
			int n = Integer.parseInt(br.readLine());
			if (n == 0) break;
			
			ntestcase++;
			if (ntestcase > 1) pr.println();
			pr.printf("Case %d:\n", ntestcase);
			FenwickTree tree = new FenwickTree(n);
			for (int i = 1; i <= n; i++) tree.change(i, Integer.parseInt(br.readLine()));
			
			// process query
			while(true) {
				line = br.readLine();
				if (line.equals("END")) break;
				tokens = line.split(" ");
				int u = Integer.parseInt(tokens[1]);
				int v = Integer.parseInt(tokens[2]);
				if (tokens[0].charAt(0) == 'S') tree.change(u, v);
				else pr.println(tree.rsq(u, v));
			}
		}
		
		pr.close();
		br.close();
	    
	}
	
	public static class FenwickTree {
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


