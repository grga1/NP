// Да се дефинира класа Component во која се чуваат:

// бојата
// тежината
// колекција од внатрешни компоненти (референци од класата Component).
// Во оваа класа да се дефинираат методите:

// Component(String color, int weight) - конструктор со аргументи боја и тежина
// void addComponent(Component component) - за додавање нова компонента во внатрешната колекција (во оваа колекција компонентите секогаш се подредени според тежината во растечки редослед, ако имаат иста тежина подредени се алфабетски според бојата).
// Да се дефинира класа Window во која се чуваат:

// име
// компоненти.
// Во оваа класа да се дефинираат следните методи:

// Window(String) - конструктор
// void addComponent(int position, Component component) - додава нова компонента на дадена позиција (цел број). На секоја позиција може да има само една компонента, ако се обидеме да додадеме компонента на зафатена позиција треба да се фрли исклучок од класата InvalidPositionException со порака Invalid position [pos], alredy taken!. Компонентите се подредени во растечки редослед според позицијата.
// String toString() - враќа стринг репрезентација на објектот (дадена во пример излезот)
// void changeColor(int weight, String color) - ја менува бојата на сите компоненти со тежина помала од проследената
// void swichComponents(int pos1, int pos2) - ги заменува компонените од проследените позиции.





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

class Component implements Comparable<Component> {
    int weight;
    String color;
    Set<Component> inner;

    public Component( String color,int weight) {
        this.weight = weight;
        this.color = color;
        this.inner = new TreeSet<>();
    }
    void addComponent(Component component){
        inner.add(component);
    }

    void changeColor(int weight,String color){
        if (this.weight<weight){
            this.color=color;
        }
        for (Component c:inner){
            c.changeColor(weight, color);
        }
    }
    @Override
    public int compareTo(Component o) {
        int w = this.weight-o.weight;
        if (w==0) return this.color.compareTo(o.color);
        return w;
    }


    static void createString(StringBuilder sb,Component component,int level) {
        for (int i = 0; i < level; i++) {
            sb.append("---");
        }
        sb.append(String.format("%d:%s\n", component.weight,component.color));
        for (Component child : component.inner){
            createString(sb,child,level+1);
        }
    }
}

class Window {
    String name;
    Map<Integer, Component> components;

    public Window(String name) {
        this.name = name;
        components = new TreeMap<>();
    }

    void addComponent(int position, Component component)
            throws InvalidPositionException {

        if (components.containsKey(position)) {
            throw new InvalidPositionException(position);
        }
        components.put(position, component);
    }

    void changeColor(int weight, String color) {
       for (Component c: components.values()){
           c.changeColor(weight, color);
       }
    }

    void swichComponents(int pos1, int pos2) {
       Component p1 = components.get(pos1);
       Component p2 = components.get(pos2);
       components.put(pos1,p2);
       components.put(pos2,p1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("WINDOW %s\n",name));
        for (Map.Entry<Integer,Component>e: components.entrySet()){
            sb.append(String.format("%d:",e.getKey()));
            Component.createString(sb,e.getValue(),0);
        }
        return sb.toString();
    }
}
class InvalidPositionException extends Exception {
    public InvalidPositionException(int pos) {
        super(String.format("Invalid position %d, alredy taken!", pos));
    }
}
