package radixsort;

import java.util.Arrays;

public class RadixSort {
    // only for non-negative value
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, getBits(arr));
    }

    public static int getBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = Math.max(max, i);
        }
        int bits = 0;
        while (max != 0) {
            max /= 10;
            bits++;
        }
        return bits;
    }

    // arr[L..R]排序  ,  最大值的十进制位数digit
    public static void radixSort(int[] arr, int l, int r, int digits) {
        final int radix = 10;
        int[] help = new int[r - l + 1];
        // 进出桶次数(位数)作为大循环
        for (int d = 0; d < digits; d++) {
            int[] count = new int[radix];
            for (int i = l; i <= r; i++) {
                int j = getDigit(arr[i], d);
                count[j]++;
            }
            // count --> 前缀和
            for (int i = 1; i < radix; i++) {
                count[i] = count[i - 1] + count[i];
            }

            // 回看这部分的思路
            for (int i = r; i >= l; i--) {
                int j = getDigit(arr[i], d);
                help[count[j] - 1] = arr[i];
                count[j]--;
            }

            for (int i = l, j = 0; i <= r; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    public static int getDigit(int x, int d) {
        return ((x / (int) (Math.pow(10, d))) % 10);  //pow返回double类型 要强制转换
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize + 1)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }

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

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i : arr) {
            System.out.print(i);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 10000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Finished!" : "Fucking fucked!");
    }

}
