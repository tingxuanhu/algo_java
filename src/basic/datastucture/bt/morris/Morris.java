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
                // 确保是找到了左树真正的最后一个节点(最右节点) --> 指向空 或者是指向cur（之前修改过）  这两种情况都是左树真正的最右节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 若从while中跳出，则证明mostRight必然是左树上的最右节点
                if (mostRight.right == null) {
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

    // morris实现前序遍历
    // morris序中首次抵达就打印(无左树者或者有左树者第一次被碰到)
    public static void morrisPre(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            // 有左子树
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    System.out.println(cur.val + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {   // mostRight.right == cur
                    mostRight.right = null;
                }
            } else {  // 无左子树 只会碰一次
                System.out.println(cur.val + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }

    // morris实现中序遍历
    // 无子树者直接打印（而后向右移动），有子树者当要向右移动时打印  --> 合并为当向右移动时打印
    public static void morrisIn(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {   // mostRight.right == cur
                    mostRight.right = null;
                }
            }
            System.out.println(cur.val + " ");
            cur = cur.right;
            System.out.println();
        }
    }


    // morris实现后序遍历
    // 左子树的右边界可以切分完整棵树，当到达某左树最右节点时，逆序打印左子树右边界，剩下整棵树的右边界最后来反转链表逆序打印
    public static void morrisPos(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {   // mostRight.right == cur
                    mostRight.right = null;
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        printEdge(head);
        System.out.println();
    }

    // 逆序打印(翻转链表)来输入节点左树的右边界   重新体会
    public static void printEdge(Node head) {
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null) {
            System.out.println(cur.val + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    // 翻转链表具体实现
    public static Node reverseEdge(Node from) {
        Node pre = null;
        Node next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }

    // 加上对数器 验证一下答案


    // morris应用？未完待续

}
