package basic.manacher;

// 在后边添加最少的字符使得原来字符串回文
public class AddShortestEnd {

    public static String shortestEnd(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] str = manacherString(s);
        int[] pArr = new int[str.length];
        int C = -1;
        int R = -1;
        int maxContainsEnd = -1; // 找从哪个位置开始能把结尾包住成回文
        for (int i = 0; i < str.length; i++) {
            pArr[i] = i < R ? Math.min(pArr[2 * C - i], R - i): 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                C = i;
                R = i + pArr[i];
            }
            if (R == str.length) {
                maxContainsEnd = pArr[i];
                break;
            }
        }

        // 扣边界 --> 用实际的具体例子 写两个看看  不用抽象的东西描述
        char[] res = new char[s.length() - maxContainsEnd + 1];
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = str[i * 2 + 1];
        }
        return String.valueOf(res);
    }

    public static char[] manacherString(String str) {
        char[] charArray = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArray[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }

}
