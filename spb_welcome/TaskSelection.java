import java.io.*;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by god on 28.10.15.
 */

public class TaskSelection {

    public static void main(String[] args) throws IOException {
        In in = new In(new FileInputStream("kth.in"));
        PrintWriter writer = new PrintWriter(new FileOutputStream("kth.out"));
        // --
        int n = in.nextInt();
        int k = in.nextInt();
        int[] a = new int[n];

        int m = Math.min(1743, n);
        for (int i = 0; i < m; i++) {
            a[i] = P(i + 1);
        }

        for (int i = m; i < n; i++) {
            a[i] = a[i%1743];
        }
        writer.println(select(a, k - 1));
        // --
        writer.flush();
    }

    public static int P(long x) {
        return (int)((1577L + x*(1345L + x*(77L + x*(132L))%1743)%1743)%1743L);
    }

    public static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    public static void shuffle(int[] a) {
        Random rnd = new Random();
        for (int i = 0; i < a.length; i++) {
            int j = i + rnd.nextInt(a.length - i);
            swap(a, i, j);
        }
    }
    public static int select(int[] a, int k) {
        int lo = 0;
        int hi = a.length - 1;

        while(lo < hi) {
            int i = lo;
            int l = lo;
            int r = hi;

            int pivot = a[(lo + hi)/2];

            while (i <= r) {
                if (a[i] < pivot) swap(a, i++, l++);
                else if (a[i] > pivot) swap(a, i, r--);
                else ++i;
            }

            if (l <= k && k <= r) return a[k];
            else if (k < l) hi = l - 1;
            else lo = r + 1;
        }
        return a[k];
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
