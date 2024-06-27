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

            Predicate<Integer> p1 = m -> m>=80;
            Predicate<Integer> p2 = m -> m>=60;
            Predicate<Integer> p3 = m -> m>=50;
            Predicate<Integer> p4 = m -> m>=35;
            Predicate<Integer> p5 = m -> m<35;

            if(p1.test(marks))
            {
                grade = "A[Distinction]";
            }
            else if(p2.test(marks))
            {
                grade = "B";
            }
            else if(p3.test(marks))
            {
                grade = "C";
            }
            else if(p4.test(marks))
            {
                grade = "D";
            }
            else if(p5.test(marks))
            {
                grade = "Failed";
            }
            return grade;
        };

        Student[] s = {new Student("Komal", 100),
                        new Student("Kartika", 65),
                        new Student("Kshitija", 55),
                        new Student("Tanmayi", 45),
                        new Student("Saket", 25),
                        };


        //displaying data of every student on screen with name, marks, grade
//        for(Student student : s)
//        {
//            System.out.println("student name" + " : " +student.name);
//            System.out.println("student marks" + " : " +student.marks);
//            System.out.println("student grade" + " : " +f.apply(student));
//        }


        //students with marks greater than 60
        Predicate<Student> p = m -> m.marks>=60;
        for(Student student : s)
        {
            if(p.test(student)) {
                System.out.println("student name" + " : " + student.name);
                System.out.println("student marks" + " : " + student.marks);
                System.out.println("student grade" + " : " + f.apply(student));
            }
        }
    }
}
