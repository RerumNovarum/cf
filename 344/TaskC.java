import javax.tools.ForwardingFileObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by god on 03.03.16.
 */

public class TaskC {

    public static class Manager implements Comparable<Manager> {
        public int i, r, t;
        public Manager(int i, int t, int r){
            this.i = i;
            this.r = r;
            this.t = t;
        }

        @Override
        public int compareTo(Manager o) {
            int cmp = -Integer.compare(this.r, o.r);
            if (cmp == 0) cmp = -Integer.compare(this.i, o.i);
            return cmp;
        }
    }

    public static void reverse(long[] a, int l, int r) {
        while (l < r) {
            long t = a[l];
            a[l] = a[r];
            a[r] = t;
            ++l; --r;
        }
    }
    public static void copy(long[] from, int l1, int r1, long[] to, int l2, int t) {
        int len = r1-l1 + 1;
        if (t == 1) {
            for (int i = 0; i < len; i++) {
                to[l2 + i] = from[l1 + i];
            }
        } else {
            for (int i = 0; i < len; ++i)
                to[l2 + i] = from[r1 - i];
        }
    }
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        int n = in.nextInt();
        int m = in.nextInt();
        long[] a = new long[n];
        Manager[] mgrs = new Manager[m];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
        }
        for (int i = 0; i < m; i++) {
            mgrs[i] = new Manager(i, in.nextInt(), in.nextInt());
        }
        Arrays.sort(mgrs);
        int R = mgrs[0].r;
        long[] out = new long[n];
        Arrays.sort(a, 0, mgrs[0].r);
        copy(a, R, n-1, out, R, 1);
        --R;

        int time = mgrs[0].i;
        int t = mgrs[0].t;
        int l = 0;
        for (int i = 1; i < m; ++i) {
            Manager mgr = mgrs[i];
            if (mgr.i <= time) continue;
            if (mgr.i > time) time = mgr.i;
            if (t == 1){
                copy(a, mgr.r, R, out, mgr.r, 1);
                R = mgr.r-1;
            }
            else {
                copy(a, l, R - mgr.r, out, mgr.r, 2);
                l = R - mgr.r + 1;
            }
            t = mgr.t;
        }
        copy(a, l, R, out, 0, t);
        // --
        for (int i = 0; i < n; i++) {
            writer.print(out[i]);
            if (i != n - 1) writer.print(' ');
        }
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
        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}
