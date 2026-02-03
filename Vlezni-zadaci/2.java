// На влез во прва линија имаш задолжителни книги b1,b2,b3... одвоени со , .
//   Во секој следен ред имаш id на читателите и книгите што ги прочитале (id1 b1 b5 b7 b2). 
//   Треба да изброиш колку книги од задолжителните има прочитано секој од читателите.
//   Ако ги има сите добива 100поени, ако има барем 2 тогаш 70поени, ако нема ниедна 0.
//   Се печатат id на читателите и бројот на освоени поени, сортирани прво по бројот на поени (опаѓачки), после по ID.

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;
public class MandatoryBooksTest {
    public static void main(String[] args) throws IOException {
        MandatoryBooks mandatoryBooks = new MandatoryBooks();
        mandatoryBooks.evaluate(System.in, System.out);
    }
}
class Citatel{
    int id;
    int poeni;

    public Citatel(int id, int poeni) {
        this.poeni = poeni;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPoeni() {
        return poeni;
    }

    @Override
    public String toString() {
        return String.format("%d %d",getId(),getPoeni());
    }
}
class MandatoryBooks{
    public void evaluate(InputStream is, OutputStream os) throws IOException {
        // тука ја пишуваш логиката:
        // 1. ја читаш првата линија со задолжителни книги (одделени со ,)
        // 2. за секој следен ред го читаш id-то и книгите
        // 3. сметаш поени (100 / 70 / 0)
        // 4. ги сортираш по поени (опаѓачки), па по id (растечки)
        // 5. печатиш во os
        Scanner sc = new Scanner(is);
        List<String> knigi = Arrays.asList(sc.nextLine().split(","));
        List<Citatel> citateli = new ArrayList<>();
        while (sc.hasNextLine()){
            String token = sc.nextLine();
            if (token.isEmpty()) break;
            String[] parts = token.split(" ");

            int counter = 0;
            int id = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                  if (knigi.contains(parts[i])) counter++;
            }
            if (counter==0){
                citateli.add(new Citatel(id,0));
            }else if (counter==1){
                citateli.add(new Citatel(id,0));
            }else if (counter==2){
                citateli.add(new Citatel(id,70));
            }else if(counter==3){
                citateli.add(new Citatel(id,100));
            }
        }
        PrintWriter pw = new PrintWriter(os);
        citateli.sort(Comparator.comparing(Citatel::getPoeni).reversed().thenComparing(Citatel::getId));
        for (Citatel c : citateli) {
            pw.println(c);
        }
        pw.flush();
    }
}
