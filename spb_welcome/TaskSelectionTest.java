import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by god on 28.10.15.
 */

public class TaskSelectionTest {

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        Random rnd = new Random();;
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Math.abs(rnd.nextInt(4*n));
        }
        //int[] sorted = Arrays.copyOf(a, a.length);
        //Arrays.sort(sorted);

        for (int i = 0; i < n; i++) {
            //writer.print(sorted[i]);
            writer.print(" ");
        }

        TaskSelection.shuffle(a);
        for (int i = 0; i < n; i++) {
            int res = TaskSelection.select(a, i);
            /*if (sorted[i] != res) {
                writer.print("k=" + i + " ");
                writer.println(
                        "Mismatch: expected " + sorted[i] + ", received " + res);
                writer.flush();
            }*/
        }
        // --
        writer.flush();
    }

    public static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    public static int partition(int[] a, int lo, int hi) {
        int i = lo;
        int l = lo;
        int r = hi;

        int pivot = a[lo];

        while (i <= r) {
            if (a[i] < pivot) swap(a, i++, l++);
            if (a[i] > pivot) swap(a, i, r--);
            else ++i;
        }

        return (l+r)/2;
    }
    public static int select(int[] a, int k) {
        int lo = 0;
        int hi = a.length - 1;

        while(lo < hi) {
            int mi = partition(a, lo, hi);
            if (k == mi) return a[k];
            if (k < mi) hi = mi - 1;
            else lo = mi + 1;
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
