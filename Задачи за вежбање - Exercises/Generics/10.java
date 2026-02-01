import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple
class Triple <T extends Number&Comparable<T>>{
    T a;
    T b;
    T c;
    List<T> lista;
    public Triple(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.lista = new ArrayList<>();
        lista.add(a);
        lista.add(b);
        lista.add(c);
    }
    double max(){
        return lista.stream().mapToDouble(x-> Double.parseDouble(String.valueOf(x))).max().getAsDouble();
    }
    double avarage(){
        return lista.stream().mapToDouble(x->Double.parseDouble(String.valueOf(x))).average().getAsDouble();
    }
    void sort() {
        lista.sort(Comparator.naturalOrder());
        a = lista.get(0);
        b = lista.get(1);
        c = lista.get(2);
    }


    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    public T getC() {
        return c;
    }

    public List<T> getLista() {
        return lista;
    }

    @Override
    public String toString() {
       return String.format("%.2f %.2f %.2f",getA().doubleValue(),getB().doubleValue(),getC().doubleValue());
    }
}

