package basic.monotonousstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MonotonousStack {

    // 单调栈 --> 求离i位置左右最近的比i位置数小的索引
    public static int[][] getNearLess(int[] arr) {
        // res[i] == [leftMostIndex, rightMostIndex]
        int[][] res = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        // 向单调栈中加入元素
        for (int i = 0; i < arr.length; i++) {
            // 弹栈判断(加入当前元素将会使得栈不再满足从下至上由小到大的规则)
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> indexes = stack.pop();
                // 弹栈时把弹出的list的左右答案全部安置好
                int leftMostIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer index : indexes) {
                    res[index][0] = leftMostIndex;
                    res[index][1] = i;
                }
            }

            // 向栈中加入遍历到的当前元素  若是当前元素与栈顶相等 加入它们
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {  // 当前元素大于栈顶元素 单独开辟list记录下来
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

    // for test
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }

    // for test
    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = getRandomArray(size, max);
            if (!isEqual(getNearLess(arr), rightWay(arr))) {
                System.out.println("Oops!");
                printArray(arr);
                break;
            }
        }
    }




}
