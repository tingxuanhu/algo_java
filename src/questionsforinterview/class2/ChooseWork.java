package questionsforinterview.class2;

import java.util.Comparator;
import java.util.Arrays;
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

    public static int[] bestIncome(int[] hard, int[] money, int[] ability) {
        if (hard == null || hard.length == 0 || hard.length != money.length || ability == null || ability.length == 0) {
            return new int[] {0};
        }
        WorkFactor[] workFactors = new WorkFactor[hard.length]();
        for (int i = 0; i < hard.length; i++) {
            workFactors[i] = new WorkFactor(hard[i], money[i]);
        }
        Arrays.sort(workFactors, new WorkComparator());


    }
}
