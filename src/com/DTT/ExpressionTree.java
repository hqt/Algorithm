package com.DTT;

import java.util.Stack;

public class ExpressionTree {
	ExprNode root = null;

	public int getOpCode(String s) { 
		return "+*-/".indexOf(s);
	}
	
	public int comparePrecedence(String op1, String op2) { return getOpCode(op1) - getOpCode(op2);}

	public void Parse(String infix) {
		String[] tokens = infix.split(" ");
		Stack<String> opStack = new Stack<String>();
		Stack<ExprNode> exprStack = new Stack<ExpressionTree.ExprNode>();

		for (String token : tokens) {
			if (getOpCode(token) >= 0) {
				while (!opStack.isEmpty() && getOpCode(opStack.peek()) >= 0 
						&& comparePrecedence(token, opStack.peek()) <= 0)
					MakeExprNode(opStack.pop(), exprStack);
				opStack.push(token);
			}
			else if (token.equals("("))
				opStack.push("(");
			else if (token.equals(")")) {
				while (!opStack.isEmpty() && !opStack.peek().equals("("))
					MakeExprNode(opStack.pop(), exprStack);
				opStack.pop(); // remove '('
			}
			else
				exprStack.add(new ExprNode(token, null, null)); // number
		}
		while (!opStack.isEmpty())
			MakeExprNode(opStack.pop(), exprStack);

		if (exprStack.size() != 1)
			throw new IllegalStateException("Wrong Input");
		else
			root = exprStack.pop();
		/*
		 * System.out.println("tree debugging"); PrintTree(root);
		 */
	}

	public void PrintTree(ExprNode root) {
		if (root == null)
			return;
		if (root.left == null)
			return;
		System.out.println(root.val + " " + root.left.val + " " + root.right.val);
		PrintTree(root.left);
		PrintTree(root.right);
	}

	public double ValueExprTree() {
		return ValueExprTree(root);
	}

	public double ValueExprTree(ExprNode node) {
		if (node.left == null && node.right == null)
			return Double.parseDouble(node.val);

		switch (node.val.charAt(0)) {
		case '+':
			return ValueExprTree(node.left) + ValueExprTree(node.right);
		case '-':
			return ValueExprTree(node.right) - ValueExprTree(node.left);
		case '*':
			return ValueExprTree(node.left) * ValueExprTree(node.right);
		case '/':
			return ValueExprTree(node.right) / ValueExprTree(node.left);
		default:
			throw new IllegalArgumentException("This operator does not currently support in context");
		}
	}

	public String convertInfixToPostFix() {
		return convertInfixToPostFix(root);
	}

	public String convertInfixToPostFix(ExprNode node) {
		if (node == null) return "";
		String res = "";
		res += convertInfixToPostFix(node.left) + " " + convertInfixToPostFix(node.right) + " " + node.val + " ";
		return res;

	}

	private void MakeExprNode(String val, Stack<ExprNode> res) {
		ExprNode node = new ExprNode(val, res.pop(), res.pop());
		res.push(node);
	}

	public static void main(String[] args) {
		String input = "( 1 + 2 ) * ( 3 / 4 ) + ( 5 + 6 )";
		//String input = "2 + 1 * 11";
		System.out.println("Input Infix : " + input);
		ExpressionTree tree = new ExpressionTree();
		tree.Parse(input);
		System.out.println("value: " + tree.ValueExprTree());
		System.out.println("Infix to Posfix: " + tree.convertInfixToPostFix());

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
