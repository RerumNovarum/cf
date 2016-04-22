import java.io.*;
import java.util.Scanner;

public class TaskA {
    public static final String TASK = "TaskA";
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
        int t = scanner.nextInt();

        if (t < 10 || n > 1) {
            StringBuilder sb = new StringBuilder(n);
            sb.append(t);
            while(sb.length() < n) {
                sb.append(0);
            }
            writer.println(sb.toString());
        } else writer.println(-1);
        // --
        writer.flush();
    }

}
