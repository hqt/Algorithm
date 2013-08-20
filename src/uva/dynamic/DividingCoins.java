package uva.dynamic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class DividingCoins {
	
	public static void main(String[] args) throws Exception {
		DividingCoins main = new DividingCoins();
		main.run();
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));

	     int n = sc.nextInt();
	     
	     while (n-- > 0) {
	    	 int m = sc.nextInt();
	    	 int[] coins = new int[m];
	    	 int total = 0;
	    	 for (int i = 0; i < m; i++) {
	    		 coins[i] = sc.nextInt();
	    		 total += coins[i];
	    	 }
	    	 
	    	 // initalize variable
	    	 int W = total / 2;
	    	 int[] memo = new int[W + 10];
	    	 // using dynamic programming
	    	 for (int i = 0; i < m; i++) {
	    		 for (int w = 1; w <= W; w++) {
	    			 if (w - coins[i] >= 0) {
	    				 memo[w] = Math.max(memo[w], memo[w - coins[i]] + coins[i]);
	    			 }
	    		 }
	    	 }
	    	 
	    	 int second = total - memo[W];
	    	 int diff = Math.abs(memo[W] - second);
	    	 System.out.println(diff);
	    	
	     }
	     
	   	 sc.close();
	     pr.close();
	}
}
