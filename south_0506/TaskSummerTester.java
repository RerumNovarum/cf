import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by god on 21.11.15.
 */
public class TaskSummerTester {
    Random rnd = new Random();
    public TaskSummerTester(int minn, int maxn) {
        this.N_MIN = minn;
        this.N_MAX = maxn;
        for (this.n = N_MIN-1; this.n < N_MAX; ) {
            iter_N();
        }
    }

    private static final String NEW_LINE = "\r\n";
    private static int testNo = 0;

    int N_MIN, N_MAX;
    public String input, output, valid;


    int n, m, x, y;
    boolean[][] graph;

    private boolean next_graph() {
        int i = n - 1;
        int j = n - 1;

        while(graph[i][j]) {
            --j;
            if (j < 0) {
                --i;
                j = i;
            }
            if (i < 0)
                if (n < N_MAX) {
                    ++n;
                    return next_graph();
                } else return false;
        }

        if (i < 0) return false;
        graph[i][j] = graph[j][i] = true;
        ++j;
        for (; i < n && j < n; ++j) {
            if (j < n) {
                graph[i][j] = graph[j][i] = false;
            } else {
                j = 0;
                i++;
            }
        }
        return true;
    }

    private long last = 0;
    private boolean iter_N() {
        ++this.n;
        graph = new boolean[n][n];
        while(next_graph()) {
            List<TaskSummer.Edge> g = parseGraph();
            this.m = g.size();
            if (m == 0) continue;
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    if (x == y) continue;
                    this.x = x;
                    this.y = y;
                    boolean result = perform(x, y, g);

                    long elapsed_time
                            = System.currentTimeMillis() - last;
                    elapsed_time =
                            TimeUnit.MINUTES.convert(
                                    elapsed_time, TimeUnit.MILLISECONDS);
                    if (elapsed_time > 4) {
                        writeAllTo(testNo + ".pow", input);
                        last = System.currentTimeMillis();
                    }

                    if (!result && !valid.isEmpty()) {

                        writeAllTo("failed." + testNo + ".in", input);
                        writeAllTo("failed." + testNo + ".out", output);
                        writeAllTo("failed." + testNo + ".valid", valid);
                    }
                    ++testNo;
                    if (testNo % 256 == 0) {
                        System.out.println(
                                testNo + " done; " + elapsed_time + "m.");
                        System.out.flush();
                    }
                }
            }
        }
        return n < this.N_MAX;
    }

    public List<TaskSummer.Edge> parseGraph() {
        ArrayList<TaskSummer.Edge> edges = new ArrayList<>();
        int edgesNo = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (graph[i][j] && i > j) {
                    ++edgesNo;
                    edges.add(new TaskSummer.Edge(i, j, 1+rnd.nextInt(200000)));
                }
            }
        }
        return edges;
    }




    public boolean perform(int x, int y, List<TaskSummer.Edge> g) {
        StringBuilder sb = new StringBuilder();

        sb.append(this.n + NEW_LINE);
        sb.append((this.x+1) + NEW_LINE);
        sb.append((this.y+1) + NEW_LINE);
        sb.append(g.size() + NEW_LINE);

        for (TaskSummer.Edge e : g) {
            sb.append((e.u+1) + " " + (e.v+1) + " " + e.len + NEW_LINE);
        }


        this.input = sb.toString(); sb = null;
        StringReader sr = new StringReader(input);
        StringWriter sw = new StringWriter();
        TaskSummer.solve(sr, sw);

        this.output = sw.toString().trim();
        try {sr.reset();} catch (Exception e) {}
        sw = new StringWriter();

        TaskSummerValid tsv = new TaskSummerValid();
        tsv.open(sr, sw);
        try {tsv.solve(); } catch (Exception e) {}

        this.valid = sw.toString().trim();
        tsv.close();

        boolean result = output.equals(valid);
        return result;
    }

    public static void writeAllTo(String filename, String text) {
        try {
            FileWriter w = new FileWriter(filename);
            w.append(text);
            w.close();
        } catch (Exception e) {}
    }
    public static void main(String[] args) {
        int min_n = Integer.parseInt(args[0]);
        int max_n = Integer.parseInt(args[1]);
//        int tests_no = Integer.parseInt(args[2]);

        TaskSummerTester t = new TaskSummerTester(min_n, max_n);
    }
}