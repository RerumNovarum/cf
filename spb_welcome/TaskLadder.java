import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by god on 31.10.15.
 */

public class TaskLadder {

    public static void main(String[] args) throws IOException {
        In in = new In(new FileInputStream("ladder.in"));
        PrintWriter writer = new PrintWriter(new FileOutputStream("ladder.out"));
        // --
        int n = in.nextInt();
        int[] a = new int[n + 2];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        int k = in.nextInt();

        int[] opt = new int[n + 2];
        for (int i = 1; i < n + 2; i++) {
            opt[i] = Integer.MIN_VALUE;
        }

        for (int i = 0; i <= n; i++) {
            for (int j = 1; (i + j < n + 2) && j <= k; j++) {
                opt[i + j] = Math.max(opt[i + j], opt[i] + a[i + j]);
            }
        }

        writer.println(opt[n + 1]);
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
