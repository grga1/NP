// Да се имплемнтира генеричка класа Triple (тројка) од нумерички вредности (три броја). За класата да се имплементираат:

// конструктор со 3 аргументи,
// double max() - го враќа најголемиот од трите броја
// double average() - кој враќа просек на трите броја
// void sort() - кој ги сортира елементите во растечки редослед
// да се преоптовари методот toString() кој враќа форматиран стринг со две децимални места за секој елемент и празно место помеѓу нив.

import java.util.*;

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
class Triple<T extends Number>{
      T a;
      T b;
      T c;

    public Triple(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
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

    public void setA(T a) {
        this.a = a;
    }

    public void setB(T b) {
        this.b = b;
    }

    public void setC(T c) {
        this.c = c;
    }

    double max(){
       return Math.max(getA().doubleValue(),Math.max(getB().doubleValue(),getC().doubleValue()));
    }
    double avarage(){
        return (getA().doubleValue()+ getB().doubleValue()+getC().doubleValue())/3.0;
    }
    void sort(){
        for (int i = 0; i < 3; i++) {
            if (getA().doubleValue()>getB().doubleValue()){
                T tmp = getA();
                setA(getB());
                setB(tmp);
            }
            else if (getA().doubleValue()>getC().doubleValue()){
                T tmp = getA();
                setA(getC());
                setC(tmp);
            }else if (getB().doubleValue()>getC().doubleValue()){
                T tmp = getB();
                setB(getC());
                setC(tmp);
            }
        }
    }

    @Override
    public String toString() {
      return String.format("%.2f %.2f %.2f",a.doubleValue(),b.doubleValue(),c.doubleValue());
    }
}
// vasiot kod ovde
// class Triple


