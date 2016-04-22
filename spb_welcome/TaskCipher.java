import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by god on 29.10.15.
 */

public class TaskCipher {
    private final static Alphabet ALPHABET = new Alphabet(32, 128 - 32);

    public static void main(String[] args) throws IOException {
        In in = new In(new FileInputStream("bacon.in"));
        PrintWriter writer = new PrintWriter(new FileOutputStream("bacon.out"));
        //In in = new In(System.in);
        //PrintWriter writer = new PrintWriter(System.out);
        // --
        char[] src = in.reader.readLine().toCharArray();
        int n = src.length;

        Trie t = new Trie();

        long count = 0;
        for (int i = 0; i < n; i++) {
            Trie tt = t;

            for (int j = i; j != n; j++) {
                int c;
                if (j < n) {
                    c = ALPHABET.toCode(src[j]);
                    if (tt.links[c] == null) tt.links[c] = new Trie();
                    tt = tt.links[c];
                } else tt = null;

                if (tt != null && !tt.isWord) {
                    tt.isWord = true;
                    ++count;
                }
            }
        }

        writer.println(count);

        // --
        writer.flush();
    }
    public static class Substring {
        private char[] raw;
        private int offset, length;

        public Substring(char[] src, int offset, int len) {
            this.raw = src;
            this.offset = offset;
            this.length = len;
        }

        public char charAt(int i) {
            assert i < offset + length;
            return raw[offset + i];
        }
        public int length() {
            return this.length;
        }

        public String toString() {
            return new String(raw, offset, length);
        }
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
    public static class Trie {

        private boolean isWord = false;
        private Trie[] links;

        public Trie() { links = new Trie[ALPHABET.radix]; }
        private Trie(boolean word) {
            this.isWord = word;
            this.links = new Trie[ALPHABET.radix];
        }

        public boolean addNew(char[] raw, int offset, int length) {
            Trie t = this;
            boolean isNew = false;
            for (int i = 0; i < length; i++) {
                int c = raw[offset + i] - 32;
                if (t.links[c] == null) {
                    t.links[c] = new Trie();
                    isNew = true;
                }
                t = t.links[c];
            }
            if (!t.isWord) isNew = true;
            t.isWord = true;
            return isNew;
        }
        public void add(Substring s) {
            Trie t = this;
            for (int i = 0; i < s.length(); i++) {
                int c = ALPHABET.toCode(s.charAt(i));
                if (t.links[c] == null) {
                    t.links[c] = new Trie();
                }
                t = t.links[c];
            }
            t.isWord = true;
        }
        public boolean contains(Substring s) {
            Trie t = this;
            for (int i = 0; i < s.length(); i++) {
                int c = ALPHABET.toCode(s.charAt(i));
                if (t.links[c] == null) return false;
                else t = t.links[c];
            }
            return t != null && t.isWord;
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
