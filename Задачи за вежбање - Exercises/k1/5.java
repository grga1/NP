import java.util.Scanner;
import  java.util.*;


class MinMax<T extends Comparable<T>>{
    T min;
    T max;
    private List<T> elements;

    public MinMax() {
        this.min = null;
        this.max = null;
        this.elements = new ArrayList<>();
    }

    void update(T element){
        elements.add(element);

        if (min == null) {
            min = max = element;
        } else if (element.compareTo(min) < 0) {
            min = element;
        } else if (element.compareTo(max) > 0) {
            max = element;
        }
    }
    public T min() {
        return min;
    }

    public T max() {
        return max;
    }

    @Override
    public String toString() {
        int count = 0;
        for (T e : elements){
            if (!e.equals(min)&&!e.equals(max)){
                count++;
            }
        }
        return String.format("%s %s %d\n",min,max,count);
    }
}
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
