package dp;

/*
给定一个整型数组arr，代表数值不同的纸牌排成一条线
玩家A和玩家B依次拿走每张纸牌
规定玩家A先拿，玩家B后拿
但是每个玩家每次只能拿走最左或最右的纸牌
玩家A和玩家B都绝顶聪明
请返回最后获胜者的分数
 */
public class CardsInLine {

    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int f = former(arr, 0, arr.length - 1);
        int l = latter(arr, 0, arr.length - 1);
        return Math.max(f, l);
    }

    // 先后手博弈  先手的最优解是对手后手在自己选完后的数中选最优解的情况下能够获得的最优解
    public static int former(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        int p1 = arr[L] + latter(arr, L + 1, R);
        int p2 = arr[R] + latter(arr, L, R - 1);
        return Math.max(p1, p2);
    }

    public static int latter(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int p1 = former(arr, L + 1, R);
        int p2 = former(arr, L, R - 1);
        return Math.min(p1, p2);
    }


}
