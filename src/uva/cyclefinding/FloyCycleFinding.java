package uva.cyclefinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;


/* UVA 350 */
public class FloyCycleFinding {
	
	public static void main(String[] args) throws Exception {
		FloyCycleFinding main = new FloyCycleFinding();
		main.run();
	}

	static int Z, I, M, L, mu, lambda;

	  static int f(int x) { return (Z * x + I) % M; }

	  static void floydCycleFinding(int x0) { // function "int f(int x)" must be defined earlier
	    // 1st part: finding k*mu, hare's speed is 2x tortoise's
	    int tortoise = f(x0), hare = f(f(x0)); // f(x0) is the node next to x0
	    while (tortoise != hare) { tortoise = f(tortoise); hare = f(f(hare)); }
	    // 2nd part: finding mu, hare and tortoise move at the same speed
	    mu = 0; hare = x0;
	    while (tortoise != hare) { tortoise = f(tortoise); hare = f(hare); mu++; }
	    // 3rd part: finding lambda, hare moves, tortoise stays
	    lambda = 1; hare = f(tortoise);
	    while (tortoise != hare) { hare = f(hare); lambda++; }
	  }
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int ntest = 0;
	     while (true) {
	    	 ntest++;
	    	 Z = sc.nextInt();
	    	 I = sc.nextInt();
	    	 M = sc.nextInt();
	    	 L = sc.nextInt();
	    	 
	    	 if (Z == 0 && I == 0 && M == 0 && L == 0) break;
	    	 floydCycleFinding(L);
	    	 System.out.printf("Case %d: %d\n", ntest, lambda);
	     }
	     sc.close();
	     pr.close();

		
	}
	
	
}


