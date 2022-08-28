package basic.sort;

// 充分利用插入排序常数项小和快排时间复杂度低的优势
public class SortEngineering {
    // 插入排序 O(N^2) 常数项小
    // 快排 O(N * lgN) 常数项大
    // N 很大的时候 快排
    // N小的时候插入排序
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, i, j);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
    }


    public static void quickSort(int[] arr, int L, int R) {
        if (L + 60 > R) {
            insertionSort(arr);
            return;
        }
        // 如果区间更大则去快排
    }


}
