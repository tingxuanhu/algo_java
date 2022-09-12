package leetcode;

/**
 * @author TingxuanHu
 * @version 2022/9/12 18:44
 */

public class RegularMatching {

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();
        if (!isValid(str, pattern)) {
            return false;
        }
        return process(str, pattern, 0, 0);
    }

    // compare str[si..] and pattern[pi..]
    // pre-request:  pattern[pi] != '*'   --> cannot compare from an independent char with *
    public static boolean process(char[] str, char[] pattern, int si, int pi) {
        // base case 1   when str come to the end  str[si..] -> ""
        if (si == str.length) {
            if (pi == pattern.length) {  // pattern[pi..] -> ""
                return true;
            }
            if (pi + 1 < pattern.length && pattern[pi + 1] == '*') {
                return process(str, pattern, si, pi + 2);
            }
            return false;
        }
        // base case 2   when pattern come to the end   pattern[pi..] -> ""
        if (pi == pattern.length) {
            return si == str.length;
        }

        // si < str.length && pi < pattern.length  (common positions)
        // sub-case1  pattern[pi + 1] != '*'    ==> si should match pi
        if (pi + 1 >= pattern.length || pattern[pi + 1] != '*') {
            return ((str[si] == pattern[pi]) || (pattern[pi] == '.')) && process(str, pattern, si + 1, pi + 1);

        }

        // sub-case2-1 pattern[pi + 1] == '*'  whereas si dose not match pi
        if (str[si] != pattern[pi] && pattern[pi] != '.') {
            return process(str, pattern, si, pi + 2);
        }

        // sub-case2-2 pattern[pi + 1] == '*' and si matches pi
        // e.g. str = [aaa]   pattern=[.*]  --> 3a   str = [aab]   --> 2a   try all the possible cases
        if (process(str, pattern, si, pi + 2)) {
            return true;
        }

        while (si < str.length && (str[si] == pattern[pi] || pattern[pi] == '.')) {
            if (process(str, pattern, si + 1, pi + 2)) {
                return true;
            }
            si++;
        }

        return false;
    }

    // " " can be handled by the following method(skip all for loops), however, null cannot be processed successfully
    public static boolean isValid(char[] str, char[] pattern) {
        // str cannot include '.' or '*'
        for (char cha : str) {
            if (cha == '.' || cha == '*') {
                return false;
            }
        }

        // '*' cannot be the first element or followed by itself
        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == '*' && (i == 0 || pattern[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }

}
