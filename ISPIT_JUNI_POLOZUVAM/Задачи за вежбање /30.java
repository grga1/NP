// Да се напише класа Canvas во која што ќе се чуваат форми од различен тип. За секоја форма треба да може да се добијат информации за колкава плоштина и периметар има, како и да се овозможи формата да биде скалирана за некој коефициент. Во класата Canvas да се имплементираат:

// default конструктор
// void readShapes (InputStream is) - метод за вчитување на информации за формите од влезен поток.
// Информациите за секоја форма се дадени во секој ред. При вчитување на формите прво се вчитува број (1 = круг/2 = квадрат/3 = правоаголник), па потоа се чита ИД-то на корисникот што ја креирал формата, па потоа доколку станува збор за круг/квадрат се вчитува еден децимален број за радиусот/страната на кругот/квадартот, а доколку е правоаголник се вчитуваат два децимални броја за должина и висина на правоаголнкот.
// ИД на корисникот мора да биде стринг со должина од 6 знаци, при што не се дозволени специјални знаци (само букви и бројки). Доколку некој ИД не е во ред да се фрли исклучок од тип InvalidIDException при креирањето на формата, а со истиот справете се во рамки на функцијата readShapes, односно неправилно ИД да не повлече прекинување на вчитувањето на формите.
// Димензија на форма не смее да биде 0. Во таков случај да се фрли исклучок од тип InvalidDimensionException. Овој исклучок треба да го прекине понатамошното читање на останатите форми.
// void scaleShapes (String userID, double coef) - метод што ќе ги скалира сите форми креирани од корисникот userID со коефициентот coef (ќе ги помножи сите димензии на формата со тој коефициент).
// void printAllShapes (OutputStream os) - метод што ќе ги испечати формите на излезен поток сортирани според нивната плоштина во растечки редослед
// void printByUserId (OutputStream os) - метод што ќе ги испечати формите групирани според корисникот којшто ги креирал, при што корисниците ќе се сортирани според бројот на форми што ги имаат креирано (доколку тој број е ист тогаш според сумата на плоштините на формите). Формите на даден корисник ќе се сортирани според периметарот во опаѓачки редослед.
// void statistics (OutputStream os) - метод што ќе испечати статистики за плоштините на сите форми во колекцијата (min, max, average, sum, count).
// Напомени:

// За да се постигне посакуваната точност, користете постојано double за сите децимални броеви!
// Забрането е користење на .sorted() за да се постигне сортирање на формите. Овој метод може да го искористите само за сортирање на групите на корисници според број на форми што ги задале (во методот printByUserId )

import java.io.*;
import java.rmi.server.ExportException;
import java.util.*;
import java.util.stream.Collectors;

public class CanvasTest {

    public static void main(String[] args) {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        canvas.readShapes(System.in);

        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}
class InvalidIDException extends Exception{
    public InvalidIDException(String id) {
        System.out.println("ID "+id+" is not valid");
    }
}
class InvalidDimensionException extends  Exception{
    public InvalidDimensionException() {
        System.out.println("Dimension 0 is not allowed!");
    }
}
abstract class Shape{
    String id;

    public Shape(String id) {
        this.id = id;
    }

    abstract double area();
    abstract double perimetar();
    public abstract String toString();
    abstract void scaleShapes(double coef);
}
class Circle extends Shape{
  double radius;

    public Circle(String id,double radius) {
        super(id);
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI*radius*radius;
    }

    @Override
    double perimetar() {
        return 2*Math.PI*radius;
    }

    @Override
    public String toString() {
        return String.format("Circle -> Radius: %.2f Area: %.2f Perimeter: %.2f\n",radius,area(),perimetar());
    }

    @Override
    void scaleShapes(double coef) {
       this.radius*=coef;
    }
}
class Square extends Shape{
    double a;

    public Square(String id,double a) {
        super(id);
        this.a = a;
    }

    @Override
    double area() {
        return a*a;
    }

    @Override
    double perimetar() {
        return 4*a;
    }

    @Override
    public String toString() {
        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f\n",a,area(),perimetar());
    }

    @Override
    void scaleShapes(double coef) {
     this.a*=coef;
    }
}
class Rectangle extends Shape{
    double a;
    double b;

    public Rectangle(String id,double a, double b) {
        super(id);
        this.a = a;
        this.b = b;
    }

    @Override
    double area() {
        return a*b;
    }

    @Override
    double perimetar() {
        return 2*a+2*b;
    }

    @Override
    public String toString() {
        return String.format("Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f\n",a,b,area(),perimetar());
    }

    @Override
    void scaleShapes(double coef) {
      this.a*=coef;
      this.b*=coef;
    }
}
class User{
    String id;
    TreeSet<Shape> shapes=new TreeSet<>(Comparator.comparing(Shape::perimetar));;

    public User( String id,Shape s) {
        this.shapes.add(s);
    this.id = id;
    }

    public String getId() {
        return id;
    }
    public int getSizeOFShapes(){
        return shapes.size();
    }
    public  double sumaNaPlostini(){
        return  shapes.stream().mapToDouble(x->x.area()).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Shapes of user: ").append(id).append("\n");
        for (Shape s: shapes){
            sb.append(s.toString());
        }
        return sb.toString();
    }
}
class Canvas{
    TreeSet<Shape> lista;
    TreeSet<User> users;

    public Canvas() {
        this.lista = new TreeSet<>(Comparator.comparing(Shape::area));
 this.users = new TreeSet<>(Comparator.comparing(User::getSizeOFShapes).reversed().thenComparing(User::sumaNaPlostini));
    }

    void readShapes (InputStream is){
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        String line = "";
        String id="";
        try{
        while ((line=bf.readLine())!=null){
            try {
                String[] parts = line.split("\\s+");
                Double radius = 0.0;
                Double a = 0.0;
                Double b = 0.0;
                Double c = 0.0;
                   id=parts[1];
                if (parts[1].length() != 6) throw new InvalidIDException(id);
                for (Character car : parts[1].toCharArray()) {
                    if (!Character.isDigit(car) && !Character.isAlphabetic(car)) throw new InvalidIDException(id);
                }

                if (parts[0].equals("1")) {
                    if (Double.parseDouble(parts[2])==0.0) throw  new InvalidDimensionException();

                    Circle krug = new Circle(parts[1],Double.parseDouble(parts[2]));

                    String finalId = id;
                    User u = users.stream().filter(x->x.id.equals(finalId)).findFirst().orElse(null);
                    if (u!=null){
                        u.shapes.add(krug);
                    }else {
                        users.add(new User(id, krug));
                    }
                    lista.add(krug);
                } else if (parts[0].equals("2")) {
                    if (Double.parseDouble(parts[2])==0.0) throw  new InvalidDimensionException();
                    Square kvadrat = new Square(parts[1],Double.parseDouble(parts[2]));
                    String finalId = id;
                    User u = users.stream().filter(x->x.id.equals(finalId)).findFirst().orElse(null);
                    if (u!=null){
                        u.shapes.add(kvadrat);
                    }else {
                        users.add(new User(id, kvadrat));
                    }
                    lista.add(kvadrat);
                } else if (parts[0].equals("3")) {
                    if (Double.parseDouble(parts[2])==0.0) throw  new InvalidDimensionException();
                    if (Double.parseDouble(parts[3])==0.0) throw  new InvalidDimensionException();

                    Rectangle pravoagolnik =new Rectangle(parts[1],Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                    String finalId = id;
                    User u = users.stream().filter(x->x.id.equals(finalId)).findFirst().orElse(null);
                    if (u!=null){
                        u.shapes.add(pravoagolnik);
                    }else {
                        users.add(new User(id, pravoagolnik));
                    }
                    lista.add(pravoagolnik);
                }
            }catch (InvalidIDException r){
                r.toString();
            }

        }}catch (IOException e){
            e.printStackTrace();
        }
        catch (InvalidDimensionException e) {
            e.toString();
        }
    }
    void scaleShapes (String userID, double coef){
         lista.stream().filter(x->x.id.equals(userID)).forEach(x->x.scaleShapes(coef));
    }
    void printAllShapes (OutputStream os){
         PrintWriter pw = new PrintWriter(os);
         lista.stream().forEach(x->pw.printf(x.toString()));
         pw.flush();
    }
    void printByUserId(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        users.stream()
                .sorted(Comparator.comparing(User::getSizeOFShapes).reversed()
                        .thenComparing(User::sumaNaPlostini))
                .forEach(u -> pw.print(u.toString()));
        pw.flush();
    }
    void statistics (OutputStream os){
     PrintWriter pw = new PrintWriter(os);
     DoubleSummaryStatistics s = lista.stream().mapToDouble(x->x.area()).summaryStatistics();
     pw.printf(String.format("count: %d\n",s.getCount()));
     pw.printf(String.format("sum: %.2f\n",s.getSum()));
     pw.printf(String.format("min: %.2f\n",s.getMin()));
     pw.printf(String.format("average: %.2f\n",s.getAverage()));
     pw.printf(String.format("max: %.2f\n",s.getMax()));

     pw.flush();
    }
}
