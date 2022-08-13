package basic.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class LowestLexicography {

    public static String lowestStringGreedy(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs, new StringComparator());
        StringBuilder res = new StringBuilder(); // 非线程安全的 比StringBuffer快 所以用
        for (String s : strs) {
            res.append(s);
        }
        return res.toString();
    }

    public static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }

    // 暴力方法 对比用
    public static String lowestStringRecursive(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        TreeSet<String> ans = process(strs);
        return ans.size() != 0 ? ans.first() : "";
    }

    // strs中所有字符串全排列，返回所有可能的结果
    public static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        // 这里的边界条件并不能省略 原因在于尽管主函数里有边界,但主函数的边界只能管到主函数strs整体边界,而递归过程需要对所有入递归者进行限制
        if (strs.length == 0) {
            ans.add("");
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] nexts = removeIndexString(strs, i);
            TreeSet<String> next = process(nexts);
            for (String cur : next) {
                ans.add(first + cur);
            }
        }
        return ans;
    }

    // {"abc", "cks", "bct"}
    // 0 1 2
    // removeIndexString(arr , 1) -> {"abc", "bct"}
    public static String[] removeIndexString(String[] arr, int index) {
        int N = arr.length;
        String[] res = new String[N - 1];
        int ansIndex = 0;
        for (int i = 0; i < N; i++) {
            if (i != index) {
                res[ansIndex++] = arr[i];
            }
        }
        return res;
    }

    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen + 1)];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            // ASCII  (A-->65   a--> 97)
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen + 1)];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static String[] copyStringArray(String[] arr) {
        String[] copy = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = String.valueOf(arr[i]);
        }
        return copy;
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestStringRecursive(arr1).equals(lowestStringGreedy(arr2))) {
                System.out.println("Oops!");

                System.out.println("Str1: ");
                for (String str : arr1) {
                    System.out.print(str + ",");
                }

                System.out.println();

                System.out.println("Str2: ");
                for (String str : arr2) {
                    System.out.print(str + ",");
                }
                break;
            }
        }
        System.out.println("Finished!");
    }

}
