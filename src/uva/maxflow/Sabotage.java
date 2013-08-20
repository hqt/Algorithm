package uva.maxflow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/** Code name : 10480 */
public class Sabotage {

	/** number of vertices */
	int n = 0;
	/** number of edges in graph */
	int m = 0;
	/** network */
	int[][] c;
	/**  residual network */
	int[][] f;
	/** source and vertex in network flow */
	int s = 0;
	int t = 1;
	/** marked vertices for bfs. at last phase, use this array to know min-cut set */
	boolean[] check;
	
	private int bfs() {
		check = new boolean[n];
		int[] trace = new int[n];
		trace[s] = -1;
		trace[t] = -1;
		Arrays.fill(check, false);
		Stack<Integer> stack = new Stack<Integer>();
		check[s] = true;
		stack.push(s);
		MainLoop:
		while (stack.size() != 0) {
			int v = stack.pop();
			for (int i = 0; i < n; i++)  {
				if ((c[v][i] - f[v][i] > 0) && check[i] == false) {
					stack.push(i);
					trace[i] = v;
					check[i] = true;
				}
				if (check[t]) break MainLoop;
			}
		}
		
		// find min value on this path
		int v = t;
		int min = Integer.MAX_VALUE;
		while (trace[v] != -1) {
			int u = trace[v];
			if (min > c[u][v] - f[u][v]) {
				min = c[u][v] - f[u][v];
			}
			v = trace[v];
		}
		
		// update the residual network base on this path
		v = t;
		while (trace[v] != -1) {
			int u = trace[v];
			f[u][v] += min;
			f[v][u] -= min;
			v = trace[v];
		}
		
		if (min == Integer.MAX_VALUE) return 0; // cannot found
		else return min;
	}
	
	private long forkFulkerson() {
		long res = 0;
		while(true) {
			int capacity = bfs();
			if (capacity == 0) break;
			res += capacity;
		}
		
		return res;
	}
	
	private void mincut() {
		List<Integer> a = new ArrayList<Integer>();
		List<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (check[i]) a.add(i); 
			else b.add(i);
		}
		
		// find all edges in this mincut
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				if (c[a.get(i)][b.get(j)] > 0) {
					System.out.println((a.get(i) + 1) + " " + (b.get(j) + 1));
				}
			}
		}
	}
	
	public void solve() {
		forkFulkerson();
		mincut();
		System.out.println();
		
	}
	
	public void run() {
		//Scanner scanner = new Scanner(System.in);
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("input.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(true) {
			n = scanner.nextInt();
			m = scanner.nextInt();
			if (n ==0 || m == 0) break;
			// initialize data
			c = new int[n][n];
			f = new int[n][n];
			// continue to read
			for (int i = 0; i < m; i++) {
				int u = scanner.nextInt() - 1;
				int v = scanner.nextInt() - 1;
				int w = scanner.nextInt();
				// because this is IP Protocol
				c[u][v] = w;
				c[v][u] = w;
			}
			solve();
		}
		scanner.close();
		
	}
	
	public static void main(String[] args) {
		Sabotage prob = new Sabotage();
		prob.run();
		
	}
}
