import java.util.*;
import java.util.stream.*;

class Student{
    String index;
    List<Integer> points;
    Integer niza[];
    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;

    }

    public String getIndex() {
        return index;
    }

    public List<Integer> getPoints() {
        return points;
    }


    String failedStudent() {
        // Ако големината на листата е помала од 8, значи има повеќе од 2 отсуства
        if (points.size() < 8) {
            return "NO";
        } else {
            return "YES";
        }
    }
   Double getAveragePoints(){
   return points.stream().mapToDouble(x->x).sum()/10;
    }
    @Override
    public String toString() {
        return String.format("%s %s %.2f ",getIndex(),failedStudent(),getAveragePoints());
    }
}
class LabExercises{
    List<Student> students;

    public LabExercises() {
        this.students = new ArrayList<>();
    }
    public void addStudent (Student student){

       students.add(student);
    }
    public void printByAveragePoints (boolean ascending, int n){
        Comparator<Student> comparator =
                Comparator.comparing(Student::getAveragePoints)
                        .thenComparing(Student::getIndex);


        if (!ascending){
            comparator=comparator.reversed();
        }
        students.stream()
                .sorted(comparator)
                .limit(n)
                .forEach(System.out::println);
    }
    public List<Student> failedStudents () {
       return students.stream()
               .filter(s->s.failedStudent().equals("NO"))
               .sorted(Comparator.comparing(Student::getIndex).
                       thenComparing(Student::getAveragePoints))
               .collect(Collectors.toList());

    }
    public Map<Integer,Double> getStatisticsByYear(){
        Map<Integer,List<Double>> mapa = new HashMap<>();
        
 
        for (Student s:students){
            int godina = 20-Integer.parseInt(s.getIndex().substring(0,2));
            if (s.failedStudent()=="NO"){
                continue;
            }else{
                if(mapa.containsKey(godina)){
                    mapa.get(godina).add(s.getAveragePoints());
                }else{
                    mapa.put(godina,new ArrayList<>());
                    mapa.get(godina).add(s.getAveragePoints());
                }
            }

         }
        Map<Integer,Double> konecno = new HashMap<>();
        for(Map.Entry<Integer,List<Double>>entry:mapa.entrySet()){
            if (!konecno.containsKey(entry.getKey())){
                konecno.put(entry.getKey(),entry.getValue().stream().mapToDouble(x->x).average().getAsDouble());
            }
        }
        return konecno;
    }
}
public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}
