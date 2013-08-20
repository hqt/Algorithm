package uva.shortestpath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FullTank {
	
	public static int INF = 100000000;
	public static void main(String[] args) throws Exception {
		FullTank main = new FullTank();
		main.run();
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int n = sc.nextInt();	// number of cities
	     int m = sc.nextInt();	// number of roads
	     
	     ArrayList<ArrayList<IntegerPairs>> adjList = new ArrayList<ArrayList<IntegerPairs>>();
	     for (int i = 0; i < n; i++) {
	    	 ArrayList<IntegerPairs> neighbor = new ArrayList<IntegerPairs>();
	    	 adjList.add(neighbor);
	     }
	     
	     int[] prices = new int[n];	// cost of fuel at each city
	     for (int i = 0; i < n; i++) prices[i] = sc.nextInt();

	     // distance between city
	     for (int i = 0; i < m; i++) {
	    	 int u = sc.nextInt();
	    	 int v = sc.nextInt();
	    	 int c = sc.nextInt();
	    	 adjList.get(u).add(new IntegerPairs(v, c));
	    	 adjList.get(v).add(new IntegerPairs(u, c));
	     }
	     
	     //	this array acts as optimized array in normal disjkstra
	     // put d outside for optimize
	     int[][] d = new int[1010][110];
	     int queries = sc.nextInt();
	     while(queries-- > 0) {
	    	 int capacity = sc.nextInt();
	    	 int start = sc.nextInt();
	    	 int end = sc.nextInt();
	    	 int res = -1;
	    	 
	    	 // Diskstra Implementation

	    	 for (int i = 0; i < n; i++)  {
	    		 for (int j = 0; j <= capacity; j++) {
	    			 d[i][j] = INF;
	    		 }
	    	 }
	    	 
	    	 PriorityQueue<State> pq = new PriorityQueue<State>(1, new Comparator<State>() {
				@Override
				public int compare(State s1, State s2) {
					return s1.val - s2.val;
				}
			});
	    	 State initialState = new State(start, 0, 0);
	    	 pq.add(initialState);
	    	 d[start][0] = 0;
	    	 
	    	 while (!pq.isEmpty()) {
	    		 State s = pq.poll();
	    		 if (s.loc == end) {
	    			res = s.val;
	    			break;	
	    		 }
	    		 
	    		 // if new state is not optimized as before. No need to use this state
	    		 if (d[s.loc][s.fuel] < s.val) continue;
	    		 
	    		 // OPTIMIZE
	    		 
	    		 // buy one more fuel. add to queue.
	    		 // (no need to buy until full capacity. just increase 1 step by step for optimize)
	    		 if (s.fuel + 1 <= capacity && d[s.loc][s.fuel+1] > s.val + prices[s.loc]) {
	    			 d[s.loc][s.fuel+1] = s.val + prices[s.loc];
	    			 pq.add(new State(s.loc, s.fuel + 1, s.val + prices[s.loc]));
	    		 }
	    		 
	    		 // check if can go to another vertex. If can. add into queue
	    		 for(int i = 0; i < adjList.get(s.loc).size(); i++) {
	    			 int u = adjList.get(s.loc).get(i).first();
	    			 int distance = adjList.get(s.loc).get(i).second();
	    			 if (s.fuel >= distance && d[u][s.fuel-distance] > s.val) {
	    				 d[u][s.fuel-distance] = s.val;		// update new state
	    				 pq.add(new State(u, s.fuel - distance, s.val));
	    			 }
	    		 }
	    	} 
	    	 
	    	 if (res != -1) {
	    		 System.out.println(res);
	    	 }
	    	 else {
	    		 System.out.println("impossible");
	    	 }
	    	 
	     }
	     
	     pr.close();
	     sc.close();
	}
	
	public static class State {
		int loc;
		int fuel;
		int val;
		public State(int loc, int fuel, int val) { this.loc = loc; this.fuel = fuel; this.val =val;}
	}
	
	public static class IntegerPairs implements Comparable<Object> {
		  Integer _first, _second;

		  public IntegerPairs(Integer f, Integer s) {
		    _first = f;
		    _second = s;
		  }

		  public int compareTo(Object o) {
		    if (this.first() != ((IntegerPairs )o).first())
		      return this.first() - ((IntegerPairs )o).first();
		    else
		      return this.second() - ((IntegerPairs )o).second();
		  }

		  Integer first() { return _first; }
		  Integer second() { return _second; }
		}


}


