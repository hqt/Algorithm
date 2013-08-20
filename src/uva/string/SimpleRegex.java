package uva.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** UVA 494 */
public class SimpleRegex {
	
	public static void main(String[] args) throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     Pattern regex = Pattern.compile("[A-Za-z]+");
	     
	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	    
	     while (sc.hasNext()) {
	    	 String line = sc.nextLine();
	    	// pr.println(line);
	    	 Matcher matcher = regex.matcher(line);
	    	 int count = 0;
	    	 while (matcher.find()) count++;
	    	 pr.println(count);
	    	 
	     }

	     pr.close();
	     sc.close();
	}
}