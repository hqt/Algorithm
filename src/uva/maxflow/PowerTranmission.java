package uva.maxflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


/* 10330  */
public class PowerTranmission {
	
	public static void main(String[] args) throws Exception {
		PowerTranmission main = new PowerTranmission();
		main.run();
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	    
	    while (sc.hasNextInt()) {
	    	int regulators = sc.nextInt();
	    	int n = 2*regulators + 2;
	    	int f_out = regulators;
	    	PushRelabel g = new PushRelabel(n);
	 
	    	// limit of vertex
	    	for (int u = 1; u <= regulators; u++) {
	    		int w = sc.nextInt();
	    		// C[u_in][u_out] = w
	    		g.AddEdge(u, u + f_out, w);
	    	}
	    	
	    	// limit of edge
	    	int m = sc.nextInt();
	    	for (int i = 0; i < m; i++) {
	    		int u = sc.nextInt();
	    		int v = sc.nextInt();
	    		int w = sc.nextInt();
	    		// C[u_out][v_in] = w
	    		g.AddEdge(u + f_out, v, w);
	    	}
	    	
	    	// special node
	    	int s = 0;
	    	int t = 2*regulators + 1;
	    	int b = sc.nextInt();
	    	int d = sc.nextInt();
	    	int INF = Integer.MAX_VALUE/4;
	    	// connect s to all b node with INF
	    	for (int i = 0; i < b; i++) {
	    		int u = sc.nextInt();
	    		// C[s][u] = INF => C[s][u_in] = INF
	    		g.AddEdge(s, u, INF);
	    	}
	    	// connect all d nodes to t with INF
	    	for (int i = 0; i < d; i++) {
	    		int u = sc.nextInt();
	    		// C[u][t] = INF => C[u_out][t] = INF
	    		g.AddEdge(u + f_out, t, INF);
	    	}
	    	
	    	long res = g.GetMaxFlow(s, t);
	    	System.out.println(res);
	    	
	    }
	     
	     pr.close();
	     sc.close();
	}
	
	static class Edge {
		int from, to, cap, flow, index;
		Edge(int from, int to, int cap, int flow, int index) {
			this.from = from;
			this.to = to;
			this.cap = cap;
			this.flow = flow;
			this.index = index;
		}
	}

	static class PushRelabel {
		int N;
		ArrayList<ArrayList<Edge>> G;
		long[] excess;
		int[] dist, count;
		boolean[] active;
		Queue<Integer> Q = new LinkedList<Integer>();
		
		public PushRelabel(int N) {
			this.N = N;
			G = new ArrayList<ArrayList<Edge>>();
			for (int i = 0; i < N; i++) G.add(new ArrayList<Edge>());
			excess = new long[N];
			dist = new int[N];
			active = new boolean[N];
			count = new int[2*N];
		}
		
		void AddEdge(int from, int to, int cap) {
			G.get(from).add(new Edge(from, to, cap, 0, G.get(to).size()));
			if (from == to) G.get(from).get(G.size()-1).index++;
			G.get(to).add(new Edge(to, from, 0, 0, G.get(from).size() -1));
			
		}
		
		void Enqueue(int v) {
			if (!active[v] && excess[v] > 0) { active[v] = true; Q.add(v);}
		}
		
		void Push(Edge e) {
			int amt = (int) Math.min(excess[e.from], (long)(e.cap - e.flow));
			if (dist[e.from] <= dist[e.to]|| amt == 0) return;
			e.flow += amt;
			G.get(e.to).get(e.index).flow -= amt;
			excess[e.to] += amt;
			excess[e.from] -= amt;
			Enqueue(e.to);
		}
		
		void Gap(int k) {
			for (int v = 0; v < N; v++) {
				if (dist[v] < k) continue;
				count[dist[v]]--;
				dist[v] = Math.max(dist[v],  N + 1);
				count[dist[v]]++;
				Enqueue(v);
			}
		}
		
		void Relabel(int v) {
			count[dist[v]]--;
			dist[v] = 2 * N;
			for (int i = 0; i < G.get(v).size(); i++)
				if (G.get(v).get(i).cap - G.get(v).get(i).flow > 0)
					dist[v] = Math.min(dist[v], dist[G.get(v).get(i).to] +1);
			count[dist[v]]++;
			Enqueue(v);
		}
		
		void Discharge(int v) {
			for (int i = 0; excess[v] > 0 && i < G.get(v).size(); i++) Push(G.get(v).get(i));
			if (excess[v] > 0) {
				if (count[dist[v]] == 1)
					Gap(dist[v]);
				else
					Relabel(v);
			}
		}
		
		long GetMaxFlow(int s, int t) {
			count[0] = N - 1;
			count[N] = 1;
			dist[s] = N;
			active[s] = active[t] = true;
			for (int i = 0; i < G.get(s).size(); i++) {
				excess[s] += G.get(s).get(i).cap;
				Push(G.get(s).get(i));
			}
			
			while (!Q.isEmpty()) {
				int v = Q.poll();
				active[v] = false;
				Discharge(v);
			}
			
			long totflow = 0;
			for (int i = 0; i < G.get(s).size(); i++) totflow += G.get(s).get(i).flow;
			return totflow;
		}
	}

}

