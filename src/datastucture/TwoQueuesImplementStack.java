package datastucture;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TwoQueuesImplementStack {

    public static class TwoQueueStack<T> {
        private Queue<T> queue;
        private Queue<T> help;

        public TwoQueueStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        public void push(T value) {
            queue.offer(value);
        }

        public T pop() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        public T peek() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            // peek 不弹出元素给用户，存在help中间
            help.offer(ans);
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
        Stack<Integer> test = new Stack<>();
        int testTimes = 10000;
        int max = 1000;
        for (int i = 0; i < testTimes; i++) {
            if (myStack.isEmpty()) {
                if (!test.isEmpty()) {
                    System.out.println("Fucking fucked!");
                }
                int nums = (int) (Math.random() * max);
                myStack.push(nums);
                test.push(nums);
            } else {
                if (Math.random() < .25) {
                    int num = (int) (Math.random() * max);
                    myStack.push(num);
                    test.push(num);
                } else if (Math.random() < .5) {
                    if (!myStack.peek().equals(test.peek())) {
                        System.out.println("Fucking fucked!");
                        break;
                    }
                } else if (Math.random() < .75) {
                    if (!myStack.pop().equals(test.pop())) {
                        System.out.println("Fucking fucked!");
                        break;
                    }
                } else {
                    // euqals（对象内容是否一致）  和 !=（对象引用是否一致） 有何区别？ 为何这里用!= 而前边都用equals
                    if (myStack.isEmpty() != test.isEmpty()) {
                        System.out.println("Fucking fucked!");
                        break;
                    }
                }
            }

        }
        System.out.println("Finished!");

    }

}
