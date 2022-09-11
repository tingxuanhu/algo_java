package leetcode;

/**
 * @author TingxuanHu
 * @version 2022/9/12 00:06
 */
//public class StringToInteger {

    class Solution {
        public int myAtoi(String s) {
            if (s == null || s.equals("")) {
                return 0;
            }

            s = filter(s.trim());  // trim ~= strip

            if (s == null || s.equals("")) {
                return 0;
            }
            char[] str = s.toCharArray();
            if (!isValid(str)) {
                return 0;
            }
            // verify whether the num lives in the range of Integer
            boolean nonNeg = str[0] != '-';
            int minq = Integer.MIN_VALUE / 10;
            int minr = Integer.MIN_VALUE % 10;

            int res = 0;
            int cur = 0;
            for (int i = (str[0] == '-') || (str[0] == '+') ? 1 : 0; i < str.length; i++) {
                // convert to negative num for process
                cur = '0' - str[i];
                // prevent from overflow
                if (res < minq || (res == minq && cur < minr)) {
                    return nonNeg ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
                res = res * 10 + cur;
            }
            // the boundary case (neg range greater than positive range by 1)
            if (nonNeg && res == Integer.MIN_VALUE) {
                return Integer.MAX_VALUE;
            }
            return nonNeg ? Math.abs(res) : res;
        }

        public static String filter(String str) {
            boolean signed = (str.startsWith("+") || str.startsWith("-"));
            // determine the start index (depeding on starts with "+/-" or not)
            int startIndex = signed ? 1 : 0;
            // eliminate the leading zeros
            for (; startIndex < str.length(); startIndex++) {
                if (str.charAt(startIndex) != '0') {
                    break;
                }
            }
            // when startIndex comes to the first non-zero position
            // what we need is to find the left non-digit character position(called e)
            int e = -1;

            // for (int i = str.length() - 1; i >= (signed ? 1 : 0); i--)
            for (int i = str.length() - 1; i >= startIndex; i--) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    e = i;
                }
            }
            return (signed ? String.valueOf(str.charAt(0)) : "") + str.substring(startIndex, e == -1 ? str.length() : e);
        }

        public static boolean isValid(char[] chars) {
            // error if it doesn't start with '-' or '+' or digital num
            if (chars[0] != '-' && chars[0] != '+' && (chars[0] < '0' || chars[0] > '9')) {
                return false;
            }
            if ((chars[0] == '-' || chars[0] == '+') && chars.length == 1) {
                return false;
            }
            for (int i = 1; i < chars.length; i++) {
                if (chars[i] < '0' || chars[i] < '9') {
                    return false;
                }
            }
            return true;
        }


    }
//}
