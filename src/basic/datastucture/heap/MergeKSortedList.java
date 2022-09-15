package basic.datastucture.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedList {

    public static class ListNode {
        private final int value;
        private ListNode next;

        public ListNode(int value) {
            this.value = value;
        }
    }

    public static class ListNodeComparator implements Comparator<ListNode> {
        @Override
        public int compare(ListNode lhs, ListNode rhs) {
            return lhs.value - rhs.value;
        }
    }

    public static ListNode MergeKList(ListNode[] lists) {
        if (lists == null) {
            return null;
        }

        PriorityQueue<ListNode> heap = new PriorityQueue<>(new ListNodeComparator());

        for (ListNode list : lists) {
            if (list != null) {
                heap.add(list);
            }
        }
        if (heap.isEmpty()) {
            return null;
        }

        ListNode head = heap.poll();
        ListNode pre = head;

        if (pre.next != null) {
            heap.add(pre.next);
        }

        while (!heap.isEmpty()) {
            ListNode cur = heap.poll();
            pre.next = cur;
            pre = cur;
            if (cur.next != null) {
                heap.add(cur.next);
            }
        }
        return head;
    }

}








