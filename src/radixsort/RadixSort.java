package radixsort;

public class RadixSort {

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
        int i = 0;
        int j = 10;
        int[] help = new int[r - l + 1];
        // 进出桶次数
        for (int d = 1; d <= digits; d++) {
            int[] count = new int[radix];
            for (i = l; i < radix; i++) {

            }
        }

    }

    public static int getDigit(int x, int d) {
        return
    }

}
