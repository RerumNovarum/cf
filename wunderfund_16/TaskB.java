import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by god on 29.01.16.
 * [Guess the Permutation](http://codeforces.com/contest/618/problem/B)
 */

public class TaskB {

    public static int max(int[] arr) {
        int n = arr.length;
        int max = arr[0];
        for (int j = 1; j < n; ++j)
            max = Math.max(arr[j], max);
        return max;
    }
    public static int count(int[] arr, int v) {
        int n = arr.length;
        int c = 0;
        for (int j = 0; j < n; j++)
            if (arr[j] == v) ++c;
        return c;
    }
    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        int n = in.nextInt();
        int[][] A = new int[n][n];
        int[] p = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = in.nextInt();
            }
        }

        for (int i = 0; i < n; i++) {
            p[i] = max(A[i]);
        }
        if (count(p, n) == 0) {
            for (int i = 0; i < n; i++) {
                if (count(A[i], p[i]) == 1) {
                    p[i] = n;
                    break;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            writer.print(p[i]);
            if (i != n - 1) writer.print(" ");
        }

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
