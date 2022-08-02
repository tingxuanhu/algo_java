package greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

public class IPO {

    // 本题没有做对数器 和https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class14/Code04_IPO.java对比或日后再补上
    public static class Program {
        public int cost;
        public int profit;

        public Program(int profit, int cost) {
            this.profit = profit;
            this.cost = cost;
        }
    }

    // 最多做K个项目, W是初始资金
    public static int maximalCapital(int K, int W, int[] Profits, int[] Capital) {
        if (Profits.length != Capital.length || Profits.length == 0) {
            return 0;
        }
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < Profits.length; i++) {
            minCostQ.add(new Program(Profits[i], Capital[i]));
        }
        for (int i = 0; i < K; i++) {
            while (!minCostQ.isEmpty() && minCostQ.peek().cost <= W) {
                maxProfitQ.add(minCostQ.poll());
            }
            // 资金解锁不了新的项目 利润大根堆里没东西  那就只能返回目前累计到的W
            if (maxProfitQ.isEmpty()) {
                return W;
            }
            W += maxProfitQ.poll().profit;
        }
        return W;
    }

    public static class MinCostComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }
    }

    public static class MaxProfitComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }

}
