// Прв колоквиум

// Да се дефинира класа ShapesApplication чување на податоци за повеќе прозорци на кои и се сцртуваат геометриски слики во различна форма (квадрати и кругови)..

// За класата да се дефинира:

// ShapesApplication(double maxArea) - конструктор, каде maxArea е најголемата дозволена плоштина на секоја форма поединечно, која може да биде исцртана на прозорците.
// void readCanvases (InputStream inputStream) - метод којшто од влезен поток на податоци ќе прочита информации за повеќе прозорци на кои се исцртуваат различните геометриски слики. Во секој ред се наоѓа информација за еден прозорец во формат: canvas_id type_1 size_1 type_2 size_2 type_3 size_3 …. type_n size_n каде што canvas_id е ИД-то на прозорецот, a после него следуваат информации за секоја форма во прозорецот. Секоја форма е означена со карактер што го означува типот на геометриската слика (S = square, C = circle) и со големината на страната на квадратот, односно радиусот на кругот.
// При додавањето на геометриските слики на прозорецот треба да се спречи креирање и додавање на прозорец во кој има форма што има плоштина поголема од максимално дозволената. Како механизам за спречување треба да се користи исклучок од тип IrregularCanvasException (фрлањето на исклучокот не треба да го попречи вчитувањето на останатите прозорци и геометриски слики. Да се испечати порака Canvas [canvas_id] has a shape with area larger than [max_area].
// void printCanvases (OutputStream os) - метод којшто на излезен поток ќе ги испечати информациите за сите прозорци во апликацијата. Прозорците да се сортирани во опаѓачки редослед според сумата на плоштините на геометриските слики во нив. Секој прозорец да е испечатен во следниот формат: ID total_shapes total_circles total_squares min_area max_area average_area.
// За вредноста на PI користете ja константата Math.PI. За постигнување на точност со тест примерите користете double за сите децимални променливи.

// --

// Define a class ShapesApplication whre you'll keep information about multiple windows on which geometric images (in different shape - square and circle) are drawn.

// For the class you need to define and implement:

// ShapesApplication(double maxArea) - constructor with one argument which represents the maximum allowed area of a shape that can be drawn on the windows.
// void readCanvases (InputStream inputStream) - method that will read info about multiple windows from input stream. Each line of the data stream represents one window and it's in the format canvas_id type_1 size_1 type_2 size_2 type_3 size_3 …. type_n size_n where canvas_id is the ID of the window and after the ID there are unknown number of pairs of data for the shapes. Each pair has its type (character S = square, C = circle) and the side of the side of the square or the size of the radius of the circle.
// When adding the geometric images on the window, the creation and addition of a window which contains a shape with area greater than the maximum area, should not be allowed. This should be done via exception of type InvalidCanvasException. Throwing an exception of this type should not stop the reading of the data from the input stream. When catching the exception, the following message should be printed: Canvas [canvas_id] has a shape with area larger than [max_area].
// void printCanvases(OutputStream os) - method that will print to output stream the information for all the windows in the application. The windows should be sorted in descending order by the sum of the areas of the geometric shapes in them. Each window should be printed in the following format: ID total_shapes total_circles total_squares min_area max_area average_area.
// For the value of PI use Math.PI. Use double for better precision of the decimal numbers.




import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Shapes2Test {

    public static void main(String[] args) throws InvalidCanvasException {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}


class InvalidCanvasException extends Exception{
    public InvalidCanvasException(String canvas_id,double max_area) {
        super(String.format("Canvas %s has a shape with area larger than %.2f",canvas_id,max_area));
    }
}
class Canvas{
    String id;
    int totalCircles;
    int totalSquares;
    List<Double> areas;

    public Canvas(String id, int totalCircles, int totalSquares, List<Double> areas) {
        this.id = id;
        this.totalCircles = totalCircles;
        this.totalSquares = totalSquares;
        this.areas = areas;
    }

    public int getTotalSquares() {
        return totalSquares;
    }

    public String getId() {
        return id;
    }

    public int getTotalCircles() {
        return totalCircles;
    }
    public double getminArea(){
        return areas.stream().mapToDouble(x->x).min().orElse(0.0);
    }
    public double getmaxArea(){
        return areas.stream().mapToDouble(x->x).max().orElse(0.0);
    }
    public double getaverageArea(){
        return areas.stream().mapToDouble(x->x).average().orElse(0.0);
    }
   public double sumArea(){
        return areas.stream().mapToDouble(x->x).sum();
   }
    @Override
    public String toString() {
        return id+" "+totalSquares+totalCircles+" "+totalCircles+" "+totalSquares+" "+getminArea()+" "+getmaxArea()+" "+getaverageArea();
    }
}

class ShapesApplication{
    List<Canvas> canvasList;
    double maxArea;

    public ShapesApplication(double maxArea) {
        this.maxArea=maxArea;
        this.canvasList = new ArrayList<>();
    }

    void readCanvases (InputStream inputStream) throws InvalidCanvasException{

        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String line="";

        try {
            while ((line= bf.readLine())!=null){
                String[] parts = line.split("\\s+");

                String id = parts[0];
                List<Double> areas = new ArrayList<>();
                int totalCircles = 0;
                int totalSquares = 0;

                for (int i = 1; i < parts.length - 1; i++) {
                    if (parts[i].equals("C")) {
                        totalCircles++;
                        double r = Double.parseDouble(parts[i + 1]);
                        areas.add(Math.PI * r * r); // pi*r^2
                    } else if (parts[i].equals("S")) {
                        totalSquares++;
                        double a = Double.parseDouble(parts[i + 1]);
                        areas.add(a * a); // a^2
                    }
                }
                try {
                    for (Double area : areas) {
                        if (area > maxArea) throw new InvalidCanvasException(id, maxArea);
                    }
                    canvasList.add(new Canvas(id, totalCircles, totalSquares, areas));
                }catch (InvalidCanvasException e){
                    System.out.println(e.getMessage());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    void printCanvases (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        canvasList.stream().sorted(Comparator.comparing(Canvas::sumArea).reversed()).forEach(x->pw.printf("%s %d %d %d %.2f %.2f %.2f\n",x.id,x.totalCircles+x.totalSquares,x.totalCircles,x.totalSquares,x.getminArea(),x.getmaxArea(),x.getaverageArea()));
        pw.flush();
    }


}
