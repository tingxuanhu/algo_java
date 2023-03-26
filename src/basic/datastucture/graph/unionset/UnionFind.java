package basic.datastucture.graph.unionset;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

// 并查集做了两处优化 都是为了减少链表长度 实现O(1)查询和合并的均摊复杂度
// 第一是用表代替了指针存储直系父子关系
// 第二是往上寻找代表节点的过程中间,在完成返回代表节点之前,把直系链表上的节点直接挂载在代表节点上

// 这个代码是看看并查集的工作原理 但实际是用数组来代替栈实现find过程  --->  用数组自己实现一下  参考其他unionset目录下文件代码
public class UnionFind {

    public static class Node<V> {
        public V value;

        public Node(V v) {
            this.value = v;
        }
    }

    public static class UnionSet<V> {
        public HashMap<V, Node<V>> nodes;   // 样本对应自己包完的那个圈
        public HashMap<Node<V>, Node<V>> parents;    // 用parents这张表替代父子关系需要用到的指针
        public HashMap<Node<V>, Integer> sizeMap;    // 记录代表节点这个集合的大小,只记录代表节点，其他节点不用记录

        // 初始化的时候把所有样本构成的列表给进来,用以初始化三个HashMap
        public UnionSet(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V v : values) {
                Node<V> node = new Node<>(v);
                nodes.put(v, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V a, V b) {
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public int sets() {
            return sizeMap.size();
        }

    }

}
