package basic.datastucture.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class07/Code02_EveryStepShowBoss.java
// 运行有问题  不对？ zuo给的代码也不对？
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


    // 加强堆优化的版本
//    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
//        if (arr.length != op.length) {
//            throw new IndexOutOfBoundsException();
//        }
//        List<List<Integer>> res = new ArrayList<>();
//        Operation operator = new Operation(k);
//        for (int i = 0; i < arr.length; i++) {
//            operator.operate(i, arr[i], op[i]);
//            res.add(operator.getResult());
//        }
//        return res;
//    }
//
//    public static class Operation {
//        private final HashMap<Integer, Customer> customers;
//        private final HeapGreater<Customer> candidateHeap;
//        private final HeapGreater<Customer> winnerHeap;
//        private final int limit;
//
//        public Operation(int l) {
//            customers = new HashMap<>();
//            candidateHeap = new HeapGreater<>(new CandidateComparator());
//            winnerHeap = new HeapGreater<>(new WinComparator());
//            limit = l;
//        }
//
//         // 当前处理第i个事件  arr[i] -- id    buyOrRefund
//        public void operate(int curTime, int id, boolean buyOrRefund) {
//            // 记录为0又退货 不合理操作 跳过
//            if (!buyOrRefund && !customers.containsKey(id)) {
//                return;
//            }
//            if (!customers.containsKey(id)) {
//                customers.put(id, new Customer(id, 0, 0));
//            }
//            Customer cur = customers.get(id);
//            if (buyOrRefund) {
//                cur.buy++;
//            } else {
//                cur.buy--;
//            }
//            if (cur.buy == 0) {
//                customers.remove(id);
//            }
//            // 调整完当前购货或者退货行为之后 可以进行候选与获奖的比拼
//            // 首先判断实施当前行为的顾客是不是已经在候选区和获奖区当中
//            if (!candidateHeap.contains(cur) && !winnerHeap.contains(cur)) {
//                cur.enterTime = curTime;
//                if (winnerHeap.size() < limit) {
//                    winnerHeap.push(cur);
//                } else {
//                    candidateHeap.push(cur);
//                }
//            } else if (candidateHeap.contains(cur)) {
//                if (cur.buy == 0) {
//                    candidateHeap.remove(cur);
//                } else {
//                    candidateHeap.resign(cur);
//                }
//            } else { // winnerHeap.contains(cur)
//                if (cur.buy == 0) {
//                    winnerHeap.remove(cur);
//                } else {
//                    winnerHeap.resign(cur);
//                }
//            }
//            moveOrIgnore(curTime);
//        }
//
//        public List<Integer> getResult() {
//            List<Customer> customers = winnerHeap.getAllElements();
//            List<Integer> res = new ArrayList<>();
//            for (Customer cur : customers) {
//                res.add(cur.id);
//            }
//            return res;
//        }
//
//        private void moveOrIgnore(int curTime) {
//            if (candidateHeap.isEmpty()) {
//                return;
//            }
//            if (winnerHeap.size() < limit) {
//                Customer replacer = candidateHeap.pop();
//                replacer.enterTime = curTime;
//                winnerHeap.push(replacer);
//            } else {
//                if (candidateHeap.peek().buy > winnerHeap.peek().buy) {
//                    Customer competitor = winnerHeap.pop();
//                    Customer replacer = candidateHeap.pop();
//                    competitor.enterTime = curTime;
//                    replacer.enterTime = curTime;
//                    winnerHeap.push(replacer);
//                    candidateHeap.push(competitor);
//                }
//            }
//        }
//    }


    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        WhosYourDaddy whoDaddies = new WhosYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            whoDaddies.operate(i, arr[i], op[i]);
            ans.add(whoDaddies.getDaddies());
        }
        return ans;
    }

    public static class WhosYourDaddy {
        private HashMap<Integer, Customer> customers;
        private HeapGreater<Customer> candHeap;
        private HeapGreater<Customer> daddyHeap;
        private final int daddyLimit;

        public WhosYourDaddy(int limit) {
            customers = new HashMap<Integer, Customer>();
            candHeap = new HeapGreater<>(new CandidateComparator());
            daddyHeap = new HeapGreater<>(new WinComparator());
            daddyLimit = limit;
        }

        // 当前处理i号事件，arr[i] -> id,  buyOrRefund
        public void operate(int time, int id, boolean buyOrRefund) {
            if (!buyOrRefund && !customers.containsKey(id)) {
                return;
            }
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }
            Customer c = customers.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                customers.remove(id);
            }
            if (!candHeap.contains(c) && !daddyHeap.contains(c)) {
                if (daddyHeap.size() < daddyLimit) {
                    c.enterTime = time;
                    daddyHeap.push(c);
                } else {
                    c.enterTime = time;
                    candHeap.push(c);
                }
            } else if (candHeap.contains(c)) {
                if (c.buy == 0) {
                    candHeap.remove(c);
                } else {
                    candHeap.resign(c);
                }
            } else {
                if (c.buy == 0) {
                    daddyHeap.remove(c);
                } else {
                    daddyHeap.resign(c);
                }
            }
            daddyMove(time);
        }

        public List<Integer> getDaddies() {
            List<Customer> customers = daddyHeap.getAllElements();
            List<Integer> ans = new ArrayList<>();
            for (Customer c : customers) {
                ans.add(c.id);
            }
            return ans;
        }

        private void daddyMove(int time) {
            if (candHeap.isEmpty()) {
                return;
            }
            if (daddyHeap.size() < daddyLimit) {
                Customer p = candHeap.pop();
                p.enterTime = time;
                daddyHeap.push(p);
            } else {
                if (candHeap.peek().buy > daddyHeap.peek().buy) {
                    Customer oldDaddy = daddyHeap.pop();
                    Customer newDaddy = candHeap.pop();
                    oldDaddy.enterTime = time;
                    newDaddy.enterTime = time;
                    daddyHeap.push(newDaddy);
                    candHeap.push(oldDaddy);
                }
            }
        }

    }






//    // for test   method 2
//    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
//        if (arr.length != op.length) {
//            throw new IndexOutOfBoundsException();
//        }
//        List<List<Integer>> ans = new ArrayList<>();
//        HashMap<Integer, Customer> customers = new HashMap<>();          // id <--> customer instance
//        ArrayList<Customer> candidates = new ArrayList<>();
//        ArrayList<Customer> winners = new ArrayList<>();
//
//        for (int i = 0; i < arr.length; i++) {
//            int id = arr[i];
//            boolean buyOrRefund = op[i];
//            // 记录为0又退货 不合理操作 跳过 维持上一步的结果作为答案
//            if (!buyOrRefund && !customers.containsKey(id)) {
//                ans.add(getCurAns(winners));
//                continue;
//            }
//            if (!customers.containsKey(id)) {
//                customers.put(id, new Customer(id, 0, 0)); // 置0方便后面来进行调整 这里只是初始化
//            }
//            // 初始化后在这里开始调整 根据买货或者卖货实施具体措施(此前map有记录或者是刚刚map初始化记录,可以用下面语句统一更新buy记录)
//            Customer cur = customers.get(id);
//            if (buyOrRefund) {
//                cur.buy++;
//            } else {
//                cur.buy--;
//            }
//            if (cur.buy == 0) {
//                customers.remove(id);
//            }
//            // 调整完当前购货或者退货行为之后 可以进行候选与获奖的比拼
//            // 首先判断实施当前行为的顾客是不是已经在候选区和获奖区当中
//            if (!candidates.contains(cur) && !winners.contains(cur)) {
//                cur.enterTime = i;
//                if (winners.size() < k) {
//                    winners.add(cur);
//                } else {
//                    candidates.add(cur);
//                }
//            }
//            cleanAllZero(candidates);
//            cleanAllZero(winners);
//            candidates.sort(new CandidateComparator());
//            winners.sort(new WinComparator());
//            replace(candidates, winners, k, i);
//            ans.add(getCurAns(winners));
//        }
//        return ans;
//    }
//
//    public static void cleanAllZero(ArrayList<Customer> arr) {
//        List<Customer> tmp = new ArrayList<>();
//        for (Customer c : arr) {
//            if (c.buy != 0) {
//                tmp.add(c);
//            }
//        }
//        arr.clear();
//        arr.addAll(tmp);
//    }
//
//    public static void replace(ArrayList<Customer> candidates, ArrayList<Customer> winners, int k, int enterTime) {
//        if (candidates.isEmpty()) {
//            return;
//        }
//        if (winners.size() < k) {
//            Customer cur = candidates.get(0);
//            cur.enterTime = enterTime;
//            winners.add(cur);
//            candidates.remove(cur);
//        } else { // 此时获奖区满了 候选区非空
//            if (candidates.get(0).buy > winners.get(0).buy) {
//                Customer cur = candidates.get(0);
//                Customer competitor = winners.get(0);
//                winners.remove(0);
//                candidates.remove(0);
//                cur.enterTime = enterTime;
//                competitor.enterTime = enterTime;
//                winners.add(cur);
//                candidates.add(competitor);
//            }
//        }
//    }
//
//    public static List<Integer> getCurAns(ArrayList<Customer> winners) {
//        List<Integer> ans = new ArrayList<>();
//        for (Customer cur : winners) {
//            ans.add(cur.id);
//        }
//        return ans;
//    }



    public static List<List<Integer>> compare2(int[] arr, boolean[] op, int k) {
        HashMap<Integer, Customer> map = new HashMap<>();
        ArrayList<Customer> cands = new ArrayList<>();
        ArrayList<Customer> daddy = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            if (!buyOrRefund && !map.containsKey(id)) {
                ans.add(getCurAns(daddy));
                continue;
            }
            // 没有发生：用户购买数为0并且又退货了
            // 用户之前购买数是0，此时买货事件
            // 用户之前购买数>0， 此时买货
            // 用户之前购买数>0, 此时退货
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }
            // 买、卖
            Customer c = map.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                map.remove(id);
            }
            // c
            // 下面做
            if (!cands.contains(c) && !daddy.contains(c)) {
                if (daddy.size() < k) {
                    c.enterTime = i;
                    daddy.add(c);
                } else {
                    c.enterTime = i;
                    cands.add(c);
                }
            }
            cleanZeroBuy(cands);
            cleanZeroBuy(daddy);
            cands.sort(new CandidateComparator());
            daddy.sort(new WinComparator());
            move(cands, daddy, k, i);
            ans.add(getCurAns(daddy));
        }
        return ans;
    }

    public static void move(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int time) {
        if (cands.isEmpty()) {
            return;
        }
        // 候选区不为空
        if (daddy.size() < k) {
            Customer c = cands.get(0);
            c.enterTime = time;
            daddy.add(c);
            cands.remove(0);
        } else { // 等奖区满了，候选区有东西
            if (cands.get(0).buy > daddy.get(0).buy) {
                Customer oldDaddy = daddy.get(0);
                daddy.remove(0);
                Customer newDaddy = cands.get(0);
                cands.remove(0);
                newDaddy.enterTime = time;
                oldDaddy.enterTime = time;
                daddy.add(newDaddy);
                cands.add(oldDaddy);
            }
        }
    }

    public static void cleanZeroBuy(ArrayList<Customer> arr) {
        List<Customer> noZero = new ArrayList<Customer>();
        for (Customer c : arr) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        arr.clear();
        for (Customer c : noZero) {
            arr.add(c);
        }
    }

    public static List<Integer> getCurAns(ArrayList<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer c : daddy) {
            ans.add(c.id);
        }
        return ans;
    }


    // for test 对数器
    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    public static Data generateRandomData(int maxVal, int maxSize) {
        int len = (int) (Math.random() * maxSize) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxVal);
            op[i] = Math.random() < 0.5;
        }
        return new Data(arr, op);
    }

    public static boolean compareAns(List<List<Integer>> res1, List<List<Integer>> res2) {
        if (res1.size() != res2.size()) {
            return false;
        }
        for (int i = 0; i < res1.size(); i++) {
            List<Integer> curRes1 = res1.get(i);
            List<Integer> curRes2 = res2.get(i);
            if (curRes1.size() != curRes2.size()) {
                return false;
            }
            curRes1.sort((a, b) -> a - b);
            curRes2.sort((a, b) -> a - b);
            for (int j = 0; j < curRes1.size(); j++) {
                if (!curRes1.get(j).equals(curRes2.get(j))) {

//                    System.out.println(res1);
//                    System.out.println(res2);
                    return false;
                }
            }
        }
        return true;
    }



    public static void main(String[] args) {
        int maxVal = 10;
        int maxSize = 100;
        int maxK = 6;
        int testTimes = 1000;
        for (int i = 0; i < testTimes; i++) {
            Data data = generateRandomData(maxVal, maxSize);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = data.arr;
            boolean[] op = data.op;
            List<List<Integer>> res1 = topK(arr, op, k);
            List<List<Integer>> res2 = compare2(arr, op, k);
            if (!compareAns(res1, res2)) {
//                for (int j = 0; j < res1.size(); j++) {
//                    System.out.println(arr[j] + "," + op[j]);
//                }
                System.out.println(k);
                System.out.println(res1);
                System.out.println(res2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Finished!");
    }

}
