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
public class MeetingProf {
	
	static int MAXN = 30;
	static int MAX = 1000000000;
	static int n = 26;
	static int[][] c = new int[MAXN][MAXN];	// mark for road for youth
	static int[][] g = new int[MAXN][MAXN]; // mark for road for old
	
	public static void print(String s) { System.out.println(s);}
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
		
		while (true) {
			
			int m = sc.nextInt();
			if (m == 0) break;
			int s = 0, t = 0;
			
			// initialize
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					c[i][j] = MAX;
					g[i][j] = MAX;
				}
				c[i][i] = 0;
				g[i][i] = 0;
			}
			
			sc.nextLine();
			
			for (int i = 0; i < m; i++) {
				String[] comp = sc.nextLine().split(" ");
				int u = comp[2].charAt(0) - 'A';
				int v = comp[3].charAt(0) - 'A';
				int w = Integer.parseInt(comp[4]);
				if (comp[0].charAt(0) == 'Y') {
					c[u][v] = w;
					if (comp[1].charAt(0) == 'B') {
						c[v][u] = w;
					}
				}
				else {
					g[u][v] = w;
					if (comp[1].charAt(0) == 'B') {
						g[v][u] = w;
					}
				}
			}
			
			String[] comp = sc.nextLine().split(" ");
			s = comp[0].charAt(0) - 'A';
			t = comp[1].charAt(0) - 'A';

			// find min of all vertices using Floy Warshall
			for (int k = 0; k < n; k++) {
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						c[i][j] = Math.min(c[i][j], c[i][k] + c[k][j]);
						g[i][j] = Math.min(g[i][j], g[i][k] + g[k][j]);
					}
				}
			}
			
			int[] path = new int[100];
			int count = 0;
			long res = Long.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				long val = c[s][i] + g[t][i];
				if (val == res) {path[count++] = i;}
				else if (val < res) {
					count = 1;
					path[0] = i;
					res = val;
				}
			}
			
			if (res > 500 * 23) {
				pr.println("You will never meet.");
			}
			else {
				pr.print(res);
				for (int i = 0; i < count; i++) {
					pr.print(" " + Character.toChars(path[i] + 'A')[0]);
				}
				pr.println();
			}
		}
		
		
		pr.close();
		sc.close();
		
		
		
	}
}

