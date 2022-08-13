package basic.datastucture.graph.unionset;

// 测试链接：https://leetcode.com/problems/friend-circles/
public class FriendCircles {

    public static int findCircleNum(int[][] isConnected) {
        int N = isConnected.length;
        UnionSet unionSet = new UnionSet(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (isConnected[i][j] == 1) {
                    unionSet.union(i, j);
                }
            }
        }
        return unionSet.getSetNum();
    }

    public static class UnionSet {
        // parent[i] = k --> i的父亲是k
        public int[] parent;
        // i是代表节点 则size[i]有意义 -- i所在的集合的size大小
        public int[] size;
        // 辅助数组 代替栈结构
        public int[] help;
        public int setNum;

        public UnionSet(int N) {
            parent = new int[N];
            size = new int[N];
            help = new int[N];
            setNum = N;
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int i) {
            int hi = 0;
            // 一路上溯直到发掘出代表节点
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            // 压缩路径
            // 注意这里for循环的写法  好好琢磨 假定hi=5 那么循环会走5次 hi值分别为4,3,2,1,0
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        public void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                setNum--;
            }
        }

        public int getSetNum() {
            return setNum;
        }

    }

}
