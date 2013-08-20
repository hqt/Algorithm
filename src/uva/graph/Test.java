package uva.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;


/* 10731 */

public class Test {
	
	
	public static void main(String[] args) throws Exception {
		Test main = new Test();
		main.run();
	}

	ArrayList<ArrayList<IntegerPair>> adjList;
	ArrayList<Integer> dfs_low;
	ArrayList<Integer> dfs_num;
	ArrayList<Integer> dfs_parent;
	ArrayList<Boolean> visited;		// special array, not in findingArticulationPoint algorithm
	Stack<Integer>	stack;			// using to store all vertex in same SCC.
	ArrayList<ArrayList<Character>> scc;
	boolean[] used;
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
    	 used = new boolean[n];
    	 scc = new ArrayList<ArrayList<Character>>();
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
		if (dfs_num.get(u) == dfs_low.get(u)) {
			dfsSCC++;
			ArrayList<Character> comp = new ArrayList<Character>();
			while(true) {
				int v = stack.pop();
				visited.set(v, false);
				comp.add((char) (v + 'A'));
				if (u == v) break;
			}
			scc.add(comp);
		}
					
		
	}
	
	
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));

	     char[] choice = new char[5];
	     while(true) {
	    	 int n = 26;
	    	 int m = sc.nextInt();
	    	 if (m == 0) break;
	    	 initialize(n);
	    	 for (int i = 0; i < m; i++) {
	    		 for (int j = 0; j < 5; j++) {
	    			 choice[j] = sc.next().charAt(0);
	    			 used[choice[j] - 'A'] = true;
	    		 }
	    		 char c = sc.next().charAt(0);
	    		 for (int j = 0; j < 5; j++) adjList.get(c - 'A').add(new IntegerPair(choice[j] - 'A', 1));
	    	 }
	    	 
	    	 // finding SCC using tarjan's algorithm
	    	 for (int i = 0; i < 26; i++) {
	    		 if (used[i] && dfs_num.get(i) == -1) tarjanSCC(i);
	    	 }
	    	 
	    	 // sort again all result
	    	 for (int i = 0; i < scc.size(); i++) {
	    		 Collections.sort(scc.get(i));
	    	 }
	    	 Collections.sort(scc, new Comparator<ArrayList<Character>>() {
				@Override
				public int compare(ArrayList<Character> r1, ArrayList<Character> r2) {
					return r1.get(0) - r2.get(0);
				}
			});
	    	 
	    	 for (int i = 0; i < scc.size(); i++) {
	    		 for (int j = 0; j < scc.get(i).size(); j++) {
	    			 System.out.print(scc.get(i).get(j) + " ");
	    		 }
	    		 System.out.println();
	    	 }
	    	 
	    	 System.out.println();
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


