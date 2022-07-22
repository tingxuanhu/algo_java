package heap;

import java.util.Comparator;

public class CoverMax {

    public static int getMax(int[][] lines) {

    }

    public static class StartComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public record Line(int start, int end) {

    }

}
