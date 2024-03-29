package basic.manacher;

/* basic.manacher 伪代码 找出最长回文子串(连续)    --注意 子串 子数组都是连续的  只有子序列可以断开（动规）
   public static int maxPLen(String str) {
       // "12321" --> "#1#2#3#2#1#"
       str -> strx

       int[] pArr = new int[strx.length];
       int R = -1;  // 回文区域右边界  更新到哪儿失败(第一个违规位置) 起始定为-1
       int C = -1;  // 回文区域中心点  C的更新没有自主性  是R扩到更右边的时候 也就是R修改的时候 彼时的C

       for (int i = 0; i < strx.length; i++) {
          if (i在R外） {
              暴力往外扩; // R变大   O(N)
          } else {
              // i在R内的情况(压着R也算i在R内）
              // 此时必然有拓扑关系 --> 刚来到i发现i在R内 证明R一定是之前更新的 --> C必然在i的左侧而不可能到右侧去
              // 也就是 C < i < R --> 整体模样 [L ...  C ... i ... R]
              // 此时可以用i找关于C的对称点i'   --> 整体模样 [L ... i' ...  C ... i ... R]
              if (i关于C对称点i'的回文区域均落在L..R范围内) {
                  pArr[i] = pArr[i'];   O(1)
              } else if (i关于C对称点i'的回文区域覆盖到L..R范围外) {
                  pArr[i] = i..R-1;   O(1)
              } else {  // i关于C对称点i'的回文区域左边界压在L上
                  从R位置开始向外扩; // R变大   O(N)
              }
          }
          return max(pArr) / 2;
      }
    */

public class Manacher {
    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        // 记录回文半径大小
        int[] pArr = new int[str.length];
        // 记录pArr最大值  --> 最大值减去1就会对应到究竟能有多长
        int max = Integer.MIN_VALUE;
        int C = -1;
        int R = -1;
        for (int i = 0; i < str.length; i++) {
            // 设置不用验证的区域              2 * C - i --> i'
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            // 两个O(1)的逻辑统一到while里 它们两种情况进去直接退出来 这样省代码
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            // 扩成功的时候 刷新右边界R 根据R去确定C
            if (i + pArr[i] > R) {
                C = i;
                R = i + pArr[i];
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    // "12321" --> "#1#2#3#2#1#"
    public static char[] manacherString(String s) {
        char[] charArr = s.toCharArray();
        char[] res = new char[s.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }


    // 生成随机String的方法
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int setSize = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String s = getRandomString(possibilities, setSize);

            char[] test = manacherString(s);
            if (manacher(s) != right(s)) {
                System.out.println("Oops!");
            }
        }
    }

}
