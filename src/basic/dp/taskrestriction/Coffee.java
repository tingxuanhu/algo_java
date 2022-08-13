package basic.dp.taskrestriction;

import java.util.Comparator;
import java.util.PriorityQueue;

// 题目
// 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
// 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
// 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
// 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
// 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
// 四个参数：arr, n, a, b
// 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
public class Coffee {

    public static class Machine {
        private int reuseTime;
        private int workTime;

        public Machine(int reuse, int work) {
            this.reuseTime = reuse;
            this.workTime = work;
        }
    }

    public static int minTime(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> machineHeap = new PriorityQueue<Machine>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            machineHeap.add(new Machine(0, arr[i]));
        }
        // 接下来获得每个人喝完咖啡的时间数组
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = machineHeap.poll();
            cur.reuseTime += cur.workTime;
            drinks[i] = cur.reuseTime;
            machineHeap.add(cur);
        }
        // 把喝完咖啡的数组进行第二阶段 清洗杯子的处理
        return
    }



    public static int bestTime(int[] drinks, int wash, int air, int index, int free){




    }

    public static class MachineComparator implements Comparator<Machine> {
        @Override
        public int compare(Machine a, Machine b) {
            return (a.reuseTime + a.workTime) - (b.reuseTime + b.workTime);
        }
    }

}
