package uva.dynamic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/** UVA 10130 - SuperSale */
public class SuperSale {
	
	public static int MAXN = 1010;
	public static int MAXW = 40;
	public static int[][] memo = new int[MAXN][MAXW];
	public static int[] W = new int[MAXN];
	public static int[] V = new int[MAXN];
	public static int[][] f = new int[MAXN][MAXW];
	
	/** totally n products, max Weight is w */
	public static int dp(int n, int w) {
		if (n == 0 || w == 0) return 0;
		if (memo[n][w] != -1) return memo[n][w];
		if (w - W[n] >= 0) return memo[n][w] = Math.max(dp(n-1,w), dp(n-1,w-W[n]) + V[n]);
		else return memo[n][w] = dp(n-1,w);
	}
	
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
	        
	     int ntest = 0;
	     ntest = sc.nextInt();
	     while(ntest-- > 0) {
	    	 for (int i = 0; i < MAXN; i++) 
	    		 for (int j = 0; j < MAXW; j++) 
	    			 memo[i][j] = -1;
	    	 
	    	 int n = sc.nextInt();
	    	 for (int i = 1; i <= n; i++) {
	    		 V[i] = sc.nextInt();
	    		 W[i] = sc.nextInt();
	    	 }
	    	 int people = sc.nextInt();
	    	 long res = 0;
	    	 while(people-- > 0) {
	    		 int w = sc.nextInt();
	    		 res += dp(n, w);
	    	 }
	    	 pr.println(res);
	     }	     

	     pr.close();
	     sc.close();
	}
}
