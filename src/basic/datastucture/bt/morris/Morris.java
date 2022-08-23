package basic.datastucture.bt.morris;

public class Morris {

    public static class Node {
        private int val;
        private Node left;
        private Node right;

        public Node(int v) {
            this.val = v;
        }
    }

    public static void morris(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        // 当遍历到最后一个节点时跳出while
        while (cur != null) {
            mostRight = cur.left;
            // 有左树
            if (mostRight != null) {
                // 确保是找到了左树真正的最后一个节点(最右节点) --> 既不能是空 也不能是指向cur  这两种情况都不是左树真正的最右节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 若从while中跳出，则证明mostRight必然是左树上的最右节点
                if (mostRight.right == null) {      // 第一次碰到
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {  // 此时 mostRight.right = cur;
                    mostRight.right = null;
                }
            }
            // 没有左树
            cur = cur.right;
        }
    }

}
