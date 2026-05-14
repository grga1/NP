import java.util.*;

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

// вашиот код овде
class InvalidPositionException extends Exception{
    public InvalidPositionException(int position) {
        super("Invalid position "+position+", alredy taken!");
    }
}
class Component{
    String color;
    int weight;
   List<Component> deca;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        this.deca = new ArrayList<>();
    }
    void addComponent(Component component){
         deca.add(component);
        deca.sort(Comparator.comparing(Component::getWeight).thenComparing(Component::getColor));

    }
    void changeColor(int weight, String color){
        if (this.weight < weight)  // провери ја и себе си
            this.color = color;
        for (Component c : deca){
            c.changeColor(weight, color);
        }
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }


    public String toString(int level) {
        StringBuilder sb = new StringBuilder();
        String crticki = "---".repeat(level);
        sb.append(crticki);
        sb.append(weight).append(":").append(color).append("\n");
        deca.stream().forEach(x->sb.append(x.toString(level+1)));
        return sb.toString();
    }
}
class Window {
    String name;
     Map<Integer,Component> mapa;
    public Window(String name) {
        this.name = name;
        this.mapa = new TreeMap<>();
    }
    void addComponent(int position, Component component) throws InvalidPositionException {
         if (mapa.containsKey(position)) throw  new  InvalidPositionException(position);
          mapa.put(position,component);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WINDOW ").append(name).append("\n");
        mapa.entrySet().stream().forEach(x->sb.append(x.getKey()+":"+x.getValue().toString(0)));
//         mapa.values().stream().forEach(x->sb.append(x.toString(0)));
         return sb.toString();
    }
    void changeColor(int weight, String color){
        mapa.values().stream().forEach(x->x.changeColor(weight,color));
    }
    void swichComponents(int pos1, int pos2){
        Component c1 = mapa.get(pos1);
        Component c2 = mapa.get(pos2);
        mapa.put(pos1,c2);
        mapa.put(pos2,c1);
    }
}
