package com.mst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class ConnectingComputerSize {
	
	public static void main(String[] args) throws Exception {
		ConnectingComputerSize main = new ConnectingComputerSize();
		main.run();
	}
	
	static double[] val = new double[26];
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int counter = 0;
	     while (sc.hasNextInt()) {
	    	 counter++;
	    	 if (counter != 1) System.out.println();
	    	 int n = sc.nextInt();
	    	 long old_mst = 0;
	    	 for (int i = 1; i <= n - 1; i++) {
	    		 sc.nextInt();
	    		 sc.nextInt();
	    		 old_mst += sc.nextLong();
	    	 }
	    	 ArrayList<Edge> edgeList = new ArrayList<ConnectingComputerSize.Edge>();
	    	 int k = sc.nextInt();
	    	 for (int i = 0; i < k; i++) {
	    		 int u = sc.nextInt()-1;
	    		 int v = sc.nextInt()-1;
	    		 int weight = sc.nextInt();
	    		 edgeList.add(new Edge(true, u, v, weight));
	    	 }
	    	 int m = sc.nextInt();
	    	 for (int i = 0; i < m; i++) {
	    		 int u = sc.nextInt()-1;
	    		 int v = sc.nextInt()-1;
	    		 int weight = sc.nextInt();
	    		 edgeList.add(new Edge(false, u, v, weight));
	    	 }
	    	 
	    	 Collections.sort(edgeList, new Comparator<Edge>() {
				@Override		
				public int compare(Edge e1, Edge e2) {
					return e1.weight - e2.weight;
				}
			});
	    	 
	    	 UnionFind uf = new UnionFind(n);
	    	 long mst_cost = 0;
	    	 int mst_edge = 0;
	    	 for (int i = 0; i < edgeList.size(); i++) {
	    		 Edge e = edgeList.get(i);
	    		 if (!uf.isSameSet(e.u, e.v)) {
	    			// System.out.println("edge: " + (e.u+1) + " " + (e.v+1) + " " + e.weight + " " + e.newline);
	    			 uf.unionSet(e.u, e.v);
	    			 //if (e.newline == true) mst_cost += e.weight;
	    			 mst_cost += e.weight;
	    			 mst_edge += 1;
	    			 if (mst_edge == n-1) break;
	    		 }
	    	 }
	    	 
	    	 System.out.println(old_mst);
	    	 System.out.println(mst_cost);
	    	 //System.out.println(counter);
	    	 
	     }
	     
	     sc.close();
	     pr.close();
	}
	
	public static class Edge {
		boolean newline;
		int u, v;
		int weight;
		public Edge(boolean newline, int u, int v, int weight) {
			this.newline = newline;
			this.u = u;
			this.v = v;
			this.weight = weight;
		}
	}
	
	public static class UnionFind {                                              // OOP style
		  private ArrayList<Integer> p, rank, setSize;
		  private int numSets;

		  public UnionFind(int N) {
		    p = new ArrayList<Integer>(N);
		    rank = new ArrayList<Integer>(N);
		    setSize = new ArrayList<Integer>(N);
		    numSets = N;
		    for (int i = 0; i < N; i++) {
		      p.add(i);
		      rank.add(0);
		      setSize.add(1);
		    }
		  }

		  public int findSet(int i) { 
		    if (p.get(i) == i) return i;
		    else {
		      int ret = findSet(p.get(i)); p.set(i, ret);
		      return ret; } }

		  public Boolean isSameSet(int i, int j) { return findSet(i) == findSet(j); }

		  public void unionSet(int i, int j) { 
		    if (!isSameSet(i, j)) { numSets--; 
		    int x = findSet(i), y = findSet(j);
		    // rank is used to keep the tree short
		    if (rank.get(x) > rank.get(y)) { p.set(y, x); setSize.set(x, setSize.get(x) + setSize.get(y)); }
		    else                           { p.set(x, y); setSize.set(y, setSize.get(y) + setSize.get(x));
		                                     if (rank.get(x) == rank.get(y)) rank.set(y, rank.get(y) + 1); } } }
		  public int numDisjointSets() { return numSets; }
		  public int sizeOfSet(int i) { return setSize.get(findSet(i)); }
		}

}


