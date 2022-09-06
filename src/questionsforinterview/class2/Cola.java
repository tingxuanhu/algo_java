package questionsforinterview.class2;

/*
 * 买饮料 时间限制： 3000MS 内存限制： 589824KB 题目描述：
 * 游游今年就要毕业了，和同学们在携程上定制了日本毕业旅行。愉快的一天行程结束后大家回到了酒店房间，这时候同学们都很口渴，
 * 石头剪刀布选出游游去楼下的自动贩卖机给大家买可乐。 贩卖机只支持硬币支付，且收退都只支持10 ，50，100
 * 三种面额。一次购买行为只能出一瓶可乐，且每次购买后总是找零最小枚数的硬币。（例如投入100圆，可乐30圆，则找零50圆一枚，10圆两枚）
 * 游游需要购买的可乐数量是 m，其中手头拥有的 10,50,100 面额硬币的枚数分别是 a,b,c，可乐的价格是x(x是10的倍数)。
 * 如果游游优先使用大面额购买且钱是够的情况下,请计算出需要投入硬币次数？ 输入描述 依次输入， 需要可乐的数量为 m 10元的张数为 a 50元的张数为 b
 * 100元的张树为 c 1瓶可乐的价格为 x 输出描述 输出当前金额下需要投入硬币的次数
 * 例如需要购买2瓶可乐，每瓶可乐250圆，手里有100圆3枚，50圆4枚，10圆1枚。 购买第1瓶投递100圆3枚，找50圆 购买第2瓶投递50圆5枚
 * 所以是总共需要操作8次金额投递操作 样例输入 2 1 4 3 250 样例输出 8
 */

public class Cola {

    public static int putTimes(int m, int a, int b, int c, int x) {
        int[] money = {100, 50, 10};
        int[] num = {c, b, a};
        // 记录投币的数量
        int puts = 0;
        // 如果买可乐的时候某种之前的面额有结余 应当是由于之前面额剩余的钱数不购买一瓶新可乐的
        // 此时就需要之前面额剩下的和当前面额的钱合并在一起试试看能不能买  当前面额的支出张数计算如下
        // 首先定义之前结余的总面额(注意不是单张面额)及结余那种面额的总张数
        int preMoneyRest = 0;
        int preNumRest = 0;
        for (int i = 0; i < 3 && m > 0; i ++) {
            // 当前面值参与搞定目前第一瓶可乐需要拿出的张数是多少 ??????






        }




    }













    // 暴力方法  对数器
    //                       #cola  #100   #50     #10  cola price
    public static int right(int m, int a, int b, int c, int x) {
        int[] money = {100, 50, 10};
        int[] num = {c, b, a};
        // 记录投币的数量
        int puts = 0;
        while (m > 0) {  // 一瓶一瓶买
            int cur = buy(money, num, x);
            if (cur == -1) {
                return -1;
            }
            puts += cur;
            m--;
        }
        return puts;
    }

    // 用当前的money和num数组去买一瓶可乐 返回需要的张数  --> 只能一瓶一瓶买 不能跳步 不然感觉扯不清
    // rest:买这瓶可乐剩余要付的价格
    public static int buy(int[] money, int[] num, int rest) {
        // 定一个标记 来方便之后检查是否能付得起  付不起就返回-1
        int first = -1;
        // 看剩下来的能花的最大面额是多少
        for (int i = 0; i < money.length; i++) {
            if (num[i] > 0) {
                first = i;
                break;
            }
        }
        // 若是三种面额都花完了
        if (first == -1) {
            return -1;
        }
        // 找到了目前最大的面额 开始买买买
        // 若是一张就够了 甚至还有多
        if (money[first] >= rest) {
            money[first]--;
            // 有多余剩下的钱 找零记录在后续面额上             i + 1
            giveNext(money, num, first + 1, money[first] - rest);
            return 1;  // 当前这张最大面额的
        } else {   // 一张目前最大面额的还不够支付
            money[first]--;
            int next = buy(money, num, rest - money[first]);
            if (next == -1) {
                return -1;
            }
            return 1 + next;  // 当前这张最大面额的加上后续需要的张数
        }
    }

    //                                                             找零
    public static void giveNext(int[] money, int[] num, int i, int charge) {
        // 记录一下找零的情况 数量要增加到对应的面额上
        for (; i < 3; i++) {
            num[i] += charge / money[i];
            charge %= money[i];
        }
    }




}
