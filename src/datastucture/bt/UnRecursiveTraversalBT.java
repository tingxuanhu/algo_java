package datastucture.bt;

import java.util.Stack;

public class UnRecursiveTraversalBT {

    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /*  先序遍历非递归
    头节点不由分说压栈
    有右压入右 有左压入左   先右再左
     */
    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<Node>();
        stack.push(head);
        while (!stack.isEmpty()) {
            head = stack.pop();
            System.out.print(head.value + " ");
            if (head.right != null) {
                stack.push(head.right);
            }
            if (head.left != null) {
                stack.push(head.left);
            }
        }
        System.out.println("pre-order traversal finished!");
    }

    public static void in(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {   // head == null
                head = stack.pop();
                System.out.print(head.value + " ");
                head = head.right;
            }
        }
        System.out.println("in-order traversal finished!");
    }


    public static void post(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> tempStack = new Stack<>();
        Stack<Node> outputStack = new Stack<>();
        tempStack.push(head);

        while (!tempStack.isEmpty()) {
            head = tempStack.pop();
            outputStack.push(head);
            if (head.left != null) {
                tempStack.push(head.left);
            }
            if (head.right != null) {
                tempStack.push(head.right);
            }
        }

        while (!outputStack.isEmpty()) {
            System.out.print(outputStack.pop().value + " ");
        }
        System.out.println("post-order traversal finished!");
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        post(head);
        System.out.println("========");
    }

}
