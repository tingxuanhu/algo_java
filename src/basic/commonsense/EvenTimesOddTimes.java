package basic.commonsense;

// 异或：相同为零不同为一｜不进位相加
public class EvenTimesOddTimes {
    // 一个数出现奇数次 其他数出现偶数次  找出出现奇数次的数
    public static void printOddTimesNum1(int[] arr) {
        int xor = 0;
        for (int a : arr) {
            xor ^= a;
        }
        System.out.println(xor);
    }

    // 两个数出现奇数次（a、b）
    public static void printOddTimesNum2(int[] arr) {
        int xor = 0;
        for (int a : arr) {
            xor ^= a;
        }
        // 提取最右侧的1
        int rightOne = xor & (~xor + 1);

        // 用提取出来的1作为鉴别条件
        int a = 0;
        for (int j : arr) {
            if ((j & rightOne) != 0) {
                a ^= j;
            }
        }
        int b = xor ^= a;
        System.out.println(a + " " + b);
    }

    public static void main(String[] args) {
        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        printOddTimesNum1(arr1);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printOddTimesNum2(arr2);

    }

}
