import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * Created by god on 24.10.15.
 */

public class TaskKingBerlOld {

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        int n = in.nextInt();
        int m = in.nextInt();

        Digraph g = new Digraph(n);

        for (int i = 0; i < m; i++) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            int c = in.nextInt();
            g.addEdge(y, x, c);
        }

        final int MIN = -10*1000;
        final int MAX = +10*1000;
        final int GAP = -1048576;

        int[] dowry = new int[n];
        for (int i = 0; i < n; i++) {
            dowry[i] = GAP;
        }

        Topo topo = new Topo(g);
        boolean possible = topo.acyclic();
        if (!possible) {
            writer.println(-1);
        } else for (int v : topo.order()) {
            if (dowry[v] == GAP) dowry[v] = MIN;
            for (Edge e : g.adj(v)) {
                int w = e.to;
                if (dowry[w] < dowry[v] + e.dowry)
                    dowry[w] = dowry[v] + e.dowry;
                if (dowry[w] > MAX) possible = false;
            }
        }


        for (int i = 0; i < n; i++) {
            writer.print(dowry[i] + " ");
        }

        // --
        writer.flush();
    }

    public static class Edge {
        public int from, to, dowry;

        public Edge() {}
        public Edge(int from, int to, int dowry) {
            this.from = from;
            this.to = to;
            this.dowry = dowry;
        }
    }
    public static class Digraph {
        private ArrayDeque<Edge>[] adj;
        private int V;
        public Digraph(int V) {
            this.V = V;
            adj = new ArrayDeque[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayDeque<Edge>();
            }
        }

        public void addEdge(int from, int to, int dowry) {
            addEdge(new Edge(from, to, dowry));
        }
        public void addEdge(Edge e) {
            adj[e.from].add(e);
        }
        public Iterable<Edge> adj(int v) {
            return adj[v];
        }
        public int V() { return this.V; }
    }
    public static class Topo {
        ArrayDeque<Integer> order;
        private boolean orderExist = true;

        public Topo(Digraph g) {
            order = new ArrayDeque<Integer>(g.V());
            int[] state = new int[g.V()];

            for (int i = 0; i < g.V(); i++) {
                dfs(g, state, i);
            }
        }

        public final int NEW = 0, PROCESSING = 1, CLOSED = 2;

        private void dfs(Digraph g, int[] state, int v) {
            state[v] = PROCESSING;
            for (Edge e : g.adj(v)) {
                int w = e.to;
                if (state[w] == NEW)
                    dfs(g, state, w);
                else if (state[w] == PROCESSING) {
                    orderExist = false;
                    return;
                }
            }
            state[v] = CLOSED;
            order.push(v);
        }

        public boolean acyclic() {
            return orderExist;
        }

        public Iterable<Integer> order() {
            return order;
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
