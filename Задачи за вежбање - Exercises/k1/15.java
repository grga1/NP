import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class Measurment{
    float temperature;
    float wind;
    float humidity;
    float visibility;
    Date date;

    public Measurment(float temperature, float wind, float humidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }
    @Override
    public String toString() {
        return String.format(Locale.US, "%.1f %.1f km/h %.1f%% %.1f km %s",
                temperature, wind, humidity, visibility, date.toString());
    }
}
class WeatherStation{
    private int days;
     List<Measurment> merenja ;
    private static final long twoHalfMinutes= 150000;

    public WeatherStation(int days) {
        this.days = days;
        this.merenja = new ArrayList<>();
    }
    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
    long newTime = date.getTime();

    boolean tooClose = merenja.stream()
             .anyMatch(i->Math.abs(newTime-i.date.getTime())<twoHalfMinutes);
    if (tooClose){
     return;
    }
        Measurment m = new Measurment(temperature, wind, humidity, visibility, date);
        merenja.add(m);
        long maxAgeMs = (long) days*24*60*60*1000;
        merenja.removeIf(old->newTime-old.date.getTime()>maxAgeMs);
    }

    public int total(){
        return (int) merenja.stream().count();
    }
    public void status(Date from, Date to) throws RuntimeException {
        List<Measurment> filtered = merenja.stream()
                .filter(m -> !m.date.before(from) && !m.date.after(to))
                .sorted(Comparator.comparing(m->m.date))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            throw new RuntimeException();
        }
        filtered.forEach(x-> System.out.println(x.toString()));
        double average = filtered.stream().mapToDouble(m->m.temperature).average().getAsDouble();
        System.out.printf(Locale.US, "Average temperature: %.2f%n", average);
    }
}
public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde
