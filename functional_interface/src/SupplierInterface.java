import java.util.function.Supplier;

public class SupplierInterface {

    public static void main(String[] args)
    {
        Supplier<String> s = () -> {
            String[] s1 = {"komal", "kriti", "yuvraj"};

            int i = (int) (Math.random() * 2 +1 );
            return s1[i];
        };

        System.out.println(s.get());
        System.out.println(s.get());
        System.out.println(s.get());
        System.out.println(s.get());
    }
}
