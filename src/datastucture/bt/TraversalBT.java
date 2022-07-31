package datastucture.bt;

import java.util.LinkedList;
import java.util.Queue;

public class TraversalBT {

    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 递归打印(先序 中序 后序)
    public static void process(Node head) {
        if (head == null) {
            return;
        }
        // 此处打印为先序遍历  System.out.println(head.value);  递归序为首次碰到就打印    头 左 右
        process(head.left);
        // 此处打印为中序遍历  System.out.println(head.value);  递归序第二次碰到时打印    左 头 右
        process(head.right);
        // 此处打印为后序遍历  System.out.println(head.value);  递归序第三次碰到时打印    左 右 头
    }

    // 层序遍历
    public static void levelTraversal(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value + " ");
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }

}
