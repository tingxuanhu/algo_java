package datastucture.graph.minspanningtree;

import datastucture.graph.Edge;
import datastucture.graph.Graph;
import datastucture.graph.Node;

import java.util.*;

/* Kruskal最小生成树   要求是无向图
1 从权值最小的边开始考虑 依次考察权值变大的边
2 当前边要么进入最小生成树的集合 要么舍弃  用并查集结构
3 若当前边进入最小生成树的集合不成环 要当前边  否则舍弃
 */
public class Kruskal {

    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind uf = new UnionFind();
        uf.makeSets(graph.nodes.values());
        PriorityQueue<Edge> edges = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge : graph.edges) {
            edges.add(edge);
        }
        Set<Edge> res = new HashSet<>();
        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            if (!uf.isSameSet(edge.from, edge.to)) {
                res.add(edge);
                uf.union(edge.from, edge.to);
            }
        }
        return res;
    }

    // 除了hash表 尝试用数组实现一下并查集
    public static class UnionFind {
        private HashMap<Node, Node> parent;
        private HashMap<Node, Integer> size;

        public UnionFind() {
            parent = new HashMap<Node, Node>();
            size = new HashMap<Node, Integer>();
        }

        // 这里用Collection是因为取的是graph.nodes的values也就是key-value pair中的value,因此不能写HashMap而用集合接口来代表
        public void makeSets(Collection<Node> nodes) {
            for (Node node : nodes) {
                parent.put(node, node);
                size.put(node, 1);
            }
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node aPa = find(a);
            Node bPa = find(b);
            if (aPa != bPa) {
                int aSetSize = size.get(aPa);
                int bSetSize = size.get(bPa);
                if (aSetSize <= bSetSize) {
                    parent.put(aPa, bPa);
                    size.put(bPa, aSetSize + bSetSize);
                    size.remove(aPa);
                } else {
                    parent.put(bPa, aPa);
                    size.put(aPa, aSetSize + bSetSize);
                    size.remove(bPa);
                }
            }
        }

        public Node find(Node n) {
            Stack<Node> stack = new Stack<>();
            while (n != parent.get(n)) {
                stack.push(n);
                n = parent.get(n);
            }
            while (!stack.isEmpty()) {
                parent.put(stack.pop(), n);
            }
            return n;
        }

        public boolean isSameSet(Node n1, Node n2) {
            return find(n1) == find(n2);
        }
    }

    // 边的权值由小到大排序
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge e1, Edge e2) {
            return e1.weight - e2.weight;
        }
    }

}
