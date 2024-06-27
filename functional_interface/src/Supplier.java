import java.util.Date;
import java.util.function.Supplier;

class Supplier1 {
    public static void main(String[] args)
    {
        Supplier<Date> d = () -> new Date();
        System.out.println(d.get());

        //StringBuilder builder = new StringBuilder();
        Supplier<String> s = () -> {
            String otp = "";
            for(int i =0; i<= 5; i++)
            {
               otp = otp + (int) (Math.random()*10);
            }
            return otp;
        };
        System.out.println(s.get());

    }
}
