package datastucture.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HeapGreater<T> {

    private ArrayList<T> heap;
    /*
    HashMap用来充当反向索引表 key记录着元素  value记录着索引位置
    HashMap对于基础类型是按值传递 对于引用类型是按地址传递  所以若是基础类型 需要包装类 获得地址传递的可能
     */
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private final Comparator<? super T> comparator;

    public HeapGreater(Comparator<? super T> c) {
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.heapSize = 0;
        this.comparator = c;
    }

    public int size() {
        return this.heapSize;
    }

    public boolean isEmpty() {
//        return this.datastucture.heap.isEmpty();
        return heapSize == 0;
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(++heapSize);
    }

    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }


    public void remove(T obj) {
        T replace = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if (obj != replace) {
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
    }

    public void resign(T obj) {
        // 两个逻辑只会中一个
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    public void heapInsert(int index) {
        while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize &&
                    comparator.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            best = comparator.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                return;
            }
            swap(best, index);
            index = best;
            index = index * 2 + 1;
        }
    }

    public List<T> getAllElements() {
        List<T> result = new ArrayList<T>();
        for (T h : heap) {
            result.add(h);
        }
        return result;
    }

    public void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(o2, i);
        indexMap.put(o1, j);
    }

}
