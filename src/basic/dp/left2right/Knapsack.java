package basic.dp.left2right;

public class Knapsack {
    public static int maxValue(int[] w, int[] v, int bag) {
        if (w.length != v.length || w == null || w.length == 0) {
            return 0;
        }
        return process(w, v, 0, bag);
    }

    public static int process(int[] w, int[] v, int cur, int rest) {
        if (rest < 0) {
            return -1;
        }
        if (cur == w.length) {
            return 0;
        }
        int p1 = process(w, v, cur + 1, rest);
        int p2 = 0;
        int next = process(w, v, cur + 1, rest - w[cur]);
        if (next != -1) {
            p2 = next + v[cur];
        }
        return Math.max(p1, p2);
    }

    // basic.dp
    public static int dp(int[] w, int[] v, int bag) {
        if (w.length != v.length || w == null || w.length == 0) {
            return 0;
        }
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1];
        for (int cur = N - 1; cur >= 0; cur--) {
            for (int rest = 0; rest <= bag; rest++) {
                int next = rest - w[cur] < 0 ? -1 : dp[cur + 1][rest - w[cur]];
                dp[cur][rest] = Math.max(dp[cur + 1][rest], next != -1 ? next + v[cur] : 0);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }

}
