// Да се имплементира класа MojDDV која што од влезен тек ќе чита информации за скенирани фискални сметки од страна на еден корисник на истоимената апликација. Податоците за фискалните сметки се во следниот формат:

// ID item_price1 item_tax_type1 item_price2 item_tax_type2 … item_price-n item_tax_type-n

// На пример: 12334 1789 А 1238 B 1222 V 111 V

// Постојат три типа на данок на додадена вредност и тоа:

// А (18% од вредноста)
// B (5% од вредноста)
// V (0% од вредноста)
// Повратокот на ДДВ изнесува 15% од данокот на додадената вредност за артикалот.

// Да се имплементираат методите:

// void readRecords (InputStream inputStream)- метод којшто ги чита од влезен тек податоците за фискалните сметки. Доколку е скенирана фискална сметка со износ поголем од 30000 денари потребно е да се фрли исклучок од тип AmountNotAllowedException. Дефинирајте каде ќе се фрла исклучокот, и каде ќе биде фатен, на начин што оваа функција, ќе може да ги прочита сите фискални коишто се скенирани. Исклучокот треба да испечати порака “Receipt with amount [сума на сите артикли] is not allowed to be scanned”.
// void printTaxReturns (OutputStream outputStream) - метод којшто на излезен тек ги печати сите скенирани фискални сметки во формат ID SUM_OF_AMOUNTS TAX_RETURN, каде што SUM_OF_AMOUNTS e збирот на сите артикли во фискалната сметка, а TAX_RETURN е пресметаниот повраток на ДДВ за таа фискална сметка.
// дополнително:

// void printStatistics (OutputStream outputStream) - метод којшто на излезен тек печати статистики за повратокот на ДДВ од сите скенирани фискални сметки во формат min: MIN max: MAX sum: SUM count: COUNT average: AVERAGE, при што секоја од статистиките е во нов ред, а пак вредноста на статистиката е оддалечена со таб од името на статистиката (погледнете тест пример). Децималните вредности се печатат со 5 места, од кои 3 се за цифрите после децималата. Целите вредности се пишуваат со 5 места (порамнети на лево).
// печатењето на вредностите во методот printTaxReturns се врши на тој начин што:
// сите информации се одвоени со таб
// id-то i сумата на фискалната сметка се печатат со 10 места
// повратокот на ДДВ со 10 места, од кои 5 се за цифрите после децималата.

import java.io.*;
        import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}
class smetki{
    int id;
    double sum;
    double tax;

    public smetki(int id,double price, double tax) {
        this.id = id;
        this.sum = price;
        this.tax = tax;
    }
}
class AmountNotAllowedException extends Exception{
    public AmountNotAllowedException(double amount) {
        super(String.format("Receipt with amount %.0f is not allowed to be scanned",amount));
    }
}
class MojDDV{
    List<smetki> smetkis;

    public MojDDV() {
        this.smetkis = new ArrayList<>();
    }
    void readRecords (InputStream inputStream){
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bf.readLine()) != null) {
                String[] parts = line.split("\\s+");
                int id = Integer.parseInt(parts[0]);
                double sum=0;
                double tax=0;
                try {
                    for (int i = 1; i < parts.length; i += 2) {
                        double price = Double.parseDouble(parts[i]);
                        sum += price;
                        if (parts[i + 1] .equals("A")) {
                            tax +=price*0.18;
                        }else if (parts[i+1].equals("B")){
                            tax+=price*0.05;
                        }
                    }
                    if (sum>30000){
                        throw new AmountNotAllowedException(sum);
                    }

                    smetkis.add(new smetki(id,sum,tax*0.15));

                }catch (AmountNotAllowedException e){
                    System.out.println(e.getMessage());
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    void printTaxReturns(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);

        for (smetki s : smetkis){
            pw.printf("%10d\t%10.0f\t%10.5f\n",
                    s.id,
                    s.sum,
                    s.tax);
        }

        pw.flush();
    }
    void printStatistics (OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);

        double min = smetkis.stream().mapToDouble(x -> x.tax).min().orElse(0);
        double max = smetkis.stream().mapToDouble(x -> x.tax).max().orElse(0);
        double sum = smetkis.stream().mapToDouble(x -> x.tax).sum();
        long count = smetkis.stream().count();
        double avg = smetkis.stream().mapToDouble(x -> x.tax).average().orElse(0);

        pw.printf("min:\t%.3f\n", min);
        pw.printf("max:\t%.3f\n", max);
        pw.printf("sum:\t%.3f\n", sum);
        pw.printf("count:\t%d\n", count);
        pw.printf("average:\t%.3f\n", avg);

        pw.flush();
    }
}
