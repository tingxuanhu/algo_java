package basic.hardcode;

// 小虎买苹果 要么6个一袋 要么8个一袋  装不满就不买  可能实现就返回最少要几个袋子 否则返回-1
public class AppleMinBags {

    // 输入苹果个数 --> 暴力求算情况 打印出来 发现规律  根据规律直接分类讨论写答案
    public static int minBags(int apple) {
        if (apple < 0) {
            return -1;
        }
        int bag8 = apple >> 3;
        int rest = apple - (bag8 << 3);
        int res = -1;
        while (bag8 != 0) {
            if (rest % 6 == 0) {
                res = rest / 6 + bag8;
            }
            bag8--;
            rest += (1 << 3);
        }
        return res;
    }

    // 打表出来看看结果如何
    public static void main(String[] args) {
        for (int apple = 0; apple < 200; apple++) {
            System.out.println(apple + " : " + minBags(apple));
        }
    }

    // 根据打表用肉眼看出来的规律找一找怎么写
    // 18以前特事特办 后面的遵循规律  不纠结数学原理 这是技巧
    public static int minBagAwesome(int apple) {
        if ((apple & 1) != 0) { // 如果是奇数，返回-1
            return -1;
        }
        if (apple < 18) {
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1
                    : (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
        }
        return (apple - 18) / 8 + 3;
    }

}
