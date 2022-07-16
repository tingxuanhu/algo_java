package datastucture;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

public class DoubleEndsQueueToStackAndQueue {

    public static class Node<T> {
        private T value;
        private Node<T> last;
        private Node<T> next;

        // 泛型的构造方法不是用尖括号
        public Node(T data) {
            this.value = data;
        }
    }

    // 双向链表实现一个双端队列
    public static class DoubleEndsQueue<T> {
        private Node<T> head;
        private Node<T> tail;

        public void pushFromHead(T value) {
            Node<T> cur = new Node<>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        public void pushFromBottom(T value) {
            Node<T> cur = new Node<T>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.last = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        public T popFromHead() {
            if (head == null) {
                return null;
            }
            Node<T> cur = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                cur.next = null;
                head.last = null;
            }
            return cur.value;

        }

        public T popFromBottom() {
            if (head == null) {
                return null;
            }
            Node<T> cur = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return cur.value;
        }

        public boolean isEmpty() {
            return head == null;
        }

    }

    public static class MyStack<T> {
        private DoubleEndsQueue<T> stack;

        public MyStack(T value) {
            stack = new DoubleEndsQueue<T>();
        }

        public void push(T value) {
            stack.pushFromHead(value);
        }

        public T pop() {
            return stack.popFromHead();
        }

        public boolean isEmpty() {
            return stack.isEmpty();
        }

    }

    public static class MyQueue<T> {
        private final DoubleEndsQueue<T> queue;

        public MyQueue(T value) {
            queue = new DoubleEndsQueue<T>();

        }

        public void offer(T value) {
            queue.pushFromHead(value);
        }

        public T poll() {
            return queue.popFromBottom();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
            return false;
        }
        return o1.equals(o2);
    }

    public static void main(String[] args) {
        int oneTestDataNum = 100;
        int value = 10000;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            MyStack<Integer> myStack = new MyStack<Integer>();
            MyQueue<Integer> myQueue = new MyQueue<Integer>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();

            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myStack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!isEqual(myStack.pop(), stack.pop())) {
                            System.out.println("Fucking fucked!");
                            break;
                        }
                    }
                }

                if (queue.isEmpty()) {
                    queue.offer(nums);
                    myQueue.offer(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.offer(nums);
                        queue.offer(nums);
                    } else {
                        if (!isEqual(myQueue.poll(), queue.poll())) {
                            System.out.println("Fucking fucked!");
                            break;
                        }
                    }
                }

            }
        }
        System.out.println("Success!");

    }

}










