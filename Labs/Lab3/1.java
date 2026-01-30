import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MovieTheaterTester {
    public static void main(String[] args) {
        MovieTheater mt = new MovieTheater();
        try {
            mt.readMovies(System.in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("SORTING BY RATING");
        mt.printByRatingAndTitle();
        System.out.println("\nSORTING BY GENRE");
        mt.printByGenreAndTitle();
        System.out.println("\nSORTING BY YEAR");
        mt.printByYearAndTitle();
    }
}
class Movie{
  String title;
  String genre;
  int year;
  double avgRating;

    public Movie(String title, String genre, int year, double avgRating) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.avgRating = avgRating;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public double getAvgRating() {
        return avgRating;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %d, %.2f",title,genre,year,avgRating);
    }
}
class MovieTheater{
   ArrayList<Movie> movies;

    public MovieTheater() {
        this.movies = new ArrayList<>();
    }
    void readMovies(InputStream is) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        String line;
        String title = "";
        String genre="";
        int year=0;
        List<Double> grades;
        int n = Integer.parseInt(bf.readLine());
        for (int i = 0; i < n; i++) {
            grades=new ArrayList<>();

            for (int j = 0; j <4; j++) {
                if (j==0){
                    title= bf.readLine();
                } else if (j==1) {
                    genre=bf.readLine();
                }else if (j==2){
                    year = Integer.parseInt(bf.readLine());
                }else if (j==3){
                    String niza[] = bf.readLine().split(" ");
                    for (int k = 0; k < niza.length; k++) {
                        grades.add(Double.parseDouble(niza[k]));
                    }

                }
            }
            Movie m = new Movie(title,genre,year,grades.stream().mapToDouble(x->x).average().getAsDouble());
            movies.add(m);
        }
    }

   void printByGenreAndTitle(){
        movies.stream().sorted(Comparator.comparing(Movie::getGenre).thenComparing(Movie::getTitle)).forEach(System.out::println);
   }
    void printByYearAndTitle(){
        movies.stream().sorted(Comparator.comparing(Movie::getYear).thenComparing(Movie::getTitle)).forEach(System.out::println);
    }
    void printByRatingAndTitle(){
        movies.stream().sorted(Comparator.comparing(Movie::getAvgRating).reversed().thenComparing(Movie::getTitle)).forEach(System.out::println);
    }
}
