package uva.datastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordAmalgamation {
	
	public static void main(String[] args) throws Exception {
		WordAmalgamation main = new WordAmalgamation();
		main.run();
	}

	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     // first. read all words from dictionary
	     // cut into chars and sort. this help us for permuation word later
	     Map<String, String> map = new HashMap<String, String>();
	     //Map<String, String> map = new LinkedHashMap<String, String>();
	     while (sc.hasNext()) {
	    	 String line = sc.nextLine();
	    	 if (line.length() == 6 && line.equals("XXXXXX")) break;
	    	 char[] word = line.toCharArray();
	    	 Arrays.sort(word);
	    	 System.out.println(line + "  "+ new String(word));
	    	 map.put(line, new String(word));
	     }
	     
	     // second. read all word. and find does it exist in dictionary
	     while (sc.hasNext()) {
	    	 boolean check = false;
	    	 String line = sc.nextLine();
	    	 if (line.length() == 6 && line.equals("XXXXXX")) break;
	    	 char[] word = line.toCharArray();
	    	 Arrays.sort(word);
	    	 String sortedWord = new String(word);
	    	 
	    	 // browse all map. Using EntrySet
	    	 for (Map.Entry<String, String> entry : map.entrySet()) {
	    		 if (entry.getValue().equals(sortedWord)) {
	    			 System.out.println(entry.getKey());
	    			 check = true;
	    		 }
	    	 }
	    	 
	    	 if (!check) System.out.println("NOT A VALID WORD");
	    	 System.out.println("******");
	     }
	     
	     sc.close();
	     pr.close();
	}
}

