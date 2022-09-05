package basic.datastucture.bt.recursion;

import java.util.ArrayList;

/* 递归地找搜索二叉树BST 四个原则:
1) 左子树是BST
2) 右子树是BST
3) 左子树收上来的最大值小于当前节点
4) 右子树收上来的最小值大于当前节点
3)和4) 对于左子树和右子树的需求是不同的 左子树只要最大值 右子树只要最小值 但为了递归玩得转 必须都要 统一入参

BST的经典结构是节点值不重复的 如果重复可以尝试着在节点中用列表往后串
规定经典搜索二叉树的值不重复  如果重复压在一起是为了在加入平衡性 旋转的时候 方便
否则旋转完之后相等值会左右都有了 不符合左边小右边大的规定
 */
public class IsBST {

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int data) {
            this.val = data;
        }
    }

    public static class Info {
        public boolean isBST;
        public int maxVal;
        public int minVal;

        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.maxVal = max;
            this.minVal = min;
        }
    }

    public static boolean isBSTRecursion(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    public static Info process(Node x) {
        if (x == null) {
            return null; // 不知道怎么设置空树的最小最大值就返回空,在上游再去处理
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int maxVal = x.val;
        int minVal = x.val;
        if (leftInfo != null) {
            maxVal = Math.max(maxVal, leftInfo.maxVal);
            minVal = Math.min(minVal, leftInfo.minVal);
        }
        if (rightInfo != null) {
            maxVal = Math.max(maxVal, rightInfo.maxVal);
            minVal = Math.min(minVal, rightInfo.minVal);
        }

        boolean isBST = true;
        if (leftInfo != null && (!leftInfo.isBST || leftInfo.maxVal >= x.val)) {
            isBST = false;
        }
        if (rightInfo != null && (!rightInfo.isBST || rightInfo.minVal <= x.val)) {
            isBST = false;
        }
        return new Info(isBST, maxVal, minVal);
    }

    // for comparison  中序遍历
    public static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        ArrayList<Node> arr = new ArrayList<>();
        process2(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return false;
            }
        }
        return true;
    }

    public static void process2(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        process2(head.left, arr);
        arr.add(head);
        process2(head.right, arr);
    }


    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBSTRecursion(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
