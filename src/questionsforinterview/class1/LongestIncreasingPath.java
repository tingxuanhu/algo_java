package questionsforinterview.class1;

// 给定一个二维数组matrix，你可以从任何位置出发，走向上、下、左、右四个方向，
// 返回能走出来的最长的递增链长度
public class LongestIncreasingPath {

    public static int longestIncreasingPath1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, process(matrix, i, j));
            }
        }
        return ans;
    }

    public static int process(int[][] m, int i, int j) {
        int up = i > 0 && m[i][j] < m[i - 1][j] ? process(m, i - 1, j) : 0;
        int down = i < (m.length - 1) && m[i][j] < m[i + 1][j] ? process(m, i + 1, j) : 0;
        int left = j > 0 && m[i][j] < m[i][j - 1] ? process(m, i, j - 1) : 0;
        int right = j < (m[0].length - 1) && m[i][j] < m[i][j + 1] ? process(m, i, j + 1) : 0;
        return Math.max(Math.max(up, down), Math.max(left, right)) + 1;
    }

    // dp  --> 没有出发状态  不用写成严格表依赖型   直接记忆化搜索优化就行
    public static int longestIncreasingPath2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int ans = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, processDP(matrix, i, j));
            }
        }
        return ans;
    }

    public static int processDP(int[][] m, int i, int j, int[][] dp) {
        int up = i > 0 && m[i][j] < m[i - 1][j] ? dp[i - 1][j] : 0;
        int down = i < (m.length - 1) && m[i][j] < m[i + 1][j] ? dp[i + 1][j] : 0;
        int left = j > 0 && m[i][j] < m[i][j - 1] ? dp[i][j - 1] : 0;
        int right = j < (m[0].length - 1) && m[i][j] < m[i][j + 1] ? dp[i][j + 1] : 0;
        int ans = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
        dp[i][j] = ans;
        return ans;
    }

}
