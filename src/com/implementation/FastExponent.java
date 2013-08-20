package com.implementation;

public class FastExponent {
	
	public static int MAXN = 105;
	
	public static int fastExp(int base, int p) {
		if (p == 0) return 1;
		if (p == 1) return base;
		int res = fastExp(base, p / 2);
		res *= res;
		if (p % 2 == 1) res *= base;
		return res;
	}
	
	public static int[][] matMul(int[][] a, int[][] b) {
		int n = a.length;
		int[][] ans = new int[n][n];
		int i, j, k;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				for (ans[i][j] = k = 0; k < n; k++) {
					ans[i][j] += a[i][k] * b[k][j];	// do modulo arimethic here
				}
			}
		}
		return ans;
	}
	
	/* this matrix power will have structure like fastExp */
	public static int[][] matPow(int[][] base, int p) {
		int n = base.length;
		int[][] ans = new int[n][n];
		for (int i = 0; i < n; i++) for (int j = 0; j <  n; j++)
			ans[i][j] = (i == j) ? 1 : 0;		// prepare identity matrix
		while (p > 0) {							// iterative version of Divide & Conquer exponentiation
			if ((p & 1) > 0) ans = matMul(ans, base);	// check if p is odd (last bit is on)
			base = matMul(base, base);			// square the base
			p >>= 1;							// divide p by 2
		}
		return ans;
	}
}
