package com.conquer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class ExactlySum {
	
	public static void main(String[] args) throws Exception {
		ExactlySum main = new ExactlySum();
		main.run();
	}

	public void run() throws Exception {
		Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));

	    while (sc.hasNextInt()) {
	    	int n = sc.nextInt();
	    	int[] arr = new int[n];
	    	for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
	    	int money = sc.nextInt();
	    	
	    	// Using Binary Search on this array
	    	Arrays.sort(arr);
	    	int x = -1, y = -1;
	    	for (int i = 0; i < n; i++) {
	    		int j = Arrays.binarySearch(arr, i + 1, n, money - arr[i]);
	    		if (j >= 0 && (arr[j] - arr[i] < y - x || (x == -1 && y == -1))) {
	    			x = arr[i];
	    			y = arr[j];
	    		}
	    	}
	    	
	    	System.out.printf("Peter should buy books whose prices are %d and %d.\n\n", x, y);
	    }
	    
	    pr.close();
	    sc.close();
	}
}

