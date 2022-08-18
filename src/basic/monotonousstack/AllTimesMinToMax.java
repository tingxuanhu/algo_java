package basic.monotonousstack;

import java.util.Stack;

public class AllTimesMinToMax {
    // 暴力解
    public static int max1(int[] arr) {
        int max = Integer.MIN_VALUE;
        // 范围 i --> j
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int sum = 0;
                int minNum = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, sum * minNum);
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

        }




    }



}
