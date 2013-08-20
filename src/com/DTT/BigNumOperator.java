package com.DTT;

import java.util.Stack;

public class BigNumOperator {
	
	public static String AddPositiveNumStack(String num1, String num2) {
		Stack<Character> a = new Stack<Character>();
		Stack<Character> b = new Stack<Character>();
		Stack<Character> res = new Stack<Character>();
		for (int i = 0; i < num1.length(); i++) a.push(num1.charAt(i));
		for (int i = 0; i < num2.length(); i++) b.push(num2.charAt(i));
		int remain = 0;
		while (!a.isEmpty() && !b.isEmpty()) {
			int add = (a.pop() - '0') + (b.pop() - '0') + remain;
			res.push((char)(add % 10  + '0'));
			remain = add / 10;
		}
		
		if (!b.isEmpty()) a = b;
		while (!a.isEmpty()) {
			int add = (a.pop() - '0') + remain;
			res.push((char)(add % 10 + '0'));
			remain = add / 10;
		}
		if (remain != 0) res.push((char)(remain + '0'));
		
		String result = "";
		while(!res.isEmpty()) result += res.pop();
		return Normalize(result);
	}
	
	public static String AddPositiveNum(String a, String b) {
		// normalize two string
		if (a.length() < b.length()) {
			String tmp = a;	a = b; b = tmp;
		}
		// make same length
		while(a.length() != b.length()) b = "0" + b;
		
		int remain = 0;
		StringBuilder res = new StringBuilder();
		for (int i = a.length() - 1; i >= 0; i--) {
			int add = (a.charAt(i) - 48) + (b.charAt(i) - 48) + remain;
			res.append(add % 10);
			remain = add / 10;
		}
		if (remain != 0) res.append(remain);
		return res.reverse().toString();
	}
	
	public static String MultiplyPositive(String num1, char num2) {
		int remain = 0;
		int num = num2 - 48;
		StringBuilder res = new StringBuilder();
		for (int i = num1.length() - 1; i >= 0; i--) {
			int mul = num * (num1.charAt(i)-48) + remain;
			res.append(mul % 10);
			remain = mul / 10;
		}
		if (remain != 0) res.append(remain);
		return res.reverse().toString();
	}
	
	public static String MultiplyPositive(String num1, String num2) {
		String res = "";
		for (int i = num2.length() - 1; i >= 0; i--) {
			String mul = MultiplyPositive(num1, num2.charAt(i));
			for (int j = 0; j < num2.length() - 1 - i; j++) mul += "0";
			res = AddPositiveNum(res, mul);
		}
		return res;
	}
	
	public static String Multiply(String num1, String num2) {
		int op = 1;
		if (num1.charAt(0) == '-') { num1 = num1.substring(1, num1.length()); op *= -1; }
		if (num2.charAt(0) == '-') { num2 = num2.substring(1, num2.length()); op *= -1; }
		String res = MultiplyPositive(num1, num2);
		if (op == -1) res = '-' + res;
		return Normalize(res);
	}
	
	public static String Normalize(String num) {
		if (num.length() > 1 && num.charAt(0) == '0') return Normalize(num.substring(1, num.length()));
		else return num;
	}
	
	public static void Recursion(int n) {
		if (n >= 0) return;
		else Recursion(n-1);
	}
	public static void main(String[] args) {
		String a = "999";
		char b = '9';
		String res = MultiplyPositive(a, b);
		System.out.println("Mul: " + res);
		
		String add1 = "3";
		String add2 = "333";
		res = AddPositiveNum(add1, add2);
		System.out.println("Add: " + res);
		
		String big1 = "999";
		String big2 = "012";
		res = MultiplyPositive(big1, big2);
		System.out.println("Mul Big Num: " + res);
		
		String gen1 = "999";
		String gen2 = "-012";
		res = Multiply(gen1, gen2);
		System.out.println("Mul General: " + res);
		
		System.out.println("Normalize: " + Normalize("0000"));
		
		String s1 = "333";
		String s2 = "3";
		res = AddPositiveNumStack(s1, s2);
		System.out.println("Using stack to add: " + res);
		
		Recursion(10000000);
	}

}
