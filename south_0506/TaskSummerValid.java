import java.io.*;
import java.util.*;

/**
 * Created by Anna on 07.10.2015.
 */
public class TaskSummerValid {
    BufferedReader in;
    PrintWriter out;
    StringTokenizer st;

    public static void main(String[] args) throws IOException {
        TaskSummerValid task = new TaskSummerValid();
        task.open(
                new InputStreamReader(System.in),
                new OutputStreamWriter(System.out)
        );

        task.solve();
        task.close();
    }

    class Vertex implements Comparable<Vertex> {
        int index;
        long mark;
        boolean isOnShortWay = false;

        ArrayList<Edge> edges = new ArrayList<Edge>();

        public Vertex(int index, long mark) {
            this.index = index;
            this.mark = mark;
        }

        @Override
        public int compareTo(Vertex o) {
            if (o.mark != this.mark) return Long.compare(this.mark, o.mark);
            return Long.compare(this.index, o.index);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            return index == vertex.index;

        }

        @Override
        public int hashCode() {
            return index;
        }
    }

    class Edge {
        Vertex a, b;
        int weight;
        boolean isGood = false;

        public Edge(Vertex a, Vertex b, int weight) {
            this.a = a;
            this.b = b;
            this.weight = weight;
        }

        public Vertex getOther(Vertex v) {
            if (a.index != v.index) return a;
            else return b;
        }

        public long getMinMark() {
            return Math.min(a.mark, b.mark);
        }

        public long getMaxMark() {
            return Math.max(a.mark, b.mark);
        }
    }

    Vertex[] graph;
    Edge[] edges;

    class SegmentEdge {
        int cntEnded = 0;
        int cntStarted = 0;
        int allCnt = 0;

        public SegmentEdge(int cntEnded, int cntStarted, int allCnt) {
            this.cntEnded = cntEnded;
            this.cntStarted = cntStarted;
            this.allCnt = allCnt;
        }
    }

    public void solve() throws IOException {
        int n = nextInt();
        int startIndex = nextInt() - 1;
        int finishIndex = nextInt() - 1;
        int m = nextInt();

        graph = new Vertex[n];
        edges = new Edge[m];

        for (int i = 0; i < n; i++) {
            graph[i] = new Vertex(i, Long.MAX_VALUE);
        }

        for (int i = 0; i < m; i++) {
            int a = nextInt() - 1;
            int b = nextInt() - 1;
            int weight = nextInt();

            edges[i] = new Edge(graph[a], graph[b], weight);
            graph[a].edges.add(edges[i]);
            graph[b].edges.add(edges[i]);
        }

        graph[startIndex].mark = 0;
        TreeSet<Vertex> markedVertexes = new TreeSet<>();

        Collections.addAll(markedVertexes, graph);

        while (!markedVertexes.isEmpty()) {
            Vertex current = markedVertexes.pollFirst();
            if (current.mark == Long.MAX_VALUE) break;
            for (Edge edge : current.edges) {
                Vertex other = edge.getOther(current);
                if (other.mark > current.mark + edge.weight) {

                    markedVertexes.remove(other);
                    other.mark = current.mark + edge.weight;
                    markedVertexes.add(other);
                }
            }
        }

        Long shortestWay = graph[finishIndex].mark;

        int[] answ = new int[n];
        Arrays.fill(answ, 0);
        if (shortestWay == Long.MAX_VALUE) throw new IOException(); // TODO;
        dfs(finishIndex);

        HashMap<Long, ArrayList<Vertex>> markToVertexesMap = new HashMap<>();
        for (Vertex vertex : graph) {
            if (vertex.isOnShortWay) {
                if (markToVertexesMap.containsKey(vertex.mark)) {
                    markToVertexesMap.get(vertex.mark).add(vertex);
                } else {
                    ArrayList<Vertex> tmp = new ArrayList<>();
                    tmp.add(vertex);
                    markToVertexesMap.put(vertex.mark, tmp);
                }
            }
        }

//        Collections.sort(goodEdges);
        TreeMap<Long, SegmentEdge> tmpCnt = new TreeMap<>();

        for (Edge goodEdge : edges) {
            if (!goodEdge.isGood) continue;
            long edgeStart = goodEdge.getMinMark();
            long edgeFinish = goodEdge.getMaxMark();
            if (tmpCnt.containsKey(edgeStart)) {
                SegmentEdge tmp = tmpCnt.get(edgeStart);
                tmp.cntStarted++;
            } else {
                tmpCnt.put(edgeStart, new SegmentEdge(0, 1, 0));
            }
            if (tmpCnt.containsKey(edgeFinish)) {
                SegmentEdge tmp = tmpCnt.get(edgeFinish);
                tmp.cntEnded++;
            } else {
                tmpCnt.put(edgeFinish, new SegmentEdge(1, 0, 0));
            }
        }
        int curCnt = 0;
        TreeMap<Long, SegmentEdge> markToEdgesCnt = new TreeMap<>();

        for (Long curMark : tmpCnt.keySet()) {
            SegmentEdge tmp = tmpCnt.get(curMark);
            curCnt += tmp.cntStarted - tmp.cntEnded;
            tmp.allCnt = curCnt;
            markToEdgesCnt.put(curMark, tmp);
        }

        for (Vertex vertex : graph) {
            if (vertex.isOnShortWay) {
                answ[vertex.index] += markToVertexesMap.get(vertex.mark).size();
                Long key = markToEdgesCnt.lowerKey(vertex.mark);
                if (key != null)
                    answ[vertex.index] += markToEdgesCnt.get(key).allCnt;

                if (markToEdgesCnt.containsKey(vertex.mark)) answ[vertex.index] -= markToEdgesCnt.get(vertex.mark).cntEnded;
            }
        }

        for (int i = 0; i < n; i++) {
            out.print(answ[i] + " ");
        }
    }

    void dfs(int vertexIndex) {
        Vertex current = graph[vertexIndex];
        current.isOnShortWay = true;

        for (Edge edge : current.edges) {
            Vertex other = edge.getOther(current);
            if (other.mark + edge.weight == current.mark) {
                edge.isGood = true;
                if (!other.isOnShortWay) dfs(other.index);
            }
        }
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    private String nextToken() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            String str = in.readLine();
            if (str == null) return null;
            st = new StringTokenizer(str);
        }
        return st.nextToken();
    }

    public void close() {
        out.close();
    }

    public void open(Reader _in, Writer _out) {
        in = new BufferedReader(_in);
        out = new PrintWriter(_out);
    }
}