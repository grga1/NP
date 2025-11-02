// Да се имплемнтира генеричка класа Triple (тројка) од нумерички вредности (три броја). За класата да се имплементираат:

// конструктор со 3 аргументи,
// double max() - го враќа најголемиот од трите броја
// double average() - кој враќа просек на трите броја
// void sort() - кој ги сортира елементите во растечки редослед
// да се преоптовари методот toString() кој враќа форматиран стринг со две децимални места за секој елемент и празно место помеѓу нив.

import java.util.*;
public class TripleTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		int c = scanner.nextInt();
		Triple<Integer> tInt = new Triple<Integer>(a, b, c);
		System.out.printf("%.2f\n", tInt.max());
		System.out.printf("%.2f\n", tInt.avarage());
		tInt.sort();
		System.out.println(tInt);
		float fa = scanner.nextFloat();
		float fb = scanner.nextFloat();
		float fc = scanner.nextFloat();
		Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
		System.out.printf("%.2f\n", tFloat.max());
		System.out.printf("%.2f\n", tFloat.avarage());
		tFloat.sort();
		System.out.println(tFloat);
		double da = scanner.nextDouble();
		double db = scanner.nextDouble();
		double dc = scanner.nextDouble();
		Triple<Double> tDouble = new Triple<Double>(da, db, dc);
		System.out.printf("%.2f\n", tDouble.max());
		System.out.printf("%.2f\n", tDouble.avarage());
		tDouble.sort();
		System.out.println(tDouble);
	}
}
// vasiot kod ovde


class Triple<T extends Number & Comparable<T>> {
    private T first;
    private T second;
    private T third;

    public Triple(T first, T second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    // го враќа најголемиот од трите
    public double max() {
        T m = first;
        if (second.compareTo(m) > 0) m = second;
        if (third.compareTo(m) > 0) m = third;
        return m.doubleValue();
    }

    // просек (име оставено како во тестот: avarage)
    public double avarage() {
        return (first.doubleValue() + second.doubleValue() + third.doubleValue()) / 3.0;
    }

    // сортирање во растечки редослед
    public void sort() {
        List<T> list = new ArrayList<>(Arrays.asList(first, second, third));
        Collections.sort(list);
        first = list.get(0);
        second = list.get(1);
        third = list.get(2);
    }

    // печатење со 2 децимали и празно место меѓу елементите
    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",
                first.doubleValue(), second.doubleValue(), third.doubleValue());
    }
}


