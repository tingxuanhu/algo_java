package basic.datastucture.segmenttree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

// https://leetcode.cn/problems/falling-squares/
/*
想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
下面是这个游戏的简化版：
1）只会下落正方形积木
2）[a,b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
3）认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
4）没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满。
给定一个N*2的二维数组matrix，可以代表N个积木依次掉落，
返回每一次掉落之后的最大高度
 */
public class FallingSquares {

    public List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size();
        SegmentTree seg = new SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = seg.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            seg.update(L, R, height, 1, N, 1);
        }
        return res;
    }

    // 下标离散化
    // 输入规模可能很大 若是不想用那么大的规模 可以把每个元素输入进来做个左右端点的收集，再排序，构成更小空间就能实现的一个数轴
    // 比如[100, 7]   [20, 30]  [19, 6]  --> 能有效利用的数轴上的点是100,106,20,49,19,24 把这几个点抽取出来 排个序  建立小数轴
    // 这是考虑空间不爆炸的要求的前提下要做的事情 这样就能在小范围上实现修改
    // 为什么[L,Range] 的右边不取L+Range而要再减1呢？ 因为如果用L+Range那么新的数如果从Range往下落就会被有的高度挡住继续累加，实际高度为0
    public HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> ans = new TreeSet<>();   // TreeSet对Integer默认会按照从小到大排列  可以自定比较器
        // 加入所有的点进去 而且有序表结构能够使得加进去的从小到大排好序
        for (int[] position : positions) {
            ans.add(position[0]);
            ans.add(position[0] + position[1] - 1);
        }
        // 按照从小到大分别标好序(上面加入有序表已经使得按照从小到大组织起来了，这里的map只是对每个人加个编号，形成一个取代原来大规模的数轴)
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : ans) {
            map.put(index, ++count);
        }
        return map;
    }

    // 用线段树解决问题
    public static class SegmentTree {
        private int[] change;
        private boolean[] update;
        private int[] max;

        public SegmentTree(int size) {
            int N = size + 1;
            change = new int[N << 2];
            update = new boolean[N << 2];
            max = new int[N << 2];
        }

        public void update(int L, int R, int height, int l, int r, int rt) {
            if (L <= l && R >= r) {
                update[rt] = true;
                change[rt] = height;
                max[rt] = height;
                return;
            }

            int mid = (l + r) >> 1;
            pushDown(rt,mid - l + 1, r - mid);

            if (L <= mid) {
                update(L, R, height, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, height, mid + 1, r, rt << 1 | 1);
            }

            pushUp(rt);
        }


        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return max[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt,mid - l + 1, r - mid);
            int left = 0;
            int right = 0;
            if (L <= mid) {
                left = query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                right = query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(left, right);
        }

        private void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                max[rt << 1] = change[rt];
                max[rt << 1 | 1] = change[rt];
                update[rt] = false;
            }
        }

        private void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

    }

}
