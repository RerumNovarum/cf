import java.io.*;
import java.util.Scanner;
import java.util.TreeSet;

public class TaskD {
    public static final String TASK = "exam";
    private static final boolean FILE_IO = true;

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
        TreeSet<Integer> slots  = new TreeSet<Integer>();
        int n = scanner.nextInt();
        int max_count = 2 * 100 * 1000;
        for (int i = 1; i <= max_count; i++) {
            slots.add(i);
        }

        for (int i = 0; i < n; i++) {
            int a = scanner.nextInt();
            if (a > 0) {
                int ceil = slots.ceiling(a);
                slots.remove(ceil);
                writer.println(ceil);
            } else {
                slots.add(Math.abs(a));
            }
        }
        // --
        writer.flush();
    }
}
