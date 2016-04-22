import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class TaskSahaKate {

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        String n = in.next();
        int k = in.nextInt();

        Digit[] num = parseNumber(n);
        Arrays.sort(num, new DigitValComparator());
        Arrays.sort(num, k, n.length(), new DigitIndexComparator());
        writer.println(numberToString (num, k, n.length()));
        // --
        writer.flush();
    }

    public static String numberToString(Digit[] num, int from, int to) {
        StringBuilder sb = new StringBuilder(to - from);
        for (int i = from; i < to; ++i) {
            Digit d = num[i];
            sb.append(d.val);
        }
        return sb.toString();
    }
    public static Digit[] parseNumber(String s) {
        Digit[] num = new Digit[s.length()];
        for (int i = 0; i < s.length(); i++) {
            num[i] = new Digit(i, Character.digit(s.charAt(i), 10));
        }
        return num;
    }
    public static class DigitIndexComparator implements Comparator<Digit> {

        @Override
        public int compare(Digit digit, Digit t1) {
            return Integer.compare(digit.i, t1.i);
        }
    }
    public static class DigitValComparator implements Comparator<Digit> {
        @Override
        public int compare(Digit digit, Digit t1) {
            int cmp = Integer.compare(digit.val, t1.val);
            if (cmp == 0) cmp = Integer.compare(digit.i, t1.i);
            return cmp;
        }
    }
    public static class Digit {
        public int i, val;

        public Digit() {}
        public Digit(int i, int val) { this.i = i; this.val = val; }
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
