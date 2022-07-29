package datastucture.linkedlist;

import java.util.ArrayList;

public class LinkedListMid {
    /*  给定头节点  实现如下四个功能  不要放到容器里做  单链表完成   完成边界调整
    1)  奇数个节点返回唯一的中点,偶数个节点则返回前一个中点;
    2)  奇数个节点返回唯一的中点,偶数个节点则返回后一个中点;
    3)  奇数个节点返回唯一的中点的前一个节点,偶数个节点则返回前一个中点的前一个;
    4)  奇数个节点返回唯一的中点的前一个节点,偶数个节点则返回后一个中点的前一个;
     */

    public static class Node {
        private final int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node midOrUpMid(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static Node midOrDownMid(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node slow = head.next;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static Node midOrUpMidPre(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static Node midOrDownMidPre(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        if (head.next.next == null) {
            return head;
        }
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static ArrayList<Node> fillArray(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr;
    }

    public static Node comp1(Node head) {
        ArrayList<Node> arr = fillArray(head);
        return arr.get((arr.size() - 1) / 2);
    }

    public static Node comp2(Node head) {
        ArrayList<Node> arr = fillArray(head);
        return arr.get(arr.size() / 2);
    }

    public static Node comp3(Node head) {
        ArrayList<Node> arr = fillArray(head);
        return arr.get((arr.size() - 3) / 2);
    }

    public static Node comp4(Node head) {
        ArrayList<Node> arr = fillArray(head);
        return arr.get((arr.size() - 2) / 2);
    }

    public static void main(String[] args) {
        Node test = null;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
        test.next.next.next.next.next.next.next.next = new Node(8);

        Node ans1 = null;
        Node ans2 = null;

        ans1 = midOrUpMid(test);
        ans2 = comp1(test);
        System.out.println(ans1 != null ? ans1.value : "null");
        System.out.println(ans2 != null ? ans2.value : "null");

        ans1 = midOrDownMid(test);
        ans2 = comp2(test);
        System.out.println(ans1 != null ? ans1.value : "null");
        System.out.println(ans2 != null ? ans2.value : "null");

        ans1 = midOrUpMidPre(test);
        ans2 = comp3(test);
        System.out.println(ans1 != null ? ans1.value : "null");
        System.out.println(ans2 != null ? ans2.value : "null");

        ans1 = midOrDownMidPre(test);
        ans2 = comp4(test);
        System.out.println(ans1 != null ? ans1.value : "null");
        System.out.println(ans2 != null ? ans2.value : "null");
    }

}





