package basic.datastucture.bt.recursion;

// 二叉树整体不一定是搜索二叉树 但以某个节点为头的子树有可能是 返回所有可能的搜索二叉子树中间包含节点最多的那个子树的节点数目
public class MaxSubBSTSize {

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int data) {
            this.val = data;
        }
    }

    // 树型dp 二叉树递归套路  分两类讨论  与x节点有关和与x节点无关
    public static int maxSubBSTRecursion(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxBSTSubtreeSize;
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
        if (x == null) {
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int max = x.val;
        int min = x.val;
        int allSize = 1;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.max(min, rightInfo.min);
            allSize += rightInfo.allSize;
        }

        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSubtreeSize;
        }
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSubtreeSize;
        }
        int p3 = -1;
        boolean leftBST = leftInfo == null || (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
        boolean rightBST = rightInfo == null || (rightInfo.maxBSTSubtreeSize == rightInfo.allSize);
        if (leftBST && rightBST) {
            boolean leftMaxLessX = leftInfo == null || leftInfo.max < x.val;
            boolean rightMaxMoreX = rightInfo == null || rightInfo.min > x.val;
            if (leftMaxLessX && rightMaxMoreX) {
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }
        return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
    }



}
