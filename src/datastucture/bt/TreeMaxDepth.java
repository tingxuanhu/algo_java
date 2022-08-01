package datastucture.bt;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// 求二叉树最宽的层数
//关键在于加入机制(flag)使得某一层结束时系统能够知晓
public class TreeMaxDepth {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int maxWidthWithoutMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        int max = 0;
        int curLevelNum = 0;
        Node curEnd = head; // 当前层，最右节点是谁
        Node nextEnd = null; // 下一层，最右节点是谁
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            curLevelNum++;
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            // 本层统计结束
            if (cur == curEnd) {
                max = Math.max(max, curLevelNum);
                curEnd = nextEnd;
                curLevelNum = 0;
            }
        }
        return max;
    }

    public static int maxWidthWithMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // key 在哪一层
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        int curLevel = 1;
        int curLevelNum = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int curNodeLevel = levelMap.get(cur);  //获知cur在哪一层
            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }
            if (curNodeLevel == curLevel) {
                curLevelNum++;
            } else {
                max = Math.max(max, curLevelNum);
                curLevel++;
                curLevelNum = 1;
            }
        }
        return Math.max(max, curLevelNum);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthWithMap(head) != maxWidthWithoutMap(head)) {
                System.out.println("Fucking fucked!");
            }
        }
        System.out.println("Finished!");

    }
}
