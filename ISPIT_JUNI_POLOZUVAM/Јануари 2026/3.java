// а се дефинира класа FlightRewardsEvaluator за работа со патници во програма за лојалност на една авиокомпанија. Класата треба да ги има следните функционалности: - FlightRewardsEvaluator() – Конструктор.

// public void loadFlights(InputStream is) – Методот од влезниот поток ги чита летовите за секој патник и ги зачувува во соодветна мапа.

// Секој ред ги содржи податоците за еден патник во формат: passenger_id flight1 flight2 ... flightN, каде секој лет (flight) е во формат destination:miles.
// Бројот на летови по патник е произволен. Се смета дека една дестинација може да се појави само еднаш кај еден патник.
// public void printPassengers(OutputStream os) – Методот на излезниот поток ги печати патниците и нивните информации, како во тест примерите. При печатење, патниците треба да бидат подредени според вкупните изминати милји во опаѓачки редослед, па потоа според passenger_id во растечки редослед.

// public Map<String, Integer> groupByDestination() – Методот враќа мапа во која клуч е дестинацијата, а вредност е бројот на патници кои имаат барем еден лет до таа дестинација. Мапата е сортирана според дестинацијата, во растечки редослед.

// --

// Define a class FlightRewardsEvaluator for working with passengers in an airline loyalty program.
// The class should have the following functionalities:

// FlightRewardsEvaluator() – Constructor.

// public void loadFlights(InputStream is) – This method reads the flights for each passenger from the input stream and stores them in an appropriate map.

// Each line contains the data for one passenger in the format: passenger_id flight1 flight2 ... flightN, where each flight is in the format destination:miles.
// The number of flights per passenger is arbitrary. It is assumed that a destination can appear only once for a given passenger.
// public void printPassengers(OutputStream os) – This method prints the passengers and their information to the output stream, as shown in the test examples. When printing, passengers should be sorted by total miles traveled in descending order, and then by passenger_id in ascending order.

// public Map<String, Integer> groupByDestination() – This method returns a map where the key is the destination and the value is the number of passengers who have at least one flight to that destination. The map is sorted by destination in ascending order.





// package midterms.january;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

class Flight{
    String id;
    int destinations=0;
    int miles=0;

    public Flight(String id,int destinations, int miles) {
        this.id = id;
        this.destinations = destinations;
        this.miles = miles;
    }

    public int getMiles() {
        return miles;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Passenger ["+id+"] totalMiles ["+miles+"] "+"destinations ["+destinations+"]";
    }
}
public class FlightRewardsEvaluator {
     List<Flight> lista;
     Map<String,Integer> mapa;
    FlightRewardsEvaluator(){
      this.lista = new ArrayList<>();
      this.mapa = new TreeMap<>();
    }
    public void loadFlights(InputStream is) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        String line;


        try {
            while ((line = bf.readLine()) != null) {
                String[] parts = line.split("[\\s+:]");
                String id="";
                id=parts[0];
                int destinations=0;
                int miles=0;
                for (int i = 1; i < parts.length; i++) {
                    if (i%2!=0){
                        destinations++;
                        if (mapa.containsKey(parts[i])){
                            mapa.put(parts[i],mapa.get(parts[i])+1);
                        }else{
                            mapa.put(parts[i],1);
                        }
                    }else{
                        miles+=Integer.parseInt(parts[i]);
                    }
                }
                lista.add(new Flight(id,destinations,miles));
                  
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void printPassengers(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        lista.stream().sorted(Comparator.comparing(Flight::getMiles).reversed().thenComparing(Flight::getId)).forEach(x->pw.println(x.toString()));
     pw.flush();
    }

    public Map<String, Integer> groupByDestination() {
        return mapa;
    }

    static void wtf(Scanner sc) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("data.txt"));
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            if (line.equals("---")){
                break;
            }
            pw.println(line);
        }
        pw.flush();
    }


    public static void main(String[] args) throws Exception {
        FlightRewardsEvaluator evaluator = new FlightRewardsEvaluator();

        Scanner sc = new Scanner(System.in);
        wtf(sc);

        evaluator.loadFlights(new FileInputStream("data.txt"));

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));

        String command = sc.nextLine();
        switch (command) {
            case "PRINT":
                evaluator.printPassengers(System.out);
                break;

            case "GROUP":
                evaluator.groupByDestination().forEach((dest, cnt) ->
                        pw.printf("Destination [%s] passengers [%d]%n", dest, cnt));
                pw.flush();
                break;

            default:
                pw.println("Invalid command");
                pw.flush();
                break;
        }
    }

}
