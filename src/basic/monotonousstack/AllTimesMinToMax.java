package basic.monotonousstack;

import java.util.Stack;

public class AllTimesMinToMax {

    // 暴力解
    public static int max1(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                // k <= j 不要漏掉==情况
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, minNum * sum);
            }
        }
        return max;
    }

    // 单调栈法
    public static int maxMonotonousStack(int[] arr) {
        // 构造前缀和数组
        int[] preSum = new int[arr.length];
        preSum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            preSum[i] = arr[i] + preSum[i - 1];
        }

        Stack<Integer> stack = new Stack<>();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int idx = stack.pop();
                max = Math.max(max, (stack.isEmpty() ? preSum[i - 1] : (preSum[i - 1] - preSum[stack.peek()])) * arr[idx]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int idx = stack.pop();
            max = Math.max(max, (stack.isEmpty() ? preSum[arr.length - 1] : (preSum[arr.length - 1] - preSum[stack.peek()])) * arr[idx]);
        }
        return max;
    }

    public static int[] generateRandomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 10);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray();
            if (max1(arr) != maxMonotonousStack(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
    }

}
