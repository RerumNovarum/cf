import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by god on 25.10.15.
 */

public class TaskStomatology {

    public static void main(String[] args) {
        In in = new In(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        // --
        int n = in.nextInt();
        int k = in.nextInt();
        int p = in.nextInt();

        int[] b = new int[k];
        for (int i = 0; i < k; i++) {
            b[i] = in.nextInt();
        }

        Tooth[][] gums = new Tooth[k][];
        {
            Tooth[] tooths = new Tooth[n];
            int[] aux = new int[k];
            for (int i = 0; i < n; i++) {
                int cost = in.nextInt();
                int gum = in.nextInt() - 1;

                Tooth t = new Tooth(i, gum, cost);
                tooths[i] = t;
                aux[gum]++;
            }

            for (int g = 0; g < k; g++) {
                gums[g] = new Tooth[aux[g]];
            }
            for (int i = 0; i < k; i++) {
                aux[i] = 0;
            }

            for (int i = 0; i < n; i++) {
                int g = tooths[i].gum;
                gums[g][aux[g]] = tooths[i];
            }

            for (int i = 0; i < k; i++) {
                Arrays.sort(gums[i]);
            }
        }



        // --
        writer.flush();
    }

    public static class Tooth implements Comparable<Tooth> {
        public int id, gum, cost;

        public Tooth() {}
        public Tooth(int id, int gum, int cost) {
            this.id = id;
            this.gum = gum;
            this.cost = cost;
        }


        @Override
        public int compareTo(Tooth that) {
            int cmp = Integer.compare(this.cost, that.cost);
            if (cmp == 0) cmp = Integer.compare(this.id, that.id);
            return cmp;
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
