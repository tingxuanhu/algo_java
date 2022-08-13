package basic.datastucture.graph.dijkstra;

import basic.datastucture.graph.Edge;
import basic.datastucture.graph.Node;

import java.util.HashMap;

// 加强堆改写的优化Dijkstra算法 优化了getMinDisAndUnselectedNode过程
public class OptimizedDijkstra {

    // 相当于一个hashmap的映射表 为什么写成record而不用hashmap呢？ 思考 是随意之举吗？
    public static class NodeRecord {
        private Node node;
        private int dis;

        public NodeRecord(Node n, int d) {
            this.node = n;
            this.dis = d;
        }
    }

    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node, Integer> dijkstraHeapGreater(Node head, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> res = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int dis = record.dis;
            res.put(cur, dis);
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + dis);
            }
        }
        return res;
    }

    // 为单源最短路径定制的加强堆
    public static class NodeHeap {
        // 堆本体
        private Node[] nodes;
        // 反向索引表 用来检索nodes在堆中的哪个位置
        private HashMap<Node, Integer> heapIndexMap;
        private HashMap<Node, Integer> distanceMap;
        private int size;

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // node 现在发现了一个从源节点出发到达node距离为dis的情形 判断是否更新并实施动作
        public void addOrUpdateOrIgnore(Node node, int dis) {
            // node在堆中间
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(dis, distanceMap.get(node)));
                heapInsert(heapIndexMap.get(node));
            }
            // 如果node没有被记录过
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, dis);
                heapInsert(size++);
            }
            // 前两个逻辑都不符合就被ignore
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            // 在堆上把不用的距离抛弃
            distanceMap.remove(nodes[size - 1]);
            // 但进来过的痕迹还是留下
            heapIndexMap.put(nodes[size - 1], -1);
            heapify(0, --size);
            return nodeRecord;
        }

        private void heapInsert(int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1 : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(index, smallest);
                index = smallest;
                left = 2 * index + 1;
            }
        }

        // node是否进入过堆 (若进入又离开了 标记为-1 但仍然是进入过的 返回true)
        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        // node现在是否在堆中
        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            // 真实的堆上交换位置
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
            // 反向索引表交换位置
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
        }

    }

}
