import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by god on 17.10.15.
 * [Boulevard](http://codeforces.com/contest/589/problem/D)
 */
public class TaskBoulevard2 {

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        int n = in.nextInt();
        Segment[] paths = new Segment[n];
        for (int i = 0; i < n; i++) {
            int t, s, f;
            t = in.nextInt();
            s = in.nextInt();
            f = in.nextInt();

            int t2 = t + Math.abs(f - s);
            Point begin = new Point(t, s);
            Point end = new Point(t2, f);
            paths[i] = new Segment(begin, end);
        }

        int[] counts = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Segment path1 = paths[i];
                Segment path2 = paths[j];
                if (Segment.intersects(path1, path2)) {
                    counts[i]++;
                    counts[j]++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            writer.print(counts[i]);
            if (i < n - 1) writer.print(" ");
        }
        // --
        writer.flush();
    }

    public static class Point {
        public int x, y;
        public Point() {}
        public Point(int x, int y) { this.x = x; this.y = y; }

        public static long vectorProduct(Point p1, Point p2) {
            return (long) p1.x * p2.y - (long) p1.y * p2.x;
        }
        public static int ccw(Point p1, Point p2, Point p3) {
            Point v1 = new Point(p2.x - p1.x, p2.y - p1.y);
            Point v2 = new Point(p3.x - p2.x, p3.y - p2.y);

            long vp = vectorProduct(v1, v2);
            if (vp == 0) return 0;
            if (vp <  0) return -1;
            else         return +1;
        }
    }

    public static class Segment {
        public Point p1, p2;

        public Segment() {}
        public Segment(Point p1, Point p2) { this.p1 = p1; this.p2 = p2; }

        public Point vector() {
            return new Point(p2.x - p1.x, p2.y - p1.y);
        }

        public static boolean intersects(Segment s1, Segment s2) {
            int t1 = Point.ccw(s1.p1, s1.p2, s2.p1) * Point.ccw(s1.p1, s1.p2, s2.p2);
            int t2 = Point.ccw(s2.p1, s2.p2, s1.p1) * Point.ccw(s2.p1, s2.p2, s1.p2);
            boolean bb = intersects(s1.p1.x, s1.p2.x, s2.p1.x, s2.p2.x) &&
                          intersects(s1.p1.y, s1.p2.y, s2.p1.y, s2.p2.y);
            return t1 <= 0 && t2 <= 0 && bb;
        }

        public static boolean intersects(int from1, int to1, int from2, int to2) {
            int f1 = Math.min(from1, to1);
            int t1 = Math.max(from1, to1);
            int f2 = Math.min(from2, to2);
            int t2 = Math.max(from2, to2);
            return (f1 <= f2 && f2 <= t1) ||
                    (f2 <= f1 && f1 <= t2);
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
