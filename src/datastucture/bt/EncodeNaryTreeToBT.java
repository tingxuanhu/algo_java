package datastucture.bt;

import heap.MergeKSortedList;

import java.util.List;

// 本题测试链接：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
public class EncodeNaryTreeToBT {
    // 定义多叉树节点 leetcode提交时不提交这个类
    public static class Node {
         public int val;
         public List<Node> children;

         public Node(int _data) {
             this.val = _data;
         }

         public Node(int _val, List<Node> _children) {
             val = _val;
             children = _children;
         }
     }

    // 定义二叉树节点 leetcode提交时不提交这个类
     public static class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;

         TreeNode(int x) {
             val = x;
         }
     }

     class Codec{
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }
            TreeNode head = new TreeNode(root.val);
            head.left = en(root.children);
            return head;
        }

        // dfs递归地把多叉树的孩子放在二叉树节点左子树的右边界收集 (也可改成右树左边界 限制一边即可 也方便回转)
        private TreeNode en(List<Node> children){
            TreeNode head = null;
            TreeNode cur = null; // 定义cur而不直接在head上做指针变化是因为要返回固定的head节点
            for (Node child : children) {
                TreeNode node = new TreeNode(child.val);
                if (head == null) {
                    head = node;
                } else {
                    cur.right = node;
                }
                cur = node;
                cur.left = en(child.children);
            }
            return head;
        }



    }




}
