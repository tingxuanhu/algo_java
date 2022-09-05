package questionsforinterview.class1;

import java.util.HashMap;

// 给定一个数组arr，你可以在每个数字之前决定+或者-但是必须所有数字都参与，再给定一个数target
// 请问最后算出target的方法数
public class TargetSum {
    // 暴力递归
    public static int findTargetSumWays1(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process1(arr, 0, target);
    }

    // arr[i...] --> 利用arr[i]及其往后的数字能够搞出rest目标来
    public static int process1(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        return process1(arr, index + 1, rest - arr[index]) + process1(arr, index + 1, rest + arr[index]);
    }

    // 记忆化搜索
    public static int findTargetSumWays2(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process2(arr, 0, target, new HashMap<>());
    }

    public static int process2(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
        if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
            return dp.get(index).get(rest);
        }
        int ans = 0;
        if (index == arr.length) {
            ans = rest == 0 ? 1 : 0;
        } else {
            ans = process2(arr, index + 1, rest - arr[index], dp) + process2(arr, index + 1, rest + arr[index], dp);
        }
        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        dp.get(index).put(rest, ans);
        return ans;
    }

    public static int findTargetNumsWays3(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i - 1] > 0) {
                    dp[i][j] += dp[i - 1][j - arr[i - 1]];
                }
            }
        }
        return dp[n][target];

    }


    // 严格位置依赖
    // 如果做以下优化可以减少常数时间 --> 业务本身优化  面试用
    // https://github.com/algorithmzuo/coding-for-great-offer/blob/main/src/class01/Code07_TargetSum.java
    // 优化点一：数组处理成非负数也是可以的
    // 优化点二：所有数处理成非负数之后 加起来 如果和小于target  必然没有答案
    // 优化点三：所有数处理成非负数之后 加起来 如果奇偶性和target不一样 必然没答案
    /*  优化点四：转化为背包问题
    // 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
    // 其中一个方案是 : +1 -2 +3 -4 +5 = 3
    // 该方案中取了正的集合为P = {1，3，5}
    // 该方案中取了负的集合为N = {2，4}
    // 所以任何一种方案，都一定有 sum(P) - sum(N) = target
    // 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
    // sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
    // 2 * sum(P) = target + 数组所有数的累加和
    // sum(P) = (target + 数组所有数的累加和) / 2
    // 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
    // 那么就一定对应一种target的方式
    // 也就是说，比如非负数组arr，target = 7, 而所有数累加和是11
    // 求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法
     */


}
