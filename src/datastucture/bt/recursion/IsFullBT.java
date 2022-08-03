package datastucture.bt.recursion;

public class IsFullBT {
    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int data) {
            val = data;
        }
    }

    public static class Info {
        public int numOfNodes;
        public int height;

        public Info(int num, int h) {
            numOfNodes = num;
            height = h;
        }
    }

    public static Info process(Node x) {
        if (x == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);


    }

}
