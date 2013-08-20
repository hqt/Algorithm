import java.util.LinkedList;
import java.util.Queue;


public class PartitionNumber {
	
	static int n;
	static int[][] memo;
	public static void main(String[] args) {
		int n = 5;
		memo = new int[n+10][n+10];
		for (int i = 0 ; i <= n; i++) {
			for (int j = 0; j <= n ; j++) {
				memo[i][j] = -1;
			}
		}

		System.out.println("Numbers of Number: " + dp(n,1));
		System.out.println("list all those numbers: ");
		generate(n);
	}
	
	public static int dp(int n, int k) {
		if (n == 0 || k > n) return 0;
		if (n == k) return 1;
		if (memo[n][k] != -1) return memo[n][k];
		if (n > k) return memo[n][k] = dp(n-k,k) + dp(n,k+1);
		else return memo[n][k] = dp(n, k+1);
	}
	
	public static void generate(int n) {
		Queue<Node> queue = new LinkedList<PartitionNumber.Node>();
		for (int i = 1; i <= n; i++) {
			queue.add(new Node(i + "", i, n - i));
		}
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node.remain == 0) System.out.println(node.val);
			for (int i = node.min; i <= node.remain; i++) {
				queue.add(new Node(node.val + i, i, node.remain - i));
			}
		}
	}
	
	public static class Node {
		String val;
		int min, remain;
		public Node(String val, int min, int remain) {
			this.val = val;
			this.min = min;
			this.remain = remain;
		}
	}
}
