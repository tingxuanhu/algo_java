package questionsforinterview.class1;

import java.io.File;
import java.util.Objects;
import java.util.Stack;
// 给定一个文件目录的路径，写一个函数统计这个目录下所有的文件数量并返回，隐藏文件也算，但是文件夹不算
public class CountFiles {

    public static int getFileNumber(String folderPath) {
        File root = new File(folderPath);
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        Stack<File> stack = new Stack<File>();
        stack.add(root);

        int files = 0; // what we need to record the final ans
        while (!stack.isEmpty()) {
            File folder = stack.pop();
            if (folder.listFiles() == null) {
                continue;
            } else {
                for (File next : folder.listFiles()) {
                    if (next.isFile()) {
                        files++;
                    }
                    if (next.isDirectory()) {
                        stack.push(next);
                    }
                }
            }
        }
        return files;
    }

    public static void main(String[] args) {
        // 你可以自己更改目录
        String path = "C:\\Users\\burt\\IdeaProjects";
        System.out.println(getFileNumber(path));
    }

}
