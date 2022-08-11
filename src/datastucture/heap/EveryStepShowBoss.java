package datastucture.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class07/Code02_EveryStepShowBoss.java
public class EveryStepShowBoss {

    public static class Customer {
        public int id;
        public int buy;
        public int enterTime;

        public Customer(int id, int b, int e) {
            this.id = id;
            this.buy = b;
            this.enterTime = e;
        }
    }



    // for test   method 2
    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        if (arr.length != op.length) {
            throw new IndexOutOfBoundsException();
        }
        List<List<Integer>> ans = new ArrayList<>();
        HashMap<Integer, Customer> map = new HashMap<>();          // id <--> customer instance
        ArrayList<Customer> candidates = new ArrayList<>();
        ArrayList<Customer> winners = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            // 记录为0又退货 不合理操作 跳过 维持上一步
            if (!buyOrRefund && !map.containsKey(id)) {
                ans.add(getCurAns(winners));
                continue;
            }
            // 用户购买数量为0，此时买货
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0)); // 置0方便后面来进行调整 这里只是初始化
            }
            // 初始化后在这里开始调整 根据买货或者卖货实施具体措施(此前map有记录或者是刚刚map初始化记录,可以用下面语句统一更新buy记录)
            Customer cur = map.get(id);
            if (buyOrRefund) {
                cur.buy++;
            } else {
                cur.buy--;
            }
            if (cur.buy == 0) {
                map.remove(id);
            }
            // 调整完当前购货或者退货行为之后 可以进行候选与获奖的比拼
            // 首先判断实施当前行为的顾客是不是已经在候选区和获奖区当中
            if (!candidates.contains(cur) && !winners.contains(cur)) {
                cur.enterTime = i;
                if (winners.size() < k) {
                    winners.add(cur);
                } else {
                    candidates.add(cur);
                }
            }
            cleanAllZero(candidates);
            cleanAllZero(winners);
            candidates.sort(new CandidateComparator());
            winners.sort(new WinComparator());
            replace(candidates, winners, k, i);
            ans.add(getCurAns(winners));
        }
        return ans;
    }

    public static void cleanAllZero(ArrayList<Customer> arr) {
        List<Customer> tmp = new ArrayList<>();
        for (Customer c : arr) {
            if (c.buy != 0) {
                tmp.add(c);
            }
        }
        arr.clear();
        arr.addAll(tmp);
    }

    public static void replace(ArrayList<Customer> candidates, ArrayList<Customer> winners, int k, int enterTime) {
        if (candidates.isEmpty()) {
            return;
        }
        Customer cur = candidates.get(0);
        Customer competitor = winners.get(0);
        if (winners.size() < k) {
            cur.enterTime = enterTime;
            winners.add(cur);
            candidates.remove(cur);
        } else { // 此时获奖区满了 候选区非空
            if (cur.buy > competitor.buy) {
                winners.remove(0);
                candidates.remove(0);
                cur.enterTime = enterTime;
                competitor.enterTime = enterTime;
                winners.add(cur);
                candidates.add(competitor);
            }
        }
    }

    public static List<Integer> getCurAns(ArrayList<Customer> winners) {
        List<Integer> ans = new ArrayList<>();
        for (Customer cur : winners) {
            ans.add(cur.id);
        }
        return ans;
    }

    // 候选区比较器  买的多的先替换得奖者 若都一样多 让先买的挤走得奖者
    public static class CandidateComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer c1, Customer c2) {
            return c1.buy != c2.buy ? c2.buy - c1.buy : c1.enterTime - c2.enterTime;
        }
    }

    // 得奖区比较器  买的少的先被挤掉 若都一样少 把先得奖的挤走
    public static class WinComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer c1, Customer c2) {
            return c1.buy != c2.buy ? c1.buy - c2.buy : c1.enterTime - c2.enterTime;
        }
    }

}
