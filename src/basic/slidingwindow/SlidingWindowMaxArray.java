package basic.slidingwindow;

import java.util.LinkedList;

// 双端队列里只放下标 要查询数值大小直接调用array[下标]即可 不需要单独定义数据结构
public class SlidingWindowMaxArray {

    // 假设一个固定大小为w的窗口，依次划过arr，返回每一次滑出状况的最大值
    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || arr.length < w || w < 1) {
            return null;
        }
        LinkedList<Integer> qMax = new LinkedList<>();
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        for (int R = 0; R < arr.length; R++) {
            // 判断新数是否打破了原有的单调性
            // 若是没有破坏则作为新个体加入其间 若是打破了则不断弹出原有队列中数 直到能够使得新数有容身之所为止
            while (!qMax.isEmpty() && arr[qMax.peekLast()] <= arr[R]) {
                qMax.pollLast();
            }

            // 只用存下标  因为可以用下标去直接访问arr
            qMax.addLast(R);

            // 队头如果下标过期了 先移除
            if (qMax.peekFirst() == R - w) {
                qMax.pollFirst();
            }

            // 队头的数没过期并且一定是队列中最大的
            // 起初几个数 不构成一个窗口大小 不收集答案
            if (R >= w - 1) {
               res[index++] = arr[qMax.peekFirst()];
            }
        }
        return res;
    }

    // test method
    public static int[] right(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < N) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = right(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


}
