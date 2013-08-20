import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOTemplates {
	
	// Using this method for small to medium input.
	// many built-in functions
	public void ScannerMethod() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     pr.close();
	     sc.close();
	}
	
	// Using this method for large input. Using Custom class InputReader
	// just some simple method. and we must know how many line of input. (ex : input n test case)
	// Petr uses this on Codeforces (Performance and without tricky input)
	public void InputReaderMethod() throws Exception {
		PrintWriter pr = null;
		InputReader sc = null;
		
		pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		sc = new InputReader(System.in);
		sc = new InputReader(new FileInputStream(new File("input.txt")));
		
		pr.close();
	}
	
	// Using this method for large input. Use Java BufferedReader
	// Must code again by hand. 
	// Using when we does not know line of input (ex : how many test case)
	public void BufferedReaderMethod() throws Exception {
		PrintWriter pr = null;
		BufferedReader br = null;
		
		pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		br = new BufferedReader(new InputStreamReader(System.in));
		br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt"))));

		String line = null;
	    String[] tokens = null;
		
	    while ((line = br.readLine()) != null && !line.trim().equals("")) {
	    	tokens = line.split(" "); 	// first input
	    	
	    	// the rest in loop
	    	tokens = br.readLine().split(" ");
	    	int a = Integer.parseInt(tokens[0]);
	    	double b = Integer.parseInt(tokens[1]);
	    	System.out.println(a + b);
	    }
	    
	    pr.close();
	    br.close();
	}
	
}
