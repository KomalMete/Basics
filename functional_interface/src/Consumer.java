import java.util.function.*;


class Movie
{
    String name;

    public Movie(String name)
    {
        this.name = name;
    }
}

class Test {

    public static void main(String[] args)
    {
        //single consumer
        Consumer<Movie> c = m -> System.out.println(m.name +" is ready to release ");
        Consumer<Movie> c1 = m -> System.out.println(m.name +" will be biggest hit ");

        Movie m = new Movie("Dhoom");
        //c1.accept(m); //Dhoom will be biggest hit

        //chained consumer
        Consumer<Movie> cc = c.andThen(c1);
        cc.accept(m);          //Dhoom is ready to release Dhoom will be biggest hit
    }

}
