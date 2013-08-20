package uva.numbertheory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

/** UVA 10219 */
public class FindTheWays {
	
	public static void main(String[] args) throws Exception {
		FindTheWays main = new FindTheWays();
		main.run();
	}

	public void run() throws Exception {

		 Scanner sc = null;
	     PrintWriter pr = null;

	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));

	     while (sc.hasNextInt()) {
	    	 int n = sc.nextInt();
	    	 int k = sc.nextInt();
	    	 
	    	 double res = 0.0;
	    	 for (int i = n - k + 1; i <= n; i++) {
	    		 res += Math.log10(i);
	    	 }
	    	 for (int i = 1; i <= k; i++) {
	    		 res -= Math.log10(i);
	    	 }
	    	 
	    	 int fres = (int) (Math.floor(res + 0.00000001) + 1);
	    	 
	    	 System.out.println(fres);
	     }    
	     sc.close();
	     
	}
}

