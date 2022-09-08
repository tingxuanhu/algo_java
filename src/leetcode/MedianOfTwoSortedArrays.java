package leetcode;

// https://leetcode.cn/problems/median-of-two-sorted-arrays/submissions/
public class MedianOfTwoSortedArrays {

    // 根据两个数组总长度的奇偶性 进行分类讨论
    // 注意结果是double类型  原因何在？  int类型两个数加和可能成为long型 --> 除以2可能是double型
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int size = m + n;
        boolean even = size % 2 == 0;
        // 注意分边界条件的思路  --> nums1  nums2
        if (m != 0 && n != 0) {
            if (even) {
                // 注意 findKthNum的k是从下标1开始的  没有第0小的数  而后面有一个数组为空的情况直接返回某一个数组的中位数信息的时候 就是从数组本身的0下标开始的
                // 所以前后的下标表达是不一样的  注意
                // 还要注意 double类型处理的时候 除以的分母的数值后面加上D  相当于python里打上小数点的写法 可以告诉Java分母是浮点数 应当保留小数而非截断之
                return (double) (findKthNum(nums1, nums2, size / 2) + findKthNum(nums1, nums2, size / 2 + 1)) / 2D;
            } else {
                return findKthNum(nums1, nums2, size / 2 + 1);
            }
        } else if (m != 0) {  // n == 0
            if (even) {
                return (double) (nums1[(size - 1) / 2] + nums1[size / 2]) / 2D;
            } else {
                return nums1[size / 2];
            }
        } else if (n != 0) {  // m == 0
            if (even) {
                return (double) (nums2[(size - 1) / 2] + nums2[size / 2]) / 2D;
            } else {
                return nums2[size / 2];
            }
        } else {  // m == 0   n == 0
            return 0;
        }
    }

    /* 找第k小的数的类别 ---> 分三类情况讨论
    1) k <= 短数组长度  --> 两个数组分别调用前k个数组成一对等长数组 调用getUpMedian(本题似乎应该最多等于 不会小于短数组长度)
    2) 短数组长度 < k  <= 长数组长度    举例讨论
        arr1 = [1  2  3  4]
        arr2 = [1' 2' 3' 4' 5' 6' 7' 8']      k = 7
        短数组所有的数都可能  6'<1<7'      3'<4<4'
        长数组中3'~7'可能  此时剩下短数组的4个数 和长数组的5个数 要人工挑出长数组左边可能的这个 才可能获得结果 扣除右侧得不到答案的
    3) 长数组长度 < k < 短数组与长数组的长度之和   比如上面例子中 k = 10
        短数组中可能的有 2  3  4
        长数组中可能的有 6' 7' 8'
        但是 虽然短数组长数组获得的结果等长 却不能直接递归
        因为在短数组中淘汰了1个 长数组中淘汰了5个 一共淘汰了6个 离k=10还差4个
        而递归调用6个数中间会求出第3小的数 加起来求到的是6+3=第9小的数  还差一个
        因此不能直接递归调用 会差一个  要人工淘汰/遴选两个  手动验证2  6'是不是第10小
        此时淘汰了2+6=8个  递归调用4个数 能凑出第8+2=10小的数
    */
    public static int findKthNum(int[] arr1, int[] arr2, int kth) {
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int l = longs.length;
        int s = shorts.length;
        // 上述情况1）
        if (kth <= s) {
            return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
        }
        // 上述情况3）
        if (kth > l) {
            // 手动遴选两个
            if (shorts[kth - l - 1] >= longs[l - 1]) {   // 2 当选
                return shorts[kth - l - 1];
            }
            if (longs[kth - s - 1] >= shorts[s - 1]) {   // 8' 当选
                return longs[kth - s - 1];
            }
            return getUpMedian(shorts, kth - l, s - 1, longs, kth - s, l - 1);
        }
        // 上述情况2）
        if (longs[kth - s - 1] >= shorts[s - 1]) {
            return longs[kth - s - 1];
        }
        return getUpMedian(shorts, 0, s - 1, longs, kth - s, kth - 1);
    }


    /*  下面的方法   输入参数  arr1[L1..R1]  arr2[L2..R2] 分别有序  等长
    因为二者等长，加和必为偶数，因此中位数不是某个数
    返回arr1和arr2(等长) 的上中位数   -->  O(lgN)
    分类讨论：  以arr1 = [1,  2,  3,  4]
                 arr2 = [1', 2', 3', 4'] 为例
    1）arr1、2长度为偶数  此时 arr1的上中位数2压过1,arr2的上中位数2'压过1'
        1. arr1的上中位数2 == arr2的上中位数2'
        --> 那么arr1压过1 arr2压过1'  加上他俩本身 一定够到上中位数 是谁都一样因为相等
        --> 直接返回这两个中的任何一个
        2.  2 > 2'
        --> 那么arr1的上中位数2一定干掉了1  2'干掉了1' 此时谁可能成为上中位数呢？
        --> 3'<1<4'时 1可能; 2 < 3'时 2可能； 1<3'<2时 3'可能; 4'<1时 4'可能;
        --> 3和4比2大 不可能(排除arr1的一半)；  1' 2' 不可能(排除arr2的一半)
        此时原来两个数组中间还剩下一半的候选人分属于两个数组 等长  所以可以递归调用继续求上中位数  新的上中位数套回原数组就是原始的上中位数
        3. 2' >= 2 和2反过来
        -> 可能成为的是1' 2'  3  4
    2）arr1、2长度为奇数
        以arr1 = [1,  2,  3,  4,  5 ]
          arr2 = [1', 2', 3', 4', 5']
        1. 3 == 3'  返回他俩随便一个  情况和1) 1.相同
        2. 3 > 3'
        --> 此时3干过了1 2 1' 2' 3' 至少是第六个数  因此 3 4 5被否定
        --> 2<3'<3时 3'可能;  4' 5' 可以尝试出也可能  此时候选人不等长了 arr1两个 arr2 手动判断3'是否可能
            （1）如果 2<=3'那么返回3'是上中位数
             (2) 如果 3'干不掉2 那么就淘汰  剩下两个等长的  可以递归
        3.  3' >= 3 和2反过来
        --> 可能成为的是1' 2' 3 4 5 把3做个人工判断 然后等长递归
    */
    public static int getUpMedian(int[] arr1, int L1, int R1, int[] arr2, int L2, int R2) {
        int mid1 = 0;
        int mid2 = 0;
        // 递归
        // 跳出的条件是尝试出来的
        // 奇数或者偶数最后变成2v2或者3v3
        // 2v2的时候 r1会收缩到l1  l2会收缩到r2 或者反过来  3v3也是一样 所以l和r相等时必然判断出结果 跳出循环
        while (L1 < R1) {
            mid1 = (L1 + R1) / 2;
            mid2 = (L2 + R2) / 2;
            // 如果二者上中位数相等 直接返回答案
            if (arr1[mid1] == arr2[mid2]) {
                return arr1[mid1];
            }
            // 如果长度均为奇数  --> 用长度与上1
            if (((R1 - L1 + 1) & 1) == 1) {
                // 3 > 3'
                if (arr1[mid1] > arr2[mid2]) {
                    if (arr1[mid1 - 1] <= arr2[mid2]) {
                        return arr2[mid2];
                    }
                    // 剔除掉单独的3' 修改arr1 arr2区间范围去递归
                    R1 = mid1 - 1;
                    L2 = mid2 + 1;
                } else {  // 3 <= 3'
                    if (arr1[mid1] >= arr2[mid2 - 1]) {
                        return arr1[mid1];
                    }
                    L1 = mid1 + 1;
                    R2 = mid2 - 1;
                }
            } else {  // 如果长度均为偶数
                if (arr1[mid1] > arr2[mid2]) {
                    R1 = mid1;
                    L2 = mid2 + 1;
                } else {  // arr1[mid] < arr2[mid]     2 < 2'
                    L1 = mid1 + 1;
                    R2 = mid2;
                }
            }
        }
        // 跳出循环时候必然剩下l1和r2(r1和l1会重叠过去)  来比一下谁更可能
        // 否定掉的一侧必然已经有比上中位数少1这么多的数存在 因此接下来接的就是上中位数
        // 那么剩下的两个数中谁更小就更先接触到比上中位数少1这么多的数 成为上中位数
        return Math.min(arr1[L1], arr2[R2]);
    }

}
