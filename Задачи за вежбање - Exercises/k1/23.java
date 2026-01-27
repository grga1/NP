import java.util.*;
import java.util.stream.Collectors;

class Particpant{
    String code;
    String name;
    int age;

    public Particpant(String code, String name, int age) {
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
class Audition{
    HashMap<String,List<Particpant>> mapa = new HashMap<>();
    void addParticpant(String city, String code, String name, int age){
        if (!mapa.containsKey(city)){
            mapa.put(city,new ArrayList<>());
            Particpant p = new Particpant(code,name,age);
            mapa.get(city).add(p);
        }else if(mapa.containsKey(city)){
            List<Particpant> pom = mapa.get(city);
            if(!pom.stream().anyMatch(x-> Objects.equals(code, x.code))){
                Particpant p = new Particpant(code,name,age);
                mapa.get(city).add(p);
            }
        }

    }
    void sort(){
    }
    void listByCity(String city){
        List<Particpant> lista = mapa.get(city);
        lista.sort(
                Comparator.comparing(Particpant::getName)
                        .thenComparing(Particpant::getAge)
                        .thenComparing(Particpant::getCode));

        for (Particpant p : lista){
            System.out.println(p.code+" "+p.name+" "+p.age);
        }
    }
}
public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}
