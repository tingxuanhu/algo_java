package questionsforinterview.class1;

// 一个非负整数num，如何不用循环语句，返回>=num，并且离num最近的，2的某次方
public class Near2Power {

    public static int tableSizeFor(int num) {
        num--;
        num |= num >>> 1;
        num |= num >>> 2;
        num |= num >>> 4;
        num |= num >>> 8;
        num |= num >>> 16;
        return (num < 0) ? 1 : num + 1;
    }

    public static void main(String[] args) {
        int cap = 120;
        System.out.println(tableSizeFor(cap));
    }
}
