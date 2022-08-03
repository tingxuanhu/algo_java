package datastucture.bt.recursion;

// 二叉树整体不一定是搜索二叉树 但以某个节点为头的子树有可能是 返回所有可能的搜索二叉子树中间包含节点最多的那个子树的节点数目
public class MaxSubBSTSize {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static class Info {
        public int maxBSTSubtreeSize;
        public int allSize;
        public int max;
        public int min;

        public Info(int m, int a, int ma, int mi) {
            maxBSTSubtreeSize = m;
            allSize = a;
            max = ma;
            min = mi;
        }
    }

    public static Info process(Node x) {

    }



}
