package uva.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class PasswordSearch {
	
	public static void main(String[] args) throws Exception {
		PasswordSearch main = new PasswordSearch();
		main.run();
	}

	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     while (sc.hasNextInt()) {
	    	 int n = sc.nextInt();
	    	 String s = sc.next();
	    	 
	    	 // proccess this test case
	    	 Map<String, Integer> hash = new HashMap<String, Integer>();
	    	 int max = -1;
	    	 String res = "";
	    	 for (int i = 0; i < s.length() - n; i++) {
	    		 // get substring
	    		 StringBuilder sb = new StringBuilder();
	    		 for (int j = i; j <=(i + n - 1); j++) {
	    			 sb.append(s.charAt(j));
	    		 }

	    		 // compare in map
	    		 String substr = sb.toString();
	    		 if (hash.containsKey(substr)) {
	    			 int freq = hash.get(substr) + 1;
	    			 hash.put(substr, freq);
	    			 if (freq > max) {
	    				 max = freq;
	    				 res = substr;
	    			 }
	    		 }
	    		 else {
	    			 hash.put(substr, 1);
	    			 if (max < 1) {
	    				 max = 1;
	    				 res = substr;
	    			 }
	    		 }
	    	 }
	    	 
	    	 System.out.println(res);
	     }
	     
	     sc.close();
	     pr.close();
	}
	
		
}

