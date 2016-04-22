import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by god on 29.01.16.
 * [Constellation](http://codeforces.com/contest/618/problem/C)
 */

public class TaskC {

    public static class Point implements Comparable<Point> {
        public int x, y, i;

        @Override
        public int compareTo(Point o) {
            int cmp = Integer.compare(this.x, o.x);
            if (cmp == 0) Integer.compare(this.y, o.y);
            return cmp;
        }
    }
    public static boolean line(Point p1, Point p2, Point p3) {
        return (p2.y - p1.y)*(p3.x - p1.x) == (p3.y - p1.y) * (p2.x - p1.x);
    }
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        int n = in.nextInt();
        Point[] p = new Point[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Point();
            p[i].x = in.nextInt();
            p[i].y = in.nextInt();
            p[i].i = i + 1;
        }
        Arrays.sort(p);
        int j = 2;
        while(line(p[0], p[1], p[j])) ++j;
        writer.println(p[0].i + " " + p[1].i + " " + p[j].i);
        // --
        writer.flush();
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

        public boolean hasNext() {
            restore();
            return tokenizer != null && tokenizer.hasMoreTokens();
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
