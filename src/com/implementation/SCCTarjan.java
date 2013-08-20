package com.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

public class SCCTarjan {
	
	  private static final int DFS_WHITE = -1; // normal DFS
	  private static ArrayList<ArrayList<IntegerPair>> AdjList = 
	             new ArrayList < ArrayList < IntegerPair > >();
	  private static ArrayList<Integer> dfs_num, dfs_low, dfs_parent;
	  private static ArrayList<Boolean> visited;
	  private static Stack <Integer> S; // additional information for SCC
	  private static int numComp, dfsNumberCounter;
	  
	  public static void initDFS(int V) { // used in normal DFS
		    dfs_num = new ArrayList<Integer> ();
		    dfs_num.addAll(Collections.nCopies(V, DFS_WHITE));
		    numComp = 0;
	  }
	  
	  public static void initGraphCheck(int V) {
		    initDFS(V);
		    dfs_parent = new ArrayList < Integer > ();
		    dfs_parent.addAll(Collections.nCopies(V, 0));
		    numComp = 0;
	  }
	  
	  public static void initTarjanSCC(int V) {
		    initGraphCheck(V);
		    dfs_low = new ArrayList < Integer > ();
		    dfs_low.addAll(Collections.nCopies(V, 0));
		    dfsNumberCounter = 0;
		    numComp = 0;
		    S = new Stack < Integer > ();
		    visited = new ArrayList < Boolean > ();
		    visited.addAll(Collections.nCopies(V, false));
	  }
	  
	 public static void tarjanSCC(int u) {
		    dfs_num.set(u, dfsNumberCounter);
		    dfs_low.set(u, dfsNumberCounter++); // dfs_low[u] <= dfs_num[u]
		    S.push(u); // store u according to order of visitation
		    visited.set(u, true);

		    Iterator<IntegerPair> it = AdjList.get(u).iterator();
		    while (it.hasNext()) { // try all neighbors v of vertex u
		      IntegerPair v = (IntegerPair)it.next();
		      if (dfs_num.get(v.first()) == DFS_WHITE) // a tree edge
		        tarjanSCC(v.first());
		      if (visited.get(v.first())) // condition for update
		        dfs_low.set(u, Math.min(dfs_low.get(u), dfs_low.get(v.first())));
		    }

		    if (dfs_low.get(u) == dfs_num.get(u)) { // if this is a root (start) of an SCC
		      System.out.printf("SCC %d: ", ++numComp);
		      while (true) {
		        int v = S.peek(); S.pop(); visited.set(v, false);
		        System.out.printf(" %d", v);
		        if (u == v) break;
		      }
		      System.out.printf("\n");
		    }
	 }
	 
	 static class IntegerPair implements Comparable<Object> {
		  public Integer _first, _second;

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

	 

}
