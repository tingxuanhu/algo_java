package commonsense;

import java.util.Comparator;

public abstract class IntroToolComparator<S> {

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

    // 任何比较器 compare方法里返回负数--第一个参数应该排在前面  返回正数第二个参数排前面   返回0的时候无所谓
    public static class IdAcendingOrder implements Comparator<Student> {
        @Override
        public int compare(Student t1, Student t2) {
            return t1.id - t2.id;
        }
    }

    public static class IdAcendingAgeDecsendings implements Comparator<Student> {
        @Override
        public int compare(Student t1, Student t2) {
            return t1.id != t2.id ? t1.id - t2.id : t2.age - t1.age;
        }
    }


    public static void main(String[] args) {
        ;
    }

}
