package datastucture;

public class ReverseList {
    public static class Node {
        public int value;
        public Node next;

        // 构造方法  在创建Node实例时一次性传入data完成初始化
        public Node(int data) {
            this.value = data;
        }
    }

    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            this.value = data;
        }
    }

    // 不要用诸如数组等容器来解题
    // 这样的题若是面试，则面试官想看到的就是有限几个变量实现链表反转，考察对它的深入理解
    public static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static DoubleNode reverseDoubleLinkedList(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }
        return pre;
    }


}
