package uva.datastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class WordIndex {
	
	public static void main(String[] args) throws Exception {
		WordIndex main = new WordIndex();
		main.run();
	}

	Map<String, Integer> map = new HashMap<String, Integer>();
	
	// generate all words from a -> zzzzz
	public void Generate() {
		Queue<String> queue = new LinkedList<String>();
		int index = 1;
		for (Character c = 'a'; c <= 'z'; c++) {
			queue.add(c + "");
			map.put(c+"", index++);
		}
		
		while(!queue.isEmpty()) {
			String word = queue.poll();
			char last = word.charAt(word.length() - 1);
			if (word.length() == 5 || last == 'z') continue;
			for (char c = ++last; c <= 'z'; c++) {
				map.put(word + c, index++);
				queue.add(word + c);
			}
		}
	}
	
	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     Generate();
	     System.out.println(map.size());
	     while (sc.hasNext()) {
	    	 String word = sc.next();
	    	 Integer a = map.get(word);
	    	 System.out.println(a==null ? 0 : a);
	     }
	          
	     sc.close();
	     pr.close();
	}
}

