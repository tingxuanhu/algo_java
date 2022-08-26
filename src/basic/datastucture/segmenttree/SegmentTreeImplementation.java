package basic.datastucture.segmenttree;

public class SegmentTreeImplementation {

    public static class SegmentTree {
        private final int MAXN;
        private int[] arr; //
        private int[] sum; //
        private int[] lazy;
        private int[] change; // update如果为true的话用来更新数组中局部范围上所有的内容记录
        private boolean[] update; // 更新则在对应位置标记为true

        // 把原始数组丢进来初始化线段树
        public SegmentTree(int[] origin) {
            // 0号位置弃而不用 因此采用1-N+1来代替原来的0-N规模数组 用MAXN记录一下数组长度+1这个大小 方便下标从1开始 原因见README.md
            MAXN = origin.length + 1;
            arr = new int[MAXN];
            // 0号位置弃而不用(为了位运算，具体原因见README.md)
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            // 相关数组配置最长不超过4N的范围即可实现所有信息的保存工作
            sum = new int[MAXN << 2];
            lazy = new int[MAXN << 2];
            change = new int[MAXN << 2];
            update = new boolean[MAXN << 2];
        }

        //------------------------------ 初始化数组，配置好sum数组的初始化信息 -------------------------------------
        // rt表示在sum中配置到哪个人
        // 大范围是arr[1..r]上去填，给出l和r的范围递归实现
        public void build(int l, int r, int rt) {
            // 递归出口
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            // 若是无法在当前直接拿值 就递归给子过程收集答案
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);  // 到左孩子节点去build
            build(mid + 1, r,rt << 1 | 1); // 到右孩子节点去build
            pushUp(rt);     // 左右孩子分别收集到sum信息后向父亲节点反馈
        }

        // 左右孩子sum信息向上反馈
        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        // ---------------- 实现update方法、add方法和query方法 ------------------------
        // L, R --> 任务给定范围     l,r --> 当前位置存储的信息范围    rt 该轮到谁了   C 目标变动的具体值
        public void update(int L, int R, int C, int l, int r, int rt) {
            // 若是任务完全能够覆盖掉当前范围 那么update方法就直接在当前范围去做事
            if (L <= l && R >= r) {
                update[rt] = true;  // 意义何在?
                change[rt] = C;
                sum[rt] = C * (r - l + 1);
                lazy[rt] = 0; // update可以清零之前积攒的lazy 反之不能够 因此在pushDown方法下放任务时才先看update(时间序早)后看lazy
                return;
            }
            // 此时任务是覆盖不了的 必须下放
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);  // 实际画个树 形象化地举例来验证区间范围
            // 判断是否左右都需要接收派发的任务还是只用给单侧
            if (L <= mid) {  // 左孩子中有任务需要的范围
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {  // 右孩子中有任务需要的范围
                update(L, R, C, mid + 1, r,rt << 1 | 1);
            }
            pushUp(rt); // sum向上传导
        }

        public void add(int L, int R, int C, int l, int r, int rt) {
            // 若是任务完全能够覆盖掉当前范围 那么add方法就直接在当前范围去做事
            if (L <= l && R >= r) {
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }
            // 任务覆盖不了当前范围 下放
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            // 左孩子中有任务需要的范围
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            // 右孩子中有任务需要的范围
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt); // sum向上传导
        }

        // query方法查询L..R范围区间总和  防止溢出 设置返回值为long型
        public long query(int L, int R, int l, int r, int rt) {
            // 若是任务完全能够覆盖掉当前范围 那么就直接在当前范围去做事
            if (L <= l && R >= r) {
                return sum[rt];
            }
            // 任务覆盖不能 下放
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            // 子过程收集答案
            long ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

        // add update query任务执行时 若是当前位置不能被任务完全覆盖掉 那么必须要下放任务给子区间去处理 再收集答案
        // ln表示左子树元素结点个数，rn表示右子树结点个数
        private void pushDown(int rt, int ln, int rn) {
            // 如果同时存在update标记(当前位置有更新)和lazy的值 必然是update时间序更早 因此先把update的相关信息下放到子过程
            if (update[rt]) {
                // 子过程打上更新标记
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                // 子过程在更新时以父亲节点为准
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                // lazy置零
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                // sum 过程修改为update后的情形
                sum[rt << 1] = ln * change[rt];
                sum[rt << 1 | 1] = rn * change[rt];
                // 下放完成 父节点标记撤销 等待执行新的命令
                update[rt] = false;
            }
            // 下放完update信息 接着下放add信息
            if (lazy[rt] != 0) {
                // 子节点吃掉父节点传导的lazy，原来存的lazy信息不清楚，一整个累加的概念
                lazy[rt << 1] += lazy[rt];
                lazy[rt << 1 | 1] += lazy[rt];
                // sum信息在原有基础上更新
                sum[rt << 1] += lazy[rt] * ln;
                sum[rt << 1 | 1] += lazy[rt] * rn;
                // 更新完毕 父节点lazy清零 执行新任务
                lazy[rt] = 0;
            }
        }

    }

    // ----------------- 对数器方法 ----------------
    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            int ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

    }

    public static int[] generateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static void main(String[] args) {
        int len = 100;
        int max = 1000;
        int testTimes = 10000;
        int addOrUpadateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = generateRandomArray(len, max);
            // 线段树初始化
            SegmentTree seg = new SegmentTree(origin);
            final int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
            final int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
            final int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
            seg.build(S, N, root);
            // 暴力方法初始化
            Right rig = new Right(origin);
            // 两种方式做对比
            for (int j = 0; j < addOrUpadateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);  // 操作区间的开始位置 -> 可变
                int R = Math.max(num1, num2);  // 操作区间的结束位置 -> 可变
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);  // 要加的数字或者要更新的数字 -> 可变
                if (Math.random() < .5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);  // 操作区间的开始位置 -> 可变
                int R = Math.max(num1, num2);  // 操作区间的结束位置 -> 可变
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    System.out.println("Oops!");
                }
            }
        }
    }

}
