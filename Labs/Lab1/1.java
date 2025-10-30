// Треба да се креира апликација за банка која ќе управуваа со сметките на повеќе корисниците и ќе врши трансакции помеѓу нив. Банката работи само со долари.

// За потребите на ваквата апликација треба да се напишат класите Account,Transaction и Bank. Класата Account претставува една сметка на еден корисник и треба да ги чува следните податоци:

// Име на корисникот,
// единствен идентификационен број (long)
// тековното салдо на сметката (реален број).
// Оваа класа исто така треба да ги имплементира и следниве методи

// Account(String name, double balance) – конструктор со параметри (id-то треба да го генерирате сами со помош на класата java.util.Random)
// getBalance():double
// getName():String
// getId():long
// setBalance(double balance)
// toString():String – враќа стринг во следниот формат, \n означува нов ред
// Name:Andrej Gajduk\n
// Balance:20.00$\n
// Класата Transaction претставува трансакција (префрлување пари од една на друга сметка), од страна на банката за што честопати се наплаќа провизија. За почеток треба да се напише класата Transaction со податочни членови за идентификационите броеви на две сметки, едната од која се одземаат парите и друга на која се додаваат парите, текстуален опис и износ на трансакцијата.

// За оваа класа треба да ги имплементирате методите:

// Transaction(long fromId, long toId, Stirng description, double amount) – конструктор со параметри
// getAmount():double
// getFromId():long
// getToId():long
// Оваа класа треба да е immutable, а можете и да ја направите и апстрактна бидејќи не е наменета директно да се користи туку само како основна класа за изведување на други класи.

// Како што споменавме претходно банката наплаќа провизија за одредени трансакции. Има два типа на провизија, фискна сума и процент. Кај фиксна сума за било која трансакција без разлика на износот на трансакцијата се наплаќа исто провизија (пример 10$). Кај процент се пресметува процент од целиот износ (процентите се зададени како цели броеви од 1-100).

// За да се прави разлика меѓу различните типови на провизија, треба да напишете уште две класи кои ќе наследуваат од Transaction кои треба да ги именувате FlatAmountProvisionTransaction и FlatPercentProvisionTransaction.

// Првата класа FlatAmountProvisionTransaction треба да содржи соодветен конструктор

// FlatAmountProvisionTransaction(long fromId, long toId,double amount, double flatProvision) кој го иницијализира полето за опис на "FlatAmount" и соодветен get метод
// getFlatAmount():double
// Слично и класата FlatPercentProvisionTransaction треба да има соодветен конструктор

// FlatPercentProvisionTransaction (long fromId, long toId, double amount, int centsPerDolar) кој го иницијализира полето за опис на "FlatPercent" и соодветен get метод
// getPercent():int
// Исто така треба да се преоптовари equals(Object o):boolean методот и за двете класи.

// За крај треба да ја имплементирате класата Bank која ги чува сметките од своите корисници и дополнително врши трансакции. Класата освен сметките на своите корисници, треба да ги чува и сопственото име и вкупната сума на трансфери како и вкупната наплатена провизија од страна на банката за сите трансакции.

// Класата Bank треба да ги нуди следните методи:

// Bank(String name, Account accounts[]) – конструктор со соодветните параметри (направете сопствена копија на низата од сметки)
// makeTransaction(Transaction t):boolean – врши проверка дали корисникот ги има потребните средства на сметка и дали и двете сметки на кои се однесува трансакцијата се нависитина во банката и ако и двата услови се исполнето ја извршува трансакцијата и враќа true, во спротивно враќа false
// totalTransfers():double – ја дава вкупната сума на пари кои се префрлени во сите трансакции до сега
// totalProvision():double – ја дава вкупната провизија наплатена од банката за сите извршени трансакции до сега
// toString():String - го враќа името на банката во посебна линија во формат
// Name:Banka na RM\n
// \n
// по што следат податоците за сите корисници.

// Провизијата се наплаќа така што на основната сума на трансакцијата се додава вредноста не провизијата и таа сума се одзема од првата сметка (праќачот).

// За сите класи да се напишат соодветни equals и hashCode методи.



import java.util.*;
import java.util.stream.Collectors;


 class Account{

    private Random RNG = new Random();
    private static Set<Long> used_ids = new HashSet<>();

    private String name;
    private long id;
    private double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.id = generateId();
        this.balance = balance;
    }

    public long generateId(){
        long number;
        do{
            number=Math.abs(RNG.nextLong());
        }while (number==0||used_ids.contains(number));
    used_ids.add(number);
            return number;
    }
    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

     @Override
     public String toString() {
         return "Name: " + name + "\n" +
                 "Balance: "+String.format("%.2f$",balance)+"\n";
     }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         Account account = (Account) o;
         return id == account.id && Double.compare(balance, account.balance) == 0 && Objects.equals(RNG, account.RNG) && Objects.equals(name, account.name);
     }

     @Override
     public int hashCode() {
         return Objects.hash(RNG, name, id, balance);
     }
 }
abstract class  Transaction{
    private long fromId;
    private  long toId;
    private  String description;
    private  double amount;

    public Transaction(long fromId, long toId, String description, double amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.description = description;
        this.amount = amount;
    }
    public long getFromId() { return fromId; }
    public long getToId() { return toId; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }

    public abstract double provision();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return fromId == that.fromId && toId == that.toId && Double.compare(amount, that.amount) == 0 && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId, description, amount);
    }
}

class FlatAmountProvisionTransaction extends Transaction{
    private double flatProvision;

    public FlatAmountProvisionTransaction(long fromId, long toId, double amount, double flatProvision) {
        super(fromId, toId, "FlatAmount", amount);
        this.flatProvision = flatProvision;
    }

    public double getFlatAmount() {
        return flatProvision;
    }

    @Override
    public double provision() {
        return flatProvision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
        return Double.compare(flatProvision, that.flatProvision) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), flatProvision);
    }
}

class FlatPercentProvisionTransaction extends Transaction{
  private int percent;

    public FlatPercentProvisionTransaction(long fromId, long toId,  double amount, int percent) {
        super(fromId, toId,"FlatPercent", amount);
        this.percent = percent;
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public double provision() {
        return getAmount()*(percent/100.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
        return percent == that.percent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), percent);
    }
    
}

class Bank{
    private  String name;
    private  Account[] accounts;
    private double totalTransfers;
    private double totalProvision;

    public Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts = Arrays.copyOf(accounts,accounts.length);
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public double totalTransfers() {
        return totalTransfers;
    }

    public double totalProvision() {
        return totalProvision;
    }

    private Account findAccount(long id){
        for (Account a: accounts){
            if(a.getId()==id) return a;
        }
        return null;
    }

    public boolean makeTransaction(Transaction t){
        Account from = findAccount(t.getFromId());
        Account to   = findAccount(t.getToId());
        if (from == null || to == null) return false;           // сметките мора да постојат

        double total = t.getAmount() + t.provision();           // праќач плаќа сума + провизија
        if (from.getBalance() < total) return false;            // доволно средства?

        from.setBalance(from.getBalance() - total);             // одземање кај праќач
        to.setBalance(to.getBalance() + t.getAmount());         // додавање кај примач

        totalTransfers += t.getAmount();                        // статистики
        totalProvision += t.provision();
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: "+name+"\n\n");
        for (Account a: accounts){
            sb.append(a.toString());
        }

        return sb.toString();

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Double.compare(totalTransfers, bank.totalTransfers) == 0 && Double.compare(totalProvision, bank.totalProvision) == 0 && Objects.equals(name, bank.name) && Objects.deepEquals(accounts, bank.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, Arrays.hashCode(accounts), totalTransfers, totalProvision);
    }
}
public class BankTester {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        String test_type = jin.nextLine();
        switch (test_type) {
            case "typical_usage":
                testTypicalUsage(jin);
                break;
            case "equals":
                testEquals();
                break;
        }
        jin.close();
    }

    private static double parseAmount (String amount){
        return Double.parseDouble(amount.replace("$",""));
    }

    private static void testEquals() {
        Account a1 = new Account("Andrej", 20.0);
        Account a2 = new Account("Andrej", 20.0);
        Account a3 = new Account("Andrej", 30.0);
        Account a4 = new Account("Gajduk", 20.0);
        List<Account> all = Arrays.asList(a1, a2, a3, a4);
        if (!(a1.equals(a1)&&!a1.equals(a2)&&!a2.equals(a1)&&!a3.equals(a1)
                && !a4.equals(a1)
                && !a1.equals(null))) {
            System.out.println("Your account equals method does not work properly.");
            return;
        }
        Set<Long> ids = all.stream().map(Account::getId).collect(Collectors.toSet());
        if (ids.size() != all.size()) {
            System.out.println("Different accounts have the same IDS. This is not allowed");
            return;
        }
        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, 20.0, 10.0);
        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(20, 20, 20.0, 10.0);
        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, 20.0, 10.0);
        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, 50.0, 50.0);
        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, 20.0, 10.0);
        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, 20.0, 10);
        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, 20.0, 10);
        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(10, 10, 20.0, 10);
        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, 50.0, 10);
        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, 20.0, 30);
        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, 20.0, 10);
        if (fa1.equals(fa1) &&
                !fa2.equals(null) &&
                fa2.equals(fa1) &&
                fa1.equals(fa2) &&
                fa1.equals(fa3) &&
                !fa1.equals(fa4) &&
                !fa1.equals(fa5) &&
                !fa1.equals(fp1) &&
                fp1.equals(fp1) &&
                !fp2.equals(null) &&
                fp2.equals(fp1) &&
                fp1.equals(fp2) &&
                fp1.equals(fp3) &&
                !fp1.equals(fp4) &&
                !fp1.equals(fp5) &&
                !fp1.equals(fp6)) {
            System.out.println("Your transactions equals methods do not work properly.");
            return;
        }
        Account accounts[] = new Account[]{a1, a2, a3, a4};
        Account accounts1[] = new Account[]{a2, a1, a3, a4};
        Account accounts2[] = new Account[]{a1, a2, a3};
        Account accounts3[] = new Account[]{a1, a2, a3, a4};

        Bank b1 = new Bank("Test", accounts);
        Bank b2 = new Bank("Test", accounts1);
        Bank b3 = new Bank("Test", accounts2);
        Bank b4 = new Bank("Sample", accounts);
        Bank b5 = new Bank("Test", accounts3);

        if (!(b1.equals(b1) &&
                !b1.equals(null) &&
                !b1.equals(b2) &&
                !b2.equals(b1) &&
                !b1.equals(b3) &&
                !b3.equals(b1) &&
                !b1.equals(b4) &&
                b1.equals(b5))) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        accounts[2] = a1;
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        long from_id = a2.getId();
        long to_id = a3.getId();
        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, 3.0, 3.0);
        b1.makeTransaction(t);
        if (b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        b5.makeTransaction(t);
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        System.out.println("All your equals methods work properly.");
    }

    private static void testTypicalUsage(Scanner jin) {
        String bank_name = jin.nextLine();
        int num_accounts = jin.nextInt();
        jin.nextLine();
        Account accounts[] = new Account[num_accounts];
        for (int i = 0; i < num_accounts; ++i)
            accounts[i] = new Account(jin.nextLine(),  parseAmount(jin.nextLine()));
        Bank bank = new Bank(bank_name, accounts);
        while (true) {
            String line = jin.nextLine();
            switch (line) {
                case "stop":
                    return;
                case "transaction":
                    String descrption = jin.nextLine();
                    double amount = parseAmount(jin.nextLine());
                    double parameter = parseAmount(jin.nextLine());
                    int from_idx = jin.nextInt();
                    int to_idx = jin.nextInt();
                    jin.nextLine();
                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
                    System.out.println("Transaction amount: " + String.format("%.2f$",t.getAmount()));
                    System.out.println("Transaction description: " + t.getDescription());
                    System.out.println("Transaction successful? " + bank.makeTransaction(t));
                    break;
                case "print":
                    System.out.println(bank.toString());
                    System.out.println("Total provisions: " + String.format("%.2f$",bank.totalProvision()));
                    System.out.println("Total transfers: " + String.format("%.2f$",bank.totalTransfers()));
                    System.out.println();
                    break;
            }
        }
    }

    private static Transaction getTransaction(String description, int from_idx, int to_idx, double amount, double o, Bank bank) {
        switch (description) {
            case "FlatAmount":
                return new FlatAmountProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, o);
            case "FlatPercent":
                return new FlatPercentProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, (int) o);
        }
        return null;
    }


}


