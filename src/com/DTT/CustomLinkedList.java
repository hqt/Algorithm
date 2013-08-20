package com.DTT;

public class CustomLinkedList {
	
	Node root;
	
	public void insert(int val) {
		root = insert(root, val);
	}
	
	public Node insert(Node node, int val) {
		if (node == null) return new Node(val, null);
		node.next = insert(node.next, val);
		return node;
	}

	public void RemoveElement() {
		root = RemoveElement(root);
	}
	
	private Node RemoveElement(Node node) {
		if (node == null) return null;
		Node current = node;
		while (current != null && (current.val != 5 && current.val != 3)) {
			current = current.next;
		}
		node = current;
		if (node != null) node.next = RemoveElement(node.next);
		return node;
	}

	public void ConvertSet() {
		Node current = root;
		while (current != null) {
			
			current = current.next;
		}
	}
	
	public Node ConvertSet(Node node, int val) {
		if (node == null) return null;
		Node current = node.next;
		while (current != null && current.val == node.val) current = current.next;
		node.next = current;
		node.next = ConvertSet(node.next, val);
		return node;
	}
	
	
	public void Print() {
		for(Node current = root; current != null; current = current.next) {
			System.out.println("element: " + current.val);
		}
	}
	
	public static void main(String[] args) {
		int[] vals = new int[]{5, 1, 9, 9, 5, 3, 5, 3, 3, 5, 6, 9, 5, 5, 10};
		CustomLinkedList manager = new CustomLinkedList();
		
		for (int i = 0; i < vals.length; i++) manager.insert(vals[i]);
		
		manager.ConvertSet();
		manager.Print();
	}
	
	public static class Node {
		int val;
		Node next;
		public Node(int val, Node next) {
			this.val = val;
			this.next = next;
		}
	}

}
