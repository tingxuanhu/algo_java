package basic.datastucture.graph;

// 图的转化接口函数举例
public class GraphGenerator {
    // 不妨假设用户喂进来的数据是 N * 3的矩阵--> 形式上表达为 [weight, from节点上的值, to节点上的值]
    public static Graph createGraph(Integer[][] matrix){
        Graph graph = new Graph();
        // 遍历每一条边 matrix[i]
        for (int i = 0; i < matrix.length; i++) {
            Integer weight = matrix[i][0];
            Integer from = matrix[i][1];
            Integer to = matrix[i][2];
            // 处理Graph的Node
            // 建立from节点
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            // 建立to节点
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }

            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            fromNode.nexts.add(toNode);
            // 若算法不要求入度出度 可以把这两项阉割
            fromNode.out++;
            toNode.in++;

            // 处理Graph的边
            // 若算法不要求与权值相关 可以阉割掉
            Edge newEdge = new Edge(weight, fromNode, toNode);
            fromNode.edges.add(newEdge);
            graph.edges.add(newEdge);
        }
        return graph;
    }


}
