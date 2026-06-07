// Да се имплементира класа Discounts за обработка на информации за цени и цени на попуст на одредени производи во неколку продавници (објекти од класа Store). Потребно е да се имплементираат следните методи:

// public int readStores(InputStream inputStream) - метод за вчитување на податоците за продавниците и цените на производите. Податоците за секоја продавница се во посебен ред во формат [ime] [cena_na_popust1:cena1] [cena_na_popust2:cena2] ... (погледнете пример). Методот враќа колку продавници се вчитани.
// public List<Store> byAverageDiscount() - метод кој враќа листа од 3-те продавници со најголем просечен попуст (просечна вредност на попустот за секој производ од таа продавница). Попустот (намалувањето на цената) е изразен во цел број (проценти) и треба да се пресмета од намалената цена и оригиналната цена. Ако две продавници имаат ист попуст, се подредуваат според името лексикографски.
// public List<Store> byTotalDiscount() - метод кој враќа листа од 3-те продавници со намал вкупен попуст (сума на апсолутен попуст од сите производи). Апсолутен попуст е разликата од цената и цената на попуст. Ако две продавници имаат ист попуст, се подредуваат според името лексикографски.
// Дополнително за класата Store да се имплементира стринг репрезентација, односно методот:

// public String toString() кој ќе враќа репрезентација во следниот формат:

// [Store_name]
// Average discount: [заокружена вредност со едно децимално место]%
// Total discount: [вкупен апсолутен попуст]
// [процент во две места]% [цена на попуст]/[цена]
// ...
// при што продуктите се подредени според процентот на попуст (ако е ист, според апсолутниот попуст) во опаѓачки редослед.

// погледнете од примерот



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Discounts
 */
public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

// Vashiot kod ovde
class Discount{
    int procent;
    String popust;

    public Discount(int procent, String popust) {
        this.procent = procent;
        this.popust = popust;
    }

    public double getProcent() {
        return procent;
    }

    public String getPopust() {
        return popust;
    }
    public int getPrvBroj(){
        return Integer.parseInt(getPopust().split("/")[0]);
    }

}

class Store{
  String ime;
  List<Discount> discountList;
  int totalDiscount;

    public Store(String ime, List<Discount> discountList,int totalDiscount) {
        this.ime = ime;
        this.discountList = discountList;
        this.totalDiscount = totalDiscount;
    }

    double getAverageDiscount(){
        return discountList.stream().mapToDouble(x-> x.procent).average().orElse(0.0);
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s \nAverage discount: %.1f%%\nTotal discount: %d\n",ime,getAverageDiscount(),totalDiscount));
        List<Discount> newList = discountList.stream().sorted(Comparator.comparing(Discount::getProcent).thenComparing(Discount::getPrvBroj).reversed()).collect(Collectors.toList());
        for (int i = 0; i < newList.size(); i++) {
            if (i==newList.size()-1){
                sb.append(String.format("%2d%% %s", newList.get(i).procent,newList.get(i).popust));
            }else sb.append(String.format("%2d%% %s\n",newList.get(i).procent,newList.get(i).popust));
        }

        return sb.toString();
    }


    public String toString2() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s \nAverage discount: %.1f%%\nTotal discount: %d\n",ime,getAverageDiscount(),totalDiscount));
        List<Discount> newList = discountList.stream().sorted(Comparator.comparing(Discount::getProcent).thenComparing(Discount::getPrvBroj).reversed()).collect(Collectors.toList());
        for (int i = 0; i < newList.size(); i++) {
            if (i==newList.size()-1){
                sb.append(String.format("%2d%% %s", newList.get(i).procent,newList.get(i).popust));
            }else sb.append(String.format("%2d%% %s\n",newList.get(i).procent,newList.get(i).popust));
        }

        return sb.toString();
    }


}
class Discounts{
    List<Store> storeList;

    public Discounts() {
        this.storeList = new ArrayList<>();
    }

    public int readStores(InputStream inputStream){
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        int counter=0;
        String ime;
        try{
            while ((line=bf.readLine())!=null){
                counter++;
                String[] parts = line.split("\\s+");
                ime = parts[0];
                int totalDiscount=0;
                List<Discount> discountList = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    String[] parts2 = parts[i].split(":");
                    double x = Double.parseDouble(parts2[1])-Double.parseDouble(parts2[0]);
                   int procent = (int) (x /Double.parseDouble(parts2[1])*100);
                   discountList.add(new Discount(procent,parts2[0]+"/"+parts2[1]));
                   totalDiscount+= x;
                }
                storeList.add(new Store(ime,discountList,totalDiscount));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return  counter;
    }
    public List<Store> byAverageDiscount(){
       return storeList.stream().sorted(Comparator.comparing(Store::getAverageDiscount).reversed()).limit(3).collect(Collectors.toList());

    }
    public List<Store> byTotalDiscount(){
        return storeList.stream().sorted(Comparator.comparing(Store::getTotalDiscount)).limit(3).collect(Collectors.toList());
    }
}
