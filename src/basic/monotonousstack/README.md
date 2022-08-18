# 单调栈
>   ## 题目
>   + 单调栈实现  ACM模式：https://www.nowcoder.com/practice/2a2c00e7a88a498693568cef63a4b7bb
>   + 给定只包含正数的arr，子数组sub的累加和乘以sub最小值的结果最大的值为多少？
>     + // 本题可以在leetcode上找到原题  
        // 测试链接 : https://leetcode.com/problems/maximum-subarray-min-product/  
        // 注意测试题目数量大，要取模，但是思路和课上讲的是完全一样的  
        // 注意溢出的处理即可，也就是用long类型来表示累加和  
        // 还有优化就是，你可以用自己手写的数组栈，来替代系统实现的栈，也会快很多  
>       // https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class25/Code02_AllTimesMinToMax.java
>     + 