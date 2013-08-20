package uva.datastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class ParanthesesBalance {
	
	public static void main(String[] args) throws Exception {
		ParanthesesBalance main = new ParanthesesBalance();
		main.run();
	}

	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     int n = sc.nextInt();
	     sc.nextLine();
	     while(n-- > 0) {
	    	 boolean check = true;
	    	 String line = sc.nextLine();
	    	 if (line.trim().length() == 0) {
	    		 System.out.println("Yes");
	    		 continue;
	    	 }

	    	 Stack<Character> stack = new Stack<Character>();
	    	 for (int i = 0; i < line.length(); i++) {
	    		 if (line.charAt(i) == '(' || line.charAt(i) == '[') stack.push(line.charAt(i));
	    		 else {
	    			 char c = '#';
	    			 if (!stack.isEmpty()) c = stack.pop();
	    			 if (line.charAt(i) == ')' && c == '(') continue;
	    			 else if (line.charAt(i) == ']' && c == '[') continue;
	    			 else {
	    				 check = false;
	    				 break;
	    			 }
	    		 }
	    	 }
	    	 if (!stack.isEmpty()) check = false;
	         if (check) System.out.println("Yes");
		     else System.out.println("No");
		
	     }
	     
	     sc.close();
	     pr.close();
	}
	
		
}

