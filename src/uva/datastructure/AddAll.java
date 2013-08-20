package uva.datastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class AddAll {
	
	public static void main(String[] args) throws Exception {
		AddAll main = new AddAll();
		main.run();
	}

	public void run() throws Exception {

		PrintWriter pr = null;
		InputReader sc = null;
		
		pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		sc = new InputReader(System.in);
		sc = new InputReader(new FileInputStream(new File("input.txt")));
		
		while (true) {
			int n = sc.nextInt();
			if (n == 0) break;
			PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
			for (int i = 0; i < n; i++) pq.add(sc.nextInt());
			
			long sum = 0;
			while(pq.size() > 1) {
				int a = pq.poll();
				int b = pq.poll();
				int c = a + b;
				pq.add(c);	sum += c;
			}
			System.out.println(sum);
		}
		
		pr.close();
	}
	
	public static class InputReader {
	    public BufferedReader reader;
	    public StringTokenizer tokenizer;

	    public InputReader(InputStream stream) {
	        reader = new BufferedReader(new InputStreamReader(stream));
	        tokenizer = null;
	    }

	    public String next() {
	        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
	            try {
	                tokenizer = new StringTokenizer(reader.readLine());
	            } catch (IOException e) {
	                throw new RuntimeException(e);
	            }
	        }
	        return tokenizer.nextToken();
	    }

	    public int nextInt() {
	        return Integer.parseInt(next());
	    }
	}
	
}

