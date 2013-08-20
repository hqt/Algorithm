package uva.maxflow;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/** 10594 */
public class DataFlow {
	
	int n;
	int m;
	int[][] c;
	int[][] f;
	int s;
	int t;
	int capacity;
	int flow;
	
	public void print(String s) {
		System.out.println(s);
	}
	
	public void run() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("input.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(scanner.hasNext()) {
			n = scanner.nextInt();
			m = scanner.nextInt();
			
			print("N: " + n);
			print("M: " + m);
			c = new int[n][n];
			f = new int[n][n];
			s = 0;
			t = n - 1;
			
			for (int i = 0; i < m; i++) {
				int u = scanner.nextInt() - 1;
				int v = scanner.nextInt() - 1;
				int w = scanner.nextInt();
				c[u][v] = w;
				c[v][u] = w;
			}
			
			flow = scanner.nextInt();
			capacity = scanner.nextInt();
			print("Flow: " + flow);
			print("Cap: " + capacity);
			
			solve();
		}
	}
	
	public void solve() {
		int times = flow / capacity;
		if (flow % capacity != 0) times++;
		print("TIMES: " + times);
		long res = forkFulkerson() * times;
		if (res < 0) System.out.println("Impossible.");
		else System.out.println(res * times);
	}

	public long forkFulkerson() {
		long res = 0;
		while(true) {
			long val = bfs();
			if (val == Long.MAX_VALUE) return -1;
			if (val == 0) return res;
			res += val;
			print("RES: " + res);
		}
	}
	public long bfs() {
		boolean[] check = new boolean[n];
		Arrays.fill(check, false);
		int[] trace = new int[n];
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(s);
		check[s] = true;
		trace[s] = -1;
		trace[t] = -1;
		MainLoop:
		while (!stack.isEmpty()) {
			int u = stack.pop();
			print("POP: " + u);
			for (int i = 0; i < n; i++) {
				if (c[u][i] - f[u][i] > 0 && !check[i]) {
					print("PUSH: " + i);
					stack.push(i);
					check[i] = true;
					trace[i] = u;
				}
				if (check[t]) break MainLoop;
			}
		}
		
		// find min
		long min = Long.MAX_VALUE;
		int v = t;
		while(trace[v] != -1) {
			int u = trace[v];
			if (c[u][v] - f[u][v] < min) min = c[u][v] - f[u][v];
			v = trace[v];
		}
		
		// assign
		v = t;
		while (trace[v] != -1) {
			int u = trace[v];
			f[u][v] += min;
			f[v][u] -= min;
			v = trace[v];
		}
		
		if (min == Long.MAX_VALUE) min = 0;
		
		print("Argument path: " + min); 
		return min;
	}
	public static void main(String[] args) {
		DataFlow prob = new DataFlow();
		prob.run();
		
	}
}
