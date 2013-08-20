package uva.dynamic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

/** 983 	Localized Summing for Blurring
*/
public class GaussBlur {
	
	static int MAXN = 1100;
	static int[][] c = new int[MAXN][MAXN];
	static long[][] g = new long[MAXN][MAXN];
	
	public static void main(String[] args) {
		Scanner sc = null;
		PrintWriter pr = null;
		
		try {
			pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
			sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			sc = new Scanner(new File("input.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while (sc.hasNext()) {
			int n = sc.nextInt();
			int m = sc.nextInt();
			
			for (int i = n - 1; i >=0; i--) {
				for (int j = 0; j < n; j++) {
					c[i][j] = sc.nextInt();
				}
			}
			
			if (sc.hasNext()) sc.nextLine();
			
			// because this graph is reverse. we cannot integrade into reading phase.
			// must be separate
			for  (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i > 0) c[i][j] += c[i-1][j];
					if (j > 0) c[i][j] += c[i][j-1];
					if (i > 0 && j > 0) c[i][j] -= c[i-1][j-1];
				}
			}
			
			
			for (int i = 0; i < n - m + 1; i++) {
				for (int j = 0; j < n - m + 1; j++) {
					int k = i + m - 1;
					int t = j + m - 1;
					long sum = c[k][t];
					if (j > 0) sum -= c[k][j-1];
					if (i > 0) sum -= c[i-1][t];
					if (i > 0 && j > 0) sum += c[i-1][j-1];
					g[i][j] = sum;
				}
			}

			BigInteger sum = BigInteger.ZERO;
			for (int i = n - m; i >=0; i--) {
				for (int j = 0; j < n-m+1; j++) {
					pr.println(g[i][j]);
					sum = sum.add(BigInteger.valueOf(g[i][j]));
				}
			}
			pr.println(sum.toString());
			pr.println();
		}
		
		pr.close();
		sc.close();
		
		
		
	}
}
