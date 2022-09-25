package basic.recursion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author TingxuanHu
 * @version 2022/9/25 15:05
 */

public class SubSet {

    public static void main(String[] args) {
        int[] nums = new int[] {1, 2, 3};
        List<List<Integer>> ans = subsets(nums);
        System.out.println(ans);
    }

    public static List<List<Integer>> subsets(int[] nums) {
        LinkedList<Integer> path = new LinkedList<>();   // LinkedList用来增删的效率更高一些
        List<List<Integer>> ans = new ArrayList<>();
        process(nums, 0, path, ans);
        return ans;
    }

    // index 指针走到了nums的第几个数字
    // 收集所有答案
    public static void process(int[] nums, int index, LinkedList<Integer> path, List<List<Integer>> ans) {
        if (index == nums.length) {
            ans.add(copyPath(path));
            return;
        }
        process(nums, index + 1, path, ans);   // index直接跳下一个 证明做的决定是没有要当前数
        path.addLast(nums[index]);   // path里加入当前的数 证明做的决定是要当前数

        // 上面两步是对index位置的数的取舍  做完之后dfs向下走
        process(nums, index + 1, path, ans);
        path.removeLast();  // 还原犯罪现场
    }

    public static ArrayList<Integer> copyPath(LinkedList<Integer> path) {
        ArrayList<Integer> ans = new ArrayList<>();
        for (Integer num : path) {
            ans.add(num);
        }
        return ans;
    }

}
