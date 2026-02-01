import java.util.*;
import java.util.stream.Collectors;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}
// vashiot kod ovde
class Movie{
    String title;
    int[] ratings;
    double avgRating;
   double ratingCoef;
    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = ratings;
        this.avgRating = 0.0;
        this.ratingCoef=0.0;
    }

    public String getTitle() {
        return title;
    }

    public int[] getRatings() {
        return ratings;
    }
    public double getAvgRating(){
        return Arrays.stream(ratings).average().getAsDouble();
    }
   public int getLengthOfRatings(){
        return ratings.length;
   }

    public void setRatingCoef(double ratingCoef) {
        this.ratingCoef = ratingCoef;
    }

    public double getRatingCoef() {
        return ratingCoef;
    }

    @Override
    public String toString() {
        return  String.format("%s (%.2f) of %d ratings ",getTitle(),getAvgRating(),getLengthOfRatings());
    }
}
class MoviesList{
    List<Movie> movies;

    public MoviesList() {
        this.movies = new ArrayList<>();
    }
    public void addMovie(String title, int[] ratings){
        Movie m = new Movie(title,ratings);
        movies.add(m);
    }
    public List<Movie> top10ByAvgRating(){
        return movies.stream().sorted(Comparator.comparing(Movie::getAvgRating).reversed().thenComparing(Movie::getTitle)).limit(10).collect(Collectors.toList());
    }
    public List<Movie> top10ByRatingCoef(){
         List<Movie> lista = new ArrayList<>();

      int max = movies.stream().mapToInt(Movie::getLengthOfRatings).max().getAsInt();
      for (Movie m : movies){
       m.setRatingCoef((m.getAvgRating()*m.getLengthOfRatings())/max);
      }
      return movies.stream().sorted(Comparator.comparing(Movie::getRatingCoef).reversed().thenComparing(Movie::getTitle)).limit(10).collect(Collectors.toList());

    }
}
