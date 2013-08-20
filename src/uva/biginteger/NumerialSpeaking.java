package uva.biginteger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * <a href="http://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=560">Numerically Speaking</a>
 */
public class NumerialSpeaking {
	
	static final BigInteger TWENTY_SIX = BigInteger.valueOf(26);
	
	public static void main(String[] args) {
		Scanner sc = null;
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		try {
			//sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			sc = new Scanner(new File("input.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while (true) {
			if (sc.hasNextBigInteger()) {
				BigInteger num = sc.nextBigInteger();
				String res = NumToStr(num);
				pr.printf("%s\t", res);
				//pr.printf("%s\n", formatNumber(num));
				pr.printf("%s\n", num.toString());
			}
			else if (sc.hasNext()) {
				String s = sc.next();
				if (s.equals("*")) break;
				String num = StrToNum(s);
				pr.printf("%s\t", s);
				//pr.printf("%s\n", formatNumber(new BigInteger(num)));
				pr.printf("%s\n", num);
			}
		}
		sc.close();
		pr.close();
	}
	
	public static String StrToNum(String str) {
		BigInteger res = BigInteger.ZERO;
		int n = str.length();
		for (int i = 0; i < n; i++) {
			res = res.multiply(TWENTY_SIX);	// first time : res = 0. --> no affect
			res = res.add(BigInteger.valueOf(str.charAt(i) - 'a' + 1));
		}
		return res.toString();
	}

	public static String NumToStr(BigInteger num) {
		StringBuilder sb = new StringBuilder();
		while (num.compareTo(BigInteger.ZERO) != 0) {
			BigInteger remain = num.mod(TWENTY_SIX);
			sb.append(Character.toChars(remain.intValue() + 'a'));
			num = num.subtract(remain).divide(BigInteger.TEN).divide(TWENTY_SIX);
		}
		return sb.reverse().toString();
	}
	
	public static String formatNumber(BigInteger val) {
		String res = val.toString();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < res.length(); i++) {
			sb.append(res.charAt(i));
			if (i % 3 == 2 && i != res.length()) sb.append(',');
		}
		return sb.reverse().toString();
	}
}
