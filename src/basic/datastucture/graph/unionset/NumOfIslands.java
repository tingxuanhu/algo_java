package basic.datastucture.graph.unionset;

// https://leetcode.com/problems/number-of-islands/
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
    // 原二元char数组中的r行c列的元素可以由 r * 列数 + c 获得其在新的数组中间的位置
    public static int numIslands(char[][] board) {
        if (board == null || board.length < 1) {
            return 0;
        }
        UnionSet unionSet = new UnionSet(board);
        int row = board.length;
        int col = board[0].length;
        // 第一行从左到右 (0,0)号元素没有上没有左 遂跳过
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                unionSet.union(i - 1, 0, i, 0);
            }
        }
        // 第一列从上到下 (0,0)号元素没有上没有左 遂跳过
        for (int j = 1; j < col; j++) {
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                unionSet.union(0, j - 1, 0, j);
            }
        }
        // 其他情况统一部署 前边的两个for循环可以省去边界的判别条件
        for (int r = 1; r < row; r++) {
            for (int c = 1; c < col; c++) {
                if (board[r][c] == '1' && board[r - 1][c] == '1') {
                    unionSet.union(r, c, r - 1, c);
                }
                if (board[r][c] == '1' && board[r][c - 1] == '1') {
                    unionSet.union(r, c, r, c - 1);
                }
            }
        }
        return unionSet.sets;
    }

    public static class UnionSet {
        private int[] parent;
        private int[] size;
        private int[] help;
        private int col;
        private int sets;

        public UnionSet(char[][] board) {
            col = board[0].length;
            sets = 0;
            int row = board.length;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (board[r][c] == '1') {
                        int idx = r * col + c;
                        parent[idx] = idx;
                        size[idx] = 1;
                        sets++;
                    }
                }
            }
        }

        private void union(int r1, int c1, int r2, int c2) {
            int i1 = r1 * col + c1;
            int i2 = r2 * col + c2;
            int f1 = find(i1);
            int f2 = find(i2);
            if (f1 != f2) {
                // 小的挂载在大的上
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        public int sets() {
            return sets;
        }
    }

    // for test
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                board[r][c] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    public static char[][] copyCharMatrix(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                ans[r][c] = board[r][c];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int row = 100;
        int col = 100;
        int testTimes = 1000;
        for (int t = 0; t < testTimes; t++) {
            char[][] board1 = generateRandomMatrix(row, col);
            char[][] board2 = copyCharMatrix(board1);
            if (numIslands(board1) != numIslandsRecursion(board2)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Finished!");
    }

}
