// Квалификациска за прв колоквиум

// Да се дефинира класа ShapesApplication во која се чуваат податоци за повеќе прозорци на кои се исцртуваат геометриски слики во форма на квадрат.

// За класата да се дефинира:

// ShapesApplication() - конструктор
// int readCanvases (InputStream inputStream) - метод којшто од влезен поток на податоци ќе прочита информации за повеќе прозорци на кои се исцртуваат квадрати. Во секој ред од потокот е дадена информација за еден прозорец во формат: canvas_id size_1 size_2 size_3 …. size_n, каде што canvas_id е ИД-то на прозорецот, а после него следуваат големините на страните на квадратите што се исцртуваат во прозорецот. Методот треба да врати цел број што означува колку квадрати за сите прозорци се успешно прочитани.
// void printLargestCanvasTo (OutputStream outputStream) - метод којшто на излезен поток ќе го испечати прозорецот чии квадрати имаат најголем периметар. Печатењето да се изврши во форматот canvas_id squares_count total_squares_perimeter.

import java.io.*;
import java.util.*;
public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}

class Canvas{
      String id;
      List<Integer> sizes;
      int  squares_count;
    public Canvas(String id, List<Integer> sizes) {
        this.id = id;
        this.sizes = sizes;
        this.squares_count = sizes.size();
    }
    int total_squares_perimeter(){
        return sizes.stream().mapToInt(x->x).sum()*4;
    }

    @Override
    public String toString() {
        return  id+" "+squares_count+" "+total_squares_perimeter();
    }
}

class ShapesApplication{
      List<Canvas> canvasList;

    public ShapesApplication() {
        this.canvasList = new ArrayList<>();
    }

    int readCanvases (InputStream inputStream){
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        int count=0;
        try {
            while ((line= bf.readLine())!=null){
                String[] parts = line.split("\\s+");
                String id = parts[0];
                List<Integer> lista = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    count++;
                    lista.add(Integer.parseInt(parts[i]));
                }
                canvasList.add(new Canvas(id,lista));

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return count;
    }

    void printLargestCanvasTo (OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
 Canvas c = canvasList.stream().sorted(Comparator.comparing(Canvas::total_squares_perimeter).reversed()).findFirst().orElse(null);
         pw.println(c.toString());
         pw.flush();
    }


}
