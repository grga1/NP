// Да се имплементира класа Subtitles која ќе чита од влезен тек (стандарден влез, датотека, ...) превод во стандарден srt формат. Секој еден елемент од преводот се состои од реден број, време на почеток на прикажување, време на крај на прикажување и текст и е во следниот формат (пример):

// 2
// 00:00:48,321 --> 00:00:50,837
// Let's see a real bet.
// Делот со текстот може да има повеќе редови. Сите елементи се разделени со еден нов ред.

// Ваша задача е да ги имплементирате методите:

// Subtitles() - default конструктор
// int loadSubtitles(InputStream inputStream) - метод за читање на преводот (враќа резултат колку елементи се прочитани)
// void print() - го печати вчитаниот превод во истиот формат како и при читањето.
// void shift(int ms) - ги поместува времињата на сите елементи од преводот за бројот на милисекунди кои се проследува како аргумент (може да биде негативен, со што се поместуваат времињата наназад).




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

// Вашиот код овде
class Subtitle{
    String redenBroj;
    String vreme;
    String text;
   int ms;

    public Subtitle(String redenBroj, String vreme, String text) {
        this.redenBroj = redenBroj;
        this.vreme = vreme;
        this.text = text;
    }

    public void setMs(int ms) {
        this.ms = ms;
    }
     void akcija(){
        String[] parts = vreme.split(" --> ");
        String[] startMs = parts[0].split("[:,]");
        String[] endMs = parts[1].split("[:,]");
        int cas1 = Integer.parseInt(startMs[0]);
        int minuta1 = Integer.parseInt(startMs[1]);
        int sekunda1 = Integer.parseInt(startMs[2]);
        int milisekunda1 = Integer.parseInt(startMs[3]);
        int vkupno1=cas1*3600000+minuta1*60000+sekunda1*1000+milisekunda1+ms;
         int cas11 = vkupno1/3600000; vkupno1=vkupno1%3600000;
         int minuta11 = vkupno1/60000; vkupno1=vkupno1%60000;
         int sekunda11 = vkupno1/1000; vkupno1=vkupno1%1000;
         int milisekunda11 = vkupno1;



         int cas2 = Integer.parseInt(endMs[0]);
         int minuta2 = Integer.parseInt(endMs[1]);
         int sekunda2 = Integer.parseInt(endMs[2]);
         int milisekunda2 = Integer.parseInt(endMs[3]);
         int vkupno2=cas2*3600000+minuta2*60000+sekunda2*1000+milisekunda2+ms;
         int cas22 = vkupno2/3600000; vkupno2=vkupno2%3600000;
         int minuta22 = vkupno2/60000; vkupno2=vkupno2%60000;
         int sekunda22 = vkupno2/1000; vkupno2=vkupno2%1000;
         int milisekunda22 = vkupno2;
         this.vreme = String.format("%02d:%02d:%02d,%03d --> %02d:%02d:%02d,%03d",
                 cas11, minuta11, sekunda11, milisekunda11,
                 cas22, minuta22, sekunda22, milisekunda22);
     }
    @Override
    public String toString() {
        return  redenBroj + '\n' +
                 vreme + '\n' +
                 text + '\n';
    }
}
class Subtitles{
    List<Subtitle> subtitleList;

    Subtitles(){
      this.subtitleList = new ArrayList<>();
    }

    int loadSubtitles(InputStream inputStream){
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        int count=0;
        String redenBroj;
        try{
            while ((redenBroj=bf.readLine())!=null) {
                 if (redenBroj.isBlank()) continue;
                String vreme = bf.readLine();
                StringBuilder text = new StringBuilder();

                while (((line = bf.readLine()) != null && !line.isBlank())) {
                    if (text.length() > 0) text.append("\n");
                    text.append(line);
                }
                subtitleList.add(new Subtitle(redenBroj, vreme, text.toString()));
                count++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return count;
    }

    void print(){
        subtitleList.stream().forEach(System.out::println);
    }

    void shift(int ms){
        subtitleList.stream().forEach(x->x.setMs(ms));
        subtitleList.stream().forEach(x->x.akcija());

    }

}
