import java.util.function.Predicate;

class PredicateExample
{
    public static void main(String[] args)
    {
        Predicate<Integer> p = n -> n>=100 ;

        System.out.println(p.test(10));
        System.out.println(p.test(100));
    }
}
