package com.mst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/* 11631 */

public class DarkRoad {
	
	public static final int INF = 1000000000;
	
	public static void main(String[] args) throws Exception {
		DarkRoad main = new DarkRoad();
		main.run();
	}

	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	   
	     while (true) {
	    	 int n = sc.nextInt();
	    	 int m = sc.nextInt();
	    	 if (n == 0 && m == 0) break;
	    	 int totalWeight = 0;
	    	 
	    	 ArrayList<IntegerTriple> edgeList = new  ArrayList<IntegerTriple>();
	    	 ArrayList<ArrayList<IntegerPair>> adjList = new ArrayList<ArrayList<IntegerPair>>();
	    	 for (int i = 0; i < n; i++) {
	    		 ArrayList<IntegerPair> neighbor = new ArrayList<IntegerPair>();
	    		 adjList.add(neighbor);
	    	 }
	    	 for (int i = 0; i < m; i++) {
	    		 int u = sc.nextInt();
	    		 int v = sc.nextInt();
	    		 int w = sc.nextInt();
	    		 totalWeight += w;
	    		 edgeList.add(new IntegerTriple(w, u, v));
	    		 adjList.get(u).add(new IntegerPair(v, w));
	    		 adjList.get(v).add(new IntegerPair(u, w));
	    	 }
	    	 Collections.sort(edgeList);
	    	 
	    	 // minimum spanning tree (with UnionFind support) implementation
	    	 int mst_cost = 0;
	    	 UnionFind UF = new UnionFind(n);
	    	 for (int i = 0; i < m; i++) {
	    		 IntegerTriple front = edgeList.get(i);
	    		 if (!UF.isSameSet(front.second(), front.third())) {
	    			 UF.unionSet(front.second(), front.third());
	    			 mst_cost += front.first();
	    		 }
	    	 }
	    	 
	    	 int res = totalWeight - mst_cost;
	    	 System.out.println(res);
	    	 
	     }
	     
	     
	     pr.close();
	     sc.close();
	}
	
	static class IntegerPair implements Comparable {
		  Integer _first, _second;

		  public IntegerPair(Integer f, Integer s) {
		    _first = f;
		    _second = s;
		  }

		  public int compareTo(Object o) {
		    if (this.first() != ((IntegerPair )o).first())
		      return this.first() - ((IntegerPair )o).first();
		    else
		      return this.second() - ((IntegerPair )o).second();
		  }

		  Integer first() { return _first; }
		  Integer second() { return _second; }
	}

	static class IntegerTriple implements Comparable {
		  Integer _first, _second, _third;

		  public IntegerTriple(Integer f, Integer s, Integer t) {
		    _first = f;
		    _second = s;
		    _third = t;
		  }

		  public int compareTo(Object o) {
		    if (!this.first().equals(((IntegerTriple)o).first()))
		      return this.first() - ((IntegerTriple)o).first();
		    else if (!this.second().equals(((IntegerTriple)o).second()))
		      return this.second() - ((IntegerTriple)o).second();
		    else
		      return this.third() - ((IntegerTriple)o).third();
		  }

		  Integer first() { return _first; }
		  Integer second() { return _second; }
		  Integer third() { return _third; }

		  public String toString() { return first() + " " + second() + " " + third(); }
	}

}
	

class UnionFind {                                              // OOP style
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
