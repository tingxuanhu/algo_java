package basic.dp.taskrestriction;

// 当前来到的位置是（x,y）
// 还剩下rest步需要跳
// 跳完rest步，正好跳到a，b的方法数是多少？
public class HorseJump {

    public static int jump(int a, int b, int k) {
        return process(0, 0, k, a, b);
    }

    public static int process(int x, int y, int rest, int a, int b) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        if (rest == 0) {
            return (x == a && y == b) ? 1 : 0;
        }
        int ways = process(x + 2, y + 1, rest - 1, a, b);
        ways += process(x + 2, y - 1, rest - 1, a, b);
        ways += process(x - 2, y + 1, rest - 1, a, b);
        ways += process(x - 2, y - 1, rest - 1, a, b);
        ways += process(x - 1, y - 2, rest - 1, a, b);
        ways += process(x - 1, y + 2, rest - 1, a, b);
        ways += process(x + 1, y + 2, rest - 1, a, b);
        ways += process(x + 1, y - 2, rest - 1, a, b);
        return ways;
    }

    // dp
    public static int jumpDP(int a, int b, int k) {
        int[][][] dp = new int[10][9][k + 1];
        dp[a][b][0] = 1;
        for (int rest = 1; rest < k; rest++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 8; j++) {
                    dp[i][j][rest] = outOfBoundJudge(dp, i - 1, j - 2, rest - 1)
                            + outOfBoundJudge(dp, i - 1, j + 2, rest - 1)
                            + outOfBoundJudge(dp, i + 1, j - 2, rest - 1)
                            + outOfBoundJudge(dp, i + 1, j + 2, rest - 1)
                            + outOfBoundJudge(dp, i - 2, j + 1, rest - 1)
                            + outOfBoundJudge(dp, i - 2, j - 1, rest - 1)
                            + outOfBoundJudge(dp, i + 2, j - 1, rest - 1)
                            + outOfBoundJudge(dp, i + 2, j + 1, rest - 1);
                }
            }
        }
        return dp[0][0][k];
    }

    public static int outOfBoundJudge(int[][][] dp, int i, int j, int rest) {
        if (i < 0 || i > 9 || j < 0 || j > 8) {
            return 0;
        }
        return dp[i][j][rest];
    }




}
