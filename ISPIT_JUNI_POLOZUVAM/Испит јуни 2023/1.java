// Да се имплементира класа DeliveryApp која ќе моделира една апликација за нарачки и достава на храна од ресторани. Во класата да се имплементираат следните методи:

// Конструктор DeliveryApp (String name)
// Метод void registerDeliveryPerson (String id, String name, Location currentLocation) - методот за регистрирање на слободен доставувач кој сака да работи за апликацијата.
// Метод void addRestaurant (String id, String name, Location location) - метод за додавање на ресторан кој сака да овозможи достава на ставките од своето мени
// Метод void addUser (String id, String name) - метод за регистрирање на корисник кој сака да ја користи апликацијата за нарачка и достава на храна
// Метод void addAddress (String id, String addressName, Location location) - метод за додавање на адреса на корисникот со ИД id. Еден корисник може да има повеќе адреси (пр. Дома, работа и сл.)
// метод void orderFood(String userId, String userAddressName, String restaurantId, float cost) - метод за нарачка на храна на корисникот со ID userID на неговата адреса userAddressName од ресторантот со ID restaurantId.

// При процесирање на нарачката потребно е прво да се најде доставувач кој ќе ја достави нарачката до клиентот. Нарачката се доделува на доставувачот кој е најблиску до ресторанот. Во случај да има повеќе доставувачи кои се најблиску до ресторанот - се избира доставувачот со најмалку извршени достави досега.
// По доделување на нарачката на определен доставувач, се менува неговата моментална локација во локацијата на клиентот кому му се доставува нарачката.
// Доставувачот заработува од нарачката така што добива 90 денари за секоја нарачка, и дополнителни 10 денари на секои10 единици растојание од ресторанот до клиентот (пр. ако растојанието е 35 единици = 90+3х10 = 120)
// метод void printUsers() - метод кој ги печати сите корисници на апликацијата сортирани во опаѓачки редослед според потрошениот износ за нарачка на храна преку апликацијата
// метод void printRestaurants() - метод кој ги печати сите регистрирани ресторани во апликацијата, сортирани во опаѓачки редослед според просечната цена на нарачките наплатени преку апликацијата
// метод void printDeliveryPeople() - метод кој ги печати сите регистрирани доставувачи сортирани во опачки редослед според заработениот износ од извршените достави.

import java.util.*;

/*
YOUR CODE HERE
DO NOT MODIFY THE interfaces and classes below!!!
*/

interface Location {
    int getX();

    int getY();

    default int distance(Location other) {
        int xDiff = Math.abs(getX() - other.getX());
        int yDiff = Math.abs(getY() - other.getY());
        return xDiff + yDiff;
    }
}

class LocationCreator {
    public static Location create(int x, int y) {

        return new Location() {
            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }
        };
    }
}

public class DeliveryAppTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String appName = sc.nextLine();
        DeliveryApp app = new DeliveryApp(appName);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");

            if (parts[0].equals("addUser")) {
                String id = parts[1];
                String name = parts[2];
                app.addUser(id, name);
            } else if (parts[0].equals("registerDeliveryPerson")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.registerDeliveryPerson(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addRestaurant")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addRestaurant(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addAddress")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addAddress(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("orderFood")) {
                String userId = parts[1];
                String userAddressName = parts[2];
                String restaurantId = parts[3];
                float cost = Float.parseFloat(parts[4]);
                app.orderFood(userId, userAddressName, restaurantId, cost);
            } else if (parts[0].equals("printUsers")) {
                app.printUsers();
            } else if (parts[0].equals("printRestaurants")) {
                app.printRestaurants();
            } else {
                app.printDeliveryPeople();
            }

        }
    }
}

class DeliveryPerson{
    String id;
    String name;
    Location currentLocation;
    int totalDeliveries;
    float totalDeliveryFee;
    float averageDeliveryFee;

    public DeliveryPerson(String id, String name, Location currentLocation) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.totalDeliveries=0;
        this.totalDeliveryFee=0;
    }
    public float getAverageDeliveryFee(){
        if (totalDeliveryFee==0)
            return 0;
        return totalDeliveryFee/totalDeliveries;
    }

    public float getTotalDeliveryFee() {
        return totalDeliveryFee;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total deliveries: %d Total delivery fee: %.2f Average delivery fee: %.2f",
                id,
                name,
                totalDeliveries,
                totalDeliveryFee ,
                getAverageDeliveryFee());
    }
}

class Restaurant{
    String id;
    String name;
    Location location;
    int totalOrders;
    float totalAmountEarned;

    public Restaurant(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.totalOrders = 0;
        this.totalAmountEarned = 0;
    }
    public float getAverageAmountEarned(){
        if (totalAmountEarned==0)
            return 0;
        return totalAmountEarned/totalOrders;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %d Total amount earned: %.2f Average amount earned: %.2f ",
                id,name,totalOrders,totalAmountEarned,getAverageAmountEarned());
    }
}
class User{
    String id;
    String name;
    List<Address> adresi;
    float totalSpent;
    int totalOrders;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.adresi = new ArrayList<>();
        this.totalOrders=0;
        this.totalSpent=0;

     }

    public float getTotalSpent() {
        return totalSpent;
    }

    public float getAverageAmount() {
        if (totalSpent==0)
            return 0;
        return totalSpent/totalOrders;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %d Total amount spent: %.2f Average amount spent: %.2f",
                id,
                name,
                totalOrders,
                 totalSpent ,
                 getAverageAmount());
    }
}
class Address{
    String addressName;
    Location location;

    public Address(String addressName, Location location) {
        this.addressName = addressName;
        this.location = location;
    }
}
class DeliveryApp{
    String name;
    List<DeliveryPerson> deliveryPersonList;
     HashMap<String,Restaurant> restaurantHashMap;
    HashMap<String,User> userHashMap;

    public DeliveryApp(String name) {
        this.name = name;
        this.deliveryPersonList = new ArrayList<>();
        this.restaurantHashMap = new HashMap<>();
        this.userHashMap = new HashMap<>();
    }
    void registerDeliveryPerson (String id, String name, Location currentLocation){
        deliveryPersonList.add(new DeliveryPerson(id,name,currentLocation));
    }
    void addRestaurant (String id, String name, Location location){
        restaurantHashMap.put(id,new Restaurant(id,name,location));
    }
    void addUser (String id, String name){
       userHashMap.put(id,new User(id,name));
    }
    void addAddress (String id, String addressName, Location location){
             userHashMap.get(id).adresi.add(new Address(addressName,location));
    }
    void orderFood(String userId, String userAddressName, String restaurantId, float cost){
       User u = userHashMap.get(userId);
       u.totalOrders++;
       u.totalSpent+=cost;
      Address adresa = u.adresi.stream().filter(x->x.addressName.equals(userAddressName)).findFirst().orElse(null);
      Restaurant r = restaurantHashMap.get(restaurantId);
      r.totalOrders++;
      r.totalAmountEarned+=cost;

      int distanca=Integer.MAX_VALUE;
      DeliveryPerson dp = null;
      for (DeliveryPerson d: deliveryPersonList){

          if (r.location.distance(d.currentLocation)<distanca){
              distanca=r.location.distance(d.currentLocation);
              dp=d;
          }
          else if (r.location.distance(d.currentLocation)==distanca){
              if (dp.totalDeliveries>d.totalDeliveries){
                  dp=d;
              }
          }
      }
        dp.totalDeliveries++;
      int edinici = distanca/10;
        dp.totalDeliveryFee+=90+edinici*10;
        dp.currentLocation= adresa.location;

    }
    void printUsers(){
      userHashMap.values().stream().sorted(Comparator.comparing(User::getTotalSpent).thenComparing(User::getId).reversed()).forEach(System.out::println);
    }
    void printRestaurants(){
      restaurantHashMap.values().stream().sorted(Comparator.comparing(Restaurant::getAverageAmountEarned).thenComparing(Restaurant::getId).reversed()).forEach(System.out::println);
    }
    void printDeliveryPeople(){
       deliveryPersonList.stream().sorted(Comparator.comparing(DeliveryPerson::getTotalDeliveryFee).thenComparing(DeliveryPerson::getId).reversed()).forEach(System.out::println);
    }
}
