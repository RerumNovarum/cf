import java.io.BufferedInputStream;
import java.util.Hashtable;
import java.util.Scanner;

public class TaskD {

    public static void main(String[] args) {
        TaskD solver = new TaskD();
        solver.solve();
    }

    public void solve() {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int n = scanner.nextInt();
        SoldierManager mgr = new SoldierManager(n);

        for (int soldier = 1; soldier <= n; ++soldier) {
            int color = scanner.nextInt();
            mgr.paint(soldier, color);
        }
        int steps = 0;
        int m = scanner.nextInt();
        for (steps = 0; steps < m && !mgr.wereMonochromatic(); ++steps) {
            int soldier = scanner.nextInt();
            int color = scanner.nextInt();
            mgr.paint(soldier, color);
        }

        if (mgr.wereMonochromatic())
            System.out.print(steps);
        else
            System.out.print(-1);

    }

    private class SoldierManager {
        private Hashtable<Integer, Integer> colors;
        private int[] soldiers;
        private int n;
        private boolean monochroma = false;

        public SoldierManager(int n) {
            this.colors = new Hashtable<Integer, Integer>(n);
            this.soldiers = new int[n + 1]; // indices: [1,n]
            this.n = n;
        }

        public void paint(int soldier, int color) {
            int oldColor = soldiers[soldier];
            dec(oldColor);
            inc(color);
            soldiers[soldier] = color;

            if (count(color) == this.n)
                this.monochroma = true;
        }
        public boolean wereMonochromatic() {
            return this.monochroma;
        }
        public int count(int color) {
            if (colors.containsKey(color))
                return colors.get(color);
            return 0;
        }
        public void inc(int color) {
            int count = count(color);
            colors.put(color, count + 1);
        }
        public void dec(int color) {
            int count = count(color);
            colors.put(color, count - 1);
        }
    }
}
