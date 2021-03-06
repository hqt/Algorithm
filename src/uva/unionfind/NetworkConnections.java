package uva.unionfind;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkConnections {
	
	public static void main(String[] args) throws Exception {
		NetworkConnections main = new NetworkConnections();
		main.run();
	}

	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int ntestcase = sc.nextInt();
	     while(ntestcase-->0) {
	    	 int n = sc.nextInt();
	    	 UnionFind uf = new UnionFind(n);
	    	 int ntrue = 0;
	    	 int nfalse = 0;
	    	 
	    	 while(!sc.hasNextInt()) {
	    		 if (!sc.hasNext()) break;
	    		 String choice = sc.next();
	    		 int u = sc.nextInt()-1;
	    		 int v= sc.nextInt()-1;
	    		 if (choice.charAt(0) == 'c') uf.unionSet(u, v);
	    		 else if (uf.isSameSet(u, v)) ntrue++;
	    		 else nfalse++;
	    	 }
	    	 
	    	 System.out.println(ntrue + "," + nfalse);
	    	 if (ntestcase != 0) System.out.println();
	     }
	     
	     sc.close();
	     pr.close();
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


