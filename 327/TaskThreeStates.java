import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by god on 31.10.15.
 */

public class TaskThreeStates {
    final static int COUNTRIES_COUNT = 3;
    final static int BLOCK_FREE = -1, BLOCK_OBSTACLE = -2;

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --

        int n = in.nextInt();
        int m = in.nextInt();

        Atlas a = new Atlas(n * m);

        for (int i = 0; i < n; i++) {
            String row = in.next();
            for (int j = 0; j < m; j++) {
                byte t = (byte)parseType(row.charAt(j));

                int v = i*m + j;
                int up = v - m;
                int down = v + m;
                int left = v - 1;
                int right = v + 1;

                a.setType(v, t);

                if (i > 0) a.addEdge(v, up);
                if (i < n - 1) a.addEdge(v, down);
                if (j > 0) a.addEdge(v, left);
                if (j < m - 1) a.addEdge(v, right);
            }
        }

        RoadBuilder rb = new RoadBuilder(a, COUNTRIES_COUNT);

        if (rb.isValid()) writer.println(rb.min());
        else writer.println(-1);

        // --
        writer.flush();
    }


    public static int parseType(char c) {
        if (Character.isDigit(c)) return Character.digit(c, 10) - 1;
        if (c == '.') return BLOCK_FREE;
        if (c == '#') return BLOCK_OBSTACLE;

        throw new InvalidParameterException();
    }

    public static class Atlas {
        private ArrayDeque<Integer>[] adj;
        private byte[] type;

        public final int V;

        public Atlas(int V) {
            this.V = V;
            adj = new ArrayDeque[V];
            type = new byte[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayDeque<Integer>();

            }
        }

        public void addEdge(int from, int to) {
            adj[from].add(to);
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }

        public int type(int v) {
            return type[v];
        }

        public void setType(int v, byte t) {
            this.type[v] = t;
        }
    }

    public static class Paths {
        private int[] distTo;

        public Paths(Atlas a, int state) {
            distTo = new int[a.V];
            for (int i = 0; i < a.V; i++) {
                distTo[i] = Integer.MAX_VALUE;
            }

            Queue<Integer> q = new ArrayDeque<Integer>();

            for (int v = 0; v < a.V; v++) {
                int vt = a.type(v);
                if (vt != state) continue;

                distTo[v] = 0;

                for (int w : a.adj(v)) {
                    int wt = a.type(w);
                    if (wt == vt || wt == BLOCK_OBSTACLE) continue;
                    if (distTo[w] == 1) continue;

                    distTo[w] = 1;
                    q.add(w);
                }
            }

            while(!q.isEmpty()) {
                int v = q.poll();
                int t = a.type(v);
                int d = distTo[v];

                for (int w : a.adj(v)) {
                    int wt = a.type(w);
                    if (wt == BLOCK_OBSTACLE) continue;
                    if (distTo[w] != Integer.MAX_VALUE) continue;
                    distTo[w] = d + 1;
                    q.add(w);
                }
            }
        }

        public boolean connectedTo(int v) {
            return distTo[v] != Integer.MAX_VALUE;
        }
        public int distTo(int v) {
            return distTo[v];
        }
    }

    public static class RoadBuilder {
        private long min;

        public RoadBuilder(Atlas a, final int statesCount) {
            build(a, statesCount);
        }

        public boolean isValid() {
            return this.min != Long.MAX_VALUE;
        }

        public long min() {
            assert isValid();
            return this.min;
        }

        private void build(Atlas a, final int statesCount) {
            assert statesCount == 3;
            Paths[] paths = new Paths[statesCount];

            for (int s = 0; s < statesCount; s++) {
                paths[s] = new Paths(a, s);
            }

            long min = StarMin(a, paths);
            min = Math.min(min, PairwiseMin(a, paths));

            this.min = min;
        }

        public long StarMin(Atlas a, Paths[] paths) {
            long min = Long.MAX_VALUE;
            for (int v = 0; v < a.V; v++) {
                boolean connected = true;
                long dist = 1;
                for (int s = 0; s < paths.length && connected; s++) {
                    connected = paths[s].connectedTo(v);
                    dist += paths[s].distTo(v) - 1;
                }
                if (!connected) continue;
                min = Math.min(min, dist);
            }
            return min;
        }

        public long PairwiseMin(Atlas a, Paths[] paths, int s1, int s2) {
            long min = Long.MAX_VALUE;
            for (int v = 0; v < a.V; v++) {
                boolean connected = true;
                connected &= paths[s1].connectedTo(v);
                connected &= paths[s2].connectedTo(v);
                if (!connected) continue;
                long dist = 1;
                dist += paths[s1].distTo(v) - 1;
                dist += paths[s2].distTo(v) - 1;
                min = Math.min(min, dist);
            }
            return min;
        }

        public long PairwiseMin(Atlas a, Paths[] paths) {
            assert paths.length == 3;
            long d01 = PairwiseMin(a, paths, 0, 1);
            long d02 = PairwiseMin(a, paths, 0, 2);
            long d12 = PairwiseMin(a, paths, 1, 2);

            if (d01 == Long.MAX_VALUE ||
                    d02 == Long.MAX_VALUE ||
                    d12 == Long.MAX_VALUE) return Long.MAX_VALUE;

            long min = Math.min(d01 + d02, d01 + d12);
            min = Math.min(min, d02 + d12);

            return min;
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
