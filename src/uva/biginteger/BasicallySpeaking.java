package uva.biginteger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * uva 389
 *<a href="http://uva.onlinejudge.org/index.php?option=onlinejudge&Itemid=8&page=show_problem&category=&problem=325&mosmsg=Submission+received+with+ID+11792920">Basically Speaking</a> 
 *
 */
public class BasicallySpeaking {

	public static void main(String[] args) {
		Scanner scanner = null;
		PrintWriter pr = null;
		
		try {
			scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			//scanner = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt")))));
			//scanner = new Scanner(new File("input.txt"));
			pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while (scanner.hasNext()) {
			String hexStr = scanner.next();
			int from = scanner.nextInt();
			int to = scanner.nextInt();
			BigInteger val = new BigInteger(hexStr, from);
			String res = val.toString(to);
			if (res.length() > 7) pr.printf("%7s%n","ERROR");
			else pr.printf("%7s\n",res.toUpperCase());
		}
		
		scanner.close();
		pr.close();
		
	}
}
