package basic.datastucture.graph;

import java.util.ArrayList;

public class Node {

    public int value;
    public int in;  // 入度
    public int out;  // 出度
    public ArrayList<Node> nexts;  // 从当前node出发能找到的直接邻居
    public ArrayList<Edge> edges;  // 从当前node出发能找到的直接的边

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
