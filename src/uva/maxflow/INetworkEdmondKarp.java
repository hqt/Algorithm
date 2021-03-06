package uva.maxflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/* 820  */
public class INetworkEdmondKarp {
	
	public static void main(String[] args) throws Exception {
		INetworkEdmondKarp main = new INetworkEdmondKarp();
		main.run();
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int count = 0;
	     while (true) {
	    	 count++;
	    	 int n = sc.nextInt();
	    	 if (n == 0) break;
	    	 System.out.println("Network "+ count);
	    	 // initialize
	    	 long[][] c = new long[n][n];
	    	 int s = sc.nextInt() - 1;
	    	 int t = sc.nextInt() - 1;
	    	 int m = sc.nextInt();
	    	 for (int j = 0; j < m; j++) {
	    		 int u = sc.nextInt() - 1;
	    		 int v = sc.nextInt() - 1;
	    		 int w = sc.nextInt();
	    		 c[u][v] += w;
	    		 c[v][u] += w;
	    	 }
	    	 
	    	 EdmonKarpStandford g = new EdmonKarpStandford();
	    	 g.initialize(n);
	    	 g.cap = c;
	    	 g.GetMaxFlow(s, t);
	    	 long res = g.totflow;
	    	 System.out.println("The bandwidth is " + res + ".");
	    	 System.out.println();
	     }
	     pr.close();
	     sc.close();
	}
	
}

class EdmonKarpStandford {
	
	final long INF = Long.MAX_VALUE / 4;
	int MAXN = 100;
	int n;
	long[][] cap, flow, cost;
	boolean[] found;
	long[] dist, pi, width;
	IntegerPair[] dad;
	long totflow, totcost;
	
	public void initialize(int n) {
		this.n = n;
		cap = new long[n][n];
		flow = new long[n][n];
		cost = new long[n][n];
		found = new boolean[n];
		dist = new long[n];
		pi = new long[n];
		width = new long[n];
		dad = new IntegerPair[n];		
	}
	
	public void AddEdge(int from, int to, long cap, long cost) {
		this.cap[from][to] = cap;
		this.cost[from][to] = cost;
	}
	
	void Relax(int s, int k, long cap, long cost, int dir) {
	    long val = dist[s] + pi[s] - pi[k] + cost;
	    if (cap > 0 && val < dist[k]) {
	      dist[k] = val;
	      dad[k] = new IntegerPair(s, dir);
	      width[k] = Math.min(cap, width[s]);
	    }
	  }
	
	long Dijkstra(int s, int t) {
		Arrays.fill(found, 0, n, false);
		Arrays.fill(dist, 0, n, INF);
		Arrays.fill(width, 0, n, 0);
	    dist[s] = 0;
	    width[s] = INF;
	    while (s != -1) {
	      int best = -1;
	      found[s] = true;
	      for (int k = 0; k < n; k++) {
	        if (found[k]) continue;
	        Relax(s, k, cap[s][k] - flow[s][k], cost[s][k], 1);
	        Relax(s, k, flow[k][s], -cost[k][s], -1);
	        if (best == -1 || dist[k] < dist[best]) best = k;
	      }
	      s = best;
	    }

	    for (int k = 0; k < n; k++)
	      pi[k] = Math.min(pi[k] + dist[k], INF);
	    return width[t];
	}

	  void GetMaxFlow(int s, int t) {
	    totflow = 0;
	    totcost = 0;
	    while (true) {
	    	long amt = Dijkstra(s, t);
	    	if (amt == 0) break;
	    	totflow += amt;
	    	for (int x = t; x != s; x = dad[x].first()) {
	        if (dad[x].second() == 1) {
	          flow[dad[x].first()][x] += amt;
	          totcost += amt * cost[dad[x].first()][x];
	        } else {
	          flow[x][dad[x].first()] -= amt;
	          totcost -= amt * cost[x][dad[x].first()];
	        }
	      }
	    }
	  }
}

class IntegerPair implements Comparable {
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
