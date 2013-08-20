package uva.biginteger;

import java.io.File;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * <a href="http://uva.onlinejudge.org/external/109/10925.html">Krakova</a>
 */
public class Krakovia {
	
	public static void main(String[] args) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("input.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int test = 1;
		while (true) {
			BigInteger sum = BigInteger.ZERO;
			int n = scanner.nextInt();
			int f = scanner.nextInt();
			if (n == 0 && f == 0) break;
			for (int i = 0; i < n; i++) {
				BigInteger v = scanner.nextBigInteger();
				sum = sum.add(v);
			}
			System.out.println("Bill #" + (test++) + " costs " + sum +": each friend should pay "
					+ sum.divide(BigInteger.valueOf(f)));
			System.out.println();
		}
		
	}
}