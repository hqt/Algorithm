package uva.dynamic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/** 11159 - Factors and Multiples
 {<a href="http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2100">Factors and Multiplies</a>}
*/
public class MaximumSum {
	
	static int[][] c;
	
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
		
		int n = sc.nextInt();
		c = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c[i][j] = sc.nextInt();
				if (i > 0) c[i][j]  += c[i - 1][j];
				if (j > 0) c[i][j]  += c[i][j - 1];
				if (i > 0 && j > 0) c[i][j] -= c[i - 1][j - 1];
				//System.out.print(c[i][j] + "\t");
			}
			//System.out.println();
		}
		
		long max = Long.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = i; k < n; k++) {
					for (int t = j; t < n; t++) {
						// find sum in matrix from [i][j] to [k][t]
						long res = c[k][t];
						if (j > 0) res -= c[k][j - 1];
						if (i > 0) res -= c[i - 1][t];
						if (j >0 && i > 0) res += c[i -1][j - 1];
						if (res > max) max = res;
					}
				}
			}
		}
		
		pr.println(max);
		
		pr.close();
		sc.close();
		
		
		
	}
}

