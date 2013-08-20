package uva.biginteger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * <a href="http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=365">Adding Integer Inquiry</a>
 */
public class IntergrityInquiry {
	
	public static void main(String[] args) {
		Scanner sc = null;
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		try {
			//sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			sc = new Scanner(new File("input.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BigInteger sum = BigInteger.ZERO;
		while (true) {
			String line = sc.nextLine();
			if (line.equals("0")) break;
			BigInteger n1 = new BigInteger(line);
			sum = sum.add(n1);
		}
		
		pr.println(sum);
		pr.close();
		sc.close();
		
	}
}
