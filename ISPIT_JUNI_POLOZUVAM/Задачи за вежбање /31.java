// Да се имплементира систем за билети за стадион. За таа цел треба да се имплементираат класите:

// Sector во која се чуват информации за:
// кодот на секторот String
// бројот на места за седење int
// информации за зафатеност на местата за седење ?
// Stadium во која се чуваат информации за:
// името на стадионот String
// и сите сектори во стадионот ?
// Во класата Stadium треба да се имплементираат следните методи:

// Stadium(String name) конструктор со аргумент име на стадионот
// void createSectors(String[] sectorNames, int[] sizes) креирање на сектори со имиња String[] sectorNames и број на места int[] sizes (двете низи се со иста големина)
// void buyTicket(String sectorName, int seat, int type) за купување билет од проследениот тип (type, 0 - неутрален, 1 - домашен, 2 - гостински), во секторот sectorName со број на место seat (местото секогаш е со вредност во опсег 1 - size). Ако местото е зафатено (претходно е купен билет на ова место) се фрла исклучок од вид SeatTakenException. Исто така ако се обидеме да купиме билет од тип 1, во сектор во кој веќе има купено билет од тип 2 (и обратно) се фрла исклучок од вид SeatNotAllowedException.
// void showSectors() ги печати сите сектори сортирани според бројот на слободни места во опаѓачки редослед (ако повеќе сектори имаат ист број на слободни места, се подредуваат според името).

import java.util.*;

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}
class SeatTakenException extends Exception{
    public SeatTakenException() {
    }
}
class SeatNotAllowedException extends  Exception{
    public SeatNotAllowedException() {
    }
}
class Sector{
    String kod;
    int mesta;
    int type;
    Set<Integer> zafateniMesta;
    public Sector(String kod, int mesta) {
        this.kod = kod;
        this.mesta = mesta;
        zafateniMesta = new HashSet<>();
        this.type=0;
    }

    public String getKod() {
        return kod;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMesta() {
        return mesta;
    }

    public int getZafateniMesta() {
        return zafateniMesta.size();
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%",
                kod,
                mesta-getZafateniMesta(),
                getMesta(),
                (double)getZafateniMesta()*100/getMesta());
    }
}
class Stadium{
    String name;
    List<Sector> sectorList;

    public Stadium(String name) {
        this.name = name;
        this.sectorList = new ArrayList<>();
    }
    void createSectors(String[] sectorNames, int[] sizes){
        for (int i = 0; i < sectorNames.length; i++) {
            sectorList.add(new Sector(sectorNames[i],sizes[i]));
        }
    }
    void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
         Sector s = sectorList.stream().filter(x->x.kod.equals(sectorName)).findFirst().orElse(null);

         if (s.zafateniMesta.contains(seat)) throw  new SeatTakenException();

         if ((s.type == 1 && type == 2) || (s.type == 2 && type == 1)) throw new SeatNotAllowedException();

        if (type!=0){
             s.type=type;
         }
         s.zafateniMesta.add(seat);

    }
    void showSectors(){
        sectorList.stream().sorted(Comparator.comparing(Sector::getZafateniMesta).thenComparing(Sector::getKod)).forEach(x-> System.out.println(x.toString()));
    }

}
