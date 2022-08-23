package basic.partitionandquicksort;

import java.util.Arrays;

public class PartitionAndQuickSort {

    // arr[l..r]上，以arr[r]位置的数做划分值   <=arr[r]  >arr[r]
    public static int partition1(int[] arr, int l, int r) {
        if (l > r) {
            return -1;
        }
        if (l == r) {
            return l;
        }

        int lessequal = l - 1;
        int idx = l;
        /*
         只有一个左边界在移动的情况下可以写成for loop
         但底下荷兰国旗问题(partition2)这种情况中间就只能写while loop了 要判断小于区域和大于区域的碰撞条件
         */
        for (; idx < r; idx++) {
            if (arr[idx] <= arr[r]) {
                swap(arr, idx, ++lessequal);
            }
        }
        swap(arr, r, ++lessequal);
        return lessequal;
    }

    // arr[l..r]上，以arr[r]位置的数做划分值   <arr[r]    =arr[r]     >arr[r]
    public static int[] partition2(int[] arr, int l, int r) {
        if (l > r) {
            return new int[]{-1, -1};
        }
        if (l == r) {
            return new int[]{l, r};
        }
        int less = l - 1;
        int more = r;  // 以最后一个人作为划分值 不参与循环过程中间的交换 只在最后交换 所以大于区域直接把它包起来
        int idx = l;
        while (idx < more) {
            if (arr[idx] < arr[r]) {
                swap(arr, idx++, ++less);
            } else if (arr[idx] == arr[r]) {
                idx++;
            } else {
                swap(arr, idx, --more);
            }
        }
        swap(arr, idx, r);
        return new int[]{less + 1, more};
    }

    // quickSort 1.0 edition
    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);

    }

    public static void process1(int[] arr, int l, int r) {
        // l==r时候也不用排序 注意这个边界条件不是l>r而是l>=r
        if (l >= r) {
            return;
        }
        // 不是严格地对半分  依赖于数据性质 所以1.0版本快排是O(N^2)
        int m = partition1(arr, l, r);
        process1(arr, l, m - 1);
        process1(arr, m + 1, r);
    }


    // quickSort 2.0 edition
    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process2(arr, 0, arr.length - 1);
    }

    public static void process2(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int[] equalZone = partition2(arr, l, r);
        process2(arr, l, equalZone[0] - 1);
        process2(arr, equalZone[1] + 1, r);
    }

    // quickSort 3.0 edition  加入概率
    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process3(arr, 0, arr.length - 1);
    }

    public static void process3(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int pickNum = (int) (Math.random() * (r - l + 1));
        swap(arr, l + pickNum, r);
        int[] equalZone = partition2(arr, l, r);
        process3(arr, l, equalZone[0] - 1);
        process3(arr, equalZone[1] + 1, r);

    }

    public static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
    }


    // 快排的非递归写法  第二轮刷题重新复习  写一写
    // 快排双向链表实现class05的补充题目  第二轮写


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
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
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;

        for (int i = 0;  i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr1, arr3)) {
                succeed = false;
                System.out.println(Arrays.toString(arr1));
                System.out.println();
                System.out.println(Arrays.toString(arr2));
                System.out.println();
                System.out.println(Arrays.toString(arr3));

                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");
    }

}
