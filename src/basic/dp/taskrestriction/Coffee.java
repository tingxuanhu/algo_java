package basic.dp.taskrestriction;

import java.util.Comparator;
import java.util.PriorityQueue;

// 题目
// 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
// 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
// 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
// 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
// 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
// 四个参数：arr, n, a, b
// 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
public class Coffee {

    public static class Machine {
        private int reuseTime;
        private final int workTime;

        public Machine(int reuse, int work) {
            this.reuseTime = reuse;
            this.workTime = work;
        }
    }

    public static class MachineComparator implements Comparator<Machine> {
        @Override
        public int compare(Machine a, Machine b) {
            return (a.reuseTime + a.workTime) - (b.reuseTime + b.workTime);
        }
    }

    // 暴力递归版本
    public static int minTime(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> machineHeap = new PriorityQueue<Machine>(new MachineComparator());
        for (int i : arr) {
            machineHeap.add(new Machine(0, i));
        }
        // 获得每个人喝完咖啡的时间数组
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = machineHeap.poll();
            cur.reuseTime += cur.workTime;
            drinks[i] = cur.reuseTime;
            machineHeap.add(cur);
        }
        // 把喝完咖啡的数组进行第二阶段 清洗杯子的处理
        return bestTime(drinks, a, b, 0, 0);
    }

    // 暴力递归地求在给定喝完咖啡地时间数组的前提下能够获得的清洁杯子的最短总时间
    // wash 单杯洗干净的时间（串行）
    // air 挥发干净的时间(并行)
    // free 洗的机器什么时候可用
    // drinks[index.....]都变干净，最早的结束时间（返回）
    public static int bestTime(int[] drinks, int wash, int air, int index, int free){
        // 越界判定
        if (index == drinks.length) {
            return 0;
        }
        // 当前来到第index号杯子  决定要放机器里洗(可能能够立即洗 也可能要等位)
        int curWash = Math.max(drinks[index], free) + wash;
        int restClean1 = bestTime(drinks, wash, air, index + 1, curWash);
        int p1 = Math.max(curWash, restClean1);

        // 当前来到第index号杯子  自然挥发
        int curAir = drinks[index] + air;
        int restClean2 = bestTime(drinks, wash, air, index + 1, free);
        int p2 = Math.max(curAir, restClean2);

        return Math.min(p1, p2);
    }

    // dp
    public static int minTimeDP(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> machineHeap = new PriorityQueue<>();
        for (int i : arr) {
            machineHeap.add(new Machine(0, i));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < arr.length; i++) {
            Machine cur = machineHeap.poll();
            cur.reuseTime += cur.workTime;
            drinks[i] = cur.reuseTime;
            machineHeap.add(cur);
        }
        // dp的范围上界需要确定 考虑最坏情况 所有杯子都需要洗干净 算出时间的可能上界
        int upperBound = 0;
        for (int d : drinks) {
            upperBound = Math.max(upperBound, d) + a;
        }
        int[][] dp = new int[n + 1][upperBound + 1];
        // base case dp[n][..] = 0 越界条件
        for (int index = n - 1; index >=0; index--) {
            for (int reuse = 0; reuse <= upperBound; reuse++) {
                int curWash = Math.max(drinks[index], reuse) + a;
                // 剪枝掉递归不到的情况(这种情况还会越界)
                if (curWash > upperBound) {
                    break;  // 不用continue而用break
                }
                int p1 = Math.max(curWash, dp[index + 1][curWash]);
                int p2 = Math.max(drinks[index] + b, dp[index + 1][reuse]);
                dp[index][reuse] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

}
