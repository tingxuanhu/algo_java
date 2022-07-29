package datastucture.linkedlist;

public class SmallEqualBigger {
    /*  把单向链表按照某值划分成左边小、中间相等、右边大的形式
    1) 把链表放入数组中，在数组上partition(笔试)
    2) 分成小中大三部分，再把各个部分串联起来(面试)  有限几个变量实现 不用额外数组
     */
    public static class Node {
        private int value;
        private Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // 申请六个节点  小头 小尾  等头  等尾  大头  大尾(大尾存在是保证稳定性的)
    public static Node linkedListPartition(Node head, int pivot) {
        Node sH = null; // small head
        Node sT = null; // small tail
        Node eH = null; // equal head
        Node eT = null; // equal tail
        Node mH = null; // big head
        Node mT = null; // big tail
        Node tmp = null; // save head.next
        while (head != null) {
            tmp = head.next;
            head.next = null; // remove head from the original linkedlist
            if (head.value < pivot) {
                if (sH == null) {
                    sH = head;
                } else {
                    sT.next = head;
                }
                sT = head;
            } else if (head.value == pivot) {
                if (eH == null) {
                    eH = head;
                } else {
                    eT.next = head;
                }
                eT = head;
            } else {
                if (mH == null) {
                    mH = head;
                } else {
                    mT.next = head;
                }
                mT = head;
            }
            head = tmp;
        }
        // 串接小尾和等头以及等尾和大头


    }

}
