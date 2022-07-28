package datastucture.bt;

public class TraversalBT {

    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 递归打印
    public static void process(Node head) {
        if (head == null) {
            return;
        }
        // 此处打印为先序遍历  System.out.println(head.value);
        process(head.left);
        // 此处打印为中序遍历  System.out.println(head.value);
        process(head.right);
        // 此处打印为后序遍历  System.out.println(head.value);
    }

}
