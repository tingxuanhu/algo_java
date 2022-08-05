package datastucture.graph.unionset;

public class NumOfIslands {

    // 感染方法
    public static int numIslandsRecursion(char[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    count++;
                    process(board, i, j);
                }
            }
        }
        return count;
    }

    public static void process(char[][] board, int i, int j) {
        // 下边这个判断条件不能省略: 原因分析：
        // 从递归主函数调用process来看,调用传入的参数i和j并不可能越界；然而从递归过程看，向下继续调用上下左右就需要边界判断了
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1') {
            return;
        }
        board[i][j] = 2;  // 为了让递归能跑完 (去调用上下左右的时候 上下左右会继续调用上下左右  这里先改了 到时候可以走上边if退出)
        process(board, i + 1, j);
        process(board, i, j + 1);
        process(board, i - 1, j);
        process(board, i, j - 1);
    }

    // 用数组实现并查集的方法
    public static int numIslands(char[][] board) {

    }

    public static class UnionSet {
        private int[] parent;
        private int[] size;
        private int[] help;
        private int col;
        private int sets;

        public UnionSet(char[][] board) {
            int N = board.length;
            int M = board[0].length;
            int len = N * M;
            sets = 0;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    if (board[row][col] == '1') {

                    }
                }
            }

        }

    }


}
