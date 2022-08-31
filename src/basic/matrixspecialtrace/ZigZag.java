package basic.matrixspecialtrace;

public class ZigZag {

    public static void printZigZagMatrix(int[][] matrix) {
        // 规定两个指针 一个先向右移动直到碰壁再向下 另一个反过来
        // 这样能够构建起一组斜线的端点值 左下<-->右上 来回来去交替顺序打印
        int Ar = 0;
        int Ac = 0;
        int Br = 0;
        int Bc = 0;
        int Endr = matrix.length - 1;   // row终结者
        int Endc = matrix[0].length - 1;  // column终结者
        boolean TopDown = false;  // 自右上向左下打印的记号

        // 移动两个点去构建起斜线端点 终止循环条件不是Ar != Endr 而是Ar != Endr + 1
        while (Ar != Endr + 1) {
            // print action
            printLevel(matrix, Ar, Ac, Br, Bc, TopDown);

            // 这里的交代变化顺序不能反  为啥嘞？？？
            // Ac = Ac == Endc ? Ac : Ac + 1;
            // Ar = Ac == Endc ? Ar + 1 : Ar;
            // 如果写成注释这样的顺序，那么Ac第一次抵达Endc的时候 Ar应当是不变化的 而判断时需要变化 矛盾
            // 反过来 先变化的列（自左向右移动）后写，后变化的行先写 就能够规避掉这个边界条件造成的bug
            // A先向右走 右到不能再往右 就往下
            Ar = Ac == Endc ? Ac + 1 : Ac;
            Ac = Ac == Endc ? Ac : Ac + 1;

            // B先向下走 下到不能再往下 就往右
            Bc = Br == Endr ? Bc + 1 : Bc;
            Br = Br == Endr ? Br : Br + 1;

            // 修改打印方向
            TopDown = !TopDown;
        }
        System.out.println();
    }

    public static void printLevel(int[][] m, int Ar, int Ac, int Br, int Bc, boolean TopDown) {
        if (TopDown) {
            while (Ar != Br + 1) {
                System.out.print(m[Ar++][Ac--] + " ");
            }
        } else {
            while (Br != Ar - 1) {
                System.out.print(m[Br--][Bc++] + " ");
            }
        }
    }

}
