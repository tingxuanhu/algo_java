package basic.datastucture.bt;

import java.util.LinkedList;
import java.util.Queue;

// 序列化：把树变成(硬盘中的文件形式)字符串; 反序列化就是重建出树的样子
// 二叉树可以通过先序、后续或者层序遍历完成序列化和反序列化,但中序遍历(左头右)会存在二义性
public class SerializeAndRebuildTree {
    public static class Node {
        private final int value;
        private Node left;
        private Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 先序遍历序列化
    public static Queue<String> preSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        preProcess(head, ans);
        return ans;
    }

    public static void preProcess(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            preProcess(head.left, ans);
            preProcess(head.right, ans);
        }
    }

    // 先序遍历反序列化  返回头节点
    public static Node buildTreeByPreQueue(Queue<String> preList) {
        // list == null 意味着堆中没有存放list的地址 物理上不存在 (list还未初始化 调用会抛出空指针异常);
        // list.size() == 0 意味着申请了堆中的地址 但还没来得及放元素 而list的长度随着元素长度的变化而变化 暂时为0
        /*
        我有一个空的水杯（list），而你没有（null）；我的size是0；
        你想要装水去买个水杯（new ArrayList();），而我可以直接装水（list.add(water)）；
        若你没有杯子（list），直接倒水（water），水就会流出来（NullPointerException）；
        因此判断list时，经常连用list != null && list.size > 0或者list != null && list.size != 0。
         */
        if (preList == null || preList.size() == 0) {
            return null;
        }
        return preBuildProcess(preList);
    }

    public static Node preBuildProcess(Queue<String> preList) {
        String value = preList.poll();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.parseInt(value));
        head.left = preBuildProcess(preList);
        head.right = preBuildProcess(preList);
        return head;
    }

    // 层序遍历序列化
    public static Queue<String> levelSerial(Node head) {
        // ans队列与先序遍历过程记录结果的作用类似   而queue则是层序遍历过程用到的数据结构
        Queue<String> ans = new LinkedList<>();
        if (head == null) {
            ans.add(null);
        } else {
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);
            ans.add(String.valueOf(head.value));
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                if (cur.left != null) {
                    queue.add(cur.left);
                    ans.add(String.valueOf(cur.left.value));
                } else {
                    ans.add(null);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                    ans.add(String.valueOf(cur.right.value));
                } else {
                    ans.add(null);
                }
            }
        }
        return ans;
    }

    // 层序遍历反序列化
    public static Node buildTreeByLevelQueue(Queue<String> levelList) {
        if (levelList == null || levelList.size() == 0) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        Node head = generateNodeOrNull(levelList.poll());
        if (head != null) {
            queue.add(head);
        }
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            cur.left = generateNodeOrNull(levelList.poll());
            cur.right = generateNodeOrNull(levelList.poll());
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        return head;
    }

    public static Node generateNodeOrNull(String val) {
        if (val == null) {
            return null;
        } else return new Node(Integer.parseInt(val));
    }

    // 后序遍历序列化(反序列化仿照先序改出来 很简单)
    public static Queue<String> posSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        postProcess(head, ans);
        return ans;
    }

    public static void postProcess(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            postProcess(head.left, ans);
            postProcess(head.right, ans);
            ans.add(String.valueOf(ans));
        }
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generateRandomNode(1, maxLevel, maxValue);
    }

    public static Node generateRandomNode(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generateRandomNode(level + 1, maxLevel, maxValue);
        head.right = generateRandomNode(level + 1, maxLevel, maxValue);
        return head;
    }

    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }


    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> level = levelSerial(head);
            Node preBuild = buildTreeByPreQueue(pre);
            Node levelBuild = buildTreeByLevelQueue(level);
            if (!isSameValueStructure(preBuild, levelBuild)) {
                System.out.println("Fucking fucked!");
                break;
            }
        }
        System.out.println("Finished!");
    }

}
