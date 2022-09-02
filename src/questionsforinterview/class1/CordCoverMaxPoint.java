package questionsforinterview.class1;

import java.util.Arrays;

// 给定一个有序数组arr，代表坐落在X轴上的点，给定一个正数L，代表绳子的长度，返回绳子最多压中几个点？
// 即使绳子边缘处盖住点也算盖住(也就是说绳子头和尾巴分别压住两个点  那么这两个点都计入最终结果)
public class CordCoverMaxPoint {
    // 普通二分解答   笔试能过
    public static int maxCover(int[] arr, int L) {
        if (L < 1 || arr == null || arr.length < 1) {
            return 0;
        }

        // 只要不是边界条件 那么至少res==1 --> 开一个空间就可以 后面要跟它比较 保留大者
        int res = 1;

        // 每个点都踩上去 二分法确定能扩展到多远 （具体位置）
        for (int i = 0; i < arr.length; i++) {
            int index = halfDivision(arr, i, arr[i] - L);
            res = Math.max(res, i - index + 1);
        }
        return res;
    }

    // 输入arr数组，调用二分的时候绳子踩在哪儿，以及要找的目标值具体值
    public static int halfDivision(int[] arr, int R, int tar) {
        // 进来的时候 arr的左端点是查找范围的左端点 右端点则是绳子所踩到的位置
        int L = 0;

        // index用来记录答案是绳子踩在谁头上
        int index = R;

        // 二分的边界思考
        // 我们定义左闭右闭的区间 因此相等是合法条件 要继续循环
        while (L <= R) {

            // 注意找中点的写法
            int mid = L + ((R - L) >> 1);

            // 中值大于目标 则往左边看
            // 把等于的条件也收归到这里 因此存储答案的index就在这里更新
            if (arr[mid] >= tar) {
                index = mid;
                // 因为更新记录的答案对应着的是mid位置 因此向左向右分别考察的时候就跳过它
                // 这样一来 如果左右都找不到答案 大while会跳出 index记录到准确位置
                R = mid - 1;
            } else {
                L = mid + 1;
            }

        }
        return index;
    }


    // 最优复杂度解 --> 双指针法
    public static int maxPoint2(int[] arr, int L) {
        if (L < 1 || arr == null || arr.length < 1) {
            return 0;
        }
        int l = 0;
        int r = 0;
        int N = arr.length;
        int res = 0;
        while (l < N) {
            while (r < N && arr[r] - arr[l] <= L) {
                r++;
            }
            res = Math.max(res, r - (l++));
        }
        return res;
    }


    // for test
    public static int[] generateOrderedArray(int maxSize, int maxVal) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxVal);
        }
        Arrays.sort(arr);  // 调用系统排序是因为只是为了写对数器
        return arr;
    }

    public static void main(String[] args) {
        int maxSize = 100;
        int maxVal = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateOrderedArray(maxSize, maxVal);
            int L = (int) (Math.random() * maxVal);
            if (maxCover(arr, L) != maxPoint2(arr, L)) {
                System.out.println("Oops!");
                System.out.println(Arrays.toString(arr));
                System.out.println("Length of cord:" + L);
                System.out.println(maxCover(arr, L));
                System.out.println(maxPoint2(arr, L));
                break;
            }
        }

    }

}
