package uva.maxflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/* 820  */
public class InternetNetwork {
	
	public static void main(String[] args) throws Exception {
		InternetNetwork main = new InternetNetwork();
		main.run();
	}
	
	int[][] c;
	int[][] f;
	boolean[] check;
	int[] trace;
	int n, m, s, t;
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int count = 0;
	     while (true) {
	    	 count++;
	    	 n = sc.nextInt();
	    	 if (n == 0) break;
	    	 // initialize
	    	 c = new int[n][n];
	    	 f = new int[n][n];
	    	 check = new boolean[n];
	    	 trace = new int[n];
	    	 s = sc.nextInt() - 1;
	    	 t = sc.nextInt() - 1;
	    	 m = sc.nextInt();
	    	 for (int j = 0; j < m; j++) {
	    		 int u = sc.nextInt() - 1;
	    		 int v = sc.nextInt() - 1;
	    		 int w = sc.nextInt();
	    		 c[u][v] += w;
	    		 c[v][u] += w;
	    	 }
	    	 
	    	 solve(count);
	    	 
	     }
	     pr.close();
	     sc.close();
	}
	
	public void solve(int test) {
		long res = forkFulkerson();
		System.out.println("Network "+ test);
		System.out.println("The bandwidth is " + res + ".");
		System.out.println();
	}

	public long forkFulkerson() {
		long res = 0;
		while(true) {
			int capacity = bfs();
			if (capacity == 0) return res;
			else res += capacity;
		}
	}
	
	public int bfs() {
		Arrays.fill(check, false);
		check[s] = true;
		trace[s] = -1;
		trace[t] = -1;	// mark
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(s);
		MainLoop:
		while (!queue.isEmpty()) {
			int v = queue.poll();
			for (int i = 0; i < n; i++) {
				if (c[v][i] - f[v][i] > 0 && !check[i]) {
					check[i] = true;
					queue.add(i);
					trace[i] = v;
				}
				if (check[t]) break MainLoop;
				
			}
		}
		
		// find min
		int v = t;
		int min = Integer.MAX_VALUE;
		while (trace[v] != -1) {
			int u = trace[v];
			if (c[u][v] - f[u][v] < min) min = c[u][v] - f[u][v];
			v = u;
		}
		
		// incr
		v = t;
		while (trace[v] != -1) {
			int u = trace[v];
			f[u][v] += min;
			f[v][u] -= min;
			v = u;
		}
		
		if (min == Integer.MAX_VALUE) return 0;
		else return min;
	}
}