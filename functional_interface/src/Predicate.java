import java.util.function.Predicate;

class PredicateExample
{
    public static void main(String[] args)
    {
//        Predicate<Integer> p = n -> n>=100 ;
//        System.out.println(p.test(10));// false
//        System.out.println(p.test(100));//true

//        Predicate<Integer> p = n -> (n%2 == 0);
//        System.out.println(p.test(10));//true
//        System.out.println(p.test(45));//false

        String[] s = {"Komal", "Virat", "Kiran", "Rohit"};

        Predicate<String> p = n -> (n.charAt(0) == 'K' || n.charAt(0) =='R');

        for(String s1 : s)
        {
            if(p.test(s1))
            {
                System.out.println(s1); //Komal Kiran Rohit
            }
        }
    }
}
