package datastucture.bt;

public class PaperFolding {
     public static void printAllFolds(int N) {
        process(1, N, true);
        System.out.println();
     }

     // 当前在i层 共有N层
     public static void process(int i, int N, boolean down) {
         if (i > N) {
             return;
         }
         process(i + 1, N, true);
         System.out.println(down ? "ao" : "tu");
         process(i + 1, N, false);
     }

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);
    }

}
