package prefixtree;

import java.util.HashMap;

public class TrieTree {

    // 写法1
    public static class Node1 {
        private int pass;
        private int end;
        private Node1[] nexts;

        public Node1() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new Node1[26];  // 假定是26个小写字母囊括了所有情况
        }
    }

    public static class Trie1 {
        private Node1 root;

        public Trie1() {
            root = new Node1();
        }

        public void insert(String word) {
            // 建立考虑边界的意识
            if (word == null) {
                return;
            }
            char[] str = word.toCharArray();             // 打成char类型 这样能够顺藤摸瓜顺着树走下去
            // 开始把新的字符串加入前缀树
            Node1 node = root;
            node.pass++;
            int index = 0; // 选择走上第index条路
            for (char c : str) {
                index = c - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node1();
                }
                node = node.nexts[index];
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node1 node = root;
                int idx = 0;
                for (char c : chs) {
                    idx = c - 'a';
                    // 避免内存泄露 把p、e=0的删掉  设为null后JAVA会自己释放掉 头结点找不着它们了（JVM的功能  怎么实现的嘞？）
                    if (--node.pass == 0) {
                        node.nexts[idx] = null;
                        return;
                    }
                    node = node.nexts[idx];
                }
                node.end--;
            }
        }

        // 搜一个词word出现的次数
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] str = word.toCharArray();
            Node1 node = root;
            int index = 0;
            for (char c : str) {
                index = c - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end;
        }

        // 查前缀树中有几个字符是以pre字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] str = word.toCharArray();
            Node1 node = root;
            int index = 0;
            for (char c : str) {
                index = c - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.pass;
        }
    }

    // for debugging
    public static class Node2 {
        private int pass;
        private int end;
        private HashMap<Integer, Node2> nexts;

        public Node2() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            this.root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] str = word.toCharArray();
            Node2 node = root;
            node.pass++;
            int index = 0;
            for (char c : str) {
                index = c - 'a';
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {


        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] str = word.toCharArray();
            Node2 node = root;
            int index = 0;
            for (char c : str) {
//                index = c - 'a';
                index = (int) c;
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.end;
        }

        public int prefixNumber(String pre) {


        }


    }




}
