package uva.dynamic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/** UVA 10496 - Collecting Beepers */
public class TraditionalTSP {
	
	public static int n = 0;
	public static int MAXN = 11; 	// karel's plus 
	public static int[][] dis = new int[MAXN][MAXN];
	public static int[] X = new int[MAXN];
	public static int[] Y = new int[MAXN];
	public static long[][] memo = new long[MAXN][1 << MAXN];

	public static long tsp(int pos, int bitmask) {
		// after go through all vertices, return again starting vertice
		// can return 0 if we don't turn again to vertice 0
		if (bitmask == (1 << (n+1)) -1) return dis[pos][0];	
		if (memo[pos][bitmask] != -1) return memo[pos][bitmask];
		
		long res = Long.MAX_VALUE;
		for (int i = 0; i <= n; i++) {
			if (pos != i && ((bitmask & (1<<i)) == 0)) {
				res = Math.min(res, dis[pos][i] + tsp(i, bitmask | (1<<i)));
			}
		}
		return memo[pos][bitmask] = res;
	}
	
	public static void main(String[] args) throws Exception {
		
		 Scanner sc = null;
	     PrintWriter pr = null;
	     
	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
         sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
         sc = new Scanner(new File("input.txt"));
	        
	    int ntestcase = sc.nextInt();
	    while(ntestcase-- > 0) {
	    	int xmax = sc.nextInt();
	    	int ymax = sc.nextInt();
	    	X[0] = sc.nextInt();
	    	Y[0] = sc.nextInt();
	    	n = sc.nextInt();
	    	for (int i = 1; i <= n; i++) {
	    		X[i] = sc.nextInt();
	    		Y[i] = sc.nextInt();
	    	}
	    	
	    	for (int i = 0; i <= n; i++) 
	    		for (int j = 0; j <= n; j++)
	    	 		dis[i][j] = Math.abs(X[i] - X[j]) + Math.abs(Y[i] - Y[j]);
	    	
	    	// WRONG IF for (int i = 0; i < n; i++)
	    	for (int i = 0; i <= n; i++)
	    		for (int j = 0; j < 1<<MAXN; j++)
	    			memo[i][j] = -1;
	    	
	    	
	    	long res = tsp(0,1);
	    	
	    	pr.println("The shortest path has length " + res);
	    	
	    }
	     pr.close();
	     sc.close();
	}
}
