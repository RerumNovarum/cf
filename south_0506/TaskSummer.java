import java.io.*;
import java.util.*;

/**
 * Created by god on 24.10.15.
 */

public class TaskSummer {

    public static void main(String[] args) {
        solve(new InputStreamReader(System.in),
                new OutputStreamWriter( System.out));
    }

    public static void solve(Reader _in, Writer _out) {

        In in = new In(_in);
        PrintWriter writer = new PrintWriter(_out);
        // --
        int N = in.nextInt();
        int X = in.nextInt() - 1;
        int Y = in.nextInt() - 1;
        int M = in.nextInt();

        Graph g = new Graph(N);
        for (int i = 0; i < M; i++) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            long l = in.nextLong();
            g.addEdge(x, y, l);
        }
        Counter c = new Counter(g, X, Y);

        for (int i = 0; i < N; i++) {
            writer.print(c.count(i));

            if (i != N - 1) writer.print(" ");
        }

        // --
        writer.flush();
    }

    public static class Edge {
        public int u, v;
        public long len;

        public Edge() {}
        public Edge(int u, int v, long len) {
            this.u = u; this.v = v;
            this.len = len;
        }

        public int other(int w) {
            if (w == u) return v;
            else return u;
        }

        public String toString() {
            return u + " " + v;
        }
    }
    public static class Graph {
        private ArrayDeque<Edge>[] adj;
        private int V;
        private int edges;
        public Graph(int V) {
            this.V = V;
            adj = new ArrayDeque[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayDeque<Edge>();
            }
        }

        public void addEdge(int u, int v, long len) {
            addEdge(new Edge(u, v, len));
            ++edges;
        }
        public void addEdge(Edge e) {
            adj[e.u].add(e);
            adj[e.v].add(e);
        }
        public Iterable<Edge> adj(int v) {
            return adj[v];
        }
        public int E() { return edges; }
        public int V() { return this.V; }
    }

    public static class Counter {
        private long[] counts;
        public Counter(Graph g, int src, int tar) {
            this.counts = new long[g.V];
            Paths p = new Paths(g, src, tar);
            PriorityQueue<Spot> pq = new PriorityQueue<Spot>(g.V);
            pq.add(new Spot(src, 0));

            boolean[] marked = new boolean[g.V];
            long ways = 0;
            ArrayDeque<Spot> eqs = new ArrayDeque<Spot>();

            while(!pq.isEmpty()) {
                long oldCount = ways;

                {
                    eqs.clear();
                    long prio = pq.peek().len;
                    Spot t;
                    while(!pq.isEmpty()) {
                        t = pq.poll();
                        if (t.len == prio) eqs.add(t);
                        else {
                            pq.add(t);
                            break;
                        }
                    }
                }

                for(Spot s : eqs) {
                    int v = s.v;

                    counts[v] = oldCount - p.inDegree(v) + 1;
                    ways -= p.inDegree(v);

                    for (Edge e : p.next(v)) {
                        int w = e.other(v);
                        if (!marked[w]) {
                            marked[w] = true;
                            pq.add(new Spot(w, p.distTo(w)));
                        }
                        ++ways;
                    }
                }
            }
        }

        public long count(int v) {
            return counts[v];
        }
    }

    public static class Paths {
        private long[] dist;
        private boolean[] onSP;
        private ArrayDeque<Edge>[] next;
        private Set<Edge>[] prev;

        public Paths(Graph g, int s, int t) {
            search(g, s, t);
        }

        public boolean onSP(int v) {
            return onSP[v];
        }

        public Iterable<Edge> next(int v) {
            return next[v];
        }

        public long distTo(int v) {
            return dist[v];
        }

        public int inDegree(int v) { return prev[v].size(); }
        public int outDegree(int v) { return next[v].size(); }

        private void search(Graph g, int s, int t) {
            dist = new long[g.V];
            prev = new HashSet[g.V];
            for (int i = 0; i < g.V; i++) {
                prev[i] = new HashSet<Edge>();
            }

            for (int i = 0; i < g.V; ++i) dist[i] = Integer.MAX_VALUE;
            dist[s] = 0;
            PriorityQueue<Spot> pq = new PriorityQueue<Spot>(g.V);
            pq.add(new Spot(s, 0));

            while(!pq.isEmpty()) {
                Spot spot = pq.poll();
                int v = spot.v;
                if (spot.len > dist[v]) continue;

                for (Edge e : g.adj(v)) {
                    int w = e.other(v);
                    long nlen = dist[v] + e.len;
                    if (nlen > dist[w]) continue;
                    if (nlen == dist[w]) prev[w].add(e);
                    if (nlen < dist[w]) {
                        dist[w] = nlen;
                        prev[w].clear();
                        prev[w].add(e);
                        pq.add(new Spot(w, nlen));
                    }
                }
            }

            onSP = new boolean[g.V];
            next = new ArrayDeque[g.V];
            for (int i = 0; i < g.V; i++) {
                next[i] = new ArrayDeque<Edge>();
            }
            pq.add(new Spot(t, 0));
            while(!pq.isEmpty()) {
                Spot spot  = pq.poll();
                int v = spot.v;
                for (Edge e : prev[v]) {
                    int u = e.other(v);
                    next[u].add(e);
                    if (!onSP[u]) {
                        onSP[u] = true;
                        pq.add(new Spot(u, spot.len + 1));
                    }
                }
            }
            // prev = null;
        }

    }

    public static class Spot implements Comparable<Spot> {
        public int v;
        public long len;
        public Spot() {}
        public Spot(int v, long len) { this.v = v; this.len = len; }

        public int compareTo(Spot that) {
            int cmp = Long.compare(this.len, that.len);
            if (cmp == 0) cmp = Integer.compare(this.v, that.v);
            return cmp;
        }
    }

    public static class In {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public In(Reader in) {
            reader = new BufferedReader(in);
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
        public long nextLong() { return Long.parseLong(next()); }
    }
}