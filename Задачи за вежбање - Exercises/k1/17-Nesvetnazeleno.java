import java.io.*;
import java.util.*;

class AmountNotAllowedException extends Exception{
    public AmountNotAllowedException(String message) {
        super(message);
    }
}
class TaxReturns{
    Integer id;
    Integer sum_of_amounts;
   Double tax_return;

    public TaxReturns(Integer id, Integer sum_of_amounts, Double tax_return) {
        this.id = id;
        this.sum_of_amounts = sum_of_amounts;
        this.tax_return = tax_return;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSum_of_amounts() {
        return sum_of_amounts;
    }

    public Double getTax_return() {
        return tax_return;
    }
}
class MojDDV{
     int ID;
     List<TaxReturns> lista = new ArrayList<>();
     HashMap<Integer,Double> mapa;
    public MojDDV() {
        this.ID = 0;
        this.lista = new ArrayList<>();
        this.mapa = new HashMap<>();
    }


    void readRecords (InputStream inputStream) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
       String line;
        while ((line=bf.readLine())!=null) {
            try {
                String parts[] = line.split("\\s++");
                int sum = 0;
                for (int i = 1; i < parts.length; i++) {
                    if (parts[i].equals("A") || parts[i].equals("B") || parts[i].equals("V")) {
                        continue;
                    } else {
                        sum += Integer.parseInt(parts[i]);
                    }
                }
                if (sum > 30000) {
                    throw new AmountNotAllowedException(
                            "Receipt with amount " + sum + " is not allowed to be scanned"
                    );
                }
                double pomTax = 0.0;

                for (int i = 1; i < parts.length; i++) {
                    if (parts[i].equals("A")) {
                        double itemReturn = Integer.parseInt(parts[i - 1]) * 0.18 * 0.15;
                        pomTax += itemReturn;
                    } else if (parts[i].equals("B")) {
                        double itemReturn = Integer.parseInt(parts[i - 1]) * 0.05 * 0.15;
                        pomTax += itemReturn;
                    }
                }
                pomTax = Math.round(pomTax * 100) / 100.0;


                    mapa.put(Integer.parseInt(parts[0]),pomTax);

                TaxReturns objekt = new TaxReturns(Integer.parseInt(parts[0]), sum, pomTax);
                lista.add(objekt);


            }catch (AmountNotAllowedException e ){
                System.out.println(e.getMessage());
            }
        }
    }
    void printTaxReturns (OutputStream outputStream){
        PrintWriter pw  = new PrintWriter(outputStream);
        lista.stream().forEach(x-> pw.printf("%d %d %.2f%n", x.id, x.sum_of_amounts, x.tax_return));
        Double min  = mapa.values().stream().min(Double::compareTo).get();
        Double max  = mapa.values().stream().max(Double::compareTo).get();
        Double count = (double) mapa.values().stream().count();
        Double average = mapa.values().stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        Double sum = mapa.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.println(min);
        System.out.println(max);
        System.out.println(count);
        System.out.println(average);
        pw.flush();
    }
}
public class MojDDVTest {

    public static void main(String[] args) throws IOException, AmountNotAllowedException {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

    }
}
