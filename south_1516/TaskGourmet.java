import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeSet;

/* [Gourmet and Banquet](http://codeforces.com/contest/589/problem/F)
 */

public class TaskGourmet {

    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        TaskGourmet task = new TaskGourmet();
        task.open();
        task.solve();
        task.close();
    }

    class Dish implements Comparable<Dish> {
        public int a, b;
        public int index;

        public Dish(int a, int b, int index) {
            this.a = a;
            this.b = b;
            this.index = index;
        }

        public int length() {
            return Math.abs(a - b);
        }

        @Override
        public int compareTo(Dish dish) {
            int t = Integer.compare(b, dish.b);
            if (t == 0)
                return Integer.compare(a, dish.a);

            return t;

        }
    }

    void solve() throws IOException {
        int n = nextInt();
        Dish[] dishs = new Dish[n];

        for (int i = 0; i < n; i++) {
            int a = nextInt();
            int b = nextInt();
            dishs[i] = new Dish(a, b, i);
        }

        int right = 10000;
        int left = 0;
        int mid = 0;
        while (right > left + 2) {
            mid = (right + left) / 2;
            if (test(dishs, mid))
                left = mid;
            else
                right = mid - 1;
        }

        while (test(dishs, left))
            left++;

        out.print((left - 1) * n);
    }

    private boolean test(Dish[] s, int value) {
        TreeSet<Dish> current = new TreeSet<>();
        int[] count = new int[s.length];

        for (int i = 0; i <= 10000; i++) {
            current.clear();
            for (int j = 0; j < s.length; j++) {
                if (s[j].a <= i && s[j].b > i && count[j] < value)
                    current.add(s[j]);
            }

            if (!current.isEmpty()) {
                Dish seg = current.first();
                count[seg.index]++;
            }
        }

        for (int i = 0; i < s.length; i++) {
            if (count[i] != value)
                return false;
        }
        return true;
    }

    String nextToken() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            String str = in.readLine();
            if (str == null) return null;
            else st = new StringTokenizer(str);
        }
        return st.nextToken();
    }
    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }
    double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }
    private void close() {
        try {
            out.close();
            in.close();
        } catch (IOException ignore) {
        }
    }
    private void open() {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(new BufferedOutputStream(System.out));
    }

}
