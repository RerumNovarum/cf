import java.io.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by god on 31.10.15.
 */

public class TaskInevitable {

    public static void main(String[] args) throws IOException {
        In in = new In(new FileInputStream("inevit.in"));
        PrintWriter writer = new PrintWriter(new FileOutputStream("inevit.out"));
        // --
        int n = in.nextInt();
        int m = in.nextInt();
        Graph g = new Graph(n);
        for (int i = 0; i < m; i++) {
            g.addEdge(i, in.nextInt() - 1, in.nextInt() - 1);
        }

        Solver s = new Solver(g, 0, n - 1);
        writer.println(s.count());
        for (int e : s.edges())
                writer.print((e + 1) + " ");
        // --
        writer.flush();
    }

    public static class Solver {
        private HashSet<Integer> importantEdges;

        public Solver(Graph g, int from, int to) {
            importantEdges = new HashSet<Integer>();
            Bridges b = new Bridges(g);
            Paths p = new Paths(g, from, to);

            for (int e = 0; e < g.E(); e++) {
                if (b.isBridge(e) && p.isEdgeOnPath(e))
                    importantEdges.add(e);
            }
        }

        public int count() {
            return importantEdges.size();
        }

        public Iterable<Integer> edges() {
            return importantEdges;
        }
    }

    public static class Edge {
        public final int id;
        public final int u, w;

        public Edge(int id, int u, int w) {
            this.id = id;
            this.u = u;
            this.w = w;
        }

        public int other(int v) {
            if (v == u) return w;
            else return u;
        }
    }
    public static class Graph {
        private ArrayDeque<Edge>[] adj;
        private HashMap<Integer, Edge> edges;

        private final int V;
        private int E;


        public Graph(int V) {
            this.V = V;
            adj = new ArrayDeque[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayDeque<Edge>();
            }
            edges = new HashMap<Integer, Edge>();
        }

        public void addEdge(int id, int v, int w) {
            Edge e = new Edge(id, v, w);
            adj[v].add(e);
            adj[w].add(e);
            edges.put(e.id, e);
            ++E;
        }

        public Iterable<Edge> adj(int i) {
            return this.adj[i];
        }

        public Edge edge(int id) {
            return edges.get(id);
        }

        public int V() {return this.V;}
        public int E() {return this.E;}
    }

    public static class Bridges {
        private static int SENTINEL = Integer.MAX_VALUE;

        private int[] tIn;
        private int[] f;
        private HashSet<Integer> bridges;
        private int timer;

        public Bridges(Graph g) {
            build(g);
        }

        public boolean isBridge(int edge) {
            return bridges.contains(edge);
        }

        private void build(Graph g) {
            bridges = new HashSet<Integer>(g.V());
            tIn = new int[g.V()];
            f = new int[g.V()];
            timer = 0;

            for (int i = 0; i < g.V(); i++) {
                tIn[i] = f[i] = SENTINEL;
            }


            for (int v = 0; v < g.V(); ++v) {
                if (tIn[v] == SENTINEL) search(g, v, new Edge(v, v, -1));
            }
            f = null;
            tIn = null;
        }

        private void search(Graph g, int v, Edge o) {
            tIn[v] = timer++;
            f[v] = tIn[v];

            for (Edge e : g.adj(v)) {
                int w = e.other(v);
                if (e.id == o.id) continue;
                if (tIn[w] == SENTINEL) {
                    search(g, w, e);
                    f[v] = Math.min(f[v], f[w]);
                    if (f[w] > tIn[v]) bridges.add(e.id);
                } else
                    f[v] = Math.min(f[v], tIn[w]);
            }
        }
    }

    public static class Paths {
        private HashSet<Integer> pathsEdges;
        private boolean[] pathPart;
        private boolean[] marked;
        private final int from, to;

        public Paths(Graph g, int from, int to) {
            this.from = from;
            this.to = to;
            build(g);
        }

        public boolean isEdgeOnPath(int edge) {
            return pathsEdges.contains(edge);
        }

        public void build(Graph g) {
            pathsEdges = new HashSet<Integer>();
            pathPart = new boolean[g.V()];
            marked = new boolean[g.V()];

            pathPart[to] = true;
            marked[to] = true;

            search(g, from, new Edge(from, from, -1));
            marked = null; pathPart = null;
        }

        private void search(Graph g, int v, Edge o) {
            marked[v] = true;

            for (Edge e : g.adj(v)) {
                int w = e.other(v);

                if (v == w) continue;
                if (o.id == e.id) continue;

                if (!marked[w]) search(g, w, e);
                if (pathPart[w]) {
                    pathPart[v] = true;
                    pathsEdges.add(e.id);
                }
            }
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
