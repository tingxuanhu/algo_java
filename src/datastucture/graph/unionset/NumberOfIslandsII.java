package datastucture.graph.unionset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 动态+1 动态初始化的技巧  动态空降1下来
// class15
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
        private final int[] parent;
        // size[i]被合并之后 不清除掉 用来标记 (size[i] != 0表示被初始化过)
        private final int[] size;
        private final int[] help;
        private int sets;
        private final int row;
        private final int col;

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
    // k个数空降成1 复杂度是O(k) 当k很小的时候事实上可以不用那么多的空间 动态动用就可以 写法如下所示
    public static List<Integer> numIslands2dynamic(int m, int n, int[][] positions) {
        UnionFindDynamic uf = new UnionFindDynamic();
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFindDynamic {
        private final HashMap<String, String> parent;
        private final HashMap<String, Integer> size;
        private final ArrayList<String> help;
        private int sets;

        public UnionFindDynamic() {
            parent = new HashMap<>();
            size = new HashMap<>();
            help = new ArrayList<>();
            sets = 0;
        }

        public int connect(int r, int c) {
            String key = String.valueOf(r) + "_" + String.valueOf(c);
            if (!parent.containsKey(key)) {
                parent.put(key, key);
                size.put(key, 1);
                sets++;
                String up = String.valueOf(r - 1) + "_" + String.valueOf(c);
                String down = String.valueOf(r + 1) + "_" + String.valueOf(c);
                String left = String.valueOf(r) + "_" + String.valueOf(c - 1);
                String right = String.valueOf(r) + "_" + String.valueOf(c + 1);
                union(up, key);
                union(down, key);
                union(left, key);
                union(right, key);
            }
            return sets;
        }

        private void union(String s1, String s2) {
            if (parent.containsKey(s1) && parent.containsKey(s2)) {
                String f1 = find(s1);
                String f2 = find(s2);
                if (!f1.equals(f2)) {
                    if (size.get(f1) >= size.get(f2)) {
                        parent.put(f2, f1);
                        size.put(f1, size.get(f1) + size.get(f2));
                    } else {
                        parent.put(f1, f2);
                        size.put(f2, size.get(f1) + size.get(f2));
                    }
                    sets--;
                }
            }
        }

        private String find(String cur) {
            while (!cur.equals(parent.get(cur))) {
                help.add(cur);  // --> key
                cur = parent.get(cur);
            }
            for (String key : help) {
                parent.put(key, cur);
            }
            help.clear();
            return cur;
        }
    }


}
