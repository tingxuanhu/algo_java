package basic.monotonousstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MonotonousStack {
    public static int[][] getNearLess(int[] arr) {
        // res[i] == [leftMostIndex, rightMostIndex]
        int[][] res = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            // 弹栈判断(栈满足从下至上由小到大的规则)
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> indexes = stack.pop();
                // 弹栈时把弹出的list的左右答案全部安置好
                int leftMostIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer index : indexes) {
                    res[index][0] = leftMostIndex;
                    res[index][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
         }
         // 没有新加元素，将栈中剩余元素依次弹出
         while (!stack.isEmpty()) {
             List<Integer> indexes = stack.pop();
             int leftMostIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
             for (Integer index : indexes) {
                 res[index][0] = leftMostIndex;
                 res[index][1] = -1;
             }
         }
         return res;
     }



}
