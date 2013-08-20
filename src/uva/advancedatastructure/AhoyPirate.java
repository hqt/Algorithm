package uva.advancedatastructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class AhoyPirate {
	
	public static void main(String[] args) throws Exception {
		AhoyPirate main = new AhoyPirate();
		main.run();
	}

	public static String occurs;
	public void run() throws Exception {
		 /*Scanner sc = null;
	     PrintWriter pr = null;

	     pr=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	     sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	     sc = new Scanner(new File("input.txt"));*/
		InputReader sc = null;
		sc = new InputReader(System.in);
		sc = new InputReader(new FileInputStream(new File("input.txt")));
		
		int ntestcase = sc.nextInt();
		for (int testcase = 1; testcase <= ntestcase; testcase++) {
			System.out.printf("Case %d:\n", testcase);
			StringBuilder sb = new StringBuilder();
			int T = sc.nextInt();
			for (int i = 0; i < T; i++) {
				int times = sc.nextInt();
				String line = sc.next();
				for (int time = 0; time < times; time++) sb.append(line);
			}
			
			//System.out.println(sb);
			// finish read input. process this input
			occurs = sb.toString();
			System.out.printf("%s\n", occurs);
			SegmentTree tree = new SegmentTree(occurs);
			
			int queries = sc.nextInt();
			int qquery = 0;
			for (int query = 1; query  <= queries; query++) {
				char type = sc.next().charAt(0);
				int u = sc.nextInt();
				int v = sc.nextInt();
				if (type == 'F' || type == 'E' || type == 'I') {
					tree.update(1, 0, occurs.length() - 1, u, v, type);
				}
				else {
					qquery++;
					int res = tree.rmq(1, 0, occurs.length() - 1, u, v);
					System.out.printf("Q%d: %d\n", qquery, res);
				}
				//System.out.printf("%c %d %d\n", type, u, v);
			}
		}
	    
	}
	
	public static class Node {
		int val, length;
		char type;
		int L, R;
		public Node(int val, int length, char type, int L, int R) {
			this.val = val;
			this.length = length;
			this.type = type;
			this.L = L;
			this.R = R;
		}
		
		@Override
		public String toString() {
			return "val: " + val + " length: " + length + " type:" + type + " L: " + L + " R: " + R;
		}
	}
	
	public static class SegmentTree {
		Node[] st;
		int n;
		String occurs;
		private int left(int p) { return p << 1; }
		private int right(int p) { return (p << 1) + 1; }
		
		private void build(int p, int L, int R) {
			if (L == R) {
				if (occurs.charAt(L) == '0') st[p] = new Node(0, 1, ' ', L, R);
				else st[p] = new Node(1, 1, ' ', L, R);
				return;
			}
			build(left(p), L, (L+R)/2);
			build(right(p), (L+R)/2 + 1, R);
			Node p1 = st[left(p)], p2 = st[right(p)];
			st[p] = new Node(p1.val + p2.val, R - L + 1, ' ', L, R);
		}
		
		public int rmq(int p, int L, int R, int i, int j) {
			// first. change this vertex
			updateNode(p);
			
			// normal routine of rmq
			// check outside
			if (i > R || j < L) return 0;
			
			// check inside range
			// when inside, we don't care status of two children
			// because if one of two chilren just update. it also update father before on its route
			// (base on update function)
			if (i <= L && R <= j) return st[p].val;
			
			// comput left interval and right interval
			int p1 = rmq(left(p), L, (L+R)/2, i, j);
			int p2 = rmq(right(p), (L+R)/2, R, i, j);
			
			// update this vertex. because it maybe has not update before
			st[p].val = p1 + p2;
			return p1 + p2;
		}
		
		public void update(int p, int L, int R, int i, int j, char type) {
			// if outside of range. nothing to do. return
			if (i > R || j < L) return;
			
			// if inside of range
			// with this function : it will also update its father on its route
			if (i <= L && R <= j) updateVertexType(p, type);
			
			// update its value. and change type of its children
			// this can make. does not depend on fit into range or not
			System.out.printf("Node %d: %s \t i:%d j:%d\n", p, st[p], i, j);
			updateNode(p);
			
			update(left(p), L, (L+R)/2, i, j, type);
			update(right(p), (L+R)/2+1, R, i, j, type);
			st[p].val = st[left(p)].val + st[right(p)].val;
		}

		private void updateVertexType(int p, char type) {
			System.out.printf("Node %d: %s\n", p, st[p]);
			Node node = st[p];
			if (node == null) return;
			if (type == 'I') {
				switch(node.type) {
				case ' ': node.type = 'I'; break;
				case 'I': node.type = ' '; break;
				case 'E': node.type = 'F'; break;
				case 'F': node.type = 'E'; break;
				}
			}
			else node.type = type;
		}
		
		// if we call updateNode for chilren in updateNode
		// will decrease performance
		// we should divide into two functions like this
		private void updateNode(int p) {
			Node node = st[p];
			if (node == null) return;
			// this node doesn't need update. so all its children
			if (node.type == ' ') return;	
			switch(node.type) {
			case 'I': node.val = node.length - node.val; break;
			case 'F': node.val = node.length; break;
			case 'E': node.val = 0; break;
			}
			updateVertexType(left(p), node.type);
			updateVertexType(right(p), node.type);
			node.type = ' ';
		}
		
		public SegmentTree(String occurs) {
			this.occurs = occurs;
			System.out.printf("length: %d\n", occurs.length());
			st = new Node[occurs.length() * 4];
			n = occurs.length();
			build(1, 0, n - 1);
		}
		
	}

	class InputReader {
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
	    
	    public double nextDouble() {
	    	return Double.parseDouble(next());
	    }
	    
	    public float nextFloat() {
	    	return Float.parseFloat(next());
	    }
	    
	    public long nextLong() {
	    	return Long.parseLong(next());
	    }
	}
}

