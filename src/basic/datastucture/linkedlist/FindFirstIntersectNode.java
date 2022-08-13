package basic.datastucture.linkedlist;

public class FindFirstIntersectNode {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }

    // 看是否有环,如果无环返回null,若有环则返回第一个入环节点
    public static Node getLoopNode(Node head) {
        if (head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            // 无环链表的情况
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    // 探查两个无环链表
    public static Node noLoop(Node head1, Node head2) {
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            cur1 = cur1.next;
            n++;
        }
        while (cur2.next != null) {
            cur2 = cur2.next;
            n--;
        }
        // 比较两链表结尾的地址是否相等
        if (cur1 != cur2) {
            return null;
        }
        cur1 = n > 0 ? head1 : head2;  // 长度长的做cur1
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n > 0) {
            cur1 = cur1.next;
            n--;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    // 探查两个有环链表
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        // 若loop1 == loop2,则两链表在同一处入环,考察入环前相交还是正好在入环处相交(入环前是否有重合)
        // 把入环节点当成终止节点,重复上面noLoop的操作找一下相交节点在哪里
        if (loop1 == loop2) {
            return noLoop(head1, head2);
        } else {  // 此时两链表入环节点不一致,考察在环内相交还是不相交(不相交则证明两环为独立关系)
            Node cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6

        System.out.println(getIntersectNode(head1, head2).value);
    }

}
