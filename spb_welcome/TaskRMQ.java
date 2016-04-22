import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by god on 31.10.15.
 */

public class TaskRMQ {

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            String cmd = in.next();

        }
        // --
        writer.flush();
    }

    public static class SegmentTree {
        private static boolean RED = true, BLACK = false;

        private Node root;

        public void put(int k, int v) {
            root = put(root, k, v);
            root.color = BLACK;
        }

        public int get(int from, int to) {
            assert root != null;
            return get(root, from, to);
        }

        private Node put(Node r, int k, int v) {
            if (r == null) return new Node(k, v);

            if (k < r.k) r.left = put(r.left, k, v);
            else if (k > r.k) r.right = put(r.right, k, v);
            else r.v = v;

            restore(r);
            r = balance(r);
            return r;
        }

        private int get(Node r, int from, int to) {
            if (r == null) return Integer.MAX_VALUE;

            if (contains(from, to, r.from, r.to)) return r.min;
            if (intersects(from, to, r.from, r.to)) {
                int min = Integer.MAX_VALUE;
                if (intersects(from, to, r.k, r.k)) min = r.v;
                min = Math.min(min, get(r.left, from, to));
                min = Math.min(min, get(r.right, from, to));
                return min;
            }
            return Integer.MAX_VALUE;
        }

        private boolean contains(int f1, int t1, int f2, int t2) {
            return f1 <= f2 && t2 <= t1;
        }
        private boolean intersects(int f1, int t1, int f2, int t2) {
            return (f1 <= f2 && f2 <= t1) ||
                    (f2 <= f1 && f1 <= t2);
        }

        private Node balance(Node r) {
            if (!isRed(r.left) && isRed(r.right))
                r = rotateLeft(r);
            if (isRed(r.left) && isRed(r.left.left))
                r = rotateRight(r);
            if (isRed(r.left) && isRed(r.right))
                flipColors(r);
            return r;
        }
        private void flipColors(Node r) {
            r.color = !r.color;
            r.left.color = !r.left.color;
            r.right.color = !r.right.color;
        }
        private Node rotateLeft(Node n) {
            Node x = n.right;
            n.right = x.left;
            x.left = n;

            restore(x, n);
            return x;
        }

        private Node rotateRight(Node n) {
            Node x = n.left;
            n.left = x.right;
            x.right = n;

            restore(x, n);
            return x;
        }

        private void restore(Node x, Node n) {
            restore(n);
            restore(x);
        }
        private void restore(Node r) {
            r.from = r.to = r.min = r.k;
            if (r.left != null) {
                r.from = r.left.from;
                r.min = Math.min(r.min, r.left.min);
            }
            if (r.right != null) {
                r.to = r.right.to;
                r.min = Math.min(r.min, r.right.min);
            }
        }

        private boolean isRed(Node n) {
            return n != null && n.color == RED;
        }
        private static class Node {
            public Node left, right;
            public int from, to, v, k;
            public int min;
            public boolean color = RED;

            public Node() {}
            public Node(int key, int val) {
                this.k = from = to = min = key;
                this.v = val;
            }
        }
    }

    public static class In {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public In(InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
        }

        private void restore() {
            while (tokenizer == null || !tokenizer.hasMoreTokens())
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (Exception e) {
                }
        }

        public String next() {
            restore();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
