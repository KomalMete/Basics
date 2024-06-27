import java.util.ArrayList;
import java.util.List;
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
        //for individual employee
        //Employee emp = bf.apply(1,"Komal");
        //System.out.println(emp.name);

        List<Employee> list = new ArrayList<>();
        list.add(bf.apply(2,"Kritika"));
        list.add(bf.apply(3,"Katrina"));
        list.add(bf.apply(4,"Preeti"));
        list.add(bf.apply(5,"Kajol"));

        for(Employee emp : list)
        {
            System.out.println("Employee ID " +emp.employeeNo);
            System.out.println("Employee name " +emp.name);
            System.out.println();
        }
    }
}
