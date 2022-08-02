package datastucture.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class CoverMax {
    public static int getMax(int[][] m) {
        // 把line类型转为一维数组
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        Arrays.sort(lines, new StartComparator());

        PriorityQueue<Integer> heap = new PriorityQueue<>();  // 小根堆
        int max = 0;

        for (Line line : lines) {
            while (!heap.isEmpty() && heap.peek() <= line.start) {
                heap.poll();
            }
            heap.add(line.end);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    // simplified comparator implementation (lambda function) -- function is the same as getMax()
    public static int getMaxSimplified(int[][] m) {
        Arrays.sort(m, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        int res = 0;
        for (int[] line : m) {
            while (!heap.isEmpty() && heap.peek() <= line[0]) {
                heap.poll();
            }
            heap.add(line[1]);
            res = Math.max(res, heap.size());
        }
        return res;
    }


    public static class StartComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public record Line(int start, int end) {
    }

    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            // generate endpoints for each line
            int l = L + (int) (Math.random() * (R - L + 1));
            int r = L + (int) (Math.random() * (R - L + 1));
            if (l == r) {
                r++;
            }
            ans[i][0] = l;
            ans[i][1] = r;
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int N = 100;
        int L = 0;
        int R = 100;

        for (int i = 0; i < testTime; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = getMax(lines);
            int ans2 = getMaxSimplified(lines);

            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Fucking fucked!");
            }
        }
        System.out.println("Finished!");

    }


}
