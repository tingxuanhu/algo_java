package datastucture.graph.minspanningtree;

import datastucture.graph.Edge;
import datastucture.graph.Graph;
import datastucture.graph.Node;

import java.util.*;

/* Prim算法   没有Kruskal简洁  注意考虑有没有森林
1 从任意节点出发
2 某个点加入被选中的点后 解锁这个点 找出所有新解锁的边
3 在所有解锁的边中选还未被选择的权值最小的(cur) 看是否成环
4 若cur加入成环  则考察解锁的其他边中最小的边  重复3
4 若cur加入不成环  则要当前边  将该边指向的节点加入选取的点中  重复2
 */
public class Prim {

    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(new EdgeComparator());
        // 看哪些点被解锁
        HashSet<Node> nodeSet = new HashSet<>();
        // 依次挑选的边放入res
        Set<Edge> res = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (Edge edge : node.edges) {
                    minHeap.add(edge);
                }
                while (!minHeap.isEmpty()) {
                    Edge edge = minHeap.poll();
                    Node toNode = edge.to;
                    if (!nodeSet.contains(toNode)) {
                        nodeSet.add(toNode);
                        res.add(edge);
                        for (Edge next : toNode.edges) {
                            minHeap.add(next);
                        }
                    }
                }
            }
            // break 确定是一个点就可以break  注释掉可以防止森林
        }
        return res;
    }


    // 边的权值由小到大排序
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge e1, Edge e2) {
            return e1.weight - e2.weight;
        }
    }



    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {

    }


}
