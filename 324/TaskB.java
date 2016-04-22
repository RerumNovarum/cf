import java.io.*;
import java.util.Scanner;

public class TaskB {
    public static final String TASK = "B";
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
        int n = scanner.nextInt();

        final long mod_base = 1000 * 1000 * 1000 + 7;
        final long ways_to_fail = 7;
        final long ways_total = 27;

        long total = 1;
        long fails = 1;

        for (int i = 0; i < n; i++) {
            total = (total * ways_total) % mod_base;
            fails = (fails * ways_to_fail) % mod_base;
        }

        long result = (total - fails + mod_base) % mod_base;

        writer.println(result);


        // --
        writer.flush();
    }
}
