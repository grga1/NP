// Прв колоквиум

// Да се имплементира класа Canvas на која ќе чуваат различни форми. За секоја форма се чува:

// id:String
// color:Color (enum дадена)
// Притоа сите форми треба да имплментираат два интерфејси:

// Scalable - дефиниран со еден метод void scale(float scaleFactor) за соодветно зголемување/намалување на формата за дадениот фактор
// Stackable - дефиниран со еден метод float weight() кој враќа тежината на формата (се пресметува како плоштина на соодветната форма)
// Во класата Canvas да се имплементираат следните методи:

// void add(String id, Color color, float radius) за додавање круг
// void add(String id, Color color, float width, float height) за додавање правоаголник
// При додавањето на нова форма, во листата со форми таа треба да се смести на соодветното место според нејзината тежина. Елементите постојано се подредени според тежината во опаѓачки редослед.

// void scale(String id, float scaleFactor) - метод кој ја скалира формата со даденото id за соодветниот scaleFactor. Притоа ако има потреба, треба да се изврши преместување на соодветните форми, за да се задржи подреденоста на елементите.
// Не смее да се користи сортирање на листата.

// toString() - враќа стринг составен од сите фигури во нов ред. За секоја фигура се додава:

// C: [id:5 места од лево] [color:10 места од десно] [weight:10.2 места од десно] ако е круг

// R: [id:5 места од лево] [color:10 места од десно] [weight:10.2 места од десно] ако е правоаголник
// Користење на instanceof ќе се смета за неточно решение

import java.util.*;

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

enum Color {
    RED, GREEN, BLUE
}
interface Scalable{
    void scale(float scaleFactor);
}
interface Stackable{
    float weight();
}
abstract  class Shape implements Comparable<Shape>{
    String id;
    Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }


   abstract public String toString();

    abstract void scale(float scaleFactor);
    public abstract float weight();

    @Override
    public int compareTo(Shape o) {
        if (Double.compare(o.weight(), this.weight()) != 0) {
            return Double.compare(o.weight(), this.weight());
        }
        return this.id.compareTo(o.id);
    }
}
class Circle extends Shape implements Stackable,Scalable{
    float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public String toString() {
        return String.format("C: %-4s %-10s %9.2f\n",id,color,weight());
    }

    @Override
    public void scale(float scaleFactor) {
      this.radius=this.radius*scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (Math.PI*radius*radius);
    }
    float getWeight(){
        return weight();
    }

}
class Square extends Shape implements Stackable,Scalable{
    float width;
    float height;

    public Square(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("R: %-4s %-10s %9.2f\n",id,color,weight());
    }

    @Override
    public float weight() {
        return height*width;
    }

    @Override
    public void scale(float scaleFactor) {
        this.height=this.height*scaleFactor;
        this.width=this.width*scaleFactor;
    }
}
class Canvas {
    List<Shape> lista;

    public Canvas() {
        this.lista = new ArrayList<>();
    }

    void add(String id, Color color, float radius){
       Circle c = new Circle(id,color,radius);
        insert(c);
    }
    void add(String id, Color color, float width, float height){
      Square sq = new Square(id,color,width,height);
       insert(sq);
    }
    void insert(Shape s){
        int index=0;
        while (index<lista.size()&&lista.get(index).compareTo(s)<0){
            index++;
        }
        lista.add(index,s);
    }
    void scale(String id, float scaleFactor){
      Optional<Shape> s = lista.stream().filter(x->x.id.equals(id)).findFirst();
      Shape shape = s.get();
      lista.remove(shape);
      shape.scale(scaleFactor);
         insert(shape);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        lista.stream().forEach(x->sb.append(x.toString()));
        return sb.toString();
    }
}




