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
        Consumer<Movie> c = m -> System.out.println(m.name +" is ready to release ");

        Movie m = new Movie("Dhoom");
        c.accept(m);
    }

}
