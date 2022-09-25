package basic.hardcode;

/**
 * @author TingxuanHu
 * @version 2022/9/25 22:43
 */

public class RotateDigits {
    public static void main(String[] args) {

    }

}


class Solution {
    public static int rotatedDigits(int n) {
        if (n == 0) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i <= n; i++) {
            // 判断i是否可以旋转
            // 如果不是0、1、2、5、6、8、9这些数字 也就是数字里带了3或4或7根本就不能旋转  不是好数
            if (i % 10 == 3 || i % 10 == 4 || i % 10 == 7 || i / 10 == 3 || i / 10 == 4 || i / 10 == 7) {
                continue;
            }
            // 剩下的是可以成功旋转的数
            while (i != 0) {
                // 1)如果是%10==0或者1或者8的话
                // 如果它/10=0 也就是就剩一位了 意味着什么？ 肯定没戏  转完是它自己
                // 如果/10!=0  那么意味着可以/10 看看剩下的位数的情况来决定是或是不是
                // 2) 如果%10==2 || 5 || 6 || 9的话 那么是可以转成功的    ans+1
                // 因为最开头已经排除了不能旋转的情况 i必然每个数都是可旋转的
                // 那么当末位为旋转后为镜像数字的情况时 不管前面转完是保持不动还是也可以镜像 这个数字都必然可以是好数
                if (i % 10 == 2 || i % 10 == 5 ||i % 10 == 6 || i % 10 == 9) {
                    ans++;
                    break;
                } else if (i / 10 != 0){   // 前面if不满足 这里必然%10是0或1或8  按第一大类去判断
                    i /= 10;
                } else {    // 只剩一位数字并且转完是自己
                    break;
                }
            }
        }
        return ans;
    }




}