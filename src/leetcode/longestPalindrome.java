package leetcode;

/**
 * @author TingxuanHu
 * @version 2022/9/920:50
 */

public class longestPalindrome {

        // manacher本意是找到最长回文串的长度 会记录全过程对应的pArr数组以及最长回文串的中心位置C和最长回文串能扩到的右边界+1（R是第一个违规位置）
        // 本题利用manacher找出pArr最大值即是i取相对应位置的时候 所构成的最长回文子串
        // 返回pArr中最大值以及对应的i 即能够获得半径 和中心 然后能够返回对应的子串   因此核心仍然是实现manacher算法

    public static String findLongestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] str = toManacherString(s);
        int C = -1;
        int R = -1;
        int[] pArr = new int[str.length];
        int max = Integer.MIN_VALUE;  // 为了记录pArr数组中间的最小值

        for (int i = 0; i < str.length; i++) {
            pArr[i] = i < R ? Math.min(R - i, pArr[2 * C - i]) : 1;
            // i在R外的时候 能够不验证的位置是i本身 大小正好是1 向外扩充的时候也是i位置加1位和减1位 合并到while判断里 节奏很妙
            // while判断有没有越界 然后if判断能否继续往外扩
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i - pArr[i]] == str[i + pArr[i]]) {
                        pArr[i]++;
                    } else {
                        break;
                    }       
            } 
            // if判断 如果扩成功了 那么就更新右边界R 以及当时对应的中心C
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);   // max反映的是最长回文子串的长度 + 1
        }

        // 遍历一遍pArr[i]数组 找一下哪个或哪些人的值和max相同 它们就是我们要找的最长回文字串的长度  
        // 先假定只有一个最长串？
        
        char[] ans = new char[max - 1];
        int cnt = 0;
        for (int i = 0; i < pArr.length; i++) {
            if (pArr[i] == max) {  // 确定i位置是
                for (int j = i - max + 2; j <= i + max - 2; j += 2) {
                    ans[cnt++] = str[j];
                }
                return String.valueOf(ans);
            }
        }
        return null;
    }

    public static char[] toManacherString(String s) {
        char[] str = s.toCharArray();
        char[] ans = new char[str.length * 2 + 1];
        int index = 0;  // 控制准备安放哪一个str
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (i & 1) == 0 ? '#' : str[index++];
        }
        return ans;
    }

}
