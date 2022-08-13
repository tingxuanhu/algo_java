package basic.dp;

/*
假设有排成一行的N个位置记为1~N，N一定大于或等于2
开始时机器人在其中的M位置上(M一定是1~N中的一个)
如果机器人来到1位置，那么下一步只能往右来到2位置；
如果机器人来到N位置，那么下一步只能往左来到N-1位置；
如果机器人来到中间位置，那么下一步可以往左走或者往右走；
规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
给定四个参数 N、M、K、P，返回方法数
 */
public class RobotWalk {

    public static int ways1(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        return process1(start, aim, K, N);
    }

    public static int process1(int cur, int target, int remain, int N) {
        // base case
        if (remain == 0) {
            return cur == target ? 1 : 0;
        }

        if (cur == 1) {
            return process1(2, target, remain - 1, N);
        }
        if (cur == N) {
            return process1(N - 1, target, remain - 1, N);
        }
        int p1 = process1(cur - 1, target, remain - 1, N);
        int p2 = process1(cur + 1, target, remain - 1, N);
        return p1 + p2;
    }

    // top-down
    public static int topDown(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        int[][] dp = new int[N + 1][K + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(start, aim, K, N, dp);
    }

    public static int process2(int cur, int target, int remain, int N, int[][] dp) {
        if (dp[cur][remain] != -1) {
            return dp[cur][remain];
        }
        int ans = 0;
        if (remain == 0) {
            ans = cur == target ? 1 : 0;
        } else if (cur == 1) {
            ans = process2(2, target, remain - 1, N, dp);
        } else if (cur == N) {
            ans = process2(N - 1, target, remain - 1, N, dp);
        } else {
            ans = process2(cur - 1, target, remain - 1, N, dp)
                    + process2(cur + 1, target, remain - 1, N, dp);
        }
        dp[cur][remain] = ans;
        return ans;
    }

    // basic.dp
    public static int dp(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        // basic.dp --> [cur][remain]
        int[][] dp = new int[N + 1][K + 1];
        dp[aim][0] = 1;
        for (int rest = 1; rest <= K; rest++) {
            dp[1][rest] = dp[2][rest - 1];
            for (int cur = 2; cur < N; cur++) {
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
            dp[N][rest] = dp[N - 1][rest - 1];
        }
        return dp[start][K];
    }


    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(topDown(5, 2, 4, 6));
        System.out.println(dp(5, 2, 4, 6));
    }

}
