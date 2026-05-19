// Да се (до)имплементира апликацијата за евиденција на работниот ангажман на вработени во една ИТ компанија. Препорака: За да немате проблем со исти имиња на класи користете посебни пакети за двете задачи, доколку сакате да ги решавате одделно.

// За таа цел да се имплементира класата PayrollSystem во која што ќе се чуваат информации за вработени во компанијата. Како и во претходната задача постојат два типа на вработени HourlyEmployee и FreelanceEmployee. Пресметката на плата за двата типа на вработени е иста како и во претходната задача. За класата PayrollSystem да се имплементираат:

// PayrollSystem(Map<String,Double> hourlyRateByLevel, Map<String,Double> ticketRateByLevel) - ист како и во претходната задача
// Employee createEmployee (String line) - метод којшто врз основа на влезен стринг во којшто се запишани информациите за даден вработен, ќе креира и врати објект од класа Employee. Дополнително, методот ќе го смести вработениот во системот за плати. Информациите за вработениот се во истиот формат како и во првата задача, со тоа што во овој случај има можност за бонус на вработениот којшто е одделен со празно место од информациите за вработениот. Постојат два типа на бонуси којшто вработениот може да ги добие и тоа:
// Фиксен паричен бонус (запишан како бројка). пр. H;ID;level;hours; 100 (во овој случај добива фиксен бонус на плата од 100\$)
// Процентуален паричен бонус (запишан како број со знак процент). пр. F;ID;level;ticketPoints1;ticketPoints2;...;ticketPointsN; 10% (во овој случај добива процентуален бонус од 10% од неговата плата).
// Во претходниот метод, со искчучок од тип BonusNotAllowedException да се спречи креирање на вработен на којшто му е доделен фиксен бонус поголем од 1000\$ или процентуален бонус поголем од 20%.
// Map<String, Double> getOvertimeSalaryForLevels () - метод којшто ќе врати мапа каде што клучот е нивото на вработениот, а вредноста е вкупниот износ која што компанијата го исплатила за прекувремена работа за вработените од тоа ниво.
// void printStatisticsForOvertimeSalary () - метод којшто ќе испечати статистки (минимум, максимум, сума, просек) на исплатените додатоци за прекувремена работа на сите вработени во компанијата.
// Map<String, Integer> ticketsDoneByLevel() - метод којшто ќе врати мапа каде што клучот е нивото на вработените, а вредноста е бројот на поени за тикети што се сработени од вработените од соодветното ниво.
// Collection<Employee> getFirstNEmployeesByBonus (int n) - метод којшто ќе врати сортирана колекција од првите n вработени сортирани во опаѓачки редослед според бонусот којшто го добиле на платата.

import java.util.*;
import java.util.stream.Collectors;

public class PayrollSystemTest2 {

    public static void main(String[] args) throws BonusNotAllowedException {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 11 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5.5 + i * 2.5);
        }

        Scanner sc = new Scanner(System.in);

        int employeesCount = Integer.parseInt(sc.nextLine());

        PayrollSystem ps = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
        Employee emp = null;
        for (int i = 0; i < employeesCount; i++) {
            try {
                emp = ps.createEmployee(sc.nextLine());
            } catch (BonusNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }

        int testCase = Integer.parseInt(sc.nextLine());

        switch (testCase) {
            case 1: //Testing createEmployee
                if (emp != null)
                    System.out.println(emp);
                break;
            case 2: //Testing getOvertimeSalaryForLevels()
                ps.getOvertimeSalaryForLevels().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Overtime salary: %.2f\n", level, overtimeSalary);
                });
                break;
            case 3: //Testing printStatisticsForOvertimeSalary()
                ps.printStatisticsForOvertimeSalary();
                break;
            case 4: //Testing ticketsDoneByLevel
                ps.ticketsDoneByLevel().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Tickets by level: %d\n", level, overtimeSalary);
                });
                break;
            case 5: //Testing getFirstNEmployeesByBonus (int n)
                ps.getFirstNEmployeesByBonus(Integer.parseInt(sc.nextLine())).forEach(System.out::println);
                break;
        }

    }
}
class BonusNotAllowedException extends Exception{
    public BonusNotAllowedException(String tmp) {
        super("Bonus of "+tmp+" is not allowed");
    }
}
class Employee {
    String id;
    String level;
    double salary;
    double bonus;

    public Employee(String id, String level, double salary,double bonus) {
        this.id = id;
        this.level = level;
        this.salary = salary;
        this.bonus = bonus;
    }

    public double getSalary() { return salary; }
    public String getLevel() { return level; }

    public double getBonus() {
        return bonus;
    }
}

class HourlyEmployee extends Employee {
    double regularHours;
    double overtimeHours;
    double OvertimeSalary;

    public HourlyEmployee(String id, String level, double salary, double regularHours, double overtimeHours,double bounus,double OvertimeSalary) {
        super(id, level, salary,bounus);
        this.regularHours = regularHours;
        this.overtimeHours = overtimeHours;
        this.OvertimeSalary = OvertimeSalary;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f Bonus: %.2f",
                id, level, salary, regularHours, overtimeHours,bonus);
    }

    public double getOvertimeSalary() {
        return OvertimeSalary;
    }

}

class FreelanceEmployee extends Employee {
    int ticketPoints;
    int ticketsCount;

    public FreelanceEmployee(String id, String level, double salary, int ticketPoints, int ticketCount,double bonus) {
        super(id, level, salary,bonus);
        this.ticketPoints = ticketPoints;
        this.ticketsCount = ticketCount;

    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %d Bonus: %.2f",
                id, level, salary, ticketsCount, ticketPoints,bonus);
    }

}

class PayrollSystem{
    Map<String, Double> hourlyRateByLevel;
    Map<String, Double> ticketRateByLevel;
    Map<String, HourlyEmployee> hourlyemployeemap;
    Map<String, FreelanceEmployee> freelanceemployeemap;
    List<FreelanceEmployee> listaFrelencer;
    List<HourlyEmployee> listaHourly;

    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
        this.hourlyemployeemap = new HashMap<>();
        this.freelanceemployeemap = new HashMap<>();
        this.listaFrelencer = new ArrayList<>();
        this.listaHourly = new ArrayList<>();
    }

    Employee createEmployee (String line) throws BonusNotAllowedException {
        String[] parts = line.split("\\s+");
        String id="";
        String level="";
        Double hours=0.0;
        double bonus=0;

        int ticketsCount=0;
        int ticketsSum=0;

        if (parts.length==2){
            String[]parts2 = parts[0].split(";");
            if (parts2[0].equals("H")){
               id=parts2[1];
               level=parts2[2];
               hours=Double.parseDouble(parts2[3]);


               if (parts[1].endsWith("%")){
                   bonus =Double.parseDouble(parts[1].substring(0,parts[1].length()-1));
                   if (bonus>20) throw  new BonusNotAllowedException(parts[1]);
               }else{
                   bonus=Double.parseDouble(parts[1]);
                   if (bonus>1000) throw  new BonusNotAllowedException(parts[1]+"$");
               }

                double hourlyRate = hourlyRateByLevel.get(level);
                double regularHour = Math.min(hours, 40.0);
                double overtimeHour = Math.max(0, hours - 40.0);
                double salary = regularHour * hourlyRate + overtimeHour * hourlyRate * 1.5;
                double OvertimeSalary = overtimeHour*hourlyRate*1.5;

                listaHourly.add(new HourlyEmployee(id,level,salary+salary/100*bonus,regularHour,overtimeHour, salary/100*bonus,OvertimeSalary));
                return new HourlyEmployee(id,level,salary+salary/100*10,regularHour,overtimeHour,salary/100*bonus,OvertimeSalary);
            }else {
                id=parts2[1];
                level=parts2[2];
                for (int i = 3; i < parts2.length; i++) {
                    ticketsSum+=Integer.parseInt(parts2[i]);
                    ticketsCount++;
                }

                if (parts[1].endsWith("%")){
                    bonus =Double.parseDouble(parts[1].substring(0,parts[1].length()-1));
                    if (bonus>20) throw  new BonusNotAllowedException(parts[1]);

                }else{
                    bonus=Double.parseDouble(parts[1]);
                    if (bonus>1000) throw  new BonusNotAllowedException(parts[1]+"$");
                }

                double ticketRate = ticketRateByLevel.get(level);
                 listaFrelencer.add(new FreelanceEmployee(id,level,ticketRate*ticketsSum+bonus,ticketsSum,ticketsCount, bonus));
                return new FreelanceEmployee(id,level,ticketRate*ticketsSum+bonus,ticketsSum,ticketsCount, bonus);
            }
        }else{
            String[]parts2 = parts[0].split(";");
            if (parts2[0].equals("H")) {
                id = parts2[1];
                level = parts2[2];
                hours = Double.parseDouble(parts2[3]);
                double hourlyRate = hourlyRateByLevel.get(level);
                double regularHour = Math.min(hours, 40.0);
                double overtimeHour = Math.max(0, hours - 40.0);
                double salary = regularHour * hourlyRate + overtimeHour * hourlyRate * 1.5;
                double OvertimeSalary = overtimeHour*hourlyRate*1.5;

                listaHourly.add(new HourlyEmployee(id,level,salary+salary/100*bonus,regularHour,overtimeHour, salary/100*bonus,OvertimeSalary));
                return new HourlyEmployee(id,level,salary+salary/100*bonus,regularHour,overtimeHour, salary/100*bonus,OvertimeSalary);
            }else{
                id=parts2[1];
                level=parts2[2];
                for (int i = 3; i < parts2.length; i++) {
                    ticketsSum+=Integer.parseInt(parts2[i]);
                    ticketsCount++;
                }
                double ticketRate = ticketRateByLevel.get(level);
                listaFrelencer.add(new FreelanceEmployee(id,level,ticketRate*ticketsSum+bonus,ticketsSum,ticketsCount, bonus));
                return new FreelanceEmployee(id,level,ticketRate*ticketsSum+bonus,ticketsSum,ticketsCount,  bonus);
            }
        }
     }

     Map<String, Double> getOvertimeSalaryForLevels (){
        return listaHourly.stream().collect(
                Collectors.groupingBy(h->h.level
                        ,
                        Collectors.summingDouble(h->h.OvertimeSalary)
                ));
    }
    void printStatisticsForOvertimeSalary() {
        DoubleSummaryStatistics stats = listaHourly.stream()
                .mapToDouble(x -> x.OvertimeSalary)
                .summaryStatistics();
        System.out.printf("Statistics for overtime salary: Min: %.2f Average: %.2f Max: %.2f Sum: %.2f%n",
                stats.getMin(), stats.getAverage(), stats.getMax(), stats.getSum());
    }

    Map<String, Integer> ticketsDoneByLevel(){
        return listaFrelencer.stream()
                .collect(Collectors.groupingBy(
                        f->f.level,
                        Collectors.summingInt(f->f.ticketsCount)
                ));
    }

    Collection<Employee> getFirstNEmployeesByBonus (int n){
        List<Employee> lista = new ArrayList<>();
        listaFrelencer.stream().forEach(x->lista.add(x));
        listaHourly.stream().forEach(x->lista.add(x));
        return lista.stream().sorted(Comparator.comparing(Employee::getBonus).reversed()).limit(n).collect(Collectors.toList());

    }

}
