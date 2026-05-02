// Да се имплементира класа F1Race која ќе чита од влезен тек (стандарден влез, датотека, ...) 
// податоци за времињата од последните 3 круга на неколку пилоти на Ф1 трка. Податоците се во следниот формат:

// Driver_name lap1 lap2 lap3, притоа lap е во формат mm:ss:nnn каде mm се минути ss се секунди 
// nnn се милисекунди (илјадити делови од секундата). Пример:

// Vetel 1:55:523 1:54:987 1:56:134.

// Ваша задача е да ги имплементирате методите:

// F1Race() - default конструктор
// void readResults(InputStream inputStream) - метод за читање на податоците
// void printSorted(OutputStream outputStream) - метод кој ги печати сите пилоти сортирани 
// според нивното најдобро време (најкраткото време од нивните 3 последни круга) во формат 
// Driver_name best_lap со 10 места за името на возачот (порамнето од лево) и 10 места за времето на најдобриот круг порамнето од десно. 
// Притоа времето е во истиот формат со времињата кои се читаат.








import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}
class vozac{
    String ime;
    String vreme;
    long vremeMillis;

    public vozac(String ime, String vreme, long vremeMillis) {
        this.ime = ime;
        this.vreme = vreme;
        this.vremeMillis = vremeMillis;
    }

    public String getIme() {
        return ime;
    }

    public String getVreme() {
        return vreme;
    }

    public long getVremeMillis() {
        return vremeMillis;
    }

    @Override
    public String toString() {
        return  ime + '\'' + vreme + '\'';
    }
}
class F1Race {
    // vashiot kod ovde
       List<vozac> vozacList;
    public F1Race() {
        this.vozacList = new ArrayList<>();
    }
    void readResults(InputStream inputStream){
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        try {

            String line;
            while ((line=bf.readLine())!=null){
                String[] parts = line.split("\\s+");

                String ime = parts[0];
                long min=1000000000;
                String vreme="";
                for (int i = 1; i < parts.length; i++) {
                    String vremeTemp=parts[i];
                    String[]vreminja = parts[i].split(":");
             

                    long temp =Long.parseLong(vreminja[0])*60*60*1000+Long.parseLong(vreminja[1])*60*1000+Long.parseLong(vreminja[2]);
                    if (temp<min){
                        min=temp;
                        vreme=vremeTemp;
                    }
                }
                vozacList.add(new vozac(ime,vreme,min));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    void printSorted(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream,true);
        AtomicInteger counter= new AtomicInteger(1);
        List<vozac> novaLista = vozacList.stream().sorted(Comparator.comparing(vozac::getVremeMillis)).collect(Collectors.toList());
        novaLista.stream().forEach(x-> pw.printf("%d. %-10s%10s\n", counter.getAndIncrement(),x.getIme(),x.getVreme()));
        pw.flush();
    }
}
