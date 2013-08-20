package uva.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

/* 247 */

public class TarjanSCCPrintResult {
	
	
	public static void main(String[] args) throws Exception {
		TarjanSCCPrintResult main = new TarjanSCCPrintResult();
		main.run();
	}

	ArrayList<ArrayList<IntegerPair>> adjList;
	ArrayList<Integer> dfs_low;
	ArrayList<Integer> dfs_num;
	ArrayList<Integer> dfs_parent;
	ArrayList<Boolean> visited;		// special array, not in findingArticulationPoint algorithm
	Stack<Integer>	stack;			// using to store all vertex in same SCC.
	ArrayList<String> name;
	int dfsNumberCounter, dfsSCC;
	
	
	public void initialize(int n) {
		 adjList = new ArrayList<ArrayList<IntegerPair>>();
    	 for (int i = 0; i < n; i++) {
    		 ArrayList<IntegerPair> neighbor = new ArrayList<IntegerPair>();
    		 adjList.add(neighbor);
    	 }
    	 dfs_low = new ArrayList<Integer>();
    	 dfs_low.addAll(Collections.nCopies(n, -1));
    	 dfs_num = new ArrayList<Integer>();
    	 dfs_num.addAll(Collections.nCopies(n, -1));
    	 dfs_parent = new ArrayList<Integer>();
    	 dfs_parent.addAll(Collections.nCopies(n, -1));
    	 visited = new ArrayList<Boolean>();
    	 visited.addAll(Collections.nCopies(n, false));
    	 stack = new Stack<Integer>();
    	 name = new ArrayList<String>();
    	 name.addAll(Collections.nCopies(n, "Unknown name"));

    	 dfsNumberCounter = 0;
    	 dfsSCC = 0;
	}
	
	public void tarjanSCC(int u) {
		dfs_num.set(u, dfsNumberCounter);
		dfs_low.set(u, dfsNumberCounter);
		dfsNumberCounter++;
		stack.push(u);
		visited.set(u, true);
		
		Iterator<IntegerPair> it = adjList.get(u).iterator();
		while (it.hasNext()) {
			int v = it.next().first();
			if (dfs_num.get(v) == -1) {		// if not visit yet
				tarjanSCC(v);
				dfs_low.set(u, Math.min(dfs_low.get(u), dfs_low.get(v)));	// if not visited yet
			}
			else if (visited.get(v))	// v still in stack. hence still in same SCC
				dfs_low.set(u, Math.min(dfs_low.get(u), dfs_num.get(v)));	// if already visited
		}
		
		// check state (out side of while loop !!!)
		// print the result
		if (dfs_num.get(u) == dfs_low.get(u)) {
			dfsSCC++;
			while(true) {
				int v = stack.pop();
				visited.set(v, false);
				if (u != v)
					System.out.print(name.get(v) +", ");
				else {
					System.out.println(name.get(v));
					break;
				}
			}
		}
		
	}
	
	
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     int ntest = 0;
	     while(true) {
	    	 int n = sc.nextInt();
	    	 int m = sc.nextInt();
	    	 if (n == 0 || m == 0) break;
	    	 initialize(n);
	    	 ntest++;
	    	
	    	 HashMap<String, Integer> hash = new HashMap<String, Integer>();
	    	 int count = 0;
	    	 for (int i = 0; i < m; i++) {
	    		 String s1 = sc.next();
	    		 String s2 = sc.next();
	    		 int p1 = 0, p2 = 0;
	    		 if (hash.containsKey(s1)) p1 = hash.get(s1);
	    		 else {
	    			 name.set(count, s1);
	    			 p1 = count;
	    			 hash.put(s1, count++);
	    		 }
	    		 if (hash.containsKey(s2)) p2 = hash.get(s2);
	    		 else {
	    			 name.set(count, s2);
	    			 p2 = count;
	    			 hash.put(s2, count++);
	    		 }
	    		 adjList.get(p1).add(new IntegerPair(p2,1));
	    	 }
	    	 
	    	 if (ntest > 1) System.out.println();
	    	 System.out.println("Calling circles for data set " + ntest + ":");
	    	 // find and print SCC using Tarjan SCC Finding Algorithm
	    	 for (int i = 0; i < n; i++) {
	    		 if (dfs_num.get(i) == -1) tarjanSCC(i);
	    	 }
	    	 //System.out.println();
	    	 
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
}
