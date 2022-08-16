package basic.slidingwindow;

import java.util.LinkedList;

public class AllLessNumSubArray {

    public static int getNum(int[] arr, int sum) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 制造查询当前双端队列最大和最小值的数据结构(sliding window) -- 需要用两个双端队列 一个实现不了
        LinkedList<Integer> maxQuery = new LinkedList<Integer>();
        LinkedList<Integer> minQuery = new LinkedList<Integer>();
        int r = 0;
        int res = 0;
        // 以每个位置作为起点向右找最长可能的子数组长度 记录子数组中所有的子数组
        for (int l = 0; l < arr.length; l++){
            while (r < arr.length) {
                while (!maxQuery.isEmpty() && arr[maxQuery.peekLast()] <= arr[r]) {
                    maxQuery.pollLast();
                }
                maxQuery.addLast(r);
                while (!minQuery.isEmpty() && arr[minQuery.peekLast()] >= arr[r]) {
                    minQuery.pollLast();
                }
                minQuery.addLast(r);
                if (arr[maxQuery.peekFirst()] - arr[minQuery.peekFirst()] > sum) {
                    break;
                } else {
                    r++;
                }
            }
            res += r - l;
            if (maxQuery.peekFirst() == l) {
                maxQuery.pollFirst();
            }
            if (minQuery.peekFirst() == l) {
                minQuery.pollFirst();
            }
        }
        return res;
    }

    // 暴力的对数器方法
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }
        return count;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int j : arr) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = getNum(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finished!");

    }

}

/*
思路：
不回退地向后扩
达标的子数组中任何一个子数组都达标
arr[L..R] 不达标时 无论L向左扩充或是R向右扩充 都不可能达标
 */