package basic.fibonacci;

public class FibonacciProblem {

    public static int f1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return f1(n - 1) + f1(n - 2);
    }

    // 线性方法
    public static int f2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        // 指针法 代码好好理解 怎么转起来的
        int res = 1;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    // 矩阵乘法 O(lgN)
    public static int f3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int[][] base = {
                {1, 1},
                {1, 0}
        };
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        // 初始化成单位阵
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // 传入的矩阵自己自娱自乐 如果当位为1就乘进结果 若当位为0则不计入结果
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            // 温故而知新 回看异或章节 识别低位是0还是1的方法？
            if ((p & 1) != 0) {
                res = matrixProduct(res, tmp);
            }
            tmp = matrixProduct(tmp, tmp);
        }
        return res;
    }

    // 矩阵相乘
    public static int[][] matrixProduct(int[][] a, int[][] b) {
        // 明确a、b矩阵各自的行列数目
        int n = a.length;  // a行数
        int m = b[0].length;  // b列数
        int k = a[0].length;  // a列数
        int p = b.length;  // b行数
        // 判断是否可进行矩阵乘法
        if (k != p) {
            return null;  // 报个错？
        }
        int[][] ans = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 19;
        System.out.println(f1(n));
        System.out.println(f2(n));
        System.out.println(f3(n));
        System.out.println("===");
    }
}
