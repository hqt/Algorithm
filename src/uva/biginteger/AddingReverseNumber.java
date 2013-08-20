package uva.biginteger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * <a href="http://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=654">Adding reverse number</a>
 */
public class AddingReverseNumber {
	
	public static void main(String[] args) {
		Scanner sc = null;
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		try {
			sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int n = sc.nextInt();
		for (int i = 0; i < n; i++) {
			StringBuffer bf1 = new StringBuffer(sc.next());
			StringBuffer bf2 = new StringBuffer(sc.next());
			BigInteger b1 = new BigInteger(bf1.reverse().toString());
			BigInteger b2 = new BigInteger(bf2.reverse().toString());
			BigInteger res = new BigInteger(new StringBuffer(b1.add(b2).toString()).reverse().toString());
			pr.println(res.toString());
		}

		sc.close();
		pr.close();
		
	}
}