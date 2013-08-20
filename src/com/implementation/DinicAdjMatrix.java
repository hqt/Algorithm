package com.implementation;

import java.util.Arrays;

public class DinicAdjMatrix {
	
	final int MAXN = 100;
	final int INF = 1000000000;
	int n, s, t;
	int[][] c = new int[MAXN][MAXN];
	int[][] f = new int[MAXN][MAXN];
	int[] d = new int[MAXN];
	int[] ptr = new int[MAXN];
	int[] q = new int[MAXN];
	
	public boolean bfs() {
		int qh = 0, qt = 0;
		q[qt++] = s;
		Arrays.fill(d, -1);
		d[s] = 0;
		while (qh < qt) {
			int v = q[qh++];
			for (int to = 0; to < n; ++to) {
				if (d[to] == -1 && f[v][to] < c[v][to]) {
					q[qt++] = to;
					d[to] = d[v] + 1;
				}
			}
		}
		return d[t] != -1;
	}
	
	public int dfs(int v, int flow) {
		if (flow == 0) return 0;
		if (v == t) return flow;
		for (int to = ptr[v]; to < n; to++) {
			if (d[to] != d[v] + 1) continue;
			int pushed = dfs(to, Math.min(flow, c[v][to] -f[v][to]));
			if (pushed > 0) {
				f[v][to] += pushed;
				f[to][v] -= pushed;
				return pushed;
			}
		}
		return 0;
	}
	
	int dinic() {
		int flow = 0;
		for (;;) {
			if (!bfs()) break;
			Arrays.fill(ptr, 0);
			while(true) {
				int pushed = dfs(s, INF);
				flow += pushed;
				if (pushed == 0) break;
			}
		}
		return flow;
	}
	

}
