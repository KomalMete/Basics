import java.util.function.*;

public class FuctionExample {

    public static void main(String [] args)
    {
        Function<Integer, Integer> f = i -> i*i;

        System.out.println(f.apply(5));
    }
}
