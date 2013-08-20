package uva.numbertheory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;


/*10229 - Modular Fibonacci 
 *Becarefully when using long such as : long = int + int * int (WRONG !!!!)
should be : long = int + (long) int * int
 * */

public class ModularFibnacci {
	
	public static void main(String[] args) throws Exception {
		ModularFibnacci main = new ModularFibnacci();
		main.run();
	}

	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     while (sc.hasNextInt()) {
	    	 int n = sc.nextInt();
	    	 int m = sc.nextInt();
	    	 int p = 1;
	    	 for (int i = 1; i <= m; i++) p <<= 1;
	    	 int[][] base = new int[2][2];
	    	 // this matrix is special for fibonacci sequence
	    	 base[0][0] = 1;
	    	 base[0][1] = 1;
	    	 base[1][0] = 1;
	    	 base[1][1] = 0;
	    	 
	    	 if (n == 0) {
	    		 System.out.println(0);
	    		 continue;
	    	 }
	    	 
	    	 int[][] res = matPow(base, n, 2, p);
	    	 System.out.println(res[1][0]);
	     }
	     
	     

		 pr.close();
	     sc.close();
	}
	
	public static int[][] matMul(int[][] a, int[][] b, int n, int modulo) {
		int[][] ans = new int[n][n];
		int i, j, k;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				for (ans[i][j] = k = 0; k < n; k++) {
					long res = (ans[i][j] + (long)a[i][k] * b[k][j]) % modulo;	// use long var to prevent overflow
					ans[i][j] = (int) res;
				}
			}
		}
		//System.out.print("debug:\n" + ans[0][0] + "\t" + ans[0][1] + "\n" + ans[1][0] + "\t" + ans[1][1] + "\n");
		return ans;
	}
	
	/* this matrix power will have structure like fastExp
	 * base^p (num) */
	public static int[][] matPow(int[][] base, int p, int n, int modulo) {
		int[][] ans = new int[n][n];
		for (int i = 0; i < n; i++) for (int j = 0; j <  n; j++)
			ans[i][j] = (i == j) ? 1 : 0;		// prepare identity matrix
		while (p > 0) {							// iterative version of Divide & Conquer exponentiation
			if ((p & 1) > 0) ans = matMul(ans, base, n, modulo);	// check if p is odd (last bit is on)
			base = matMul(base, base, n, modulo);			// square the base
			p >>= 1;							// divide p by 2
		}
		return ans;
	}
	
}

