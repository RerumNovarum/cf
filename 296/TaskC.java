import java.io.*;
import java.util.Comparator;
import java.util.TreeSet;

public class TaskC {

    public static void main(String[] args) throws IOException {
        // write your code here
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        Task527C solver = new TaskC().new Task527C(reader, writer);
        solver.solve();
        writer.close();
    }

    public class Task527C {
        public Task527C(BufferedReader reader, BufferedWriter writer) {
            this.reader = reader;
            this.writer = writer;
        }

        public GlassManager glassManager;
        public long w, h, n;

        BufferedReader reader;
        BufferedWriter writer;

        public void solve() throws IOException {
            String[] input;
            String inputLine;
            input = reader.readLine().split(" ");
            w = Long.parseLong(input[0]);
            h = Long.parseLong(input[1]);
            n = Long.parseLong(input[2]);
            glassManager = new GlassManager(w, h);

            for (long i = 0; i < n; ++i) {
                inputLine = reader.readLine();
                glassManager.Carve(inputLine);
                writer.write(Long.toString(glassManager.getMaxArea()));
                if (i != n - 1) writer.newLine();
            }
        }
    }

    public class Segment {
        public Segment(long x1, long x2) {
            this.x1 = x1;
            this.x2 = x2;
        }

        long x1, x2;

        public long getLength() {
            return x2 - x1;
        }
    }

    public class SegmentManager {
        public SegmentManager(long length) {
            positionOrdered = new TreeSet<Segment>(
                    new Comparator<Segment>() {
                        @Override
                        public int compare(Segment segment, Segment t1) {
                            int order = Long.compare(segment.x1, t1.x1);
                            return order != 0 ? order : Long.compare(segment.getLength(), t1.getLength());
                        }
                    }
            );
            lengthOrdered = new TreeSet<Segment>(
                    new Comparator<Segment>() {
                        @Override
                        public int compare(Segment segment, Segment t1) {
                            int order = Long.compare(segment.getLength(), t1.getLength());
                            return order != 0 ? order : Long.compare(segment.x1, t1.x1);
                        }
                    }
            );
            Segment initial = new Segment(0, length);
            add(initial);
        }

        TreeSet<Segment> positionOrdered;
        TreeSet<Segment> lengthOrdered;


        public long getMaxLength() {
            return lengthOrdered.last().getLength();
        }

        public void carve(long x) {
            Segment workingSegment = find(x);

            remove(workingSegment);

            add(new Segment(workingSegment.x1, x));
            add(new Segment(x, workingSegment.x2));
        }

        public Segment find(long x) {
            Segment fictive = new Segment(x, x);
            return positionOrdered.floor(fictive);
        }

        public void remove(Segment s) {
            positionOrdered.remove(s);
            lengthOrdered.remove(s);
        }

        public void add(Segment s) {
            positionOrdered.add(s);
            lengthOrdered.add(s);
        }
    }

    public enum Action {
        VerticalCarve,
        HorizontalCarve
    }

    public class GlassManager {
        public GlassManager(long w, long h) {
            vertical = new SegmentManager(h);
            horizontal = new SegmentManager(w);
        }

        SegmentManager vertical, horizontal;

        public long Carve(String rawInput) {
            String[] splitted = rawInput.split(" ");
            Action action = splitted[0].equals("H") ?
                    Action.HorizontalCarve :
                    Action.VerticalCarve;
            long position = Long.parseLong(splitted[1]);

            return Carve(action, position);
        }

        public long Carve(Action action, long position) {
            switch (action) {
                case HorizontalCarve:
                    vertical.carve(position);   // HorizontalCarve mean carve vertical (!) segment horisontally
                    break;
                case VerticalCarve:
                    horizontal.carve(position); // Vice versa
                    break;
            }

            return getMaxArea();
        }

        public long getMaxArea() {
            return vertical.getMaxLength() * horizontal.getMaxLength();
        }
    }
}
