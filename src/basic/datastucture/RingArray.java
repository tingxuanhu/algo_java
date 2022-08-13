package basic.datastucture;

// 用环形数组实现队列
// 在引入begin和end双指针之外加入一个size记录放没放满，而不用在意begin和end的追赶关系，简化了问题
public class RingArray {

    public static class MyQueue {
        private int[] arr;
        private int pushi; // begin
        private int polli; // end
        private int size;
        private final int limit;

        public MyQueue(int limit) {
            this.limit = limit;
            arr = new int[limit];
            pushi = 0;
            polli = 0;
            size = 0;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("The queue is full");
            }
            size++;
            arr[pushi] = value;
            pushi = nextIndex(pushi);
        }

        public int poll() {
            if (size == 0) {
                throw new RuntimeException("The queue is empty");
            }
            size--;
            int ans = arr[polli];
            polli = nextIndex(polli);
            return ans;
        }

        public int nextIndex(int idx) {
            return idx < limit - 1 ? idx + 1: 0;
        }

    }


}
