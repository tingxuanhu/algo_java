package datastucture.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SortArrayDistanceLessK {

    public static void sortedArrDistanceLessK(int[] arr, int k) {
        if (k == 0) {
            return;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>();

        int idx = 0;  // index是要继承到后续的for loop的 所以定义在外围
        // 建一个小根堆
        for (; idx < Math.min(arr.length, k); idx++) {
            heap.add(arr[idx]);
        }

        // 不断加入后续元素并弹出堆顶元素  有序排列起来
        int i = 0;
        for (; idx < arr.length; i++, idx++) {
            heap.add(arr[idx]);
            arr[i] = heap.poll();
        }
        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // 制造一个移动不超过k的基本有序的数组
    public static int[] arrRandomSeries(int maxSize, int maxValue, int k) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        Arrays.sort(arr);

        boolean[] isSwapped = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) ((k + 1) * Math.random()), arr.length - 1);
            if (!isSwapped[i] && !isSwapped[j]) {
                isSwapped[i] = true;
                isSwapped[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
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

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeeded = true;
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize);
            int[] arr = arrRandomSeries(maxSize, maxValue, k);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            sortedArrDistanceLessK(arr1, k);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeeded = false;
                System.out.println("K:" + k);
                printArray(arr);
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeeded ? "OK" : "FAILED");
    }

}
