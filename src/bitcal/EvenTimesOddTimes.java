package bitcal;

import java.util.HashMap;
import java.util.HashSet;

public class EvenTimesOddTimes {
    // 一个数出现奇数次 其他数出现偶数次  找出出现奇数次的数
    public static void printOddTimesNum1(int[] arr) {
        int xor = 0;
        for (int i: arr){
            xor ^= i;
        }
        System.out.println(xor);
    }

    // 两个数出现奇数次（a、b）
    public static void printOddTimesNum2(int[] arr) {
        int xor = 0;
        for (int i : arr) {
            xor ^= i;   // xor == a ^ b
        }
        int rightOne = xor & (~xor + 1);  // 提取最右侧1

        int xor1 = 0;
        for (int cur: arr) {
            if ((rightOne & cur) == 0) {
                xor1 ^= cur;
            }
        }
        int other = xor ^ xor1;
        System.out.println(xor + " " + other);
    }

    // 一个数组中有一种数出现K次，其他数出现M次(M>1, K<M) 找出出现k次的数  要求时间复杂度O（N），空间O（1）
    // 开辟一个整型范围的数组 每个位都记录arr中的数的出现情况
    public static int onlyKTimes(int[] arr, int k, int m) {
        int[] help = new int[32];
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                help[i] += (num >> i) & 1;
            }
        }

        int ans = 0;
        for (int i = 0; i < 32; i++) {
            help[i] %= m;
            if (help[i] != 0) {
                ans |= 1 << i;
            }
        }
        return ans;
    }



    // 哈希表统计词频--对数器
    public static int comparator(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1) ;
            }
            else {
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


    // 写法要多加考虑  反复回看视频体会
    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int ktimesNum = generator(range);
        int numKinds = (int) (Math.random() * maxKinds) + 2;  // 至少要2种数
        int[] arr = new int[k + (numKinds - 1) * m];
        int idx = 0;
        for(; idx < k; idx++) {
            arr[idx] = ktimesNum;
        }
        numKinds--; // 还剩numKinds-1种数出现m次待填
        HashSet<Integer> set = new HashSet<>();
        set.add(ktimesNum);
        while (numKinds != 0) {
            int cur = 0;
            // 确保放进去的数是不重复的 此前没有出现过  不然就可能重复选中某个数然后重复累加出现次数了
            do {
                cur = generator(range);
            } while (set.contains(cur));
            set.add(cur);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[idx++] = cur;
            }
        }

        // 随机设置arr中数字的出现顺序
        for (int i = 0; i < arr.length; i++) {
            int j = (int) (Math.random() * arr.length);
            if (i != j) {
                arr[i] ^= arr[j];
                arr[j] ^= arr[i];
                arr[i] ^= arr[j];
            }
        }
        return arr;
    }


    // [-range, range] 上生成int型随机数
    public static int generator(int range) {
        return (int) (Math.random() * (range + 1)) - (int) (Math.random() * (range + 1));
    }

    public static void main(String[] args) {
            int kinds = 5;
            int range = 30;
            int testTime = 100000;
            int max = 9;
            System.out.println("start");
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
            int ans1 = comparator(arr, k, m);
            int ans2 = onlyKTimes(arr, k, m);
            if (ans1 != ans2) {
            System.out.println(ans1);
            System.out.println("wrong！");
            }
            }
            System.out.println("finished");
            }

}
