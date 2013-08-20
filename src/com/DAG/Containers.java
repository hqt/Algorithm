package com.DAG;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/* UVA 1062 */
public class Containers {
	
	public static void main(String[] args) throws Exception {
		Containers main = new Containers();
		main.run();
	}
	
	
	boolean[] v;
	int[] pair;
	ArrayList<ArrayList<Integer>> adjList;
	
	public int path(int i) {
		if(v[i]) return 0;
		v[i] = true;
		for (int j : adjList.get(i)) {
			if (pair[j] == -1 || path(pair[j]) != 0) {
				pair[j] = i;
				return 1;
			}
		}
		return 0;
	}
	
	public int match() {
		int match = 0;
		Arrays.fill(pair, -1);
		for (int i = 0; i < adjList.size(); i++) {
			Arrays.fill(v, false);
			match += path(i);
		}
		return match;
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int ntestcase = 0;
	     v = new boolean[1010];
	     pair = new int[1010];
	     while(true) {
	    	 ntestcase++;
	    	 String line = sc.nextLine().trim();
	    	 if (line.length() == 3 && line.charAt(0) == 'e' && line.charAt(1) == 'n' && line.charAt(2) == 'd') break;
	    	 int n = line.length();
	    	 adjList = new ArrayList<ArrayList<Integer>>();
	    	 for (int i = 0; i < n; i++) {
	    		 ArrayList<Integer> neighbor = new ArrayList<Integer>();
	    		 adjList.add(neighbor);
	    	 }
	    	 
	    	 // create DAG graph : Directed Acyclic Graph
	    	 // exist edge if this vertex appear after and have lower priority on alphabet
	    	 for (int i = 0; i < n; i++) {
	    		 for (int j = i + 1; j < n; j++) {
	    			 if (line.charAt(j) <= line.charAt(i)) adjList.get(i).add(j);
	    		 }
	    	 }
	    	 
	    	 // finding min path cover on DAG
	    	 int MinPathCover = n - match();
	    	 System.out.println("Case " + ntestcase+": " + MinPathCover);
	     }
	     
	     pr.close();
	     sc.close();
	}
}
