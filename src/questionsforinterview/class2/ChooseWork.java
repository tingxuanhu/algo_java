package questionsforinterview.class2;

import java.util.Comparator;
import java.util.Arrays;
import java.util.TreeMap;
/*
给定数组hard和money，长度都为N，hard[i]表示i号工作的难度， money[i]表示i号工作的收入
给定数组ability，长度都为M，ability[j]表示j号人的能力，每一号工作，都可以提供无数的岗位，难度和收入都一样
但是人的能力必须>=这份工作的难度，才能上班。
返回一个长度为M的数组ans，ans[j]表示j号人能获得的最好收入

 需要有序表
 */
public class ChooseWork {
    public static class WorkFactor {
        private int hard;
        private int income;

        public WorkFactor(int h, int m) {
            hard = h;
            income = m;
        }
    }

    // 排个序  难度优先 难度相同的收入优先
    public static class WorkComparator implements Comparator<WorkFactor> {
        @Override
        public int compare(WorkFactor w1, WorkFactor w2) {
            return w1.hard != w2.hard ? w1.hard - w2.hard : w2.income - w1.income;
        }
    }

    // ZuoChengYun的算法里是直接输入了hard和money合并起来的数据类型
    public static int[] bestIncome(int[] hard, int[] money, int[] ability) {
        if (hard == null || hard.length == 0 || hard.length != money.length || ability == null || ability.length == 0) {
            return new int[] {0};
        }
        WorkFactor[] workFactors = new WorkFactor[hard.length];
        for (int i = 0; i < hard.length; i++) {
            workFactors[i] = new WorkFactor(hard[i], money[i]);
        }
        Arrays.sort(workFactors, new WorkComparator());

        // 有序表依照key默认升序排列，找一个人能够负担的同等能力岗位可以在O(lgN)的水平实现
        //       hard     money
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(workFactors[0].hard, workFactors[0].income);
        for (int i = 1; i < workFactors.length; i++) {
            WorkFactor pre = workFactors[0];
            // 因为之前已经用比较器排序，当难度相当时income大的排在前先进入比较，所以当hard相等时第一个进来的保留就行
            // 因此只需要比较当hard不同的时候，如果income更大就替换
            if (workFactors[i].hard != pre.hard && workFactors[i].income > pre.income) {
                pre = workFactors[i];
                map.put(pre.hard, pre.income);
            }
        }

        // 准备好查询用的有序表，就来书写答案
        int M = ability.length;
        int[] ans = new int[M];
        for (int i = 0; i < ability.length; i++) {
            // 不能直接赋floorKey  因为可能是null(能力比最低要求还差) 找不到工作
            Integer key = map.floorKey(ability[i]);
            ans[i] = key == null ? 0 : map.get(key);
        }
        return ans;
    }
}
