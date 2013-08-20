package uva.biginteger;

import java.io.File;
import java.math.BigInteger;
import java.util.Scanner;

/** uva 10551 */
public class BasicRemain {
	
	public static void main(String[] args) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("input.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int n = scanner.nextInt();
		
		while (n-- > 0) {
			BigInteger p = scanner.nextBigInteger();
			String ch = scanner.next();
			BigInteger q = scanner.nextBigInteger();
			BigInteger gcd = p.gcd(q);
			System.out.println(p.divide(gcd) + " / " + q.divide(gcd));
		}
		
	}

}
