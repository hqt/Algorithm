package uva.topology;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/* 10305 */

public class TopologySort {
	
	public static void main(String[] args) throws Exception {
		TopologySort main = new TopologySort();
		main.run();
	}
	
	int[][] g;
	int[] topo;
	boolean[] check;
	int n = 0;
	int work;
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     while(true) {
	    	 n = sc.nextInt();
	    	 int m = sc.nextInt();
	    	 if (n == 0 && m == 0) break;
	    	 work = n;
	    	 
	    	 g = new int[n][n];
	    	 topo = new int[n];
	    	 check = new boolean[n];
	    	 
	    	 for (int i = 0; i < m; i++) {
	    		 int u = sc.nextInt() - 1;
	    		 int v = sc.nextInt() - 1;
	    		 g[u][v] = 1;
	    	 }
	    	 
	    	 topologySort();
	    	 for (int i = 0; i < n; i++) pr.print((topo[i] + 1) + " ");
	    	 pr.println();
	    	 
	     }
	     
	     pr.close();
	     sc.close();
	}
	
	public void topologySort() {
		for (int i = 0; i < n; i++) {
			if(!check[i]) dfs(i);
		}
	}
	
	public void dfs(int v) {
		check[v] = true;
		for (int i = 0; i < n; i++) {
			if (g[v][i] > 0 && !check[i]) {
				dfs(i);
			}
		}
		topo[--work] = v;
	}
}
