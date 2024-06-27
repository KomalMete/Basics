import java.util.function.*;

public class BipredicateExample {

    public static void main(String[] args)
    {
        BiPredicate<Integer, Integer> p = (a,b) -> (a+b)%2 == 0;

        System.out.println(p.test(72,23));
    }
}
