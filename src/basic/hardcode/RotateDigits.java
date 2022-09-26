package basic.hardcode;

/**
 * @author TingxuanHu
 * @version 2022/9/25 22:43
 */

public class RotateDigits {
    public static void main(String[] args) {

        for (int i = 0; i < 101; i++) {
            System.out.println("The amount of good num belongs to " + i + " is: " + Solution.rotatedDigits(i));
        }

//        System.out.println("The amount of good num belongs to 568 " + " is: " + Solution.rotatedDigits(568));

    }

}


class Solution {
    public static int rotatedDigits(int n) {
        int ans = 0;
        for (int i = 0; i <= n; i++) {
            int rest = i;
            int cnt = 0;
            while (rest != 0) {
                int tmp = rest % 10;
                if (tmp == 2 || tmp == 5 || tmp == 6 || tmp == 9) {
                    cnt = 1;
                } else if (tmp == 3 || tmp == 4 || tmp == 7) {
                    cnt = 0;
                    break;
                }
                rest /= 10;
            }
            ans += cnt;
        }
        return ans;
    }




}