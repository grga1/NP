import java.util.*;

class InvalidPositionException extends Exception {
    public InvalidPositionException(String message) {
        super(message);
    }
}

class Component{
    private String color;
    private int weight;
    private List<Component> innerComponents;

    public Component( String color,int weight) {
        this.weight = weight;
        this.color = color;
        this.innerComponents = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    public List<Component> getInnerComponents() {
        return innerComponents;
    }

    public int getWeight() {
        return weight;
    }
    void addComponent(Component component){
        innerComponents.add(component);
        innerComponents.sort(
                Comparator.comparingInt(Component::getWeight)
                        .thenComparing(Component::getColor)
        );
    }
    void changeColorRecursive(int limitWeight,String newColor){
        if (this.weight<limitWeight){
            this.color=newColor;
        }
        for (Component c:innerComponents){
            c.changeColorRecursive(limitWeight,newColor);
        }
    }
    private String toString(String indent){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%d:%s\n",indent,weight,color));
        for (Component c:innerComponents){
            sb.append(c.toString(indent+"---"));
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        return toString("");
    }
}
class Window{
    private String name;
    private Map<Integer,Component> components;

    public Window(String name) {
        this.name = name;
        this.components = new TreeMap<>();
    }
    void addComponent(int position, Component component) throws InvalidPositionException{
        if (components.containsKey(position)){
            throw  new InvalidPositionException(
                    String.format("Invalid position %d, alredy taken!", position)
            );
        }
        components.put(position,component);
    }
    void changeColor(int weight,String color){
        for (Component c: components.values()){
            c.changeColorRecursive(weight,color);
        }
    }
    void swichComponents(int pos1, int pos2){
     Component c1 = components.get(pos1);
     Component c2 = components.get(pos2);
     components.put(pos1,c2);
     components.put(pos2,c1);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("WINDOW %s\n",name));
        for (Map.Entry<Integer,Component> e : components.entrySet()){
//            sb.append(String.format("Position %d:\n",e.getKey()));
            sb.append(e.getKey()+":"+e.getValue().toString());
        }
        return sb.toString();
    }
}
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
