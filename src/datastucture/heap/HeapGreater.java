package datastucture.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HeapGreater<T> {

    private ArrayList<T> heap;
    // HashMap用来充当反向索引表 key记元素 value记索引位置 基础类型按值传递 引用类型按地址传递  所以若是基础类型 需要包装类
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private final Comparator<? super T> comparator;

    public HeapGreater(Comparator<? super T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comparator = c;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public int size() {
        return heapSize;
    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T cur : heap) {
            ans.add(cur);
        }
        return ans;
    }

    public T peek() {
        return heap.get(0);
    }

    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public void remove(T obj) {
        // 找到obj的索引位置在哪儿
        int idx = indexMap.get(obj);
        indexMap.remove(obj);
        T replace = heap.get(heapSize - 1);
        heap.remove(--heapSize);
        if (obj != replace) {
            // 用堆尾元素替换掉要换下来删掉的元素
            heap.set(idx, replace);
            indexMap.put(replace, idx);
            resign(replace);
        }
    }

    public void resign(T obj) {
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    // bottom-up
    public void heapInsert(int index) {
        while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    // top-down
    public void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && comparator.compare(heap.get(left + 1), heap.get(left)) < 0 ?
                    left + 1 : left;
            best = comparator.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(index, best);
            left = index * 2 + 1;
        }
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
