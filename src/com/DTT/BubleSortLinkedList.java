package com.DTT;


public class BubleSortLinkedList {

	public static class Node implements Comparable<Node> {
		int val;
		Node next;
		public Node(int val) { this.val = val; this.next = null;}
		
		@Override
		public int compareTo(Node that) {
			if (this == that) return 0;
			if (this.val < that.val) return -1;
			if (this.val == that.val) return 0;
			else return 1;
		}
		
	}

	private static Node AddNode(Node node, int val) {
		Node newNode = new Node(val);
		if (node != null) node.next = newNode;
		return newNode;
	}
	
	public static Node AddVal(Node root, int val) {
		Node current = root;
		if (root == null) return AddNode(null, val);
		while (current.next != null) current = current.next;
		AddNode(current, val);
		return root;
	}
	
	public static void IterateNode(Node root) {
		while (root != null) {
			System.out.println(root.val);
			root = root.next;
		}
	}
	
	public static Node BubleSortNew(Node root) {
		Node current, pivot;
		for (pivot = null; pivot != root; pivot = current) {
			for (current = root; current.next != pivot; current = current.next) {
				if (current.compareTo(current.next) > 0) {
					int tmp = current.val;
					current.val = current.next.val;
					current.next.val = tmp;
				}
			}
		}
		return pivot;
	}
	
	public static Node BubleSort(Node root) {
		Node pivot = null;
		while (pivot != root) {		// iterate from null to root
			Node current = root;	// iterate from head
			while (current.next != pivot) {		// buble : max element will go to the end of list
				if (current.compareTo(current.next) > 0) {
				//if (current.val > current.next.val) {	// simple swap
					int tmp = current.val;
					current.val = current.next.val;
					current.next.val = tmp;
				}
				current = current.next;
			}
			pivot = current;	// increase pivot to 1. more nearer than root
		}
		return pivot;
	}
	
	public static void main(String[] args) {
		Node root = null;
		root = AddVal(root, 1);
		root = AddVal(root, 7);
		root = AddVal(root, 5);
		root = AddVal(root, 6);
		root = AddVal(root, 7);
		root = AddVal(root, 3);
		IterateNode(root);
		
		System.out.println("**************");
		root = BubleSortNew(root);
		IterateNode(root);
		
	}

}
