import java.util.*;
import java.util.stream.Collectors;

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde
class Name{
    String name;

    public Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int unique(){
        Set<Character> bukvi = new HashSet<>();
        for (Character c :getName().toCharArray()){
            bukvi.add(Character.toLowerCase(c));
        }
        return bukvi.size();
    }
}
class Names{
    List<Name> iminja;

    public Names() {
        this.iminja = new ArrayList<>();
    }

    public void addName(String name){
        iminja.add(new Name(name));
    }

    public void printN(int n){
        iminja.sort(Comparator.comparing(Name::getName));
     HashMap<String,Integer> mapa = new HashMap<>();

        for (Name name :iminja){
        if (mapa.containsKey(name.getName())){
            mapa.put(name.getName(),mapa.get(name.getName())+1);
        }else{
            mapa.put(name.getName(),1);
        }
     }
        List<Map.Entry<String,Integer>> lista = new ArrayList<>(mapa.entrySet());
        lista.sort(Map.Entry.comparingByKey());
        for (Map.Entry<String, Integer> entry : lista) {
            if (entry.getValue() >= n) {
                Set<Character> bukvi = new HashSet<>();
                for (Character c :entry.getKey().toCharArray()){
                    bukvi.add(Character.toLowerCase(c));
                }
                System.out.println(entry.getKey() + " (" + entry.getValue()+") "+bukvi.size());
            }
        }
    }
    public String findName(int len, int x) {
        TreeSet<String> set = new TreeSet<>(); // важен е TreeSet наместо HashSet

        for (Name n : iminja) {
            if (n.getName().length() < len) {
                set.add(n.getName());
            }
        }

        // 2. Префрли во листа за пристап по индекс
        List<String> lista = new ArrayList<>(set);

        // (опционално, за safety)
        if (lista.isEmpty()) {
            return ""; // или некоја порака, ама во тестовите нема да биде празно
        }

        // 3. Вртење во круг со modulo
        int idx = x % lista.size(); // индексот од задачата е 0-based

        return lista.get(idx);
    
    }
}
