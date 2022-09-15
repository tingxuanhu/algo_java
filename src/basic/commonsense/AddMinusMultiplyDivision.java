package basic.commonsense;

/**
 * @author TingxuanHu
 * @version 2022/9/15 11:30
 */

// 不用算术运算符完成加减乘除   不讨论溢出情况
public class AddMinusMultiplyDivision {
    // =========================== add ============================
    public static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;   // 无进位信息保存在异或的结果中间
            b =  (a & b) << 1; // b记录了进位信息 因此当循环跳出时 是进位信息为0时
            a = sum;   // a记录新的sum信息 当作新的加数之一重复循环直到不再有进位信息
        }
        return sum;  // 这样也考虑到了b为0的情况
    }

    // =========================== minus =============================
    public static int minus(int a, int b) {
        return add(a, negNum(b));
    }

    public static int negNum(int a) {
        return add(~a, 1);
    }

    // ============================ multiply ===========================
    public static int multiply(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;  // 无符号右移动  用0补高位  如果>>则是用符号位补高位
        }
        return res;
    }

    // ============================ divide ===============================
    public static int divide(int a, int b) {

    }

    // a b均非系统最小值时的除法
    public static int div(int a, int b) {

    }






    public static void main(String[] args) {
        int a = -3;
        System.out.println(a >> 1);
        System.out.println(a >>> 1);
        System.out.println(Integer.MAX_VALUE);
    }

}
