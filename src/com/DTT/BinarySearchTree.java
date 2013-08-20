package com.DTT;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

	public static class Node {
		int value;
		Node left;
		Node right;
		
		public Node(int value) { 
			this.value = value;
			left = null;
			right = null;
		}
	}
	
	Node root = null;
	
	public void insert(int value) {
		root = insert(root, value);
	}
	
	private Node insert(Node root, int value) {
		if (root == null)
			return new Node(value);
		if (value <= root.value)
			root.left = insert(root.left, value);
		else 
			root.right = insert(root.right, value);
		return root;
	}
	public void inorderTraversal(Node root) {
		if (root == null) return;
		if (root.left != null) inorderTraversal(root.left);
		System.out.print(root.value + " ->");
		if (root.right != null) inorderTraversal(root.right);
	}
	
	public void preorderTraversal(Node root) {
		if (root == null) return;
		System.out.print(root.value + "->");
		if (root.left != null) preorderTraversal(root.left);
		if (root.right != null) preorderTraversal(root.right);
	}
	
	public void postorderTraversal(Node root) {
		if (root == null) return;
		if (root.left != null) postorderTraversal(root.left);
		if (root.right != null) postorderTraversal(root.right);
		System.out.print(root.value + "->");
	}
	
	
	private void FindDistance(Node root, int small, int big) {
		if (root == null) return;
		List<Node> p1 = new ArrayList<Node>();
		List<Node> p2 = new ArrayList<Node>();
		boolean res1 = true;
		boolean res2 = true;
		if (big == root.value) {
			res1 &= FindPath(root.left, small, p1);
			p2.add(root);
		}
		else if (small == root.value) {
			p1.add(root);
			res2 &= FindPath(root.right, big, p2);
		}
		else if (big < root.value) FindDistance(root.left, small, big);
		else if (small > root.value) FindDistance(root.right, small, big);
		else if (small < root.value && big > root.value) {
			p1.add(root);
			res1 &= FindPath(root.left, small, p1);
			res2 &= FindPath(root.right, big, p2);
		}
		
		if (!res1 || !res2) {
			System.out.println("does not contain this node in binary search tree");
			return;
		}
		// print
		for (int i = p1.size() - 1; i >= 0; i--) System.out.print(p1.get(i).value + "->");
		for (int i = 0;i < p2.size() - 1; i++) System.out.print(p2.get(i).value + "->");
		System.out.println(p2.get(p2.size() - 1).value);
	}
		 
		 
	private boolean FindPath(Node root, int val, List<Node> path) {
		if (root == null) return false;
		path.add(root);
		if (root.value == val) return true;
		if (root.value > val) return FindPath(root.left, val, path); 
		else return FindPath(root.right, val, path);
	}
		 
	private void FindDistanceNormalize(int small, int big) {
		FindDistance(root, small, big);
	}
	
	public void FindDistance(int a, int b) {
		if (a > b) FindDistanceNormalize(b, a);
		else FindDistanceNormalize(b, a);
	}
		 
	
	public static void main(String[] args) {
		BinarySearchTree tree = new BinarySearchTree();
		tree.insert(9);
		tree.insert(3);
		tree.insert(16);
		tree.insert(2);
		tree.insert(5);
		tree.insert(14);
		tree.insert(18);
		tree.insert(4);
		tree.insert(8);
		
		System.out.println("Distance:");
		tree.FindDistance(14, 4);
		
	}
}
