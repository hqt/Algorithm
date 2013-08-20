package com.DTT;

/**
 * Simple Hash Using Linear Probling Algorithm
 * Include delete. But don't resize hash when full.
 * @author Huynh Quang Thao
 *
 */
public class HashLinearProbing {

	Data[] hashArr;
	int size = 0;
	
	public HashLinearProbing(int n) { hashArr = new Data[n];}
	
	private int hashFunction(int key) { return key % hashArr.length;}
	
	public void insert(Data data) {
		if (size == hashArr.length) return;
		int hashVal = hashFunction(data.getKey());
		for (; hashArr[hashVal] != null; hashVal = (hashVal+1) % hashArr.length);
		hashArr[hashVal] = data;
		size++;
	}
	
	public Data find(int key) {
		int hashVal = hashFunction(key);
		for (int count = 0; hashArr[hashVal] != null && count < hashArr.length; hashVal++, hashVal %= hashArr.length, count++)
			if (hashArr[hashVal].getKey() == key) return hashArr[hashVal];
		return null;
	}
	
	public Data delete(int key) {
		int hashVal = hashFunction(key);
		for (int count = 0; hashArr[hashVal] != null && count < hashArr.length; hashVal++, hashVal %= hashArr.length,count++) 
			if (hashArr[hashVal].getKey() == key) break;
		if (hashArr[hashVal] == null) return null;
		hashArr[hashVal] = null; size--;
		for (int curr = (hashVal+1)%hashArr.length; hashArr[curr] != null; curr++, curr %= hashArr.length) {
			Data tmp = hashArr[curr]; hashArr[curr] = null; size--;
			insert(tmp);
		}
		return hashArr[hashVal];
	}
	
	public void PrintTable() {
		for (int i = 0; i < hashArr.length; i++) {
			System.out.println(hashArr[i] == null ? "Empty cell" : hashArr[i].data);
		}
	}
	
	public static void main(String[] args) {
		HashLinearProbing hash = new HashLinearProbing(4);
		hash.insert(new Data(1));
		hash.insert(new Data(3));
		hash.insert(new Data(2));
		hash.insert(new Data(4));
		hash.PrintTable();
		System.out.println("f1: " + hash.find(3));
		System.out.println("f2: " + hash.find(10));
		hash.delete(0);
		hash.delete(3);
		hash.PrintTable();
	}
	
	public static class Data {
		int data;
		
		public Data(int data) {
			this.data = data;
		}
		
		public int getKey() {
			return data;
		}

		@Override
		public String toString() {
			return "Data: " + data;
		}
		
	}
}

