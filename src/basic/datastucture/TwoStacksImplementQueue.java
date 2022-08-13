package basic.datastucture;

import java.util.Stack;

// 用两个栈实现队列或者用两个队列实现一个栈---- 图的广度优先如何用栈实现？深度优先如何用队列实现？
public class TwoStacksImplementQueue {

    public static class TwoStackQueue {
        private final Stack<Integer> pushStack;
        private final Stack<Integer> popStack;

        // 这里不能直接定义成new ？ 还要设计构造器？
        public TwoStackQueue() {
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }

        public void add(int value) {
            pushStack.push(value);
            pushToPop();
        }

        public int poll() {
            if (popStack.isEmpty() && pushStack.isEmpty()) {
                throw new RuntimeException("No elements in queue");
            }
            pushToPop();
            return popStack.pop();
        }

        public int peek() {
            if (popStack.isEmpty() && pushStack.isEmpty()) {
                throw new RuntimeException("No elements in queue");
            }
            pushToPop();
            return popStack.peek();
        }

        /* 倒入pop栈的数据有两个要求
        1--pop栈没空的时候先倒出
        2--pop栈空的时候一次性把push栈中元素倒出
         */
        private void pushToPop() {
            if (popStack.isEmpty()) {
                while (!pushStack.isEmpty()) {
                    int trans = pushStack.pop();
                    popStack.push(trans);
                }
            }
        }

        public static void main(String[] args) {
            TwoStackQueue testQueue = new TwoStackQueue();
            testQueue.add(1);
            testQueue.add(2);
            testQueue.add(3);
            System.out.println(testQueue.peek());
            System.out.println(testQueue.poll());
            System.out.println(testQueue.peek());
            System.out.println(testQueue.poll());
            System.out.println(testQueue.peek());
            System.out.println(testQueue.poll());
        }

    }

}
