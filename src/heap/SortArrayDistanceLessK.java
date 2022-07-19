package heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SortArrayDistanceLessK {

    public static void sortedArrDistanceLessK(int[] arr, int k) {
        if (k == 0) {
            return;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();

        int idx = 0;  // index是要继承到后续的for loop的 所以定义在外围
        // 建一个小根堆 k+1规模
        for (; )


        // 不断加入后续元素并弹出堆顶元素  有序排列起来

    }


    // for test
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


    public static void main(String[] args)  {

    }

}
