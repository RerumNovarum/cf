import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TaskTriangle {

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        int n = in.nextInt();

        BigInteger[] segments = new BigInteger[n];
        for (int i = 0; i < n; i++) {
            segments[i] = new BigInteger(in.next());
        }
        Arrays.sort(segments);
        BigInteger a, b, c;
        a = b = c = BigInteger.ZERO;
        boolean found = false;
        for (int i = 2; !found && i < n; i++) {
            a = segments[i - 2];
            b = segments[i - 1];
            c = segments[i];

            if (a.add(b).compareTo(c) != 1) continue;
            if (a.add(c).compareTo(b) != 1) continue;
            if (b.add(c).compareTo(a) != 1) continue;
            found = true;
        }
        if (!found) {
            a = b = c = BigInteger.ZERO;
        }
        writer.println(a + " " + b + " " + c);
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

        public String next() {
            restore();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
