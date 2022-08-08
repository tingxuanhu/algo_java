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
        // 解锁的边进入小根堆 按照权值小到大放置
        PriorityQueue<Edge> unlockedEdges = new PriorityQueue<>(new EdgeComparator());
        // 看哪些点被解锁(考察过的点)
        HashSet<Node> nodeSet = new HashSet<>();
        // 依次挑选的边放入res
        Set<Edge> res = new HashSet<>();
        // 这个for循环是考虑是否不连通 存在分片区域  如果整个图联通可以省略掉这个for循环
        for (Node node : graph.nodes.values()) {
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (Edge edge : node.edges) {
                    // 这里的for循环和底下的for循环会重复把边放进优先级队列中 但不影响结果
                    // 即便被重复处理了 nodeSet中间已经处理了已经放入的点 处理也将直接被跳过 也只是增加一些常数时间
                    unlockedEdges.add(edge);
                }
                while (!unlockedEdges.isEmpty()) {
                    Edge edge = unlockedEdges.poll();
                    Node toNode = edge.to;
                    if (!nodeSet.contains(toNode)) {
                        nodeSet.add(toNode);
                        res.add(edge);
                        for (Edge next : toNode.edges) {
                            unlockedEdges.add(next);
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
