EDEN TESET CASE IMA GRESEN OUTPUT NA COURSES
ZADACATA E TOCNO RESENA!

// Да се имплементира класа FileSystem за едноставен податочен систем. За вашиот податочен систем треба да имплементирате сопствена класа за датотека File со податоци за име (String), големина (Integer) и време на креирање (LocalDateTime) Класата треба да ги овозможува следните функционалности:

// public void addFile(char folder, String name, int size, LocalDateTime createdAt) - метод за додавање нова датотека File во фолдер со даденото име (името на фолдерот е еден знак, може да биде . или голема буква)
// public List<File> findAllHiddenFilesWithSizeLessThen(int size) - враќа листа на сите скриени датотеки (тоа се датотеки чие што име започнува со знакот за точка .) со големина помала од size.
// public int totalSizeOfFilesFromFolders(List<Character> folders) - враќа вкупна големина на сите датотеки кои се наоѓаат во фолдерите кои се зададени во листата folders
// public Map<Integer, Set<File>> byYear() - враќа мапа Map во која за датотеките се групирани според годината на креирање.
// public Map<String, Long> sizeByMonthAndDay() - враќа мапа Map во која за секој месец и ден (независно од годината) се пресметува вкупната големина на сите датотеки креирани во тој месец и тој ден. Месецот се добива со повик на методот getMonth(), а денот getDayOfMonth().
// Датотеките во секој фолдер се подредени според датумот на креирање во растечки редослед, потоа според името лексикографски и на крај според големината. Да се имплементира ваков компаратор во самата класа File. Исто така да се имплементира и toString репрезентација во следниот формат:

// %-10[name] %5[size]B %[createdAt]

  import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */
public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here
class File implements Comparable<File>{
    String name;
    int size;
    LocalDateTime createdAt;

    public File(String name, int size, LocalDateTime createdAt) {
        this.name = name;
        this.size = size;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public int compareTo(File o) {
        int cmp = this.createdAt.compareTo(o.createdAt);
        if (cmp!=0) return cmp;

        cmp = this.name.compareTo(o.name);
        if (cmp!=0) return cmp;

        return Integer.compare(this.size,o.size);
    }


    @Override
    public String toString() {
        return String.format("%-10s %5dB %s",name,size,createdAt);
    }
}

class FileSystem{
    HashMap<Character,List<File>>mapa;
    List<File> files;

    public FileSystem() {
        this.mapa = new HashMap<>();
        this.files = new ArrayList<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt){
        File fajl = new File(name, size, createdAt);
        files.add(fajl);

      mapa.putIfAbsent(folder,new ArrayList<>());
      mapa.get(folder).add(fajl);

    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size){
       return files.stream().filter(x->x.getName().startsWith(".")&&x.getSize()<size).sorted().collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders){
      return folders.stream().mapToInt(a->{
         return mapa.get(a).stream().mapToInt(b->b.size).sum();
              })
              .sum();
    }

    public Map<Integer, Set<File>> byYear(){
        Map<Integer,Set<File>> byYear = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry<Character,List<File>>e:mapa.entrySet()){
            for (File file:e.getValue()){
                byYear.putIfAbsent(file.getCreatedAt().getYear(),new TreeSet<>());
                byYear.get(file.createdAt.getYear()).add(file);
            }
        }
        return byYear;
    }

    public Map<String, Long> sizeByMonthAndDay(){
        Map<String,Long> sizebymonthandday = new TreeMap<>();
        for(Map.Entry<Character,List<File>>e:mapa.entrySet()){
            for (File file:e.getValue()){
                sizebymonthandday.putIfAbsent(file.getCreatedAt().getMonth()+"-"+file.getCreatedAt().getDayOfMonth(),0L);
                sizebymonthandday
                        .put(file.getCreatedAt().getMonth()+"-"+file.getCreatedAt().getDayOfMonth(),sizebymonthandday.get(file.getCreatedAt().getMonth()+"-"+file.getCreatedAt().getDayOfMonth())+file.size);
            }
        }
      return sizebymonthandday;
    }
}
