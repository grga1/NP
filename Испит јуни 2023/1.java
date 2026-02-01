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
class DeliveryApp{

    String name;
    List<DeliveryPerson> deliveryPersonList;
    List<Restaurant> restaurantList;
    List<User> userList;
    DeliveryApp (String name){
        this.name = name;
        this.deliveryPersonList = new ArrayList<>();
     this.restaurantList = new ArrayList<>();
     this.userList = new ArrayList<>();
    }

    void registerDeliveryPerson (String id, String name, Location currentLocation){
    DeliveryPerson p = new DeliveryPerson(id,name,currentLocation);
      deliveryPersonList.add(p);
    }
    void addRestaurant (String id, String name, Location location){
     Restaurant r = new Restaurant(id,name,location);
     restaurantList.add(r);
    }
    void addUser (String id, String name){
      User u = new User(id,name);
      userList.add(u);
    }
    void addAddress (String id, String addressName, Location location){
        User u = userList.stream().filter(s->s.getId().equals(id)).findFirst().orElse(null);
        if (u==null) {
            return;
        }
        u.addresses.put(addressName, location);
    }
    void printUsers(){
        userList.stream().sorted(Comparator.comparing(User::getTotal).reversed().thenComparing(User::getId,Comparator.reverseOrder())).forEach(System.out::println);
    }
    void printRestaurants(){
       restaurantList.stream().sorted(Comparator.comparing(Restaurant::getAverageOrderPrice).reversed().thenComparing(Restaurant::getId,Comparator.reverseOrder())).forEach(System.out::println);
    }
    void printDeliveryPeople(){
       deliveryPersonList.stream().sorted(Comparator.comparing(DeliveryPerson::getEarned).reversed().thenComparing(DeliveryPerson::getId,Comparator.reverseOrder())).forEach(System.out::println);
    }
    void orderFood(String userId, String userAddressName, String restaurantId, float cost){

        User u = userList.stream().filter(x-> Objects.equals(x.getId(), userId)).findFirst().orElse(null);
        assert u != null;
        u.setOrdersCount(u.getOrdersCount()+1);
        u.setTotal(u.getTotal()+cost);
        Location lokacija = u.getAddresses().get(userAddressName);

        Restaurant restorant = restaurantList.stream().filter(x->x.getId().equals(restaurantId)).findFirst().orElse(null);
        assert restorant != null;
        restorant.setOrdersCount(restorant.getOrdersCount()+1);
         restorant.setTotalAmount(restorant.getTotalAmount()+cost);

        int minDistance= 100000000;

        DeliveryPerson dostavuvac  = null;
        for(DeliveryPerson dp : deliveryPersonList){
           if (dp.getCurrentLocation().distance(restorant.getLocation())<minDistance)
           {
               minDistance=dp.getCurrentLocation().distance(restorant.getLocation());
               dostavuvac = dp;
           }else if (dp.getCurrentLocation().distance(restorant.getLocation())==minDistance){
               if (dostavuvac.getDeliveriesCount()>dp.getDeliveriesCount()){
                   dostavuvac=dp;
               }
           }
        }

        dostavuvac.setCurrentLocation(lokacija);  // nova lokacija;
        dostavuvac.setDeliveriesCount(dostavuvac.getDeliveriesCount()+1);
        dostavuvac.setEarned(dostavuvac.getEarned()+90+(minDistance/10)*10);
    }
}
class DeliveryPerson{
    String id;
    String name;
    Location currentLocation;
    int deliveriesCount; // МНОГУ ВАЖНО
    float earned;

    public DeliveryPerson(String id, String name, Location currentLocation) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.deliveriesCount=0;
        this.earned=0;
    }

    public int getDeliveriesCount() {
        return deliveriesCount;
    }

    public float getEarned() {
        return earned;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setDeliveriesCount(int deliveriesCount) {
        this.deliveriesCount = deliveriesCount;
    }

    public void setEarned(float earned) {
        this.earned = earned;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
    double getAverageDeliveryFee(){
        if (deliveriesCount==0) return 0.0;
        return  (double) earned /deliveriesCount;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total deliveries: %d Total delivery fee: %.2f Average delivery fee: %.2f",getId(),getName(),getDeliveriesCount(),getEarned(),getAverageDeliveryFee());
    }
}

class Restaurant{
    String id;
    String name;
    Location location;
    int ordersCount;
    double totalAmount;

    public Restaurant(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.ordersCount=0;
        this.totalAmount=0.0;
    }
    double getAverageOrderPrice(){
        if (ordersCount==0) return 0.0;
        return  totalAmount/ordersCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %d Total amount earned: %.2f Average amount earned: %.2f",getId(),getName(),getOrdersCount(),getTotalAmount(),getAverageOrderPrice());
    }
}

class User{
    String id;
    String name;
    Map<String, Location> addresses;
    int ordersCount;
    Double total;

    public User(String id, String name, Map<String, Location> addresses) {
        this.id = id;
        this.name = name;
        this.addresses = addresses;
        this.ordersCount=0;
        this.total=0.0;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.addresses = new HashMap<>();
        this.ordersCount=0;
        this.total=0.0;
    }

    public String getId() {
        return id;
    }

    public Double getTotal() {
        return total;
    }

    public String getName() {
        return name;
    }

    public int getOrdersCount() {
        return ordersCount;
    }
    double getAverageOrderPrice(){
        if (ordersCount==0) return 0.0;
        return  total/ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Map<String, Location> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %d Total amount spent: %.2f Average amount spent: %.2f",getId(),getName(),getOrdersCount(),getTotal(),getAverageOrderPrice());
    }
}
