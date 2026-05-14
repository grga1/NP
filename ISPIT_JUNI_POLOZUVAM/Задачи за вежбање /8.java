// Да се имплементира класа ArchiveStore во која се чува листа на архиви (елементи за архивирање).

// Секој елемент за архивирање Archive има:

// id - цел број
// dateArchived - датум на архивирање.
// Постојат два видови на елементи за архивирање, LockedArchive за кој дополнително се чува датум до кој не смее да се отвори dateToOpen и SpecialArchive за кој се чуваат максимален број на дозволени отварања maxOpen. За елементите за архивирање треба да се обезбедат следните методи:

// LockedArchive(int id, Date dateToOpen) - конструктор за заклучена архива
// SpecialArchive(int id, int maxOpen) - конструктор за специјална архива
// За класата ArchiveStore да се обезбедат следните методи:

// ArchiveStore() - default конструктор
// void archiveItem(Archive item, Date date) - метод за архивирање елемент item на одреден датум date
// void openItem(int id, Date date) - метод за отварање елемент од архивата со зададен id и одреден датум date. Ако не постои елемент со даденото id треба да се фрли исклучок од тип NonExistingItemException со порака Item with id [id] doesn't exist.
// String getLog() - враќа стринг со сите пораки запишани при архивирањето и отварањето архиви во посебен ред.
// За секоја акција на архивирање во текст треба да се додаде следната порака Item [id] archived at [date], додека за секоја акција на отварање архива треба да се додаде Item [id] opened at [date]. При отварање ако се работи за LockedArhive и датумот на отварање е пред датумот кога може да се отвори, да се додаде порака Item [id] cannot be opened before [date]. Ако се работи за SpecialArhive и се обидиеме да
//   ја отвориме повеќе пати од дозволениот број (maxOpen) да се додаде порака Item [id] cannot be opened more than [maxOpen] times.

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
//        Date date = new Date(113, 10, 7);
        LocalDate localDate = LocalDate.of(2013, 11, 7);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.of("UTC")).toInstant());

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

// вашиот код овде
class NonExistingItemException extends Exception{
    public NonExistingItemException(int id) {
        super("Item with id "+id+" doesn't exist");
    }
}

class Archive{
   int id;
   Date dateArchived;
    public Archive(int id) {
        this.id = id;

    }

}
class LockedArchive extends Archive {
    Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }
}
class SpecialArchive extends Archive{
    int maxOpen;
    int counter=0;
    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }
}

class ArchiveStore{
    HashMap<Integer,Archive> mapa;
    StringBuilder sb;

    ArchiveStore(){
        this.mapa = new HashMap<>();
        this.sb = new StringBuilder();
    }

    void archiveItem(Archive item, Date date){
        item.dateArchived = date;
        mapa.put(item.id, item);
        sb.append("Item " + item.id + " archived at " + date + "\n");
    }

    void openItem(int id, Date date) throws NonExistingItemException {
    if (!mapa.containsKey(id)) throw new NonExistingItemException(id);

    if  ( mapa.get(id) instanceof LockedArchive){
            LockedArchive a = (LockedArchive) mapa.get(id);
            if (date.before(a.dateToOpen)){
                sb.append("Item "+id+" cannot be opened before "+a.dateToOpen+"\n");
                return;
            }
    }else if (mapa.get(id) instanceof  SpecialArchive){
        SpecialArchive s  = (SpecialArchive) mapa.get(id);
        s.counter++;

        if (s.counter>s.maxOpen){
            sb.append("Item "+s.id+" cannot be opened more than "+s.maxOpen+" times\n");
            return;
        }
        
    }
    sb.append("Item "+id+" opened at "+date+"\n");

    }
    String getLog(){
    return sb.toString();
    }

}
