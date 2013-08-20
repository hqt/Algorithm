package uva.shortestpath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class WormHoles {
	
	public static int INF = 100000000;
	public static void main(String[] args) throws Exception {
		WormHoles main = new WormHoles();
		main.run();
	}

	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int ntestcase = sc.nextInt();
	     while (ntestcase-->0) {
	    	 int n = sc.nextInt();
	    	 int m = sc.nextInt();
	    	 
	    	 ArrayList<ArrayList<AdjEdge>> adjList = new ArrayList<ArrayList<AdjEdge>>();
	    	 for (int i = 0; i < n; i++) {
	    		 ArrayList<AdjEdge> neighbor = new ArrayList<WormHoles.AdjEdge>();
	    		 adjList.add(neighbor);
	    	 }
	    	 
	    	 for (int i = 0; i < m; i++) {
	    		 int u = sc.nextInt();
	    		 int v = sc.nextInt();
	    		 int w = sc.nextInt();
	    		 adjList.get(u).add(new AdjEdge(v, w));
	    	 }
	    	 
	    	 // Ford Bellman Algorithm
	    	 // just use this algorithm for detect negative cycle
	    	 // we can use arbitrary source vertex. in this case, such cas s = 0
	    	 int s = 0;
	    	 int[] d = new int[n];
	    	 for (int i = 0; i < n; i++) d[i] = INF;
	    	 d[s] = 0;
	    	 
	    	 // dynamic formula
	    	 // d[v][k] = min d[v][k-1] || d[u][k-1] + c[u][v]
	    	 
	    	 for (int i = 1; i <= n-1; i++) {
	    		 for (int v = 0; v < n; v++) {
	    			 for (int j = 0; j < adjList.get(v).size(); j++) {
	    				 int u = adjList.get(v).get(j).u;
	    				 int w = adjList.get(v).get(j).weight;
	    				 d[v] = Math.min(d[v], d[u] + w);
	    			 }
	    		 }
	    	 }
	    	 
	    	 // run addiontional looop for detect negative cycle
	    	 boolean negativeCycle = false;
	    	 LOOP:
	    	 for (int v = 0; v < n; v++) {
	    		 for (int j = 0; j < adjList.get(v).size(); j++) {
	    			 int u = adjList.get(v).get(j).u;
	    			 int w = adjList.get(v).get(j).weight;
	    			 if (d[v] > d[u] + w) {
	    				 negativeCycle = true;
	    				 break LOOP;
	    			 }
	    		 }
	    	 }
	    	 
	    	 if (negativeCycle) {
	    		 System.out.println("possible");
	    	 }
	    	 else {
	    		 System.out.println("not possible");
	    	 }
	    	 
	     }
	     
	     sc.close();
	     pr.close();
	}
	
	public static class AdjEdge {
		int u, weight;
		public AdjEdge(int u, int weight) {
			this.u = u;
			this.weight = weight;
		}
	}
	
}

