package uva.biginteger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * <a href="http://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=1405">Big big real number</a>
 */
public class BigRealNumber {
	
	public static void main(String[] args) {
		Scanner sc = null;
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		try {
			//sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			sc = new Scanner(new File("input.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int n = sc.nextInt();
		
		for (int i = 0; i < n; i++) {
			String str1 = sc.next();
			String str2 = sc.next();
			BigDecimal n1 = new BigDecimal(str1);
			BigDecimal n2 = new BigDecimal(str2);
			BigDecimal res = n1.add(n2);
			pr.println(res.toString());
		}
		
		sc.close();
		pr.close();
		
	}
}
