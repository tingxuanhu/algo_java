package basic.sort;

import basic.sort.mergesort.MergeSort;

public class InsertionSort {

    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            // 下面for loop终止条件有两个
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                    swap(arr, j + 1, j);   // 不是swap i和j  特别注意！
            }
        }
    }

    public static void swap(int[] arr, int a, int b) {
        // 此处不用声明a ！= b才执行 原因在于 j < i 但一般情况下要做判断 否则异或可能失效
        arr[a] ^= arr[b];
        arr[b] ^= arr[a];
        arr[a] ^= arr[b];
    }

    // 对数器
    public static void mergeSortForCompare(int[] arr) {
        MergeSort.mergeSort1(arr);
    }

    public static int[] generateRandomArray(int maxSize, int maxVal) {
        int[] res = new int[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) (Math.random() * maxVal) - (int) (Math.random() * maxVal);
        }
        return res;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0 ; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 != null && arr2 == null) || (arr1 == null && arr2 != null))  {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        // 两者都不空时
        if (arr1.length != arr2.length) {
            return false;
        }
        // 两者长度相当时
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        int testTimes = 100000;
        int maxSize = 100;
        int maxVal = 100;
        for (int i = 0; i < testTimes; i++) {
            // step1：产生数组 用我们写的方法和对数器方法作比较  因此需要原数组和克隆数组
            int[] arr = generateRandomArray(maxSize, maxVal);
            int[] arrClone = copyArray(arr);

            // step2：用两种方法，判断是否结果有差异  因此需要设计判断的方法
            insertionSort(arr);
            mergeSortForCompare(arrClone);
            if (!isEqual(arr, arrClone)) {
                System.out.println("oops!");
            }
        }

    }


}
