package uva.shortestpath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**11463 	Commandos
*/
public class Commandos {
	
	static int MAXN = 110;
	static int[][] c = new int[MAXN][MAXN];
	
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
		
		int ntest = sc.nextInt();
		
		for (int tcase = 0; tcase < ntest; tcase++) {
			int n = sc.nextInt();
			int m = sc.nextInt();

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) 
					c[i][j] = 1000000000;
				c[i][i] = 0;
			}
			
			for (int i = 0; i < m; i++) {
				int u = sc.nextInt();
				int v = sc.nextInt();
				c[u][v] = 1;
				c[v][u] = 1;
			}
			int s = sc.nextInt();
			int t = sc.nextInt();
			
			// using Floyd Warshall because n is small
			for (int k = 0; k < n; k++) {
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						c[i][j] = Math.min(c[i][j], c[i][k] + c[k][j]);
					}
				}
			}
			
			/**
			 * Explain :
			 * giao cua (s,i) va (i,t) la rong. (voi i khac s va t)
			 * vi : (s,i) la dung di ngan nhat. neu ton tai dinh khac thuoc (i,t) : (s,x,i) thi (i,t) --> (x,t) (VO LI)
			 * (do do thi co trong so la 1)
			 * ta tim max cua tong nay : so dinh di qua nhieu nhat !!!
			 */
			long res = Long.MIN_VALUE;
			for (int i = 0; i < n; i++) {
				res = Math.max(res, c[s][i] + c[i][t]);
			}
			pr.println("Case " + (tcase+1) + ": " + res);
		}
		
		pr.close();
		sc.close();
		
		
		
	}
}

