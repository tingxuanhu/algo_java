package datastucture.graph.unionset;

import java.util.ArrayList;
import java.util.List;

// 动态+1 动态初始化的技巧  动态空降1下来
public class NumberOfIslandsII {

    public static List<Integer> numIslands2(int m, int n, int[][] positions) {
        UnionFind uf = new UnionFind(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind {
        private int[] parent;
        // size[i]被合并之后 不清除掉 用来标记 (size[i] != 0表示被初始化过)
        private int[] size;
        private int[] help;
        private int sets;
        private int row;
        private int col;

        public UnionFind(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = m * n;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        // 动态连接
        public int connect(int r, int c) {
            int index = index(r, c);
            // 空降的1从未出现过 现场建集合
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }

        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r2 < 0 || c1 < 0 || c2 < 0 || r1 == row || r2 == row || c1 == col || c2 == col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            // 判断是否初始化过
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }
            int f1 = find(i1);
            int f2 = find(i2);
            if (f1 != f2) {
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

        private int index(int r, int c) {
            return r * col + c;
        }

    }



    // m,n 很大的时候 (17, 1009) --> '17_1009' 代表



}
