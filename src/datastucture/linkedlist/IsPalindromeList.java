package datastucture.linkedlist;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.OutputStream;
import java.util.Stack;

public class IsPalindromeList {
    public static class Node {
        private int value;
        private Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = slow.next;
        slow.next = null;
        Node tmp = null;
        while (fast != null) {
            tmp = fast.next;
            fast.next = slow;
            slow = fast;
            fast = tmp;
        }
        //前后向中间靠拢 查看是否回文
        //需引入一个boolean变量记录回文与否而不能直接在接下来的while循环中遇到值不一致的情况直接返回false，
        //原因在于，在遍历完后还要复原原来的链表串联顺序，循环过程中间不能直接跳出
       boolean res = true;
        fast = head; // 记录下原链表的第一个节点
        tmp = slow; // slow此时指向原链表最后一个节点，记录下来
        while (fast != null && slow != null) {
            if (fast.value != slow.value) {
                res = false;
                break;
            }
            fast = fast.next;
            slow = slow.next;
        }
        // 复原链表
        slow = tmp.next;
        tmp.next = null;
        while (slow != null) {
            fast = slow.next;
            slow.next = tmp;
            tmp = slow;
            slow = fast;
        }
        return res;
    }

    public static boolean comp(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static void printLinkedList(Node head) {
        System.out.println("Linked list: ");
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;
        printLinkedList(head);
        System.out.println(isPalindrome(head) + " | ");
        System.out.println(comp(head) + " | ");

        head = new Node(1);
        printLinkedList(head);
        System.out.println(isPalindrome(head) + " | ");
        System.out.println(comp(head) + " | ");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.println(isPalindrome(head) + " | ");
        System.out.println(comp(head) + " | ");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.println(isPalindrome(head) + " | ");
        System.out.println(comp(head) + " | ");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.println(isPalindrome(head) + " | ");
        System.out.println(comp(head) + " | ");
    }

}
