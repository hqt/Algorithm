package SPOJ;

import java.util.Scanner;
import java.util.Stack;

/**
 	Problem code: ONP
	Transform the algebraic expression with brackets into RPN form (Reverse Polish Notation). 
	Two-argument operators: +, -, *, /, ^ (priority from the lowest to the highest), brackets ( ).
	Operands: only letters: a,b,...,z. Assume that there is only one RPN form (no expressions like a*b*c).d

	Input:
	 3
	(a+(b*c))
	((a+b)*(z+x))
	((a+t)*((b+(a+c))^(c+d)))
	
	Output:
	abc*+
	ab+zx+*
	at+bac++cd+^*

 */
public class ONP {
	
	public static void process(String s) {
		Stack<Character> stack = new Stack<Character>();
		StringBuilder res = new StringBuilder(1000);
		
		for (int i = 0; i < s.length(); i++) {
			
			// if number/character, print permantly
			if ('a' <= s.charAt(i) && s.charAt(i) <= 'z') {
				res.append(s.charAt(i));
				continue;
			}
			
			// rest, put into stack
			stack.push(s.charAt(i));
			
			if (s.charAt(i) == ')') {
				stack.pop(); // remove new  ')'
				// append result from stack, until first meet '(' or empty stack
				while (!stack.isEmpty() && stack.peek() != '(') {
					res.append(stack.pop());
				}
				if (!stack.isEmpty()) stack.pop(); // remove '('
			}
		}
		
		System.out.println(res);
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();	// implicity clear buffer
		for (int i = 0; i < n; i++) {
			String s = scanner.nextLine();
			process(s);
		}
		scanner.close();
	}

}
