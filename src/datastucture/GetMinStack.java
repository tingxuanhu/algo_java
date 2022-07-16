package datastucture;

import java.util.Stack;

// pop push getmin 均为O(1)
// 思维定势突破-->没有约定说只能用一个栈 --> 一个数据栈 一个最小栈
// (后边的用栈实现队列和用队列实现栈也分别使用两个来实现一个--比如考用队列实现深度优先或者用栈实现广度优先)
public class GetMinStack {

    public static class MyStack {
        // final与不final有何区别?  final class 基础类 不能被继承
        private final Stack<Integer> dataStack;
        private final Stack<Integer> minStack;

        public MyStack() {
            this.dataStack = new Stack<Integer>();
            this.minStack = new Stack<Integer>();
        }

        public void push(int value) {
            // 若是空栈则两个栈都放置一个相同的数
            if (this.dataStack.isEmpty()) {
                this.dataStack.push(value);
                this.minStack.push(value);
            }
            else if (value <= this.getmin()) {
                this.minStack.push(value);
            }
            else {
                this.minStack.push(this.getmin());
            }
            this.dataStack.push(value);
        }

        public int pop() {
            if (this.dataStack.isEmpty()) {
                throw new RuntimeException("The stack is empty");
            }
            this.minStack.pop();
            return this.dataStack.pop();
        }

        public int getmin() {
            if (this.minStack.isEmpty()) {
                throw new RuntimeException("The minStack is empty");
            }
            return this.minStack.peek();
        }

    }

    public static void main(String[] args) {
        MyStack stack1 = new MyStack();
        stack1.push(3);
        System.out.println(stack1.getmin());
        stack1.push(1);
        System.out.println(stack1.getmin());
        stack1.push(4);
        System.out.println(stack1.getmin());
        stack1.push(2);
        System.out.println(stack1.getmin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getmin());


    }


}
