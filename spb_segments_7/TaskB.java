import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class TaskB {
    public static final String TASK = "rvq";
    private static final boolean FILE_IO = true;

    public static void main(String[] args) throws IOException {
        InputStream in;
        OutputStream out;

        // long buildingTime = 0, minMaxTime = 0, putTime = 0;

        if (FILE_IO) {
            in = new FileInputStream(TASK + ".in");
            out = new FileOutputStream(TASK + ".out");
        } else {
            in = System.in;
            out = System.out;
        }

        Scanner scanner = new Scanner(new BufferedInputStream(in));
        PrintWriter writer = new PrintWriter(out);
        // --
        final int SEQUENCE_LENGTH = 100 * 1000;

        final int k = scanner.nextInt();
        SegmentTree st = new SegmentTree();
        {
            // long t = System.nanoTime();
            for (long i = 1; i <= SEQUENCE_LENGTH; i++) {
                st.put((int)i, A(i));
            }
            // buildingTime += System.nanoTime() - t;
        }

        for (int i = 0; i < k; i++) {
            final int x = scanner.nextInt();
            final int y = scanner.nextInt();

            // long t = System.nanoTime();
            if (x >= 1) {

                long max = st.max(x, y);
                long min = st.min(x, y);
                // minMaxTime += System.nanoTime() - t;
                writer.println(max - min);
            } else {
                st.put(Math.abs(x), y);
                // putTime += System.nanoTime() - t;
            }
        }

        /**
        System.err.println("Building time:\t\t" + buildingTime);
        System.err.println("Min/Max querying time:\t" + minMaxTime);
        System.err.println("Updating time:\t\t" + putTime);
        System.err.println("Total:\t\t\t" + (buildingTime + minMaxTime + putTime));
         */

        // --
        writer.flush();
    }
    public static long A(long i) {
        final long base2 = 12345;
        final long base3 = 23456;
        final long sqr = i * i;
        final long cube = sqr * i;
        final long val = (sqr % base2) + (cube % base3);

        return val;
    }
    public static class SegmentTree {
        private static boolean RED = true, BLACK = false;

        private Node root;

        public void put(int key, long val) {
            root = put(root, key, val);
            root.color = BLACK;
        }
        private Node put(Node r, int key, long val) {
            if (r == null) return new Node(key, val);

            int cmp = Integer.compare(key, r.key);

            if (cmp == 0) r.val = val;
            else if (cmp < 0) r.left = put(r.left, key, val);
            else r.right = put(r.right, key, val);

            repair(r);

            if (!isRed(r.left) && isRed(r.right))
                r = rotateLeft(r);
            if (isRed(r.left) && isRed(r.left.left))
                r = rotateRight(r);
            if (isRed(r.left) && isRed(r.right))
                flipColors(r);

            return r;
        }

        public long min(int from, int to) {
            return getMin(root, from, to);
        }
        public long max(int from, int to) {
            return getMax(root, from, to);
        }
        private long getMin(Node r, int from, int to) {

            if (r != null) {
                if (contains(from, to, r.from, r.to))
                    return r.min;
                if (intersects(from, to, r.from, r.to)) {
                    long min = getMin(r.left, from, to);
                    min = Math.min(min, getMin(r.right, from, to));
                    if(from <= r.key && r.key <= to)
                        min = Math.min(min, r.val);
                    return min;
                }
            }
            return Integer.MAX_VALUE;
        }
        private long getMax(Node r, int from, int to) {

            if (r != null) {
                if (contains(from, to, r.from, r.to))
                    return r.max;
                if (intersects(from, to, r.from, r.to)) {
                    long max = getMax(r.left, from, to);
                    max = Math.max(max, getMax(r.right, from, to));
                    if (from <= r.key && r.key <= to)
                        max = Math.max(max, r.val);
                    return max;
                }
            }
            return Integer.MIN_VALUE;
        }

        private boolean intersects(int from1, int to1, int from2, int to2) {
            return (from1 <= from2 && from2 <= to1) ||
                    (from2 <= from1 && from1 <= to2);
        }
        private boolean contains(int from1, int to1, int from2, int to2) {
            return from1 <= from2 && to2 <= to1;
        }

        private void flipColors(Node n) {
            n.left.color = n.right.color = n.color;
            n.color = RED;
        }
        private boolean isRed(Node n) {
            return n != null && n.color == RED;
        }
        private Node rotateLeft(Node n) {
            Node x = n.right;
            n.right = x.left;
            x.left = n;

            repairRotation(n, x);
            return x;
        }
        private Node rotateRight(Node n) {
            Node x = n.left;
            n.left = x.right;
            x.right = n;

            repairRotation(n, x);
            return x;
        }
        private void repairRotation(Node n, Node x) {
            x.color = n.color;
            n.color = RED;

            repair(n);
            repair(x);
        }
        private void repair(Node n) {
            long min = n.val;
            long max = n.val;
            n.from = n.to = n.key;

            if (n.left != null) {
                n.from = n.left.from;
                min = Math.min(min, n.left.min);
                max = Math.max(max, n.left.max);
            }

            if (n.right != null) {
                n.to = n.right.to;
                min = Math.min(min, n.right.min);
                max = Math.max(max, n.right.max);
            }

            n.min = min;
            n.max = max;

        }
        public Iterable<String> Inorder() {
            Queue<String> q = new ArrayDeque<String>(root != null ? root.to + 1 : 0);
            Inorder(q, root);
            return q;
        }
        private void Inorder(Queue<String> q, Node r) {
            if (r != null) {
                Inorder(q, r.left);
                q.add("[" + r.key + ", " + r.val + "]");
                Inorder(q, r.right);
            }
        }
        private static class Node {
            private boolean color = RED;
            private Node left, right;
            private int key;
            private long val;
            private int from, to;
            private long min, max;

            public Node(int key, long val) {
                this.key = this.from = this.to = key;
                this.val = this.min = this.max = val;
            }
        }
    }
}
