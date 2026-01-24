// Треба да се развие генеричка класа за работа со дропки. Класата GenericFraction има два генерички параметри T и U кои мора да бидат од некоја класа која наследува од класата Number. GenericFraction има две променливи:

// numerator - броител
// denominator - именител.
// Треба да се имплементираат следните методи:

// GenericFraction(T numerator, U denominator) - конструктор кој ги иницијализира броителот и именителот на дропката. Ако се обидиме да иницијализираме дропка со 0 вредност за именителот треба да се фрли исклучок од тип ZeroDenominatorException
// GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) - собирање на две дропки
// double toDouble() - враќа вредност на дропката како реален број
// toString():String - ја печати дропката во следниот формат [numerator] / [denominator], скратена (нормализирана) и секој со две децимални места.

import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

class ZeroDenominatorException extends Exception{
    public ZeroDenominatorException() {
        super("Denominator cannot be zero");
    }
}
// вашиот код овде
class GenericFraction <T extends Number,U extends Number> {
    private T numerator;
    private U denominator;

    GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        if (denominator.doubleValue() == 0) {
        throw new ZeroDenominatorException();
        }
        this.denominator=denominator;
        this.numerator=numerator;
    }
    GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
        double n1=this.numerator.doubleValue();
        double d1=this.denominator.doubleValue();
        double n2=gf.numerator.doubleValue();
        double d2=gf.denominator.doubleValue();

        double gore = n1*d2+n2*d1;
        double dolu = d1*d2;
        return new GenericFraction<Double,Double>(gore,dolu);
    }
    double toDouble(){
        return numerator.doubleValue()/denominator.doubleValue();
    }

    private GenericFraction<Double, Double> normalize() throws ZeroDenominatorException{
       double num = numerator.doubleValue();
       double den = denominator.doubleValue();
       double i =2;
       while (i<=Math.abs(num)&&i<=Math.abs(den)){
           if(num%i==0&&den%i==0){
               num/=i;
               den/=i;
           }else i++;
       }
       return new GenericFraction<Double, Double>(num,den);
    }
    @Override
    public String toString() {
        try {
            GenericFraction<Double, Double> norm = normalize();
            return String.format("%.2f / %.2f",
                    norm.numerator, norm.denominator);
        } catch (ZeroDenominatorException e) {
            return e.toString();
        }
    }

}
