package basic.kmp;

import java.util.ArrayList;

// 测试链接 : https://leetcode.cn/problems/subtree-of-another-tree/
// 如果把tree1和tree2都用先序方式序列化，那么只要tree2序列化后的是tree1的子串 就证明两者存在结构一样的部分 --> KMP上场
public class TreeEqual {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    public static boolean isSubTree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        // 序列化
        ArrayList<String> b = preSerial(root);
        ArrayList<String> s = preSerial(subRoot);
        // KMP 子串匹配
        String[] str = new String[b.size()];
        String[] match = new String[s.size()];
        for (int i = 0; i < str.length; i++) {
            str[i] = b.get(i);
        }
        for (int i = 0; i < match.length; i++) {
            match[i] = s.get(i);
        }
        return getIndexOf(str, match) != -1;
    }

    // 先序方式序列化（无二义）
    public static ArrayList<String> preSerial(TreeNode head) {
        ArrayList<String> ans = new ArrayList<>();
        pres(head, ans);
        return ans;
    }

    public static void pres(TreeNode cur, ArrayList<String> ans) {
        if (cur == null) {
            ans.add(null);
        } else {
            // add的是cur.val而非cur
            ans.add(String.valueOf(cur.val));
            pres(cur.left, ans);
            pres(cur.right, ans);

        }

    }

    // KMP部分
    public static int getIndexOf(String[] str, String[] match) {
        if (str == null || match == null || str.length < 1 || str.length < match.length) {
            return -1;
        }
        int curs = 0;
        int curm = 0;
        int[] next = getNextArray(match);
        while (curs < str.length && curm < match.length) {
            if (isEqual(str[curs], match[curm])) {
                curs++;
                curm++;
            } else if (curm == 0) {
                curs++;
            } else {
                curm = next[curm];
            }
        }
        return curm == match.length ? curs - curm : -1;
    }

    public static int[] getNextArray(String[] match) {
        if (match.length == 1) {
            return new int[] {-1};
        }
        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (isEqual(match[i - 1], match[cn])) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static boolean isEqual(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else {
                return a.equals(b);
            }
        }
    }

}
