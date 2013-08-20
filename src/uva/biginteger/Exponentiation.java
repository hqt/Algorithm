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
import java.util.Stack;

/**  748  : Exponentiation */
public class Exponentiation {
	
	
	public static void main(String[] args) {
		Scanner sc = null;
		PrintWriter pr = null;
		
		try {
			sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			sc = new Scanner(new File("input.txt"));
			pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		}
		catch (Exception e) {}
		
		while (sc.hasNext()) {
			BigDecimal dec = sc.nextBigDecimal();
			int exp = sc.nextInt();
			BigDecimal res = dec.pow(exp);
			res = setScale(res);
			String str = res.stripTrailingZeros().toPlainString();
			if (str.charAt(0) == '0') str = str.substring(1);
			pr.println(str);
		}
		
		pr.close();
		sc.close();
	}
	
	public static BigDecimal setScale(BigDecimal n) {
		try {
			while (true) {
				n = n.setScale(n.scale() -1);
			}
		}
		catch (Exception e) {}
		return n;
	}
}
