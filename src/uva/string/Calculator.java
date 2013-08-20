package uva.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/*10932*/
public class Calculator {
	
	public static int INF = 100000000;
	public static void main(String[] args) throws Exception {
		Calculator main = new Calculator();
		main.run();
	}
	
	static double[] val = new double[26];
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     ExpressionTree tree = new ExpressionTree();
	     while (sc.hasNextLine()) {
	    	 String line = sc.nextLine().trim();
	    	 if (line.length() >= 2 && line.charAt(1) == '=') {
	    		 tree.Parse(line.substring(2));
	    		 val[line.charAt(0)-'a'] = tree.ValueExprTree();
	    	 }
	    	 else {
	    		 tree.Parse(line);
	    		 double res = tree.ValueExprTree();
	    		 DecimalFormat twoDForm = new DecimalFormat("0.00");
	    		 System.out.println(twoDForm.format(res));
	    	 }
	     }
	     
	     pr.close();
	     sc.close();
	}
	
	public static class ExpressionTree {
		ExprNode root = null;	
		
		public static Map<String, Integer> map = new HashMap<String, Integer>();
		{
			map.put("+", 0); map.put("-", 0); map.put("*", 1); map.put("/", 1);
		};
		
		public int comparePrecedence(String op1, String op2) {
			return map.get(op1) - map.get(op2);
		}
		
		// (3+5)*2/4+a
		public void Parse(String infix) {
			Stack<String> opStack  = new Stack<String>();
			Stack<ExprNode> exprStack = new Stack<ExprNode>();
			
			for (int i = 0; i < infix.length(); i++) {
				// + - * /
				if (map.containsKey(infix.charAt(i)+"")) {
					String token = infix.charAt(i) + "";
					// if higher or equal precedence : make node here. because we can calculate previous node
					while (!opStack.isEmpty() && map.containsKey(opStack.peek()) && 
							comparePrecedence(token, opStack.peek()) <= 0) MakeExprNode(opStack.pop(), exprStack);
					// put this operator into stack
					opStack.push(token);
				}
				else if (infix.charAt(i) == '(') opStack.push("(");
				else if (infix.charAt(i) == ')') {
					while (!opStack.isEmpty() && !opStack.peek().equals("(")) MakeExprNode(opStack.pop(), exprStack);
					opStack.pop();	// remove '('
				}
				// this is variable : just one letter. get it out
				else if (infix.charAt(i) >= 'a' && infix.charAt(i) <= 'z') {
					exprStack.add(new ExprNode(infix.charAt(i) + "", null, null));
				}
				// now. ignore all other case. this must be a number. Get this number out by hand
				else {
					String num = infix.charAt(i) + "";
					while((i+1) < infix.length() && Character.isDigit(infix.charAt(i+1))) {
						i++;
						num += infix.charAt(i)+"";
					}
					exprStack.add(new ExprNode(num, null, null));	// almost same with above case
				}
			}
			while (!opStack.isEmpty()) MakeExprNode(opStack.pop(), exprStack);
			if (exprStack.size() != 1) throw new IllegalStateException("Wrong Input");
			else root = exprStack.pop();
			/*System.out.println("tree debugging");
			PrintTree(root);*/
		}

		public void PrintTree(ExprNode root) {
			if (root == null) return;
			if (root.left == null) return;
			System.out.println(root.val + " " + root.left.val + " " + root.right.val);
			PrintTree(root.left);
			PrintTree(root.right);
		}
		
		public double ValueExprTree() {
			return ValueExprTree(root);
		}
		
		public double ValueExprTree(ExprNode node) {
			if (node.left == null && node.right == null) {
				if (Character.isDigit(node.val.charAt(0))) {
					return Double.parseDouble(node.val);
				}
				else {
					return Calculator.val[node.val.charAt(0) - 'a'];
				}
			}
			
			switch(node.val.charAt(0)) {
				case '+': return ValueExprTree(node.left) + ValueExprTree(node.right);
				case '-': return ValueExprTree(node.right) - ValueExprTree(node.left);
				case '*': return ValueExprTree(node.left) * ValueExprTree(node.right);
				case '/': return ValueExprTree(node.right) / ValueExprTree(node.left);
				default: throw new IllegalArgumentException("This operator does not currently support in context");
			}
		}
		
		private void MakeExprNode(String val, Stack<ExprNode> res) {
			ExprNode node = new ExprNode(val, res.pop(), res.pop());
		//	System.out.println("make expr node: " + node.val + "   " + node.left.val + "  " + node.right.val);
			res.push(node);
		}
	}
	
	static class ExprNode {
		String val;
		ExprNode left, right;
		public ExprNode(String val, ExprNode left, ExprNode right) { 
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
}

