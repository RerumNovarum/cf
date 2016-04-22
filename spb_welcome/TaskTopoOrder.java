import java.io.*;
import java.util.ArrayDeque;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by god on 28.10.15.
 */

public class TaskTopoOrder {

    public static void main(String[] args) throws IOException {
        In in = new In(new FileInputStream("topo.in"));
        PrintWriter writer = new PrintWriter(new FileOutputStream("topo.out"));
        // --
        int n = in.nextInt();
        int m = in.nextInt();

        Graph g = new Graph(n);
        for (int i = 0; i < m; i++) {
            g.addEdge(in.nextInt() - 1, in.nextInt() - 1);
        }

        Topo topo = new Topo(g);
        for (int v : topo.order)
            writer.print((v + 1) + " ");
        // --
        writer.flush();
    }

    public static class Graph {
        private Stack<Integer>[] adj;

        public final int V;

        public Graph(int V) {
            this.V = V;
            adj = new Stack[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new Stack<Integer>();
            }
        }

        public void addEdge(int from, int to) {
            adj[from].add(to);
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }
    }

    public static class Topo {
        private final static int WHITE = 0, PROCESSING = 1, HANDLED = 2;

        private ArrayDeque<Integer> order;

        public Topo(Graph g) {
            int[] state;
            order = new ArrayDeque<Integer>(g.V);
            state = new int[g.V];
            for (int i = 0; i < g.V; i++) {
                if (state[i] == WHITE) process(g, state, i);
            }
        }

        public Iterable<Integer> order() { return this.order; }

        private void process(Graph g, int[] state, int v) {
            state[v] = PROCESSING;
            for (int w : g.adj(v))
                if (state[w] == WHITE)
                    process(g, state, w);
            this.order.push(v);
            state[v] = HANDLED;
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
