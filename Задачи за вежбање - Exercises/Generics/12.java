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

// вашиот код овде
class ZeroDenominatorException extends Exception{
    public ZeroDenominatorException() {
        super("Denominator cannot be zero");
    }
}
class GenericFraction< T extends Number,U extends  Number>{
       T numerator;
       U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        this.numerator = numerator;
        if (denominator.doubleValue()==0) throw  new ZeroDenominatorException();
        this.denominator = denominator;
    }
    GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
         GenericFraction<Double,Double> nov;
         Double n1=this.numerator.doubleValue();
         Double d1=this.denominator.doubleValue();
         Double n2=gf.numerator.doubleValue();
         Double d2=gf.denominator.doubleValue();
         double gore = n1*d2+n2*d1;
         double dolu = d1*d2;

        return new GenericFraction<Double, Double>(gore,dolu);
    }
    private GenericFraction<Double, Double> normalize() throws ZeroDenominatorException{
        Double numerator = this.numerator.doubleValue();
        Double denominator = this.denominator.doubleValue();
        int i=2;
        while (i<=Math.abs(numerator)&&i<=Math.abs(denominator)){
            if (numerator%i==0&&denominator%i==0){
                numerator/=i;
                denominator/=i;

            }else  i++;
        }
        return new GenericFraction<Double, Double>(numerator,denominator);
    }
    double toDouble(){
        return numerator.doubleValue()/denominator.doubleValue();
    }

    @Override
    public String toString() {
        try {
            GenericFraction<Double, Double>norm = normalize();
            return String.format("%.2f / %.2f",norm.numerator,norm.denominator);
        }catch (ZeroDenominatorException e){
           return  e.toString();
        }
    }
}
