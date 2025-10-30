// Да се дефинира интерфејс Movable што ќе ги дефинира основните својства на еден движечки објект:

// движење нагоре (void moveUp())
// движење надолу (void moveLeft())
// движење надесно (void moveRight())
// движење налево (void moveLeft())
// пристап до моменталните x,y координати на објектот (int getCurrentXPosition() и int getCurrentYPosition()).
// Постојат два типа на движечки објекти: движечка точка (MovingPoint) и движечки круг (MovingCircle). Да се дефинираат овие две класи коишто го имплементираат интерфејсот Movable.

// Во класата MovingPoint се чуваат информации за:

// x и y координати (цели броеви)
// xSpeed и ySpeed : степенот на поместување на движечката точка во x насока и y насока (цели броеви)
// За класата да се имплементираат:

// конструктор со аргументи: MovablePoint(int x, int y, int xSpeed, int ySpeed),
// методите наведени во интерфејсот Movable
// toString метод кој дава репрезентација на објектите во следнот формат Movable point with coordinates (5,35)
// Во класата MovingCircle се чуваат информации за:

// радиусот на движечкиот круг (цел број)
// центарот на движечкиот круг (објект од класата MovingPoint).
// За класата да се имплементираат:

// конструктор со аргументи: MovableCircle(int radius, MovablePoint center)
// методите наведени во интерфејсот Movable
// toString метод којшто дава репрезентација на објектите во следниот формат Movable circle with center coordinates (48,21) and radius 3
// Првите четири методи од Movable (moveUp, modeDown, moveRight, moveLeft) треба да фрлат исклучок од тип ObjectCanNotBeMovedException доколку придвижувањето во соодветната насока не е возможно, односно со придвижувањето се излегува од дефинираниот простор во класата MovablesCollection. При движење на објекти од тип MovableCircle се смета дека кругот излегол од просторот, доколку неговиот центар излезе од просторот. Дозволено е дел до кругот да излезе од просторот, се додека центарот е се уште во просторот. Справете се со овие исклучоци на соодветните места. Погледнете во тест примерите какви пораки треба да се печатат кога ќе се фати исклучок од овој тип и имплементирајте го истото.

// Да се дефинира класа MovablesCollection во која што ќе се чуваат информации за:

// низа од движечки објекти (Movable [] movable)
// статичка променлива за максималната вредност на координатата X (минималната е предодредена на 0)
// статичка променлива за максималната вредност на координатата Y (минималната е предодредена на 0)
// За класата да се имплементираат следните методи:

// конструктор MovablesCollection(int x_MAX, int y_MAX)
// void addMovableObject(Movable m) - метод за додавање на движечки објект во колекцијата од сите движечки објекти. Пред да се додади објектот, мора да се провери дали истиот е може да се вклопи во дефинираниот простор, односно истиот да не излегува од границите 0-X_MAX за x координатата и 0-Y_MAX за y координатата. Доколку станува збор за движечки круг, потребно е целиот круг да се наоѓа во наведениот интервал на вредности. Доколку движечкиот објект не може да биде вклопен во просторот, да се фрли исклучок од тип MovableObjectNotFittableException. Потребно е да се справите со исклучокот на соодветното место во main методот. Погледнете во тест примерите какви пораки треба да се печатат кога ќе се фати исклучок од овој тип и имплементирајте го истото.
// void moveObjectsFromTypeWithDirection (TYPE type, DIRECTION direction)- метод за придвижување на движечките објекти од тип type во насока direction. TYPE и DIRECTION се енумерации кои се задедени во почетниот код. Во зависност од насоката зададена во аргументот, да се повика соодветниот метод за придвижување.
// toString() - метод кој дава репрезентација на колекцијата од движечки објекти во следниот формат: Collection of movable objects with size [големина на колекцијата]: , по што во нов ред следуваат информации за сите движечки објекти во колекцијата.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

// ================== EXCEPTIONS ==================
class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String message) { super(message); }
}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(String message) { super(message); }
}

// ================== INTERFACE ==================
interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;
    void moveDown() throws ObjectCanNotBeMovedException;
    void moveLeft() throws ObjectCanNotBeMovedException;
    void moveRight() throws ObjectCanNotBeMovedException;

    int getCurrentXPosition();
    int getCurrentYPosition();
}

// ================== POINT ==================
class MovablePoint implements Movable {
    protected int x, y;
    protected int xSpeed, ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x; this.y = y;
        this.xSpeed = xSpeed; this.ySpeed = ySpeed;
    }

    private static boolean inBounds(int nx, int ny) {
        return nx >= 0 && nx <= MovablesCollection.getxMax()
                && ny >= 0 && ny <= MovablesCollection.getyMax();
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        int nx = x, ny = y + ySpeed;
        if (!inBounds(nx, ny))
            throw new ObjectCanNotBeMovedException(
                String.format("Point (%d,%d) is out of bounds", nx, ny));
        y = ny;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        int nx = x, ny = y - ySpeed;
        if (!inBounds(nx, ny))
            throw new ObjectCanNotBeMovedException(
                String.format("Point (%d,%d) is out of bounds", nx, ny));
        y = ny;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        int nx = x - xSpeed, ny = y;
        if (!inBounds(nx, ny))
            throw new ObjectCanNotBeMovedException(
                String.format("Point (%d,%d) is out of bounds", nx, ny));
        x = nx;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        int nx = x + xSpeed, ny = y;
        if (!inBounds(nx, ny))
            throw new ObjectCanNotBeMovedException(
                String.format("Point (%d,%d) is out of bounds", nx, ny));
        x = nx;
    }

    @Override public int getCurrentXPosition() { return x; }
    @Override public int getCurrentYPosition() { return y; }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d,%d)", x, y);
    }
}

// ================== CIRCLE ==================
class MovableCircle implements Movable {
    private final int radius;
    private final MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    private static boolean centerInBounds(int nx, int ny) {
        return nx >= 0 && nx <= MovablesCollection.getxMax()
                && ny >= 0 && ny <= MovablesCollection.getyMax();
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        int nx = center.x, ny = center.y + center.ySpeed;
        if (!centerInBounds(nx, ny))
            throw new ObjectCanNotBeMovedException(
                String.format("Point (%d,%d) is out of bounds", nx, ny));
        center.y = ny;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        int nx = center.x, ny = center.y - center.ySpeed;
        if (!centerInBounds(nx, ny))
            throw new ObjectCanNotBeMovedException(
                String.format("Point (%d,%d) is out of bounds", nx, ny));
        center.y = ny;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        int nx = center.x - center.xSpeed, ny = center.y;
        if (!centerInBounds(nx, ny))
            throw new ObjectCanNotBeMovedException(
                String.format("Point (%d,%d) is out of bounds", nx, ny));
        center.x = nx;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        int nx = center.x + center.xSpeed, ny = center.y;
        if (!centerInBounds(nx, ny))
            throw new ObjectCanNotBeMovedException(
                String.format("Point (%d,%d) is out of bounds", nx, ny));
        center.x = nx;
    }

    @Override public int getCurrentXPosition() { return center.getCurrentXPosition(); }
    @Override public int getCurrentYPosition() { return center.getCurrentYPosition(); }
    public int getRadius() { return radius; }
    public MovablePoint getCenter() { return center; }

    @Override
    public String toString() {
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d",
                center.getCurrentXPosition(), center.getCurrentYPosition(), radius);
    }
}

// ================== COLLECTION ==================
class MovablesCollection {
    private static int xMax;
    private static int yMax;

    public static int getxMax() { return xMax; }
    public static int getyMax() { return yMax; }
    public static void setxMax(int xMax) { MovablesCollection.xMax = xMax; }
    public static void setyMax(int yMax) { MovablesCollection.yMax = yMax; }

    private final List<Movable> movables;

    public MovablesCollection(int x_MAX, int y_MAX) {
        xMax = x_MAX; yMax = y_MAX;
        movables = new ArrayList<>();
    }

    private static boolean pointFits(int x, int y) {
        return x >= 0 && x <= xMax && y >= 0 && y <= yMax;
    }

    private static boolean circleFitsCompletely(MovableCircle c) {
        int x = c.getCenter().getCurrentXPosition();
        int y = c.getCenter().getCurrentYPosition();
        int r = c.getRadius();
        return (x - r) >= 0 && (x + r) <= xMax && (y - r) >= 0 && (y + r) <= yMax;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        if (m instanceof MovableCircle) {
            MovableCircle c = (MovableCircle) m;
            if (!circleFitsCompletely(c)) {
                throw new MovableObjectNotFittableException(
                    String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection",
                        c.getCurrentXPosition(), c.getCurrentYPosition(), c.getRadius()));
            }
        } else {
            if (!pointFits(m.getCurrentXPosition(), m.getCurrentYPosition())) {
                throw new MovableObjectNotFittableException(
                    String.format("Movable point with coordinates (%d,%d) can not be fitted into the collection",
                        m.getCurrentXPosition(), m.getCurrentYPosition()));
            }
        }
        movables.add(m);
    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) {
        for (Movable m : movables) {
            boolean isCircle = m instanceof MovableCircle;
            boolean matches = (type == TYPE.POINT && !isCircle) || (type == TYPE.CIRCLE && isCircle);
            if (!matches) continue;

            try {
                switch (direction) {
                    case UP:
                        m.moveUp();
                        break;
                    case DOWN:
                        m.moveDown();
                        break;
                    case LEFT:
                        m.moveLeft();
                        break;
                    case RIGHT:
                        m.moveRight();
                        break;
                }
            } catch (ObjectCanNotBeMovedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                String.format("Collection of movable objects with size %d:\n", movables.size()));
        for (Movable m : movables) sb.append(m).append("\n");
        return sb.toString();
    }
}

// ================== MAIN ==================
public class CirclesTest {
    public static void main(String[] args) {
        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            try {
                if (Integer.parseInt(parts[0]) == 0) { // point
                    collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
                } else { // circle
                    int radius = Integer.parseInt(parts[5]);
                    collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
                }
            } catch (MovableObjectNotFittableException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(collection.toString());

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());
    }
}
