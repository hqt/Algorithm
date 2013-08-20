package uva.graph;

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
import java.util.Stack;

/**
 * <a href="http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3053">Dominator</a>
 */
public class Dominator {
	
	int n;
	int test;
	boolean[][] c;
	PrintWriter pr;
	
	public void print(String s) {
		pr.println(s);
	}
	
	public void run() {
		Scanner sc = null;
		pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		try {
			sc = new Scanner(new File("input.txt"));
			//sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		}
		catch (Exception e) {}
		
		test = sc.nextInt();
		for (int i = 0; i < test; i++) {
			n = sc.nextInt();
			c = new boolean[n][n];
			for (int j = 0; j < n; j++) {
				for (int t = 0; t < n; t++) {
					int tmp = sc.nextInt();
					if (tmp == 0) c[j][t] = false;
					else c[j][t] = true;
				}
			}
			solve(i);
		}
		
		sc.close();
		pr.close();
	}
	
	boolean[] reach;
	boolean[] reachDis;
	public void solve(int t) {
		print("Case " + (t + 1) +":");
		reach = new boolean[n];
		reachDis = new boolean[n];
		Arrays.fill(reach, false);
		bfs();
		
		/** try to disable vertex by vertex */
		
		printline();
		// first vertext is special case
		pr.print("|");
		for (int i = 0; i < n; i++) {
			if (reach[i]) pr.print("Y|");
			else pr.print("N|");
		}
		pr.println();
		printline();

		for (int i = 1; i < n; i++) {
			// temporary remove i vertex
			dfs(i);
			pr.print("|");
			for (int j = 0; j < n; j++) {
				if (reach[j] != reachDis[j]) {
					pr.print("Y|");
				}
				else {
					pr.print("N|");
				}
			}
			pr.println();
			printline();
		}
	}
		
	
	public void printline() {
		pr.print("+");
		for (int i = 0; i < 2*n-1; i++) pr.print("-");
		pr.println("+");
	}
	
	public void dfs(int t) {
		Arrays.fill(reachDis, false);
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);
		reachDis[0] = true;
		while (!stack.isEmpty()) {
			int u = stack.pop();
			for (int v = 1; v < n; v++) {
				if (v != t && c[u][v] && !reachDis[v]) {
					stack.push(v);
					reachDis[v] = true;
				}
			}
		}
	}
	
	/** bfs all graph */
	public void bfs() {
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(0);
		reach[0] = true;
		while (!queue.isEmpty()) {
			int u = queue.poll();
			for (int v = 0; v < n; v++) {
				if (c[u][v] && !reach[v]) {
					reach[v] = true;
					queue.add(v);
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		Dominator prob = new Dominator();
		prob.run();
	}
	

}
