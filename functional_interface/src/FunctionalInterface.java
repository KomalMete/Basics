import java.util.function.Supplier;

interface Interf
{
    public int squareIt(int n);
}

 class FunctionalInterface
 {
     public static void main(String[] args)
     {
         Interf i = n -> n*n;

         System.out.println(i.squareIt(9));
     }

}
