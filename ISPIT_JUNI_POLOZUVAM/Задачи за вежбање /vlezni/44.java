// Да се имплементира класа за календар на настани EventCalendar. Секој настан е дефиниран со:

// име
// локација
// време (Date).
// Класата треба да ги овозможува следните функционалности:

// public EventCalendar(int year) - конструктор со еден аргумент годината на календарот
// public void addEvent(String name, String location, Date date) - додава нов настан зададен со име, локација и време. Ако годината на настанот не се совпаѓа со годината на календарот да се фрли исклучок од вид WrongDateException со порака Wrong date: [date].
// public void listEvents(Date date) - ги печати сите настани на одреден датум (ден) подредени според времето на одржување во растечки редослед (ако два настани имаат исто време на одржување, се подредуваат лексикографски според името). Добивањето колекција од настани на одреден датум треба да биде во константно време $O(1)$, а печатењето во линеарно време $O(n)$ (без сортирање, само изминување)! Форматот на печатење настан е dd MMM, YYY HH:mm at [location], [name].
// public void listByMonth() - ги печати сите месеци (1-12) со бројот на настани во тој месец.

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde

class WrongDateException extends Exception{
    public WrongDateException(Date date) {
        super("Wrong date: " + date);
    }
}
class Event{
    String name;
    String location;
    String date;

    public Event(String name, String location, String date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date+" at "+location+", "+name;
    }
}
class EventCalendar{
    int year;
    Map<String, TreeSet<Event>> mapa;
    Map<Integer,Integer> mapa2;
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");

    public EventCalendar(int year){
     this.year=year;
     this.mapa = new TreeMap<>();
     this.mapa2 = new TreeMap<>();
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, YYY HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        int key = cal.get(Calendar.MONTH)+1;


        if (cal.get(Calendar.YEAR)!=this.year) throw  new WrongDateException(date);
        if (mapa2.isEmpty()){
        for (int i = 1; i <=12 ; i++) {
            mapa2.put(i,0);
        }}

        mapa2.put(key,mapa2.get(key)+1);


        if (mapa.containsKey(sdf2.format(date))){
            mapa.get(sdf2.format(date)).add(new Event(name, location, sdf.format(date)));
        }else{
            mapa.put(sdf2.format(date),new TreeSet<>(Comparator.comparing(Event::getDate).thenComparing(Event::getName)) );
            mapa.get(sdf2.format(date)).add(new Event(name,location,sdf.format(date)));
        }
    }
    public void listEvents(Date date){
        if (mapa.get(sdf2.format(date))==null||mapa.get(sdf2.format(date)).isEmpty()){
            System.out.println("No events on this day!");
            return;
        }
        TreeSet<Event> e = mapa.get(sdf2.format(date));

        e.stream().forEach(System.out::println);
    }
    public void listByMonth(){
        mapa2.entrySet().stream().forEach(x-> System.out.println(x.getKey()+" : "+x.getValue()));
    }
}
