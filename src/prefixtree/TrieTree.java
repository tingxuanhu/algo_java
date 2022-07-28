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
            char[] str = pre.toCharArray();
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
                index = (int) c;
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                int index = 0;
                Node2 node = root;
                node.pass--;
                for (char c : chs) {
                    index = (int) c;
                    if (node.nexts.get(index).pass == 0) {
                        node.nexts.remove(index);
                        return;
                    }
                    node = node.nexts.get(index);
                }
                node.end--;
            }
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
            if (pre == null) {
                return 0;
            }
            char[] str = pre.toCharArray();
            Node2 node = root;
            int index = 0;
            for (char c : str) {
                index = (int) c;
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.pass;
        }
    }

    public static class Right {
        private HashMap<String, Integer> box;

        public Right() {
            this.box = new HashMap<>();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }
            return box.getOrDefault(word, 0);
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count++;
                }
            }
            return count;
        }

        // for test
        public static String[] generateRandomStringArray(int arrLen, int strLen) {
            String[] ans = new String[(int) (Math.random() * arrLen + 1)];
            for (String s : ans) {
                s = generateRandomString(strLen);
            }
            return ans;
        }

        public static String generateRandomString(int strLen) {
            char[] ans = new char[(int) (Math.random() * strLen + 1)];
            for (char cur : ans) {
                int value = (int) (Math.random() * 6);
                cur = (char) (97 + value);
            }
            return String.valueOf(ans);
        }

        public static void main(String[] args) {
            int arrLen = 100;
            int strLen = 20;
            int testTimes = 100000;
            for (int i = 0; i < testTimes; i++) {
                String[] arr = generateRandomStringArray(arrLen, strLen);
                Trie1 trie1 = new Trie1();
                Trie2 trie2 = new Trie2();
                Right right = new Right();
                for (int j = 0; j < arr.length; j++) {
                    if (Math.random() < .25) {
                        trie1.insert(arr[j]);
                        trie2.insert(arr[j]);
                        right.insert(arr[j]);
                    } else if (Math.random() < .5) {
                        trie1.delete(arr[j]);
                        trie2.delete(arr[j]);
                        right.delete(arr[j]);
                    } else if (Math.random() < .75) {
                        int ans1 = trie1.search(arr[j]);
                        int ans2 = trie2.search(arr[j]);
                        int ans3 = right.search(arr[j]);
                    } else {
                        int ans1 = trie1.prefixNumber(arr[j]);
                        int ans2 = trie2.prefixNumber(arr[j]);
                        int ans3 = right.prefixNumber(arr[j]);

                        if (ans1 != ans2 || ans1 != ans3) {
                            System.out.println("ans1" + ans1);
                            System.out.println("ans2" + ans2);
                            System.out.println("ans3" + ans3);
                            System.out.println("Fucking fucked!");
                        }
                    }
                }
            }
            System.out.println("finished!");
        }

    }

}
