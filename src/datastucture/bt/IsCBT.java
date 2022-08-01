package datastucture.bt;

import java.util.LinkedList;
import java.util.List;

public class IsCBT {

    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static boolean isCBT1(Node head) {
        if (head == null) {
            return true; // 空树算完全二叉树
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(head);

        boolean vacantChild = false;  // 是否碰到过左右孩子不双全的节点
        while (!queue.isEmpty()) {
            head = queue.poll();
            Node l = head.left;
            Node r = head.right;

            // 两个判断主逻辑可以敲定为false
            // 第一 有右孩子没有左孩子
            // 第二 遇到过左右孩子不双全的节点 后面又发现了非叶子节点
            if ((l == null && r != null) || (vacantChild && (l != null || r != null))) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (l == null || r == null) {
                vacantChild = true;
            }
        }
        return true;
    }


}
