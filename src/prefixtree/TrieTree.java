package prefixtree;

public class TrieTree {

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
                node.pass--;
                int path = 0;
                for (char c : chs) {
                    path = c - 'a';

                }
            }




            // 避免内存泄露 把p、e=0的删掉


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

}