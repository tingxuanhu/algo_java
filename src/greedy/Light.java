package greedy;

public class Light {

    // 更全的版本 https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class14/Code01_Light.java
    public static int MinimalLight(String road) {
        char[] str = road.toCharArray();
        int i = 0;
        int light = 0;
        while (i < str.length) {
            if (str[i] == 'X') {
                i++;
            } else {
                if (i + 1 == str.length) {
                    break;
                } else {
                    if (str[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        i = i + 3;
                    }
                }
                light++;
            }
        }
        return light;
    }
}
