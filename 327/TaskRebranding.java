import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by god on 25.10.15.
 */

public class TaskRebranding {

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        int n = in.nextInt();
        int m = in.nextInt();
        String src = in.next();

        NameProcessor np = new NameProcessor();
        for (int i = 0; i < m; i++) {
            char from = in.next().charAt(0);
            char to = in.next().charAt(0);
            np.addRule(from, to);
        }

        writer.println(np.process(src));
        // --
        writer.flush();
    }

    public static class NameProcessor {
        private char[] map;
        public NameProcessor() {
            map = new char[26];
            for (int i = 0; i < 26; i++) {
                map[i] = (char)(i + 'a');
            }
        }

        public void addRule(char from, char to) {
            for (int i = 0; i < 26; i++) {
                if (map[i] == from) map[i] = to;
                else if (map[i] == to) map[i] = from;
            }
        }

        public String process(String s) {
            char[] name = new char[s.length()];
            for (int i = 0; i < s.length(); i++) {
                name[i] = process(s.charAt(i));
            }
            return new String(name);
        }

        private char process(char c) {
            return map[c - 'a'];
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
