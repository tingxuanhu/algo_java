package basic.mergesort;

public class ReversePair {

    public static int reversePairNumber(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return process(arr, l, m) + process(arr, m + 1, r) + merge(arr, l, m, r);
    }

    public static int merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int pair = 0;
        int idx = help.length - 1;
        int p1 = m;
        int p2 = r;
        while (p1 >= l && p2 > m) {
            pair += arr[p1] > arr[p2] ? (p2 - m) : 0;
            help[idx--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
        }
        while (p1 >= l) {
            help[idx--] = arr[p1--];
        }
        while (p2 > m) {
            help[idx--] = arr[p2--];
        }
        System.arraycopy(help, 0, arr, l, help.length);
        return pair;
    }

    public static int comparator(int[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    res++;
                }
            }
        }
        return res;
    }

    public static int[] generateRandomArray(int size, int value) {
        int[] arr = new int[(int) (Math.random() * size)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (value + 1)) - (int) (Math.random() * value);
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

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1 == null || arr2 != null) {
            return false;
        }
        if (arr1 != null || arr2 == null) {
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
        for (int j : arr) {
            System.out.println(j + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (reversePairNumber(arr1) != comparator(arr2)) {
                printArray(arr1);
                printArray(arr2);
                System.out.println("Fucking fucked!");
                break;
            }
        }
        System.out.println("Finished!");
    }


}
