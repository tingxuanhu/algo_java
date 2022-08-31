package basic.datastucture;

public class ReverseList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }

    public static class DoubleNode {
        public int value;
        public DoubleNode next;
        public DoubleNode last;

        public Node(int v) {
            value = v;
        }
    }

    //  head
    //   a    ->   b    ->  c  ->  null
    //   c    ->   b    ->  a  ->  null

    // 如果是单向链表 如何翻转
    public static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node next = null;

        while (head != null) {
            // 定义当前节点的下一个节点 这样当当前节点改变指向的时候 当前节点原来这个下一个节点仍然可以被抓住
            next = head.next;
            // 先处理当前节点的任务 --> 当前节点指向上一个节点
            head.next = pre;
            // 当前节点完成使命之后 上一个节点变成当前节点(准备被下一个节点指回来(当前节点变成下一个节点))
            pre = head;
            // head向后挪一位 准备重复循环
            head = next;
        }
        return pre; // head此时为null
    }


    // 如果是双向链表 如何翻转
    public static DoubleNode reverseDoubleLinkedList(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next = null;

        while (head != null) {
            next = head.next;
            // 注意不能调换顺序
            // 先处理当前节点
            head.next = pre;
            head.last = next;
            // 当前节点处理完 保证下一个节点能够找到当前节点(用pre存下来)
            pre = head;
            // 然后跳转到下一个节点 视为当前节点去循环
            head = next;
        }
        return pre;
    }

}
