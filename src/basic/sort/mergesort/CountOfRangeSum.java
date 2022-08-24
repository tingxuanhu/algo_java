package basic.sort.mergesort;

public class CountOfRangeSum {

    public static int countRangeSum(int[] nums, int lower, int upper) {
        // 变数组为前缀和统计结果(int-- long)
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return process(sum, 0, nums.length - 1, lower, upper);
    }

    public static int process(long[] sum, int l, int r, int lower, int upper) {
        if (l == r) {
            return sum[l] >= lower && sum[l] <= upper ? 1 : 0;
        }
        int m = l + ((r - l) >> 1);
        return process(sum, l, m, lower, upper) + process(sum, m + 1, r, lower, upper)
                + merge(sum, l, m, r, lower, upper);
    }

    public static int merge(long[] arr, int l, int m, int r, int lower, int upper) {
        // 利用归并排序的两个子整体是有序结构，因此操作过程中间窗口扫描左右端点可以不回退  在O(N)时间内完成计数工作
        // 然后再进行经典归并操作
        int windowL = l;
        int windowR = l;
        int ans = 0;

        // 扫描区间[windowL, windowR) -- 左闭右开区间  注意边界条件
        for (int i = m + 1; i <= r; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (windowR <= m && arr[windowR] <= max) {
                windowR++;
            }
            while (windowL <= m && arr[windowL] < min) {
                windowL++;
            }
            ans += windowR - windowL;
        }

        long[] help = new long[r - l + 1];
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
        return ans;
    }

}
