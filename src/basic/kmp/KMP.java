package basic.kmp;

public class KMP {
    // 用string（s）和match（m）做匹配
    public static int getIndexOf(String s, String m) {
        if (s == null || m == null || m.length() < 1 || m.length() > s.length()) {
            return -1;
        }
        char[] str = s.toCharArray();
        char[] match = m.toCharArray();
        // x --> str当前对比到的位置  y --> match当前对比到的位置
        int x = 0;
        int y = 0;
        int[] next = getNextArray(match);
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) {
                x++;
                y++;
            } else if (y == 0) {
                x++;
            } else {
                y = next[y];
            }
        }
        // y越界证明匹配成功
        return y == match.length ? x - y: -1;
    }

    public static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[] {-1};
        }
        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;  // i-1位置的信息,也是跳回去到和i-1位置比较的字符
        while (i < next.length) {
            if (match[i - 1] == match[cn]) {
//                next[i] = cn+1;
//                i++;
//                cn++; // i+1位置用i位置信息  此时i位置更新成cn+1了
                next[i++] = ++cn;  // 上面三句合并为一句
            } else if (cn > 0) {  // 跳失败了  继续向前找
                cn = next[cn];
            } else {  // cn == 0  不能再跳了
                next[i++] = 0;
            }
        }
        return next;
    }

}
