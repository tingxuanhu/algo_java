package datastucture;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DoubleEndsQueueToStackAndQueue {

   public static class Node<T> {
       public T value;
       public Node<T> last;
       public Node<T> next;

       // 泛型的构造方法不用带尖括号
       public Node(T data) {
           value = data;
       }
   }

   // 双向链表实现一个双端队列
   public static class DoubleEndsQueue<T> {
       public Node<T> head;
       public Node<T> tail;

       // 从前加 尾部节点不动
       public void addFromHead(T value) {
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

       // 从后加 头节点不动
       public void addFromBottom(T value) {
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

       // 从头弹出节点
       public T popFromHead() {
           // 队列中没有元素
           if (head == null) {
               return null;
           }
           // 要弹出的元素交代一下 -- cur
           Node<T> cur = head;
           // 队列中只剩一个元素
           if (head == tail) {
               head = null;
               tail = null;
           }
           else {
               head = head.next;
               // 下面这两步是否有先后顺序要求？
               head.last = null;
               cur.next = null;
           }
           return cur.value;
       }


       // 从尾弹出节点
       public T popFromBottom() {
           if (head == null) {
               return null;
           }
           Node<T> cur = head;
           if (head == tail) {
               head = null;
               tail = null;
           }
           else {
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

       public MyStack() {
           stack = new DoubleEndsQueue<T>();
       }

       public void push(T value) {
           stack.addFromHead(value);
       }

       public T pop() {
           return stack.popFromHead();
       }

       public boolean isEmpty() {
           return stack.isEmpty();
       }

   }

    public static class MyQueue<T> {
       private DoubleEndsQueue<T> queue;

       public MyQueue() {
           queue = new DoubleEndsQueue<T>();
       }

       public void push(T value) {
           queue.addFromHead(value);
       }

       public T poll() {
           return queue.popFromBottom();
       }

       public boolean isEmpty() {
           return queue.isEmpty();
       }

    }

    // test
    public static boolean isEqual(Integer o1, Integer o2) {
       if (o1 == null && o2 == null) {
           return true;
       }
       if (o1 == null && o2 != null) {
           return false;
       }
       if (o1 != null && o2 == null) {
            return false;
       }
       return o1.equals(o2);
    }

    public static void main(String[] args) {
       int dataNumOneEpoch = 100;
       int value = 100;
       int testTime = 100000;
       for (int i = 0; i < testTime; i++) {
           MyStack<Integer> myStack = new MyStack<>();
           MyQueue<Integer> myQueue = new MyQueue<>();
           Stack<Integer> stack = new Stack<>();
           // LinkedList类实现了Queue接口，因此我们可以把LinkedList当成Queue来用。
           Queue<Integer> queue = new LinkedList<>();
           // Queue ----
           // queue.poll() --> 返回第一个元素，并在队列中删除
           // queue.peek() --> 返回第一个元素
           // queue.offer(T) --> 添加元素

           for (int j = 0; j < dataNumOneEpoch; j++) {
               int num = (int) (Math.random() * (value));

               // 测stack
               if (stack.isEmpty()) {
                   myStack.push(num);
                   stack.push(num);
               } else {
                   if (Math.random() < 0.5) {
                       myStack.push(num);
                       stack.push(num);
                   } else {
                       if (!isEqual(myStack.pop(), stack.pop())) {
                           System.out.println("Fucking fucked!");
                       }
                   }
               }

               // 测queue
               if (queue.isEmpty()) {
                   myQueue.push(num);
                   queue.offer(num);
               } else {
                   if (Math.random() < 0.5) {
                       myQueue.push(num);
                       queue.offer(num);
                   } else {
                       if (!isEqual(myQueue.poll(), queue.poll())) {
                           System.out.println("Fucking fucked!");
                       }
                   }
               }
           }
           System.out.println("Finished!");
       }

    }

}
