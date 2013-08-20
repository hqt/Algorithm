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

/* 10801 */

public class LiftHopping {
	
	public static final int INF = 1000000000;
	
	public static void main(String[] args) throws Exception {
		LiftHopping main = new LiftHopping();
		main.run();
	}

	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	   
	     while (sc.hasNextInt()) {
	    	 int elevator = sc.nextInt();
	    	 int nfloor = 100;
	    	 int k = sc.nextInt() + 1;
	    	 int n = elevator * 100 + 1;
	    	 int[] T = new int[elevator];
	    	 ArrayList<ArrayList<IntegerPair>> adjList = new ArrayList<ArrayList<IntegerPair>>();
	    	 for (int i = 0; i < n; i++) {
	    		 ArrayList<IntegerPair> neighbor = new ArrayList<IntegerPair>();
	    		 adjList.add(neighbor);
	    	 }
	    	 
	    	 // time each elevator go down or up one floor
	    	 for (int i = 0; i < elevator; i++) {
	    		 T[i] = sc.nextInt();
	    	 }
	    	 
	    	 sc.nextLine();
	    	 // floor can reach of each elevator
	    	 for (int i = 0; i < elevator; i++) {
	    		 String s = sc.nextLine();
	    		 Scanner sc1 = new Scanner(s);
	    		 int previous = -1;
	    		 while (sc1.hasNextInt()) {
	    			 int f = sc1.nextInt()  + 1;
	    			 if (previous == -1) {
	    				 previous = f;
	    				 continue;
	    			 }
	    			 int time = (f - previous) * T[i];
	    			 // connect two floor previous and f
	    			 // two way. because can go up and down
	    			 adjList.get(i * nfloor + previous).add(new IntegerPair(i * nfloor + f, time));
	    			 adjList.get(i * nfloor + f).add( new IntegerPair(i * nfloor + previous, time));
	    			 previous = f;
	    		 }
	    		 sc1.close();
	    	 }
	    	 
	    	 // connect virtual-source to another source (first floor of each elevator kind)
	    	 for (int i = 0; i < elevator; i++) {
	    		 adjList.get(0).add(new IntegerPair(i * nfloor + 1, 0));
	    	 }
	    	 
	    	 // connect floor of elevator ith and jth
	    	 for (int i = 1; i <= nfloor; i++) {	// floor
	    		 for (int j = 0; j < elevator; j++) {	// elevator
	    			 for (int t = j + 1; t < elevator; t++) {	// elevator
	    				 adjList.get(j * nfloor + i).add(new IntegerPair(t * nfloor + i, 60));
	    				 adjList.get(t * nfloor + i).add(new IntegerPair(j * nfloor + i, 60));
	    			 }
	    		 }
	    	 }
	    	 
	    	 // disjkstra implement
	    	 int s = 0;
	    	 ArrayList<Integer> dist = new ArrayList<Integer>();
	    	 dist.addAll(Collections.nCopies(n, INF));
	    	 dist.set(s, 0);
	    	 PriorityQueue<IntegerPair> pq = new PriorityQueue<IntegerPair>(1,
	    			 new Comparator<IntegerPair>() {
						@Override
						public int compare(IntegerPair i, IntegerPair j) {
							return i.first() - j.first();
						}
					});
	    	 pq.offer(new IntegerPair(0, s));
	    	 while (!pq.isEmpty()) {
	    		 IntegerPair top = pq.poll();
	    		 int d = top.first();
	    		 int u = top.second();
	    		 if (d > dist.get(u)) continue;		// special case for this extend algorithm
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
	    	 
	    	 // find min value of k-elevator
	    	 int min = INF;
	    	 for (int i = 0; i < elevator; i++) {
	    		 int val = dist.get(i * nfloor + k);
	    		 if (val < min) min = val;
	    	 }
	    	 
	    	 if (min == INF) System.out.println("IMPOSSIBLE");
	    	 else System.out.println(min);
	    	 
	     }
	     
	     pr.close();
	     sc.close();
	}

}
	
class IntegerPair implements Comparable {
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