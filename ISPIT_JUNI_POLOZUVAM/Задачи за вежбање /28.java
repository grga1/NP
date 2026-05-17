MORA ZADACATA SO TREEMAP DA SE RESI BIDEJKI IMA TRIK KADE STO SPOREDUVA LEVELI I GO PECATI LEVEL10 PRED DRUGITE LEVELI !
  
// Да се имплементира апликација за евиденција на работниот ангажман на вработени во една ИТ компанија. За таа цел да се имплементира класата PayrollSystem во која што ќе се чуваат информации за вработени во компанијата. Постојат два типа на вработени HourlyEmployee и FreelanceEmployee. HourlyEmployee добиваат плата базирана на вкупниот број на изработени часови, додека пак FreelanceEmployee добиваат плата базирана на поените на тикетите што ги решиле. За класата PayrollSystem да се имплементираат:

// PayrollSystem(Map<String,Double> hourlyRateByLevel, Map<String,Double> ticketRateByLevel) - конструктор со два аргументи - мапи. Првата мапа означува колку е саатницата за соодветно ниво за вработените што земаат плата по час работа, а втората мапа означува колку е платата по поен од тикет за соодветното ниво за фриленсерите.
// void readEmployeesData (InputStream is) - метод за вчитување на податоците за вработените во компанијата, при што за секој вработен податоците се дадени во нов ред. Податоците за вработените се во следниот формат:
// Доколку вработениот е HourlyEmployee: H;ID;level;hours;
// Доколку вработениот е FreelanceEmployee: F;ID;level;ticketPoints1;ticketPoints2;...;ticketPointsN;
// Map<String, Collection<Employee>> printEmployeesByLevels (OutputStream os, Set<String> levels) - метод којшто нa излезен поток ќе врати мапа од вработeните во нивоата levels групирани по нивоа. Вработените да бидат сортирани според плата во опаѓачки редослед во рамките на нивото. Доколку платата е иста, да се споредуваат според нивото.
// Дополнителни информации:

// Платата на HourlyEmployee се пресметува така што сите часови работа до 40 часа се множат со саатницата определена за нивото, а сите часови работа над 40 часа, се множат со саатницата на нивото зголемена за коефициент 1.5.
// Платата на FreelanceEmployee се пресметува така што сумата на поените на тикетите коишто програмерот ги решил се множат со плата по тикет (ticket rate) за нивото.

  import java.io.*;
import java.util.*;

public class PayrollSystemTest {
    public static void main(String[] args) throws IOException {
        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
        }
        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
        System.out.println("READING OF THE EMPLOYEES DATA");
        payrollSystem.readEmployees(System.in);
        System.out.println("PRINTING EMPLOYEES BY LEVEL");
        Set<String> levels = new LinkedHashSet<>();
        for (int i = 5; i <= 10; i++) {
            levels.add("level" + i);
        }
        Map<String, Set<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
        result.forEach((level, employees) -> {
            System.out.println("LEVEL: " + level);
            System.out.println("Employees: ");
            employees.forEach(System.out::println);
            System.out.println("------------");
        });
    }
}

class Employee {
    String id;
    String level;
    double salary;

    public Employee(String id, String level, double salary) {
        this.id = id;
        this.level = level;
        this.salary = salary;
    }

    public double getSalary() { return salary; }
    public String getLevel() { return level; }
}

class HourlyEmployee extends Employee {
    double regularHours;
    double overtimeHours;

    public HourlyEmployee(String id, String level, double salary, double regularHours, double overtimeHours) {
        super(id, level, salary);
        this.regularHours = regularHours;
        this.overtimeHours = overtimeHours;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f",
                id, level, salary, regularHours, overtimeHours);
    }
}

class FreelanceEmployee extends Employee {
    int ticketPoints;
    int ticketsCount;

    public FreelanceEmployee(String id, String level, double salary, int ticketPoints, int ticketCount) {
        super(id, level, salary);
        this.ticketPoints = ticketPoints;
        this.ticketsCount = ticketCount;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %d",
                id, level, salary, ticketsCount, ticketPoints);
    }
}

class PayrollSystem {
    Map<String, Double> hourlyRateByLevel;
    Map<String, Double> ticketRateByLevel;
    Map<String, HourlyEmployee> hourlyemployeemap;
    Map<String, FreelanceEmployee> freelanceemployeemap;

    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
        this.hourlyemployeemap = new HashMap<>();
        this.freelanceemployeemap = new HashMap<>();
    }

    void readEmployees(InputStream is) {  
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        String line = "";
        try {
            while ((line = bf.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals("F")) {
                    String id = parts[1];
                    String level = parts[2];
                    int poeni = 0;
                    for (int i = 3; i < parts.length; i++) {
                        poeni += Integer.parseInt(parts[i]);
                    }
                    double ticketRate = ticketRateByLevel.get(level);
                    freelanceemployeemap.put(id, new FreelanceEmployee(id, level, ticketRate * poeni, poeni, parts.length - 3));
                } else {
                    String id = parts[1];
                    String level = parts[2];
                    double hours = Double.parseDouble(parts[3]);
                    double hourlyRate = hourlyRateByLevel.get(level);
                    double regularHour = Math.min(hours, 40.0);
                    double overtimeHour = Math.max(0, hours - 40.0);
                    double salary = regularHour * hourlyRate + overtimeHour * hourlyRate * 1.5;
                    hourlyemployeemap.put(id, new HourlyEmployee(id, level, salary, regularHour, overtimeHour));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Map<String, Set<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {  
        Map<String, Set<Employee>> result = new TreeMap<>();
        for (String l : levels) {
            Set<Employee> lista = new TreeSet<>(
                    Comparator.comparing(Employee::getSalary).reversed()
                            .thenComparing(Employee::getLevel)
                            .thenComparing(x->x.id)
            );
            freelanceemployeemap.values().stream().filter(x -> x.level.equals(l)).forEach(lista::add);
            hourlyemployeemap.values().stream().filter(x -> x.level.equals(l)).forEach(lista::add);
            if (lista.isEmpty()) continue;

            result.put(l, lista);
        }
        return result;
    }
}
