# 线段树
## 线段树是一种支持区间范围整体修改或查询的数据结构 --> 区间修改树

## 基本要求：给定数据arr，在O(lgN)内实现三个方法

- void add(int L, int R, int V, arr)  --> arr[L..R]每个数加上V
- void update(int L, int R, int V, arr)  -->  arr[L..R]每个数变成V
- int sum(int L, int R)  --> 返回arr[L..R]累加和

## 线段树解决的问题范畴及注意点：
- 大范围信息可以由左右两侧信息加工出来，而不必遍历左右两个子范围的具体状况
- 假设：下标从1开始  0位置弃而不用