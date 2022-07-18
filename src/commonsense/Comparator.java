package commonsense;

public class Comparator {

    public static class Student{
        private final String name;
        private final int id;
        private final int age;

        public Student(String name, int id, int age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }
    }

    // 任何比较器 compare方法里，遵循一个统一的规范：
    // 返回负数的时候，认为第一个参数应该排在前面
    // 返回正数的时候，认为第二个参数应该排在前面
    // 返回0的时候，认为无所谓谁放前面
    public static class IdAscendingStudent implements Comparator<Student> {

    }



    public static void main(String[] args) {

    }

}
