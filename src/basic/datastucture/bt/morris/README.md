# morris遍历
二叉树遍历的基本情况不论递归或是非递归
- 时间复杂度O(N)
- 空间复杂度O(h) --> 递归栈存入最多树的高度个元素

morris遍历优化了空间复杂度 使之在O(1)内完成
- 笔试坚决不用
- 面试优先考虑

morris遍历流程：
- 当前节点cur，一开始cur来到整棵树的头节点
  - 若是当前cur无左树，cur = cur.right;
  - 若是当前cur有左子树，找到左树最右节点(.right.right下去到指向null) mostRight
    - mostRight的右指针若是指向空的（第一次来到）
      - mostRight.right = cur;   cur = cur.left;
    - mostRight的右指针若是指向当前节点（节点做了上面这步导致的，是第二次来到）
      - mostRight.right = null;   cur = cur.right;
- morris遍历序中 有左树的必然来到两次，否则来一次