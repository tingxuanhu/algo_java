package basic.datastucture.linkedlist;

import java.util.HashMap;

// 测试链接 : https://leetcode.com/problems/copy-list-with-random-pointer/

public class CopyListWithRandom {
    public static class Node {
        private int value;
        private Node next;
        private Node rand;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node copyListWithHashMap(Node head) {
        HashMap<Node, Node> copyMap = new HashMap<Node, Node>();  // 老节点为key,克隆(新)节点为value
        Node cur = head;
        // 老节点拷贝到新节点
        while (cur != null) {
            copyMap.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        cur = head;
        // 设置新节点的next和rand指针
        while (cur != null) {
            copyMap.get(cur).next = copyMap.get(cur.next);
            copyMap.get(cur).rand = copyMap.get(cur.rand);
            cur = cur.next;
        }
        return copyMap.get(head);
    }

    public static Node copyListWithoutHashMap(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;
        // 从结构上构造一一对应(新节点插在老节点下一个) 从而省去用哈希表记录新老节点对应关系 完成rand和next指针挂载的必要性
        // 1 -> 2  ==>  1 -> 1' -> 2
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.value);  // 把新节点挂载在原来的老节点之间
            cur.next.next = next;
            cur = next;
        }
        // 一对一对地设置rand节点(保证rand的顺序不用再修改)
        cur = head;
        Node copy = null;
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            copy.rand = cur.rand != null ? cur.rand.next : null;
            cur = next;
        }
        // 分离新老节点next方向上的串联关系
        cur = head;
        Node res = head.next;  // 新节点剥离后的头节点
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            cur.next = next;
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }

}
