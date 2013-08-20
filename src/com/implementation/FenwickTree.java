package com.implementation;

import java.util.*;

public class FenwickTree {
	private ArrayList<Integer> ft;

	// return index of last non-zero digit (from right)
	private int LSOne(int S) { return (S & (-S)); }

	// initialization: n + 1 zeroes, ignore index 0
	public FenwickTree(int n) {
		ft = new ArrayList<Integer>();
		for (int i = 0; i <= n; i++)
			ft.add(0);
	}

	// returns RSQ(1, b)
	public int rsq(int b) { 
		int sum = 0;
		for (; b > 0; b -= LSOne(b))
			sum += ft.get(b);
		return sum;
	}

	// returns RSQ(a, b)
	public int rsq(int a, int b) { 
		return rsq(b) - (a == 1 ? 0 : rsq(a - 1));
	}

	// adjusts value of the k-th element by v (v can be +ve/inc or -ve/dec)
	void adjust(int k, int v) { // note: n = ft.size() - 1
		for (; k < (int) ft.size(); k += LSOne(k))
			ft.set(k, ft.get(k) + v);
	}
	
	// change the frequency value at k-th element by v
	void change(int k, int v) {
		int val = readSingle(k); int adjustVal = v - val;
		adjust(k, adjustVal);
	}
	
	// this method more optimize then rsq(idx, idx)
	public int readSingle(int idx) {
		int sum = ft.get(idx);	// sum will be decrease
		if (idx > 0) {			// special case
			int z = idx - LSOne(idx);	// make z first
			idx--;	// idx is no important anymore. so. instead y, you can use idx
			while (idx != z) { // at some iteration idx (y) will become z
				sum -= ft.get(idx);
				// substruct tree frequency which is between y and "the same path"
				idx -= LSOne(idx);
			}
		}
		return sum;
	}
	
	public void scale(int c) {
		for (int i = 0; i <= ft.size(); i++) 
			ft.set(i, ft.get(i) / c);
	}
	
	// if in tree exist more then one index with a same cumulative frequency
	// this procedure will return one of them (we do not know which one)
	
	// bitMask : initially, it is  the greateset bit of MaxVal
	// bitMask store interval which should be searched
	
	// under construction
	
	public static void main(String[] args) {
		// idx 0 1 2 3 4 5 6 7 8 9 10, no index 0!
		FenwickTree ft = new FenwickTree(10); // ft = {-,0,0,0,0,0,0,0, 0,0,0}
		ft.adjust(2, 1); // ft = {-,0,1,0,1,0,0,0, 1,0,0}, idx 2,4,8 => +1
		ft.adjust(4, 1); // ft = {-,0,1,0,2,0,0,0, 2,0,0}, idx 4,8 => +1
		ft.adjust(5, 2); // ft = {-,0,1,0,2,2,2,0, 4,0,0}, idx 5,6,8 => +2
		ft.adjust(6, 3); // ft = {-,0,1,0,2,2,5,0, 7,0,0}, idx 6,8 => +3
		ft.adjust(7, 2); // ft = {-,0,1,0,2,2,5,2, 9,0,0}, idx 7,8 => +2
		ft.adjust(8, 1); // ft = {-,0,1,0,2,2,5,2,10,0,0}, idx 8 => +1
		ft.adjust(9, 1); // ft = {-,0,1,0,2,2,5,2,10,1,1}, idx 9,10 => +1
		System.out.printf("Check arr: %d %d %d %d %d %d %d\n", ft.readSingle(2), ft.readSingle(4), ft.readSingle(5), ft.readSingle(6),
													ft.readSingle(7), ft.readSingle(8), ft.readSingle(9));	// 1 1 2 3 2 1 1
		System.out.printf("%d\n", ft.rsq(1, 1)); // 0 => ft[1] = 0
		System.out.printf("%d\n", ft.rsq(1, 2)); // 1 => ft[2] = 1
		System.out.printf("%d\n", ft.rsq(1, 6)); // 7 => ft[6] + ft[4] = 5 + 2 = 7
		System.out.printf("%d\n", ft.rsq(1, 10)); // 11 => ft[10] + ft[8] = 1 + 10 = 11
		System.out.printf("%d\n", ft.rsq(3, 6)); // 6 => rsq(1, 6) - rsq(1, 2) = 7 - 1

		ft.adjust(5, 2); // update demo
		System.out.printf("%d\n", ft.rsq(1, 10)); // now 13
		
		ft.change(2, 100);
		ft.change(9, 14);
		ft.change(8, 7);
		System.out.printf("Check arr: %d %d %d %d %d %d %d\n", ft.readSingle(2), ft.readSingle(4), ft.readSingle(5), ft.readSingle(6),ft.readSingle(7), ft.readSingle(8), ft.readSingle(9));	// 1 1 2 3 2 1 1
														     // 100, 1, 4, 3, 2, 7, 14
	}
}