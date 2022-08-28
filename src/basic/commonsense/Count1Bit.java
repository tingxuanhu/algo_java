package basic.commonsense;

// 记录二进制位有多少个1
// 暴力方法需要32位挨个尝试  异或可以加速这个过程
public class Count1Bit {

    public static int bit1Count(int N) {
        int count = 0;

        while (N != 0) {
            int rightOne = N & (~N + 1);
            count++;
            N ^= rightOne;   // 异或上提取出来的最右边的1 就可以把这个1抹掉
        }

        return count;
    }


}
