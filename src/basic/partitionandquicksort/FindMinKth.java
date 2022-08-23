package basic.partitionandquicksort;

// 数组中找到第K小的值 O(N)完成
public class FindMinKth {

    // process2是用概率的partition方法
    // index一定位于[L..R]范围上
    public static int process2(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        // arr中不止一个数
        int pivot = L + (int) (Math.random() * (R - L + 1));
        int[] equalRange = partition(arr, L, R, pivot);
        if (index >= equalRange[0] && index <= equalRange[1]) {
            return arr[index];
        } else if (index < equalRange[0]) {
            return process2(arr, L, equalRange[0] - 1, index);
        } else {
            return process2(arr,equalRange[1] + 1, R, index);
        }
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1; // 记录小于区最后一个数
        int more = R + 1; // 记录大于区最前一个数
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[] {less + 1, more - 1};
    }

    public static void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] ^= arr[b];
        arr[b] ^= arr[a];
        arr[a] ^= arr[b];
    }

}
