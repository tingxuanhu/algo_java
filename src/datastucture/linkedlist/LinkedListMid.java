package datastucture.linkedlist;

public class LinkedListMid {
    /*  给定头节点  实现如下四个功能  不要放到容器里做  单链表完成   完成边界调整
    1)  奇数个节点返回唯一的中点,偶数个节点则返回前一个中点;
    2)  奇数个节点返回唯一的中点,偶数个节点则返回后一个中点;
    3)  奇数个节点返回唯一的中点的前一个节点,偶数个节点则返回前一个中点的前一个;
    4)  奇数个节点返回唯一的中点的前一个节点,偶数个节点则返回后一个中点的前一个;
     */

    public static class Node {
        private int value;
        private Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node midOrUpMidNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        // 否则的话 链表就一定超过3个点
        Node slow = head.next;
        Node fast = head.next.next;

    }


}
