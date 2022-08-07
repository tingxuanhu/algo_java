package datastucture.graph.topologysort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

// https://www.lintcode.com/problem/127/
// https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class16/Code03_TopologicalOrderDFS1.java
// 还有一种 https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class16/Code03_TopologicalOrderDFS2.java
public class TopologicalSortDFS1 {

    // oj平台自己规定好的图结构来写拓扑排序 (邻接表法的表达)
    public static class DirectedGraphNode {
        public int value;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            value = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 定义记忆化搜索的缓存
    public static class Record {
        public DirectedGraphNode node;
        public int deep;

        public Record(DirectedGraphNode n, int d) {
            node = n;
            deep = d;
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        // 填充缓存表
        for (DirectedGraphNode cur : graph) {
            memory(cur, order);
        }
        ArrayList<Record> record = new ArrayList<>(order.values());
        record.sort(new CountNumsComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record r : record) {
            ans.add(r.node);
        }
        return ans;
    }

    // 按照深度从高到低排序的比较器
    public static class CountNumsComparator implements Comparator<Record> {
        @Override
        public int compare(Record o1, Record o2) {
            return o2.deep - o1.deep;
        }
    }

    // 缓存法  记忆化搜索
    public static Record memory(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        // cur点次之前没算过
        int follow = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            follow = Math.max(follow, memory(next, order).deep);
        }
        Record ans = new Record(cur, follow + 1);
        order.put(cur, ans);
        return ans;
    }

}
