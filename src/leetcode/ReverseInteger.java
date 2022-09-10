package leetcode;

/**
 * @author TingxuanHu
 * @version 2022/9/109:51
 */

public class ReverseInteger {
    // https://leetcode.cn/problems/reverse-integer/
    public static int reverse(int x) {
        // >>> 右移过程中 左侧空位不再用符号位的值(负数是1)来填充，而是用0来填充。
        boolean isNeg = ((x >>> 31) & 1) == 1;
        x = isNeg ? x : -x;   // x不论正负一律按照负数处理

        // 考虑溢出情况
        int m = Integer.MIN_VALUE / 10;
        int o = Integer.MIN_VALUE % 10;

        int res = 0;
        while (x != 0) {
            if (res < m || (res == m && x % 10 < o)) {
                return 0;
            }
            res = res * 10 + x % 10;
            x /= 10;
        }
        return isNeg ? res : Math.abs(res);
    }

}
