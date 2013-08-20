package uva.stuff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

/**  993  : Product of digits */
public class ProductOfDigits {
	
	public static Stack<Integer> stack; 
	
	public static void fac(long n) {
		/** divide all n from 9 -> 2. we divide from big to small because choose smallest number */
		for (int i = 9; i >= 2; i--) {
			if (n % i == 0) {
				while (n % i == 0) {
					stack.push(i);
					n = n / i;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = null;
		PrintWriter pr = null;
		
		try {
			sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			sc = new Scanner(new File("input.txt"));
			pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		}
		catch (Exception e) {}
		
		int n = sc.nextInt();
		
		for (int i = 0; i < n; i++) {
			long num = sc.nextInt();
			if (num == 0 || num == 1) { pr.println(num);continue;}
			stack = new Stack<Integer>();
			fac(num);
			// check if multiply in stack equals no n 
			// n can fact into number from 2->9. not prime larger than 10. OK
			long product = 1;
			for (int j = 0; j < stack.size(); j++) {
				product *= stack.get(j);
			}
			if (product != num) { pr.println(-1); continue;}
			StringBuilder buf = new StringBuilder();
			while (!stack.isEmpty()) {
				buf.append(stack.pop());
			}
			pr.println(buf.toString());
		}
		
		pr.close();
		sc.close();
	}
}