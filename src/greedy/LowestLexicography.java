package greedy;

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


    public static String lowesrStringRecursive(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }


    }

    public static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        if (strs.length == 0) {
            ans.add("");
            return ans;
        }






    }

    public static String[] removeIndexString(String[] arr, int index) {
        String[] res = new String[arr.length - 1];
        int idx = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != idx) {
                res[idx++] = arr[i];
            }
        }
        return res;
    }



}
