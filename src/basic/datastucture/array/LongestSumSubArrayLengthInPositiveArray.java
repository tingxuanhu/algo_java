package basic.datastucture.array;

public class LongestSumSubArrayLengthInPositiveArray {

    public static int getMaxLength(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }

        int l = 0;
        int r = 0;
        // 假定是左闭右闭区间 一开始把0号位置的元素纳入窗口来 [0, 0]
        int sum = arr[0]; // 记录窗口中数值的总和
        int len = 0; // 记录答案
        while (r < arr.length) {
            if (sum == K) {
                len = Math.max(len, r - l + 1);
                sum -= arr[l++];  // 记录完答案之后可以加入右边的新数也可以剔除左边的数 做一件事即可
            } else if (sum < K) {
                r++;
                if (r == arr.length) {
                    break;
                }
                sum += arr[r]; // ++r有可能越界 所以要先判断
            } else {
                sum -= arr[l++];
            }
        }
        return len;

    }


    // for test
    public static int right(int[] arr, int K) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr, i, j, K)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    // for test
    public static boolean valid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == K;
    }

    // for test
    public static int[] generatePositiveArray(int size, int value) {
        int[] ans = new int[size];
        for (int i = 0; i != size; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 500000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generatePositiveArray(len, value);
            int K = (int) (Math.random() * value) + 1;
            int ans1 = getMaxLength(arr, K);
            int ans2 = right(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println("K : " + K);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test end");
    }





}
