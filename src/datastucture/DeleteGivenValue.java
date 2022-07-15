package datastucture;

// 给定具体int值删除对应链表中间相同值的位置，可能给定的值是头节点的值，所以要预估换头的可能
// 因此返回值不能是void类型
public class DeleteGivenValue {
    public static class Node{
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node removeValue(Node head, int num) {
        // 链表题目最恶心的边界条件
        // 若是开头很多个都是待删除的值，那么需要把头一直往后挪  直至第一个不需要删除的节点处或者为空(链表中全是num)
        while (head != null) {
            if (head.value != num) {
                break;
            }
            head = head.next;
        }
        Node pre = head;
        Node cur = head;

        // 向后遍历链表直到走完全部链表
        while (cur != null) {
            // 要删除的情况
            if (cur.value == num) {
                pre.next = cur.next;
            }
            else {
                pre = cur;
            }
            cur = cur.next;
        }

        return head;
    }

    // 随机生成单链表结构
    public static Node generateRandomLinkedList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }

        size--;  // 这里为什么--
        Node head = new Node((int) (Math.random() * (value + 1)));  // head 找出来之后固定不动
        Node pre = head;
        while (size != 0) {
            Node cur = new Node((int) (Math.random() * (value + 1)));
            pre.next = cur;
            pre = cur;
            size--;
        }
        return head;
    }

}
