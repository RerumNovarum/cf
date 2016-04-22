import java.io.*;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * Created by god on 24.10.15.
 */

public class TaskBHTML {

    final static int ORIGINAL = 0, UP = 1, DOWN = 2;
    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter(System.out);

        // --
        StringBuilder sb = new StringBuilder();
        ArrayDeque<Integer> tags = new ArrayDeque<Integer>();
        int mode = ORIGINAL;

        Reader r = new Reader(new BufferedInputStream(System.in));

        while (r.hasNext) {
            String s = r.readUntil('<');
            sb.append(process(mode, s));
            if (!r.hasNext) continue;
            s = r.readUntil('>');
            if (s.charAt(0) == '/') {
                mode = tags.poll();
            } else {
                tags.push(mode);
                if (s.charAt(0) == 'U') mode = UP;
                else mode = DOWN;
            }
        }

        writer.println(sb.toString());
        // --
        writer.flush();
    }

    public static class Reader {
        private InputStream in;
        private boolean hasNext = true;
        public Reader(InputStream in) {
            this.in = in;
        }

        StringBuilder sb = new StringBuilder();

        public String readUntil(char delim) {
            try {
                int code;
                while ((code = in.read()) != -1) {
                    char c = (char) code;
                    if (c != delim) sb.append(c);
                    else break;
                }
                if (code == -1) this.hasNext = false;
            } catch (Exception e) {}
            String result = sb.toString();
            sb.setLength(0);
            return result;
        }
    }

    public static String process(int mode, String s) {
        if (mode == ORIGINAL) return s;
        if (mode == UP) return s.toUpperCase();
        if (mode == DOWN) return s.toLowerCase();

        throw new IllegalArgumentException();
    }
}
