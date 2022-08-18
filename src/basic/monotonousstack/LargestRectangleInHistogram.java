package basic.monotonousstack;

import java.util.Stack;

//    https://leetcode.com/problems/largest-rectangle-in-histogram/
public class LargestRectangleInHistogram {
    public static int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int curIndex = stack.pop();
                max = Math.max(max, (stack.isEmpty() ? i : i - stack.peek() - 1) * heights[curIndex]);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int curIndex = stack.pop();
            max = Math.max(
                    max, (stack.isEmpty() ? heights.length : heights.length - stack.peek() - 1) * heights[curIndex]
            );
        }
        return max;
    }

}
