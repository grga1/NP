// Квалификациска за прв колоквиум

// Да се дефинира класа ShapesApplication во која се чуваат податоци за повеќе прозорци на кои се исцртуваат геометриски слики во форма на квадрат.

// За класата да се дефинира:

// ShapesApplication() - конструктор
// int readCanvases (InputStream inputStream) - метод којшто од влезен поток на податоци ќе прочита информации за повеќе прозорци на кои се исцртуваат квадрати. 
//   Во секој ред од потокот е дадена информација за еден прозорец во формат: canvas_id size_1 size_2 size_3 …. size_n, каде што canvas_id е ИД-то на прозорецот, 
//   а после него следуваат големините на страните на квадратите што се исцртуваат во прозорецот. Методот треба да врати цел број што означува колку квадрати за сите прозорци се успешно прочитани.
// void printLargestCanvasTo (OutputStream outputStream) - метод којшто на излезен поток ќе го испечати прозорецот чии квадрати имаат најголем периметар. 
//   Печатењето да се изврши во форматот canvas_id squares_count total_squares_perimeter.
import java.io.*;
import java.util.*;

class Canvas{
 private  final String id;
 private final List<Integer> sizes;

    public Canvas(String id, List<Integer> sizes) {
        this.id = id;
        this.sizes = sizes;
    }

    public String getId() {
        return id;
    }

    public int getSquaresCount() {
        return sizes.size();
    }
    public long totalPerimeter() {
        long sum = 0;
        for (int s : sizes) sum += 4L * s; // 4 * страна
        return sum;
    }
}
class ShapesApplication{
   private final List<Canvas> canvases;

    public ShapesApplication() {
        this.canvases = new ArrayList<>();
    }
    int readCanvases (InputStream inputStream){
       int totalSquares=0;
       BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
       try {
           String line;
           while ((line=bf.readLine())!=null){
             line = line.trim();
             if (line.isEmpty())continue;
             String[] parts = line.split("\\s+");
             String id = parts[0];
             List<Integer> sizes = new ArrayList<>();
               for (int i = 1; i < parts.length; i++) {
                    try {
                        int s = Integer.parseInt(parts[i]);
                        if (s>0){
                            sizes.add(s);
                            totalSquares++;
                        }
                    }catch (NumberFormatException ignore){

                    }

               }
               canvases.add(new Canvas(id,sizes));
           }
       } catch (IOException e) {

       }
        return totalSquares;
    }

    void printLargestCanvasTo (OutputStream outputStream){
        if (canvases.isEmpty()) return;
        Canvas best = canvases.get(0);
        for (int i = 1; i < canvases.size(); i++) {
            Canvas c = canvases.get(i);
            if (c.totalPerimeter()>best.totalPerimeter()){
                best=c;
            }
        }
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream));
        pw.printf("%s %d %d%n",best.getId(),best.getSquaresCount(),best.totalPerimeter());
        pw.flush();
    }

}
public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
