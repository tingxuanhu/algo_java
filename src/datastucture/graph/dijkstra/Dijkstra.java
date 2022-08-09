package datastucture.graph.dijkstra;

import datastucture.graph.Edge;
import datastucture.graph.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

// 适用没有权值为负数的边 (准确表述:允许存在权值为负数的边 但不允许存在权值为负数的边构建起的一个环)
public class Dijkstra {

    public static HashMap<Node, Integer> dijkstraOriginal(Node head) {
        // 这张表记录从head出发到所有点的最小距离  key:head出发到达的点   val:head出发到达key的最短距离
        // 若在表中没有对应距离记录 默认为正无穷
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(head, 0);
        // 记录已经求算过距离的出发节点 锁定它们 不再更改
        HashSet<Node> selectedNodes = new HashSet<>();
        // 挑一个distanceMap中还没被选过的点 -- 这是可以用加强堆优化的
        Node minNode = getMinDisAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            int cumDistance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                // toNode没记录过 -- 原来距离是正无穷 -- 新增一个更短的距离进distanceMap
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, cumDistance + edge.weight);
                } else {
                    distanceMap.put(edge.to, Math.min(cumDistance + edge.weight, distanceMap.get(toNode)));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDisAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    public static Node getMinDisAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        Node minNode = null;
        int minDis = Integer.MAX_VALUE;
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int dis = entry.getValue();
            if (!selectedNodes.contains(node) && dis < minDis) {
                minNode = node;
                minDis = dis;
            }
        }
        return minNode;
    }

}
