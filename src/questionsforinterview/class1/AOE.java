package questionsforinterview.class1;

/*
 * 给定两个数组x和hp，长度都是N。
 * x数组一定是有序的，x[i]表示i号怪兽在x轴上的位置
 * hp数组不要求有序，hp[i]表示i号怪兽的血量
 * 为了方便起见，可以认为x数组和hp数组中没有负数。
 * 再给定一个正数range，表示如果法师释放技能的范围长度(直径！)
 * 被打到的每只怪兽损失1点血量。
 * 返回要把所有怪兽血量清空，至少需要释放多少次aoe技能？
 * 三个参数：int[] x, int[] hp, int range
 * 返回：int 次数
 * */
public class AOE {

    // 纯暴力解法 太容易超时 只能小样本量使用
    public static int minAoe1(int[] x, int[] hp, int range) {
        boolean allClear = true;
        // 确认是否还有怪兽没死
        for (int i = 0; i < hp.length; i++) {
            if (hp[i] > 0) {
                allClear = false;
                break;
            }
        }
        if (allClear) {
            return 0;
        } else {  // 的确有怪兽有血
            int ans = Integer.MAX_VALUE;
            for (int left = 0; left < x.length; left++) {
                if (hasHp(x, hp, left, range)) {
                    minusOneHp(x, hp, left, range);
                    ans = Math.min(ans, 1 + minAoe1(x, hp, range));
                    backtracking(x, hp, left, range);
                }
            }
            return ans;
        }
    }

    // 判断i位置的怪兽是否死了
    public static boolean hasHp(int[] x, int[] hp, int left, int range) {
        for (int i = 0; i < x.length && x[i] - x[left] <= range; i++) {
            if (hp[i] > 0) {
                return true;
            }
        }
        return false;
    }

    public static void minusOneHp(int[] x, int[] hp, int left, int range) {
        for (int i = 0; i < x.length && x[i] - x[left] <= range; i++) {
            hp[i]--;
        }
    }

    // 回溯
    public static void backtracking(int[] x, int[] hp, int left, int range) {
        for (int i = 0; i < x.length && x[i] - x[left] <= range; i++) {
            hp[i]++;
        }
    }


    // 方法二：贪心策略   永远让range左边扫一下最左边的怪兽 让range右边能够最远
    public static int minAoe2(int[] x, int[] hp, int range) {
        int n = x.length;
        // cover数组记录在遍历到每个位置的时候 range能够往右边覆盖到哪（记录的数值是第一个违规的 到不了的位置）
        int[] cover = new int[n];
        int r = 0;
        for (int i = 0; i < n; i++) {
            while (r < n && x[r] - x[i] <= range) {
                r++;
            }
            cover[i] = r;
        }
        // 上面已经制作好了cover数组
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (hp[i] > 0) {
                int minus = hp[i];
                for (int index = i; index < cover[i]; index++) {
                    hp[index] -= minus;
                }
                ans += minus;
            }
        }
        return ans;
    }

    // 方法三：线段树+总是用技能的最左边缘刮死当前最左侧的没死的怪物+向右边寻找下一个没死的重复刮死动作
    public static int minAoe3(int[] x, int[] hp, int range) {
        int n = x.length;
        int[] cover = new int[n];
        int r = 0;



    }

}
