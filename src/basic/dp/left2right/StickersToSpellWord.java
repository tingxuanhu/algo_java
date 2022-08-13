package basic.dp.left2right;

import java.util.HashMap;

public class StickersToSpellWord {

    public static int minStickers(String[] stickers, String target) {
        if (stickers == null || stickers.length < 1 || process(stickers, target) == Integer.MAX_VALUE) {
            return -1;
        }
        return process(stickers, target);
    }

    public static int process(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String cur : stickers) {
            String rest = minusChar(target, cur);
            if (rest.length() != target.length()) {
                min = Math.min(min, process(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    // top-down  target是String类型 很难做类似于处理int型目标的严格表结构(可能性太多了)  做记忆化搜索就好
    // 优化两处：第一 词频表统计代替数组   第二 剪枝操作
    public static int topDown(String[] stickers, String target) {
        int[][] counts = new int[stickers.length][26];
        for (int i = 0; i < stickers.length; i++) {
            char[] sticker = stickers[i].toCharArray();
            for (char s : sticker) {
                counts[i][s - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        return process3(counts, target, dp) == Integer.MAX_VALUE ? -1 : process3(counts, target, dp);
    }

    public static int process3(int[][] stickers, String tar, HashMap<String, Integer> dp) {
        if (dp.containsKey(tar)) {
            return dp.get(tar);
        }
        // 对目标做词频统计
        char[] target = tar.toCharArray();
        int[] tCounts = new int[26];
        for (char c : target) {
            tCounts[c - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            // 剪枝 包含target[0]的sticker才走 因为现在不包含 之后必有某一个时刻要包含 先进行剪枝
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tCounts[j] > 0) {
                        int remainNums = tCounts[j] - sticker[j];
                        for (int k = 0; k < remainNums; k++) {
                            stringBuilder.append((char) (j + 'a'));
                        }
                    }
                }
                min = Math.min(min, process3(stickers, stringBuilder.toString(), dp));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(tar, ans);
        return ans;
    }

}
