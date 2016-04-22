import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by god on 01.11.15.
 */

public class TaskRainy {

    public static void main(String[] args) throws IOException {
        In in = new In(new FileInputStream("road.in"));
        PrintWriter writer = new PrintWriter(new FileOutputStream("road.out"));
        // --
        int n = in.nextInt();

        final int CLEAN = 0, WET = 1;
        int[][] map = new int[2][n];

        for (int i = 0; i < 2; i++) {
            String row = in.next();
            for (int j = 0; j < n; j++) {
                map[i][j] = row.charAt(j) == 'W' ? WET : CLEAN;
            }
        }

        UF uf = new UF(2 * n);
        for (int i = 0; i < n; i++) {
            boolean u = map[0][i] == CLEAN;
            boolean l = map[1][i] == CLEAN;
            boolean ur = i < n - 1 && map[0][i + 1] == CLEAN;
            boolean lr = i < n - 1 && map[1][i + 1] == CLEAN;
            if (l && u)  uf.union(id(1, i, n), id(0, i, n));
            if (u && ur) uf.union(id(0, i, n), id(0, i + 1, n));
            if (l && lr) uf.union(id(1, i, n), id(1, i + 1, n));
            if (l && ur) uf.union(id(1, i, n), id(0, i + 1, n));
            if (u && lr) uf.union(id(0, i, n), id(1, i + 1, n));
        }

        if (uf.connected(id(0, 0, n), id(0, n - 1, n))) writer.print("YES");
        else writer.print("NO");
        // --
        writer.flush();
    }

    public static int id(int i, int j, int n) {
        return n*i + j;
    }

    public static class UF {
        private int[] id;
        private int[] size;

        public UF(int V) {
            id = new int[V];
            size = new int[V];

            for (int i = 0; i < V; i++) {
                id[i] = i;
                size[i] = 1;
            }
        }

        public void union(int v1, int v2) {
            v1 = find(v1);
            v2 = find(v2);
            if (size[v1] > size[v2]) {
                int t = v1; v1 = v2; v2 = t;
            }

            id[v1] = v2;
            size[v2] += size[v1];
        }

        public boolean connected(int v1, int v2) {
            return find(v1) == find(v2);
        }

        public int find(int v) {
            while(v != id[v]) {
                id[v] = id[id[v]];
                v = id[v];
            }
            return v;
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

        public boolean hasNext() {
            restore();
            return tokenizer != null && tokenizer.hasMoreTokens();
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
