import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClusterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cluster<Point2D> cluster = new Cluster<>();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            long id = Long.parseLong(parts[0]);
            float x = Float.parseFloat(parts[1]);
            float y = Float.parseFloat(parts[2]);
            cluster.addItem(new Point2D(id, x, y));
        }
        int id = scanner.nextInt();
        int top = scanner.nextInt();
        cluster.near(id, top);
        scanner.close();
    }
}
interface ClusterElement {
    long getId();
    double distance(ClusterElement other);
}
class Cluster<T extends ClusterElement>{
    private List<T> elements = new ArrayList<>();
    public void addItem(T element) {
        elements.add(element);
    }
    void near(long id, int top){
        AtomicInteger counter= new AtomicInteger(1);
        T center = elements.stream()
                .filter(x->x.getId()==id)
                .findFirst()
                .orElse(null);

        if (center==null) return;

        elements.stream()
                .filter(e->e.getId()!=id)
                .sorted(Comparator.comparing(e->e.distance(center)))
                .limit(top)
                .forEach(
                        e-> System.out.printf(
                                "%d. %d -> %.3f%n",
                                counter.getAndIncrement(),
                                e.getId(),
                                e.distance(center)
                        ));
    }
}

class Point2D implements ClusterElement{
    long id;
    float x;
    float y;

    public Point2D(long id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    @Override
    public long getId() {
        return id;
    }
    @Override
    public double distance(ClusterElement other){
        Point2D p = (Point2D) other;
        double dx = this.x - p.x;
        double dy = this.y - p.y;
        return Math.sqrt(dx*dx+dy*dy);
    }
}
