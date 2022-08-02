package greedy;

import java.util.PriorityQueue;

public class SplitGold {

    // greedy algorithm
    public static int splitCostGreedy(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        PriorityQueue<Integer> splitHeap = new PriorityQueue<>();
        for (int num : arr) {
            splitHeap.add(num);
        }
        int cost = 0;
        int cur = 0;
        while (splitHeap.size() > 1) {
            cur = splitHeap.poll() + splitHeap.poll();
            cost += cur;
            splitHeap.add(cur);
        }
        return cost;
    }

    // glob all possible situations
    public static int splitCostGlob(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    public static int process(int[] arr, int preCost) {
        if (arr.length == 1) {
            return preCost;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(mergeTwo(arr, i, j), preCost + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    public static int[] mergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int index = 0; index < arr.length; index++) {
            if (index != i && index != j) {
                ans[ansi++] = arr[index];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxVal) {
        int[] arr = new int[(int) (Math.random() * maxSize + 1)];
        for (int i : arr) {
            i = (int) (Math.random() * maxVal + 1);
        }
        return arr;
    }

    public static void main(String[] agrs) {
        int testTimes = 10000;
        int maxSize = 6;
        int maxVal = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxSize, maxVal);
            if (splitCostGlob(arr) != splitCostGreedy(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("Finished!");
    }

}
