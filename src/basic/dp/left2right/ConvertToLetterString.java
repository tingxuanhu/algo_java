package basic.dp.left2right;

/* 数字字串转英文字串
规定1和A对应、2和B对应、3和C对应...26和Z对应
那么一个数字字符串比如"111”就可以转化为:
"AAA"、"KA"和"AK"
给定一个只有数字字符0~9组成的字符串str，返回转化结果的种类数目
 */
public class ConvertToLetterString {

    public static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    // index(i)之前已经弄完  从i开始向右看能用多少转化方法
    public static int process(char[] str, int i) {
        int N = str.length;
        if (i == N) {
            return 1;
        }
        // '105' 让'1'转化后 i独立面对'0' 无法形成有效转化
        if (str[i] == '0') {
            return 0; // 此前决定是不成立的 单独面对'0' 是转化不出来的
        }
        // 那么有几种转化情况呢？
        // 第一 单独转化
        int ways = process(str, i + 1);
        // 第二 当前i位置和i+1位置(若i+1不越界并且它们一起面对构成的数字大小小于等于26)
        if (i + 1 < N && str[i] - '0' * 10 + str[i + 1] < 27) {
            ways += process(str, i + 2);
        }
        return ways;
    }

    // dp  right to left   dp[i]表示：str[i...]有多少种转化方式
    public static int numberDP(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] string = str.toCharArray();
        int N = string.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            if (string[i] != '0') {
//                if (i + 1 < N && string[i] - '0' * 10 + string[i + 1] < 27) {
//                    dp[i] = dp[i + 2] + dp[i + 1];
//                } else {
//                    dp[i] = dp[i + 1];
//                }
                dp[i] = dp[i + 1] + (i + 1 < N && string[i] - '0' * 10 + string[i + 1] < 27 ? dp[i + 2] : 0);
            }
        }
        return dp[0];
    }

    // dp  left to right   dp[i]表示：str[0..i]有多少种转化方式
    // 这个复杂些 重新看？


    // 为了测试
    public static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 30;
        int testTime = 1000000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            String s = randomString(len);
            int ans0 = number(s);
            int ans1 = numberDP(s);
//            int ans2 = dpLeftToRight(s);
            if (ans0 != ans1) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans1);
//                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
    }

}
