package uva.biginteger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;

public class HighPrecisionNumber {
	public static void main(String[] args) {
		Scanner sc = null;
		PrintWriter pr = null;
		
		try {
			sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			sc = new Scanner(new File("input.txt"));
			pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		}
		catch (Exception e) {}
		
		int n = sc.nextInt();
		
		for (int i = 0; i < n; i++) {
			BigDecimal res = BigDecimal.ZERO;
			while(true) {
				BigDecimal val = sc.nextBigDecimal();
				if (val.compareTo(BigDecimal.ZERO) == 0) {
					char[] output = res.stripTrailingZeros().toPlainString().toCharArray();
					int length = output.length - 1;
					while (length >= 0 && output[length] == '0') length--;
					if (length >= 0 && output[length] == '.') length--;
					
					for (int j = 0; j <= length; j++) pr.print(output[j]);
					pr.println();
					break;
			}
				res = res.add(val);
			}
		}
		
		pr.close();
		sc.close();
	}
}
