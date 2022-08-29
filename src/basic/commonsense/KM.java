package basic.commonsense;

import java.util.HashMap;
import java.util.HashSet;

// 一个数组中有一种数出现K次，其他数出现M次(M>1, K<M)
// 找出出现k次的数  要求时间复杂度O（N），空间O（1）
// 开辟一个整型范围的数组 每个位都记录arr中的1的出现次数
public class KM {

    // 假定arr非空
    public static int km(int[] arr, int k, int m) {
        // 开辟一个32位覆盖int类型的整数数组
        int[] help = new int[32];

        // 开辟的数组的每一位分别统计arr中的每个数字在这一位上是1的个数
        // 这里不能只用一个int i = 0; i < 32; i++的循环 原因在于arr中的数不一定是32位的 可能没到
        // 但是我们需要help数组在每一位都做记录  所以要套两层 但由于内层数量有限 还是O(N)
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                help[i] += (num >> i) & 1;
            }
        }

        // 把每一位的统计结果去%m，看看出现m次的数究竟在哪些位会是1，把它们记录成结果
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (help[i] % m != 0) {
                ans |= 1 << i;
            }
        }
        return ans;
    }

    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        int ans = 0;
        for (int num : map.keySet()) {
            if (map.get(num) == k) {
                ans = num;
                break;
            }
        }
        return ans;
    }

    // 为了测试
    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        // 真命天子出现的次数
        int times = k;
        // 2
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        // k * 1 + (numKinds - 1) * m
        int[] arr = new int[times + (numKinds - 1) * m];
        int index = 0;
        for (; index < times; index++) {
            arr[index] = ktimeNum;
        }
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(ktimeNum);
        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // arr 填好了
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    // 为了测试
    // [-range, +range]
    public static int randomNumber(int range) {
        return (int) (Math.random() * (range + 1)) - (int) (Math.random() * (range + 1));
    }

    // 为了测试
    public static void main(String[] args) {
        int kinds = 5;
        int range = 30;
        int testTime = 100000;
        int max = 9;
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = km(arr, k, m);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println("Oops！");
            }
        }
    }

}
