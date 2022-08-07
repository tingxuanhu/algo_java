package datastucture.graph.topologysort;

import datastucture.graph.Graph;
import datastucture.graph.Node;

import java.util.*;

public class TopologicalSort {
    public static List<Node> topologySort(Graph graph) {
        // 记录某个节点对应的入度数量
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 遍历graph的所有节点 找出入度为零者 进入zeroInQueue  而后消除其影响  将新的入度为零者加入zeroInQueue
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node cur : graph.nodes.values()) {
            inMap.put(cur, cur.in);
            if (cur.in == 0) {
                zeroInQueue.add(cur);
            }
        }

        List<Node> res = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            res.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return res;
    }

}
