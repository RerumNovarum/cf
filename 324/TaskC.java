import java.io.*;
import java.util.Scanner;

public class TaskC {
    public static final String TASK = "C";
    private static final boolean FILE_IO = false;

    public static void main(String[] args) throws IOException {
        InputStream in;
        OutputStream out;

        if (FILE_IO) {
            in = new FileInputStream(TASK + ".in");
            out = new FileOutputStream(TASK + ".out");
        } else {
            in = System.in;
            out = System.out;
        }

        Scanner scanner = new Scanner(new BufferedInputStream(in));
        PrintWriter writer = new PrintWriter(out);
        // --
        final int n = scanner.nextInt();
        final int t = scanner.nextInt();
        final String s1 = scanner.next();
        final String s2 = scanner.next();

        Finder finder = new Finder(s1, s2, n, t);
        if (finder.isSolvable()) {
            writer.println(finder.getSolution());
        } else {
            writer.println("-1");
        }

        // --
        writer.flush();
    }

    public static class Finder {
        private boolean solvable;
        private String solution;
        public Finder(String s1, String s2, int n, int t) {
            int d = dist(s1, s2, n);
            int reqEQ = n - t;

            int eq1 = 0, eq2 = 0, d1 = 0, d2 = 0;

            StringBuilder sb = new StringBuilder(n);
            final char SIGN_UNASSIGNED = '\0';
            for (int i = 0; i < n; i++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);
                if (c1 == c2) {
                    if (eq1 < reqEQ && eq2 < reqEQ) {
                        sb.append(c1);
                        eq1++;
                        eq2++;
                    } else {
                        sb.append(different(c1, c2));
                        d1++;
                        d2++;
                    }
                } else sb.append(SIGN_UNASSIGNED);
            }

            for (int i = 0; i < n; i++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);
                if (c1 != c2) {
                    if (eq1 < reqEQ) {
                        sb.setCharAt(i, c1);
                        ++eq1;
                        ++d2;
                    } else if (eq2 < reqEQ) {
                        sb.setCharAt(i, c2);
                        ++eq2;
                        ++d1;
                    } else {
                        sb.setCharAt(i, different(c1, c2));
                        ++d1; ++ d2;
                    }
                }
            }

            solvable = (eq1 + d1 == n) && (eq2 + d2 == n) && (d1 == t) && (d2 == t);
            if (solvable) solution = sb.toString();
        }

        public boolean isSolvable() { return solvable; }
        public String getSolution() { return solution; }

        private static int dist(String s1, String s2, int n) {
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (s1.charAt(i) != s2.charAt(i)) ++count;
            }
            return count;
        }
        private static char different(char from1, char from2) {
            char result = 'a';
            while(from1 == result || from2 == result) ++result;
            return result;
        }
    }
}
