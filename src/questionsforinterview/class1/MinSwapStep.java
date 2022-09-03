package questionsforinterview.class1;

// 一个数组中只有两种字符'G'和'B'，
// 可以让所有的G都放在左侧，所有的B都放在右侧
// 或者可以让所有的G都放在右侧，所有的B都放在左侧
// 但是只能在相邻字符之间进行交换操作，请问请问至少需要交换几次，
public class MinSwapStep {

    public static int minStep1(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int gl = 0;
        int gCount = 0;
        // G -> left
        for (int i = 0; i < str.length; i++) {
            // 这里==如果写"G"会标红  'G'则不会标红
            if (str[i] == 'G') {
                gCount += i - (gl++);
            }
        }
        // B -> left
        int bl = 0;
        int bCount = 0;
        for (int i = 0; i < str.length; i++) {
            // 这里==如果写"G"会标红  'G'则不会标红
            if (str[i] == 'B') {
                bCount += i - (bl++);
            }
        }
        return Math.min(gCount, bCount);
    }

    // 上面可以合并
    public static int minStep2(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int gl = 0;
        int bl = 0;
        int gCount = 0;
        int bCount = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'G') {
                gCount += i - (gl++);
            } else {
                bCount += i - (bl++);
            }
        }
        return Math.min(gCount, bCount);
    }


    // 为了测试
    public static String randomString(int maxLen) {
        char[] str = new char[(int) (Math.random() * maxLen)];
        for (int i = 0; i < str.length; i++) {
            str[i] = Math.random() < 0.5 ? 'G' : 'B';
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int testTime = 1000000;
        System.out.println("test Start");
        for (int i = 0; i < testTime; i++) {
            String str = randomString(maxLen);
            int ans1 = minStep1(str);
            int ans2 = minStep2(str);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }

}
