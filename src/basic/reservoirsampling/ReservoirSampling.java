package basic.reservoirsampling;

public class ReservoirSampling {

    public static class RandomBox {
        private final int[] bag;
        private final int N;
        private int count;

        // 限定这个盒子能装多大
        public RandomBox(int capacity) {
            bag = new int[capacity];
            N = capacity;
        }

        // 在这个固定大小的盒子中实现一些方法
        public void add(int num) {
            count++;
            // 开始的几个能直接装入
            if (count <= N) {
                bag[count - 1] = num;
            } else {  // 盒子里已经满了 那么就要斟酌一番
                if (rand(count) <= N) {
                    bag[rand(N) - 1] = num;
                }
            }
        }

        // 随机挑一个数
        private int rand(int max) {
            return (int) (Math.random() * max) + 1;
        }

        // 返回最终选出来的这几个天选之子
        public int[] choices() {
            int[] ans = new int[N];
            for (int i = 0; i < N; i++) {
                ans[i] = bag[i];
            }
            return ans;
        }

    }

    // 用来比较的方法 等概率返回1~i中的一个数字
    public static int random(int i) {
        return (int) (Math.random() * i) + 1;
    }

    public static void main(String[] args) {
        int test = 10000;
        int ballNum = 17;
        int[] count = new int[ballNum + 1];
        for (int i = 0; i < test; i++) {
            int[] bag = new int[10];
            int bagi = 0;
            for (int num = 1; num <= ballNum; num++) {
                if (num <= 10) {
                    bag[bag++] = num;
                } else {
                    if (random(num) <= 10) {
                        bagi = (int) (Math.random() * 10);
                        bag[bagi] = num;
                    }
                }
            }
            for (int num : bag) {
                count[num]++;
            }
        }
        for (int i = 0; i <= ballNum; i++) {
            System.out.println(count[i]);
        }

        // 蓄水池算法
        System.out.println("reservoirSampling algorithm");
        int all = 100;
        int capacity = 10;
        int testTimes = 50000;
        int[] counts = new int[all + 1];
        for (int i = 0; i < testTimes; i++) {
            RandomBox box = new RandomBox(capacity);
            for (int num = 1; num <= all; num++) {
                box.add(num);
            }
            int[] ans = box.choices();
            for (int j = 0; j < ans.length; j++) {
                counts[ans[j]]++;
            }
        }

        for (int i = 0; i < counts.length; i++) {
            System.out.println(i + " times : " + counts[i]);
        }


    }

}
