package uva.datastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UVA484 {
	
	public static void main(String[] args) throws Exception {
		UVA484 main = new UVA484();
		main.run();
	}

	public void run() throws Exception {
		 Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));
	     
	     ArrayList<Integer> order = new ArrayList<Integer>();
	     Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	     while (sc.hasNextInt()) {
	    	 int a = sc.nextInt();
	    	 if (map.containsKey(a)) {
	    		 map.put(a, map.get(a) + 1);
	    	 }
	    	 else {
	    		 map.put(a, 1);
	    		 order.add(a);
	    	 }
	     }
	     
	     // print data
	     for (int i = 0; i < order.size(); i++) {
	    	 System.out.println(order.get(i) + " " + map.get(order.get(i)));
	     }
	     
	     sc.close();
	     pr.close();
	}
	
	public static class AdjEdge {
		int u, weight;
		public AdjEdge(int u, int weight) {
			this.u = u;
			this.weight = weight;
		}
	}
}

