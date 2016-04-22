import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by god on 28.10.15.
 */

public class TaskRight {

    public static void main(String[] args) throws IOException {
        In in = new In(new FileInputStream("right.in"));
        PrintWriter writer = new PrintWriter(new FileOutputStream("right.out"));
        // --
        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 0; i < m; i++) {
            int q = in.nextInt();
            writer.println(lastOccurence(a, q) + 1);
        }
        // --
        writer.flush();
    }

    public static int lastOccurence(int[] a, int v) {
        int lo = 0;
        int hi = a.length - 1;

        while(lo <= hi) {
            int mi = (lo + hi) / 2;
            int miv = a[mi];

            if (miv == v) {
                lo = mi;
                while (lo < hi) {
                    mi = 1 + (lo + hi) / 2;
                    miv = a[mi];
                    if (miv == v) lo = mi;
                    else hi = mi - 1;
                }
                return lo;
            }
            else if (v < miv) hi = mi - 1;
            else if (v > miv) lo = mi + 1;
        }

        return -1;
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
