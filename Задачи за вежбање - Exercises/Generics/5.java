import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}
class MinMax <T extends  Comparable<T>>{
  T min;
  T max;
 List<T> elements;
    public MinMax() {
     elements = new ArrayList<>();
    }
    void update(T element){
        elements.add(element);
        if (min == null){
            min=max=element;
        }else
        if (element.compareTo(max)>0){
            max = element;
        }else if (element.compareTo(min)<0){
            min = element;
        }
    }
    T max(){
        return max;
    }
    T min(){
        return min;
    }

    @Override
    public String toString() {
        int counter=0;
        for (T e : elements){
            if (!e.equals(max)&&!e.equals(min)) {
                counter++;
            }
        }
        return min +" "+ max+" "+counter+"\n";
    }
}
