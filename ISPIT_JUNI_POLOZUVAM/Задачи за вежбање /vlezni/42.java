// Да се имплементира класа DailyTemperatures во која се вчитуваат температури на воздухот (цели броеви) за различни денови од годината (број од 1 до 366). Температурите за еден ден се во еден ред во следниот формат (пример): 137 23C 15C 28C. Првиот број претставува денот во годината, а потоа следуваат непознат број на мерења на температури за тој ден во скала во Целзиусови степени (C) или Фаренхајтови степени (F).

// Во оваа класа да се имплементираат методите:

// DailyTemperatures() - default конструктор
// void readTemperatures(InputStream inputStream) - метод за вчитување на податоците од влезен тек
// void writeDailyStats(OutputStream outputStream, char scale) - метод за печатање на дневна статистика (вкупно мерења, минимална температура, максимална температура, просечна температура) за секој ден, подредени во растечки редослед според денот. Вториот аргумент scale одредува во која скала се печатат температурите C - Целзиусова, F - Фаренхајтова. Форматот за печатање на статистиката за одреден ден е следниот:
// [ден]: Count: [вк. мерења - 3 места] Min: [мин. температура] Max: [макс. температура] Avg: [просек ]

// Минималната, максималната и просечната температура се печатат со 6 места, од кои 2 децимални, а по бројот се запишува во која скала е температурата (C/F).

// Формула за конверзија од Целзиусуви во Фаренхајтови: $\frac{T * 9}{5} + 32$

// Формула за конверзија од Фаренхајтови во Целзиусуви: $\frac{(T - 32) * 5}{9}$

// Забелешка: да се постигне иста точност како во резултатите од решението, за пресметување на просекот и конверзијата во различна скала температурите се чуваат со тип Double.

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * I partial exam 2016
 */
public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

// Vashiot kod ovde
class DailyTemperatures {
    Map<Integer, List<Double>> mapa;

    public DailyTemperatures() {
        this.mapa = new TreeMap<>();
    }
    void readTemperatures(InputStream inputStream){
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try{
            while ((line= bf.readLine())!=null){
                String[] parts = line.split("\\s+");
                int den = Integer.parseInt(parts[0]);
                List<Double> temperaturi = new ArrayList<>();

                for (int i = 1; i < parts.length; i++) {
                   if (parts[i].length()==2&&String.valueOf(parts[i].charAt(1)).equals("C")){
                       Double broj = Double.parseDouble(parts[i].substring(0,1));
                       temperaturi.add(broj);
                   }else if (parts[i].length()==3&&String.valueOf(parts[i].charAt(2)).equals("C")){
                       Double broj = Double.parseDouble(parts[i].substring(0,2));
                       temperaturi.add(broj);
                   }else if (parts[i].length()==4&&String.valueOf(parts[i].charAt(3)).equals("C")){
                       Double broj = Double.parseDouble(parts[i].substring(0,3));
                       temperaturi.add(broj);
                   }else  if (parts[i].length()==2&&String.valueOf(parts[i].charAt(1)).equals("F")){
                       Double broj = Double.parseDouble(parts[i].substring(0,1));
                       temperaturi.add((broj-32.0)*5.0/9.0);
                   }else if (parts[i].length()==3&&String.valueOf(parts[i].charAt(2)).equals("F")){
                       Double broj = Double.parseDouble(parts[i].substring(0,2));
                       temperaturi.add((broj-32.0)*5.0/9.0);
                   }else if (parts[i].length()==4&&String.valueOf(parts[i].charAt(3)).equals("F")){
                       Double broj = Double.parseDouble(parts[i].substring(0,3));
                       temperaturi.add((broj-32.0)*5.0/9.0);
                   }
                }
               mapa.put(den,temperaturi);

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    void writeDailyStats(OutputStream outputStream, char scale){
        PrintWriter pw = new PrintWriter(outputStream);
         for (Map.Entry<Integer,List<Double>>e:mapa.entrySet()){
             if (scale=='C') {
                 pw.printf("%3d: Count: %3d Min: %6.2fC Max: %6.2fC Avg: %6.2fC\n", e.getKey(), e.getValue().size(),
                         e.getValue().stream().min(Double::compareTo).get(),
                         e.getValue().stream().max(Double::compareTo).get(),
                         e.getValue().stream().mapToDouble(x -> x).average().orElse(0.0));
             }else{
                 List<Double> lista = e.getValue().stream().map(x->(x*9.0)/5.0+32.0).collect(Collectors.toList());
                 pw.printf("%3d: Count: %3d Min: %6.2fF Max: %6.2fF Avg: %6.2fF\n", e.getKey(), lista.size(),
                         lista.stream().min(Double::compareTo).get(),
                         lista.stream().max(Double::compareTo).get(),
                         lista.stream().mapToDouble(x -> x).average().orElse(0.0));
             }
         }
        pw.flush();
    }
}
