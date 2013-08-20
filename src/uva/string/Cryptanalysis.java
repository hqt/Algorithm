package uva.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/* uva 10008 */
public class Cryptanalysis {
	
	public static void main(String[] args) throws Exception {
		Cryptanalysis main = new Cryptanalysis();
		main.run();
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));

	     int n = sc.nextInt();
	     sc.nextLine();
	     int[] freq = new int[26];
	     for (int i = 0; i < n; i++) {
	    	 String line = sc.nextLine(); 
	    	// System.out.println(line);
	    	 for (int j = 0; j < line.length(); j++) {
	    		 char c = line.charAt(j);
	    		 if (Character.isLetter(c)) {
	    			 freq[Character.toUpperCase(c) - 'A']++;
	    		 }
	    	 }
	     }
	     
	     // building again
	     Pair[] pairs = new Pair[26];
	     for (int i = 0; i < 26; i++) {
	    	 pairs[i] = new Pair(freq[i], (char)(i + 'A'));
	     }
	     Arrays.sort(pairs, new Comp());

	     for (int i = 0; i < 26; i++) {
	    	 if(pairs[i].freq > 0) {
	    		 System.out.println(pairs[i].c + " " + pairs[i].freq);
	    	 }
	     }
	     
	     sc.close();
	     pr.close();
	}
}

class Comp implements Comparator<Pair> {
	public int compare(Pair p1, Pair p2) {
		if (p1.freq < p2.freq) return 1;
		else if (p1.freq == p2.freq) {
			if ( p1.c < p2.c ) return -1;
			else if ( p1.c > p2.c ) return 1;
			return 0;
		}
		return -1;
	}
}

class Pair {
	int freq;
	char c;
	Pair(int freq, char c){
		this.freq = freq;
		this.c = c;
	}
}

