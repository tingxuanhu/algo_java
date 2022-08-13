package basic.greedy;

import javax.sound.sampled.Port;
import java.util.Arrays;
import java.util.Comparator;

public class BestArrange {

    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static int bestArrangeGreedy(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        Arrays.sort(programs, new ProgramComparator());
        int timeLine = 0;
        int count = 0;
        for (Program program : programs) {
            // <= 而不是 <
            if (timeLine <= program.start) {
                count++;
                timeLine = program.end;
            }
        }
        return count;
    }

    public static class ProgramComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    // for comparison
    public static int bestArrangeAllPossible(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0, 0);
    }

    public static int process(Program[] programs, int done, int timeLine) {
        if (programs.length == 0) {
            return done;
        }
        int max = done;
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start) {
                Program[] next = deleteIndexMeeting(programs, i);
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }

    public static Program[] deleteIndexMeeting(Program[] programs, int i) {
        Program[] res = new Program[programs.length - 1];
        int index = 0;
        for (int j = 0; j < programs.length; j++) {
            if (j != i) {
                res[index++] = programs[j];
            }
        }
        return res;
    }

    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * programSize + 1)];
        for (int i = 0; i < ans.length; i++) {
            int m1 = (int) (Math.random() * timeMax + 1);
            int m2 = (int) (Math.random() * timeMax + 1);
            if (m1 == m2) {
                ans[i] = new Program(m1, m1 + 1);
            } else {
                ans[i] = new Program(Math.min(m1, m2), Math.max(m1, m2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int programSize = 13;
        int timeMax = 20;
        for (int i = 0; i < testTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            int ans1 = bestArrangeGreedy(programs);
            int ans2 = bestArrangeAllPossible(programs);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println("Original programs[]:");
                for (int j = 0; j < programs.length; j++) {
                    System.out.print("programs" + j +".start:" + programs[j].start + " ");
                    System.out.println("programs" + j +".end:" + programs[j].end);
                }
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finished!");
    }

}
