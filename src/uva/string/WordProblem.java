package uva.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;


/* UVA 895 */
public class WordProblem {
	
	public static void main(String[] args) throws Exception {
		WordProblem main = new WordProblem();
		main.run();
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int[][] dict = new int[1010][26];
	     
	     // building a dictionary
	     int counter = 0;
	     while(true) {
	    	 String line = sc.nextLine();
	    	 if (line.equals("#")) break;
	    	 for (int i = 0; i < line.length(); i++) {
	    		 dict[counter][line.charAt(i) - 'a']++;
	    	 }
	    	 counter++;
	     }
	     
	     // process word
	     while(true) {
	    	 String line = sc.nextLine();
	    	 if (line.equals("#")) break;
	    	 int[] freq = new int[26];
	    	 for (int i = 0; i < line.length(); i++) {
	    		 char c = line.charAt(i);
	    		 if (c < 'a' || c > 'z') continue;
	    		 freq[c - 'a']++;
	    	 }
	    	 
	    	 // check this with word in dictionary
	    	 int count = 0;
	    	 for (int i = 0; i < counter; i++) {
	    		 boolean found = true;
	    		 for (int j = 0; j < 26; j++) {
	    			 if (dict[i][j] > 0 && dict[i][j] > freq[j]) {
	    				 found = false;
	    				 /*System.out.println("false at " + (char)(j + 'a') + " because " + dict[i][j] 
	    						 + " is greater than "  + freq[j]);*/
	    				 break;
	    			 }
	    		 }
	    		 if (found) count++;
	    	 }
	    	 System.out.println(count);
	     }
	     
	     sc.close();
	     pr.close();
	}
	
		
}


