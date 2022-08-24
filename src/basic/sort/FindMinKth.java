package basic.sort;

import java.util.Comparator;
import java.util.PriorityQueue;

// 数组中找到第K小的值 O(N)完成
// bfprt算法
public class FindMinKth {
    public static class ValComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    // 方法一：大根堆
    public static int minKth1(int[] array, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
        for (int i = 0; i < k; i++) {
            maxHeap.add(array[i]);
        }
        for (int i = k; i < array.length; i++) {
            if (array[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(array[i]);
            }
        }
        return maxHeap.peek();
    }

    // 方法二：用概率为基础的快排改写
    public static int minKth2(int[] array, int k) {
        int[] arr = copyArray(array);
        return process2(arr, 0, arr.length - 1, k - 1);
    }

    // process2是用概率的partition方法 （与快排的区别在于 只用进入左右中的一侧递归）
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
        return new int[] { less + 1, more - 1 };
    }

    public static void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] ^= arr[b];
        arr[b] ^= arr[a];
        arr[a] ^= arr[b];
    }

    // bfprt算法
    public static int minKth3(int[] array, int k) {
        int[] arr = copyArray(array);
        return bfprt(arr, 0, arr.length, k - 1);
    }

    public static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        // bfprt比起概率的办法精进之处就在于此 精挑细选一个数用来partition而不是依概率随机选择
        int pivot = medianMedians(arr, L, R);
        int[] equalRange = partition(arr, L, R, pivot);
        // 要找的数的下标落在相等区域内
        if (index >= equalRange[0] && index <= equalRange[1]) {
            return arr[index];
        } else if (index < equalRange[0]) {
            return bfprt(arr, L, equalRange[0] - 1, index);
        } else {
            return bfprt(arr, equalRange[1] + 1, R,  index);
        }
    }

    // bfprt算法精挑细选的过程
    public static int medianMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        // 若最后一组不到5个数，也要算1组，用offset表达
        int offset = size % 5 == 0 ? 0 : 1;
        int[] medianArr = new int[size / 5 + offset];

        // 分别挑选每个五人小组的中位数加入medianArr战队
        for (int team = 0; team < medianArr.length; team++) {
            int teamFirst = L + team * 5;
            medianArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }

        // 从medianArr战队中挑出中位数作为天选之子
        return bfprt(medianArr, 0, medianArr.length - 1, medianArr.length / 2);
    }

    public static int getMedian(int[] arr, int L, int R) {
        insertionSort(arr, L, R);
        return arr[(R + L) / 2];
    }

    public static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

}
