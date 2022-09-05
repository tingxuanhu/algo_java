package real;

public class CorporateFlightBookings {
    // 差分数组  前缀和
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] prefix = new int[n + 2]; // 前面补0后面补个n+1
        // book[0] -- first     book[1]  -- last    book[2]  -- added num
        for (int[] book : bookings) {
            prefix[book[0]] += book[2];
            prefix[book[1] + 1] -= book[2];
        }
        for (int i = 1; i < prefix.length; i++) {
            prefix[i] += prefix[i - 1];
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = prefix[i + 1];
        }
        return ans;
    }

}
