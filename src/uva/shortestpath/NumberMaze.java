package uva.shortestpath;

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
import java.util.PriorityQueue;
import java.util.Scanner;

/* 929 */

public class NumberMaze {
	
	public static final int INF = 1000000000;
	
	public static void main(String[] args) throws Exception {
		NumberMaze main = new NumberMaze();
		main.run();
	}

	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	   
	     int ntest = sc.nextInt();
	     int[][] c = new int[1010][1010];
	     while (ntest -- > 0) {
	    	 int n = sc.nextInt();
	    	 int m = sc.nextInt();

	    	 for (int i = 0; i < n; i++) 
	    		 for (int j = 0; j < m; j++) {
	    			 c[i][j] = sc.nextInt();
	    		 }
	    	 
	    	 ArrayList<ArrayList<IntegerPair>> adjList = new ArrayList<ArrayList<IntegerPair>>();
	    	 for (int i = 0; i < n * m; i++) {
	    		 ArrayList<IntegerPair> neighbor = new ArrayList<IntegerPair>();
	    		 adjList.add(neighbor);
	    	 }
	    	 
	    	 for (int i = 0; i < n; i++) {
	    		 for (int j = 0; j < m; j++) {
	    			 int v = i * m + j;
	    			 if (i > 0) {
	    				 // c[i-1][j]
	    				 int u = (i-1) * m + j;
	    				 adjList.get(v).add(new IntegerPair(u, c[i-1][j]));
	    			 }
	    			 if (i < n - 1) {
	    				 // c[i+1][j];
	    				 int u = (i+1)*m + j;
	    				 adjList.get(v).add(new IntegerPair(u, c[i+1][j]));
	    			 }
	    			 if (j > 0) {
	    				 // c[i][j-1];
	    				 int u = i*m + j-1;
	    				 adjList.get(v).add(new IntegerPair(u, c[i][j-1]));
	    			 }
	    			 if (j < m-1) {
	    				 // c[i][j+1]
	    				 int u = i *m + j + 1;
	    				 adjList.get(v).add(new IntegerPair(u, c[i][j+1]));
	    			 }
	    		 }
	    	 }
	    	 
	    	 int s = 0;
	    	 
	    	 // Diskstra routine
	    	 ArrayList<Integer> dist = new ArrayList<Integer>();
	    	 boolean[] check = new boolean[n*m];
	    	 dist.addAll(Collections.nCopies(n*m, INF));
	    	 dist.set(s, 0);
	    	 PriorityQueue<IntegerPair> pq = new PriorityQueue<IntegerPair>(1,			// must be greater than 1 
	    			 new Comparator<IntegerPair>() {
						@Override
						public int compare(IntegerPair i, IntegerPair j) {
							return i.first() - j.first();
						}
			});
	    	 // notice : IntegerPair of pq is different from IntegerPair of adjList
	    	 pq.offer(new IntegerPair(0, s));
	    	 while (!pq.isEmpty()) {
	    		 IntegerPair top = pq.poll();
	    		 int d = top.first();
	    		 int u = top.second();
	    		 // this line is special from orginal Dijkstra algorithm
	    		 // because we not remove old element, so old element (with bigger cost) always exist in heap
	    		 // we should check this case, to avoid time consuming
	    		 if (d > dist.get(u)) continue;
	    		 if (check[u]) continue;
	    		 check[u] = true;
	    		 Iterator<IntegerPair> it = adjList.get(u).iterator();
	    		 while (it.hasNext()) {
	    			 IntegerPair p = it.next();
	    			 int v = p.first();
	    			 int weightUV = p.second();
	    			 if (dist.get(u) + weightUV < dist.get(v)) {
	    				 dist.set(v, dist.get(u) + weightUV);
	    				 pq.offer(new IntegerPair(dist.get(v), v));
	    			 }
	    		 }
	    	 }
	    	 // c[n-1][m-1]
	    	pr.println(dist.get(n*m-1));
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
	
