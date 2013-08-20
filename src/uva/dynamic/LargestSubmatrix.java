package uva.dynamic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/** 836 - Largest Submatrix
*/
public class LargestSubmatrix {
	
	static int MAXN = 30;
	static int[][] c = new int[MAXN][MAXN];
	static int MINCELL = -MAXN * MAXN;
	
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
		
		int test = sc.nextInt();
		
		for (int tcase = 0; tcase < test; tcase++) {
			sc.nextLine();
			String line = sc.next().trim();
			int n = line.length();
			// first line : special case
			for (int i = 0; i < n; i++) {
				if (line.charAt(i) == '1') c[0][i] = 1;
				else c[0][i] = MINCELL;
				if (i > 0) c[0][i] += c[0][i-1];
			}
			for (int i = 1; i < n; i++) {
				line = sc.next().trim();
				for (int j = 0; j < n; j++) {
					if (line.charAt(j) == '1') c[i][j] = 1;
					else c[i][j] = MINCELL;
					if (i > 0) c[i][j] += c[i-1][j];
					if (j > 0) c[i][j] += c[i][j-1];
					if (i > 0 && j > 0) c[i][j] -= c[i-1][j-1];
				}
			}

			long max = Integer.MIN_VALUE;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = i; k < n; k++) {
						for (int t = j; t < n; t++) {
							long res = c[k][t];
							if (j > 0) res -= c[k][j-1];
							if (i > 0) res -= c[i-1][t];
							if (i > 0 && j > 0) res += c[i-1][j-1];
							if (res > 0) {
								if (res > max) {
									max = res;
								}
							}
						}
					}
				}
			}
			
			// this special case makes me headache :(
			if (max < 0) pr.println(0);
			else pr.println(max);
			if (tcase + 1 != test) pr.println();
			
		}
		
		
		pr.close();
		sc.close();
		
		
		
	}
}

