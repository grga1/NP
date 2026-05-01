// Да се имплементира класа MojDDV која што од влезен тек ќе чита информации за скенирани фискални сметки од страна на еден корисник на истоимената апликација.
//   Податоците за фискалните сметки се во следниот формат:

// ID item_price1 item_tax_type1 item_price2 item_tax_type2 … item_price-n item_tax_type-n

// На пример: 12334 1789 А 1238 B 1222 V 111 V

// Постојат три типа на данок на додадена вредност и тоа:

// А (18% од вредноста)
// B (5% од вредноста)
// V (0% од вредноста)
// Повратокот на ДДВ изнесува 15% од данокот на додадената вредност за артикалот.

// Да се имплементираат методите:

// void readRecords (InputStream inputStream)- метод којшто ги чита од влезен тек податоците за фискалните сметки. 
//    Доколку е скенирана фискална сметка со износ поголем од 30000 денари потребно е да се фрли исклучок од тип AmountNotAllowedException.
//    Дефинирајте каде ќе се фрла исклучокот, и каде ќе биде фатен, на начин што оваа функција, ќе може да ги прочита сите фискални коишто се скенирани. 
//    Исклучокот треба да испечати порака “Receipt with amount [сума на сите артикли] is not allowed to be scanned”.
// void printTaxReturns (OutputStream outputStream) - 
//   метод којшто на излезен тек ги печати сите скенирани фискални сметки во формат “ID SUM_OF_AMOUNTS TAX_RETURN”, 
//   каде што SUM_OF_AMOUNTS e збирот на сите артикли во фискалната сметка, а TAX_RETURN е пресметаниот повраток на ДДВ за таа фискална сметка.


import java.io.*;
import java.util.*;

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(double amount) {
        super(String.format("Receipt with amount %.0f is not allowed to be scanned", amount));
    }
}

class Receipt {
    String id;
    double sum;
    double taxReturn;

    public Receipt(String id, double sum, double taxReturn) {
        this.id = id;
        this.sum = sum;
        this.taxReturn = taxReturn;
    }
}

class MojDDV {

    List<Receipt> receipts = new ArrayList<>();

    void readRecords(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");

                String id = parts[0];
                double sum = 0;
                double tax = 0;

                try {
                    for (int i = 1; i < parts.length; i += 2) {
                        double price = Double.parseDouble(parts[i]);
                        String type = parts[i + 1];

                        sum += price;

                        if (type.equals("A")) {
                            tax += price * 0.18;
                        } else if (type.equals("B")) {
                            tax += price * 0.05;
                        }
                    }

                    if (sum > 30000) {
                        throw new AmountNotAllowedException(sum);
                    }

                    receipts.add(new Receipt(id, sum, tax * 0.15));

                } catch (AmountNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printTaxReturns(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);

        for (Receipt r : receipts) {
            pw.printf("%s %.0f %.2f\n", r.id, r.sum, r.taxReturn);
        }

        pw.flush();
    }
}
