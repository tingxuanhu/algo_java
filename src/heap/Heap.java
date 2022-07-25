package heap;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Heap {

    public static class MyMaxHeap {
        private final int limit;
        private final int[] heap;
        private int heapSize;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        public void push(int value) {
            if (heapSize == limit) {
                throw new IllegalStateException("Heap size exceeded");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        // 新加进来的数，现在停在了index位置  依次往上移动
        public void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public int pop() {
            int head = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return head;
        }

        public void heapify(int[] arr, int index, int heapSize) {
            int left = 2 * index + 1;
            while (left < heapSize) {
                int largest = left + 1 < heapSize && arr[left] < arr[left + 1] ? left + 1 : left;
                largest = arr[largest] > arr[index] ? largest : index;
                if (index == largest) {
                    break;
                }
                swap(arr, largest, index);
                index = largest;
                left = 2 * index + 1;
            }
        }


        private void swap(int[] arr, int i, int j) {
            if (i == j) {
                return;
            }
            arr[i] ^= arr[j];
            arr[j] ^= arr[i];
            arr[i] ^= arr[j];
        }
    }


    public static class RightMaxHeap {
        private final int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }
    }

    public static class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    public static void main(String[] args) {
        // 默认的优先队列是最小堆
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(new MyComparator());
        heap.add(5);
        heap.add(3);
        System.out.println(heap.peek());
        heap.add(7);
        heap.add(0);
        System.out.println(heap.peek());

        int value = 1000;
        int limit = 100;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap maxHeap = new MyMaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            for (int j = 0; j < curLimit; j++) {
                if (maxHeap.isEmpty() != test.isEmpty()) {
                    System.out.println("Fucking fucked!");
                }
                if (maxHeap.isFull() != test.isFull()) {
                    System.out.println("Fucking fucked!");
                }
                if (maxHeap.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    maxHeap.push(curValue);
                    test.push(curValue);
                } else if (maxHeap.isFull()) {
                    if (maxHeap.pop() != test.pop()) {
                        System.out.println("Fucking fucked!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        maxHeap.push(curValue);
                        test.push(curValue);
                    } else {
                        if (maxHeap.pop() != test.pop()) {
                            System.out.println("Fucking fucked!");
                            System.out.println(maxHeap);
                            System.out.println(test);
                        }
                    }
                }
            }

        }
        System.out.println("Finished!");
    }

}
