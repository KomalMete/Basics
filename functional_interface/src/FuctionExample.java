import java.util.function.*;

public class FuctionExample {

    public static void main(String [] args)
    {
        //square
//        Function<Integer, Integer> f = i -> i*i;
//        System.out.println(f.apply(5));

        //return length of string
        Function<String, Integer> f = s -> s.length();
        System.out.println(f.apply("Komal"));
    }
}
