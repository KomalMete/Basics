import java.util.function.*;

class Employee
{
    int employeeNo;
    String name;

    public Employee(int employeeNo, String name)
    {
        this.employeeNo = employeeNo;
        this.name = name;
    }
}

public class BiFunctionExample {

    public static void main(String[] args)
    {
        BiFunction<Integer, String, Employee> bf = (eno,name) ->  new Employee(eno, name);
        Employee emp = bf.apply(1,"Komal");
        System.out.println(emp.name);
    }
}
