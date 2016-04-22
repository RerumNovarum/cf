import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by god on 29.10.15.
 */

public class TaskCipher2 {

    public static void main(String[] args) throws IOException {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        String s = in.reader.readLine();
        Alphabet alpha = new Alphabet(32, 128 - 32);
        int[] count = new int[alpha.radix];
        for (int i = 0; i < s.length(); i++) {
            count[alpha.toCode(s.charAt(i))]++;
        }
        int uniques = 0;
        for (int i = 0; i < alpha.radix; i++) { if (count[i] > 0) ++uniques; }
        BigInteger result = BigInteger.valueOf(uniques).pow(s.length());

        writer.println(result.toString());
        // --
        writer.flush();
    }
    public static class Alphabet {
        public final int radix;
        public final int offset;

        public Alphabet(int offset, int radix) {
            this.radix = radix;
            this.offset = offset;
        }

        public int toCode(char c) {
            int code = c - offset;
            assert 0 <= code && code < radix;
            return code;
        }
        public char toChar(int code) {
            assert 0 <= code && code < radix;
            return (char) (code + offset);
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
