package leetcode;

/**
 * @author TingxuanHu
 * @version 2022/9/12 00:06
 */
public class StringToInteger {

}

class Solution {
    public int myAtoi(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }

        s = s.trim();  // first filter
        s = filter(s);
        // s = filter(s).trim();  // first filter

        if (s == null || s.equals("")) {
            return 0;
        }

        char[] str = s.toCharArray();


        if (!isValid(str)) {
            return 0;
        }

        // =================================================================
        //                         processing stage
        // =================================================================
        boolean nonNeg = str[0] != '-';
        int minq = Integer.MIN_VALUE / 10;
        int minr = Integer.MIN_VALUE % 10;

        int res = 0;
        int cur = 0;
        // convert to negative for processing
        for (int i = ((str[0] == '-') || (str[0] == '+')) ? 1 : 0; i < str.length; i++) {
            cur = '0' - str[i];
            // prevent from overflow
            if (res < minq || (res == minq && cur < minr)) {
                return nonNeg ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            res = res * 10 + cur;
        }

        // the final boundary case (negative range is greater than positive range by 1)
        if (nonNeg && res == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        return nonNeg ? Math.abs(res) : res;
    }

    // Before diving into the following field, remove all the leading zeros by calling trim()
    public static String filter(String str) {
        boolean signed = (str.startsWith("+") || str.startsWith("-"));
        int startIndex = signed ? 1 : 0;
        // record the first digital position by startIndex
        for (; startIndex < str.length(); startIndex++) {
            if (str.charAt(startIndex) != '0') {
                break;
            }
        }
        // record the first non-digital position by e
        int e = -1;
        for (int i = str.length() - 1; i >= startIndex; i--) {
            // for (int i = str.length() - 1; i >= (signed ? 1 : 0); i--) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                e = i;
            }
        }
        return (signed ? String.valueOf(str.charAt(0)) : "") + str.substring(startIndex, e == -1 ? str.length() : e);
    }

    public static boolean isValid(char[] chars) {
        if (chars[0] != '-' && chars[0] != '+' && (chars[0] < '0' || chars[0] > '9')) {
            return false;
        }
        if ((chars[0] == '-' || chars[0] == '+') && chars.length == 1) {
            return false;
        }
        // position of index 0 is valid
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] < '0' || chars[i] > '9') {
                return false;
            }
        }
        return true;
    }
}