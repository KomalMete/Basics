import java.util.function.Function;
import java.util.function.Predicate;

class Student {
    String name;
    int marks;

    public Student(String name, int marks)
    {
        this.name = name;
        this.marks = marks;
    }
}

public class FunctionExample1 {

    public static void main(String[] args)
    {
        Function<Student, String> f = n -> {
            int marks = n.marks;
            String grade = "";

            Predicate<Integer> p1 = m -> m>90;
            Predicate<Integer> p2 = m -> m>80;
            Predicate<Integer> p3 = m -> m>60;
            Predicate<Integer> p4 = m -> m>55;
            Predicate<Integer> p5 = m -> m<35;

            if(p1.test(marks))
            {
                grade = "Grade Distinction";
            }
            else if(p2.test(marks))
            {
                grade = "Grade A";
            }
            else if(p3.test(marks))
            {
                grade = "Grade B";
            }
            else if(p4.test(marks))
            {
                grade = "Grade C";
            }
            else if(p5.test(marks))
            {
                grade = "Grade Failed";
            }
            return grade;
        };

        Student[] s = {new Student("Komal", 65),
                        new Student("Kartika", 96),
                        new Student("Kshitija", 75),
                        new Student("Tanmayi", 56),
                        new Student("Saket", 34),
                        };

        for(Student student : s)
        {
            System.out.println("student name" + " " +student.name);
            System.out.println("student marks" + " " +student.marks);
            System.out.println("student" + " " +f.apply(student));
        }
    }
}
