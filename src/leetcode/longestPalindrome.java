package leetcode;

/**
 * @author TingxuanHu
 * @version 2022/9/920:50
 */

public class longestPalindrome {

        // manacher本意是找到最长回文串的长度 会记录全过程对应的pArr数组以及最长回文串的中心位置C和最长回文串能扩到的右边界+1（R是第一个违规位置）
        // 本题利用manacher找出pArr最大值即是i取相对应位置的时候 所构成的最长回文子串
        // 返回pArr中最大值以及对应的i 即能够获得半径 和中心 然后能够返回对应的子串   因此核心仍然是实现manacher算法

        public String longestPalindrome(String s) {
            if (s == null || s.length() == 0) {
                return null;
            }
            char[] str = toManacherString(s);
            int C = -1;
            int R = -1;
            int[] pArr = new int[str.length];

            for (int i = 0; i < str.length; i++) {
                pArr[i] = i < R ? Math.min(R - i, pArr[2 * C - i]) : 1;
                // i在R外的时候 能够不验证的位置是i本身 大小正好是1 向外扩充的时候也是i位置加1位和减1位 合并到while判断里 节奏很妙
                while (str[i - pArr[i]] == str[i + pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }

                if (i + pArr[i] > R) {
                    R = i + pArr[i];
                    C = i;
                }
            }

            int max = pArr[0];   // 记录最长答案出现的位置
            int index = -1;   // 记录最长答案出现的位置的下标
            for (int i = 1; i < pArr.length; i++) {
                if (pArr[i] > max) {
                    max = pArr[i];
                    index = i;
                }
            }

            // 此时有了pArr[i]最大值max和对应下标index
            // 回文半径长度包括了i自身 所以从i往两头扩的时候要注意把i自己拿掉
            char[] maxPalindrom = new char[pArr.length / 2];
            int cur = 0;
            for (int i = index - max + 1; i <= index + max - 1; i += 2) {
                maxPalindrom[cur++] = str[i];
            }
            return maxPalindrom.toString();
        }




        public char[] toManacherString(String s) {
            char[] str = s.toCharArray();
            char[] ans = new int[str.length() * 2 + 1];
            int index = 0;  // 控制准备安放哪一个str
            for (int i = 0; i < ans.length; i++) {
                ans[i] = (i & 1) == 0 ? '#' : str[index++];
            }
            return ans;
        }


    }
}
