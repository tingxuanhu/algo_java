package basic.sort.mergesort;

public class MergeSort {

    // 递归实现
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int m = l + ((r - l) >> 1);
        process(arr, l, m);
        process(arr, m + 1, r);
        merge(arr, l, m, r);
    }

    // 非递归写法
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int N = arr.length;
        int mergeSize = 1;   // 当前有序的左组长度
        while (mergeSize < N) {
            int L = 0;      // 初始化左端点位置，枚举每次左半边组的开始位置
            while (L < N) {
                int M = L + mergeSize - 1;   // 理论上能算出来中点
                if (M >= N) {  // 最后这一组不够凑齐一整组左半边和右半边，甚至左半边都没凑齐，证明一定是有序的，不需merge最后一组了
                    break;
                }
                
                // 找完了当前的左半边，找右半边(M + 1, R)
                int R = Math.min(M + mergeSize, N - 1);   // 保证凑不齐的情况下，R取到整体数组最后位置，不越界
                merge(arr, L, M, R);
                L = R + 1;
            }
            
            // 防止当N接近系统最大值时，N/2 乘以2会溢出变负数
            if (mergeSize > N / 2) {
                break;
            }
            
            mergeSize <<= 1;
        }
    }

    public static void merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= m) {
            help[i++] = arr[p1++];
        }

        while (p2 <= r) {
            help[i++] = arr[p2++];
        }

        for (i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }

    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[maxSize];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * maxValue);
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
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if ((arr1 != null && arr2 == null) || (arr1 ==null && arr2 != null)) {
            return false;
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
        for (int j : arr) {
            System.out.println(j + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 5000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin!");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort1(arr1);
            mergeSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("Fucking fucked!");
                printArray(arr1);
                printArray(arr2);
                break;
            }

        }
        System.out.println("Finished!");
    }

}
