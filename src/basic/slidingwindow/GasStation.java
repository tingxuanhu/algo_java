package basic.slidingwindow;

import java.util.LinkedList;

// https://leetcode.com/problems/gas-station/
public class GasStation {

    //https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class24/Code03_GasStation.java ????

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || gas.length == 0 || gas.length != cost.length) {
            return -1;
        }
        boolean[] yesOrNo = goodArray(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (yesOrNo[i]) {
                return i;
            }
        }
        return -1;
    }

    // 返回分别以每个位置作为起点是否成功的布尔数组
    public static boolean[] goodArray(int[] g, int[] c) {
        int N = g.length;
        int M = N << 1;
        int[] arr = new int[M];
        // offset计算 & 环形子数组展开
        for (int i = 0; i < N; i++) {
            arr[i] = g[i] - c[i];
            arr[i + N] = g[i] - c[i];
        }
        for (int i = 1; i < M; i++) {
            arr[i] += arr[i - 1];
        }
        LinkedList<Integer> w = new LinkedList<>();
        // 从头开始分别走一个环(长度为N) 可以用滑动窗口限定开始和结束的范围是一个环
        // 遍历的每一次都相当于是走一个环的长度
        // 遍历的每一次 都首先建立一个offset最小者在队头的滑动窗口
        for (int i = 0; i < N; i++) {
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[i]) {
                w.pollLast();
            }
            w.addLast(i);
        }
        // 每一次遍历整个环都找出代价最大的点和环起点开始的offset作比较 如果offset能够负担得了最大点产生的代价 那么就证明环能跑通 否则不然
        boolean[] ans = new boolean[N];
        for (int offset = 0, i = 0, j = N; j < M; offset = arr[i++], j++) {
            if (offset <= arr[w.peekFirst()]) {
                ans[i] = true;
            }
            if (w.peekFirst() == i) {
                w.pollFirst();
            }
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[j]) {
                w.pollLast();
            }
            w.addLast(j);
        }
        return ans;
    }




}
