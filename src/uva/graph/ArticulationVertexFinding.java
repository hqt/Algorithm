package uva.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/* 315 */

/* should set dfsRoot, both try plicity, set to true or false (because it can set wrong !!!!)
 */

public class ArticulationVertexFinding {
	
	
	public static void main(String[] args) throws Exception {
		ArticulationVertexFinding main = new ArticulationVertexFinding();
		main.run();
	}

	ArrayList<ArrayList<IntegerPair>> adjList;
	ArrayList<Integer> dfs_low;
	ArrayList<Integer> dfs_num;
	ArrayList<Integer> dfs_parent;
	ArrayList<Boolean> articulation_vertex;
	int dfsNumberCounter, dfsRoot, rootChildren;
	
	
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
    	 articulation_vertex = new ArrayList<Boolean>();
    	 articulation_vertex.addAll(Collections.nCopies(n, false));
    	 dfsNumberCounter = 0;
	}
	
	public void ArticulationPointAndBridge(int u) {
		dfs_low.set(u, dfsNumberCounter);
		dfs_num.set(u, dfsNumberCounter);	//dfs_low[u] <= dfs_num[u]
		dfsNumberCounter++;	
		
		Iterator<IntegerPair> it = adjList.get(u).iterator();
		while (it.hasNext()) {
			IntegerPair v = it.next();
			if (dfs_num.get(v.first()) == -1) {	// not visited yet
				dfs_parent.set(v.first(), u);
				if (u == dfsRoot) rootChildren++;
				ArticulationPointAndBridge(v.first());
				if (dfs_low.get(v.first()) >= dfs_num.get(u)) {
					articulation_vertex.set(u, true);
				}
				// bridge. not need in this problem
				//if (dfs_low.get(v.first()) > dfs_num.get(u))
					//System.out.printf("Edge: (%d, %d) is a bridge\n", u, v.first());
				dfs_low.set(u, Math.min(dfs_low.get(u), dfs_low.get(v.first())));
			}
			else if (v.first() != dfs_parent.get(u)) {// a back edge and not direct cycle
				/// update dfs_low[u]
				dfs_low.set(u, Math.min(dfs_low.get(u), dfs_num.get(v.first())));
			}
		}
		
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     while (sc.hasNextInt()) {
	    	 int n = sc.nextInt();
	    	 if (n == 0) break;
	    	 
	    	 initialize(n);

	    	 sc.nextLine();
	    	 while (sc.hasNextLine()) {
	    		 String line = sc.nextLine();
	    		 if (line.length() == 1 && line.charAt(0) == '0') break;
	    		 Scanner sc1 = new Scanner(line);
	    		 int u = sc1.nextInt() - 1;
	    		 while (sc1.hasNextInt()) {
	    			 int v = sc1.nextInt() - 1;
	    			 adjList.get(u).add(new IntegerPair(v, 1));
	    			 adjList.get(v).add(new IntegerPair(u, 1));
	    		 }
	    		 sc1.close();
	    	 }
	    	 
	    	 // find articulation point
	    	 for (int i = 0; i < n; i++) {
	    		 if (dfs_num.get(i) == -1) {
	    			 dfsRoot = i;
	    			 rootChildren = 0;
	    			 ArticulationPointAndBridge(i);
	    			 // SPECIAL CASE that this algorithm cannot detect
	    			 if (rootChildren > 1) 
	    				 articulation_vertex.set(dfsRoot, true);
	    			 else
	    				 articulation_vertex.set(dfsRoot, false);  // must do this. because articulation_vertex[dfsRoot] can set WRONG (:( PAIN)
	    			 
	    			 // OR
	    			 articulation_vertex.set(dfsRoot, (rootChildren > 1));
	    		 }
	    	 }
	    	 
	    	 int nArticulationPoint = 0;
	    	 for (int i = 0; i < n; i++) 
	    		 if (articulation_vertex.get(i)) {
	    			 nArticulationPoint++;
	    		 }
	    	 System.out.println(nArticulationPoint);
	    	 
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

