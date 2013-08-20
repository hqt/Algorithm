package com.mst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class Freckles {
	
	public static void main(String[] args) throws Exception {
		Freckles main = new Freckles();
		main.run();
	}
	
	static double[] val = new double[26];
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int ntest = sc.nextInt();
	     while(ntest-->0) {
	    	 int n = sc.nextInt();
	    	 double[] X = new double[n];
	    	 double[] Y = new double[n];
	    	 for (int i = 0; i < n; i++) {
	    		 X[i] = sc.nextDouble();
	    		 Y[i] = sc.nextDouble();
	    	 }
	    	 
	    	 ArrayList<Edge> edgeList = new ArrayList<Freckles.Edge>();
	    	 for (int i = 0; i < n; i++) {
	    		 for (int j = i + 1; j < n; j++) {
	    			 Edge e = new Edge(i, j, Math.pow(X[i] - X[j], 2) + Math.pow(Y[i] - Y[j],2));
	    			 edgeList.add(e);
	    		 }
	    	 }
	    	 Collections.sort(edgeList, new Comparator<Edge>() {
				@Override
				public int compare(Edge e1, Edge e2) {
					if (e1.weight > e2.weight) return 1;
					else if (e1.weight < e2.weight) return -1;
					else return 0;
				}
			});
	    	 
	    	 double mst_cost = 0;
	    	 int edge_join = 0;
	    	 UnionFind uf = new UnionFind(n);
	    	 for (int i = 0; i < edgeList.size(); i++) {
	    		 Edge e = edgeList.get(i);
	    		 if (!uf.isSameSet(e.u, e.v)) {
	    			 uf.unionSet(e.u, e.v);
	    			 mst_cost += Math.sqrt(e.weight);
	    			 edge_join++;
	    			 if (edge_join == n - 1) break;
	    		 }
	    	 }
	    	 
	    	 DecimalFormat twoDForm = new DecimalFormat("0.00");
    		 //System.out.println(twoDForm.format(mst_cost));
    		 System.out.printf("%.2f\n", mst_cost);
    		 if (ntest != 0) System.out.println();
	    	 
	    	 
	     }
	     
	     sc.close();
	     pr.close();
	}
	
	public static class Edge {
		int u, v;
		double weight;
		public Edge(int u, int v, double weight) {
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

