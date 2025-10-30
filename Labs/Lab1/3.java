// Дадени ви се следниве класи:

// Класа Doctor

// Преставува еден доктор со основните информации за него: бројот на лиценцата, неговото име, ниво на експертиза (1-10), број на пациенти

// Доколку нивото на експертиза е 10, се смета дека докторот е Chief.
// Имплементиран е toString методот кој го печати докторот во читлив формат (име, број на лиценца, специјализација, број на пациенти и доколку е со највисоко ниво на експертиза се печати и [Chief])
// При промена на нивото на експертиза, вредноста мора да се движи во рамките од 1-10 и не смее да биде помала од претходната
// Класа EmergencyRoom

// репрезентира еден ургентен центар во една болница и содржи информации за: името на болницата, медицински персонал (низа од објекти Doctor), капацитет
// Имплементирани се следниве методи: treat, forEach, count, findFirst, filter, mapToLabels, mutate, conditionalMutate, countForEvaluation, evaluate
// treat(Supplier<Doctor> supplier) - додава доктор во ургентниот центар, доколку има слободно место
// forEach(Consumer<Doctor> action) - применува зададена акција (Consumer) врз секој доктор во низата (пример: печатење)
// count(Predicate<Doctor> condition) - го враќа бројот на доктори кои го исполнуваат дадениот услов
// findFirst(Predicate<Doctor> condition) - го враќа првиот доктор кој исполнува даден услов
// filter(Predicate<Student> condition - Враќа нова низа која ги содржи само докторите кои го исполнуваат условот.
// mapToLabels(Function<Student, String> mapper) - Враќа низа од текстуални описи, добиени со трансформирање на секој доктор со дадената функција.
// mutate(Consumer<Student> mutator) - Применува промена на сите доктори (на пример, зголемување на нивото на експертиза).
// conditionalMutate(Predicate<Student> condition, Consumer<Student> mutator) - Ја применува промената само на докторите кои го исполнуваат дадениот услов.
// countForEvaluation(DoctorEvaluator evaluator) - Користи DoctorEvaluator за да изброи колку доктори исполнуваат еден услов
// evaluate(DoctorEvaluator evaluator) - Враќа нова низа која ги содржи сите доктори кои исполнуваат услов поставен со DoctorEvaluator
// toString() - Враќа текстуален опис на ургентниот центар, кој ги содржи името на болницата, бројот на доктори кои моментално работат во него и списокот од истите.
// Од ваша страна потребно е да:

// Креирате функциски интерфејс DoctorEvaluator кој ќе има еден метод: boolean evaluate(Doctor doctor);
// Да креирате класа HighExpertiseEvaluator кој ќе враќа TRUE само доколку докторот има ниво на експертиза поголем или еднаков на 7.
// Да ги разрешите барањата во main делот:
// Отворете Scanner и прочитајте цел број n што го означува бројот на доктори кои ќе се внесат.
// Креирајте Supplier<Student> кој чита податоци за еден доктор од конзолата (број на лиценца, име, ниво на експертиза и број на пациенти) и враќа нов објект Doctor.
// Додадете n доктори во користејќи го методот treat.
// Користете Consumer<Student> заедно со forEach за да ги испечатите сите доктори кои работат моментално во ургентниот центар.
// Искористете ги креираните функциски интерфејси за да одредите кои доктори:
// имаат повеќе од 20 пациенти
// имаат повисоко ниво на експертиза (7+)
// Комбинирајте ги двете состојби од функциските интерфејси и искористете го методот evaluate од класата EmergencyRoom за да ги прикажеш само тие доктори.
// Користете findFirst за да го пронајдите и прикажете Chief докторот во ургентниот центар.
// Користете mutate за да и го зголемите нивото на експертиза на сите доктори за 1.
// Користете conditionalMutate за да ја зголемите експертизата за 1 само на докторите со повеќе од 30 пациенти.
// Користете mapToLabels за да ги трансформирате сите доктори во текстуални описи и испечати ги.
// На крај, испечатете ги сите информации за ургентниот центар со користење на методот toString.

// ---

// You are given the following classes:

// Class Doctor

// Represents a doctor with basic information about him: license number, his name, expertise level (1-10), number of patients
// If the expertise level is 10, the doctor is considered to be a Chief.
// The toString method has been implemented, which prints the doctor in a readable format (name, license number, specialization, number of patients, and if he has the highest level of expertise, [Chief] is also printed.)
// When changing the level of expertise, the value must be in the range of 1-10 and must not be lower than the previous one
// Class EmergencyRoom

// represents an emergency room in a hospital and contains information about: the name of the hospital, medical staff (an array of Doctor objects), capacity
// The following methods have been implemented: treat, forEach, count, findFirst, filter, mapToLabels, mutate, conditionalMutate, countForEvaluation, evaluate
// treat(Supplier<Doctor> supplier) - adds a doctor to the emergency room, if there is a free place
// forEach(Consumer<Doctor> action) - applies a given action (Consumer) to each doctor in the array (example: print)
// count(Predicate<Doctor> condition) - returns the number of doctors who meet the given condition
// findFirst(Predicate<Doctor> condition) - returns the first doctor who meets a given condition
// filter(Predicate<Student> condition) - Returns a new array containing only the doctors who meet the condition.
// mapToLabels(Function<Student, String> mapper) - Returns an array of text descriptions, obtained by transforming each doctor with the given function.
// mutate(Consumer<Student> mutator) - Applies a change to all doctors (for example, increasing the level of expertise)
// conditionalMutate(Predicate<Student> condition, Consumer<Student> mutator) - Applies the change only to doctors who meet the given condition.
// countForEvaluation(DoctorEvaluator evaluator) - Uses DoctorEvaluator to count how many doctors meet a condition
// evaluate(DoctorEvaluator evaluator) - Returns a new array containing all doctors who meet the condition set by DoctorEvaluator
// toString() - Returns a text description of the emergency center, containing the name of the hospital, the number of doctors currently working in it and a list of them.
// On your part, you need to:

// Create a functional interface DoctorEvaluator that will have one method: boolean evaluate(Doctor doctor);
// Create a class HighExpertiseEvaluator that will return TRUE only if the doctor has an expertise level greater than or equal to 7.
// Resolve the requirements in the main section:
// Open a Scanner and read an integer n that indicates the number of doctors to be entered.
// Create a Supplier<Student> that reads data about a doctor from the console (license number, name, expertise level and number of patients) and returns a new Doctor object.
// Add n doctors to using the treat method.
// Use Consumer<Student> together with forEach to print all doctors currently working in the emergency center.
// Use the created functional interfaces to determine which doctors:
// have more than 20 patients
// have a higher level of expertise (7+)
// Combine the two states from the functional interfaces and use the evaluate method of the EmergencyRoom class to display only those doctors.
// Use findFirst to find and display the Chief doctor in the emergency room.
// Use mutate to increase the expertise level of all doctors by 1.
// Use conditionalMutate to increase the expertise level of only doctors with more than 30 patients by 1.
// Use mapToLabels to transform all doctors into text descriptions and print them.
// Finally, print all the information about the emergency room using the toString method.




import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.Consumer;

interface DoctorEvaluator {
    boolean evaluate(Doctor doctor);
}


class HighExpertiseEvaluator implements DoctorEvaluator {
    @Override
    public boolean evaluate(Doctor doctor) {
        return doctor.getLevel() >= 7;
    }
}

class Doctor {
    private final int licenseNumber;
    private String name;
    private int level; // 1..10
    private int patients;

    public Doctor(int licenseNumber, String name, int level, int patients) {
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.level = level;
        this.patients = patients;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getPatients() {
        return patients;
    }

    public void setLevel(int level) {
        if (level > 10) level = 10;
        if (level < this.level) return; // cannot decrease
        this.level = level;
    }

    public void setPatients(int patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %d %d%s",
                name, licenseNumber, level, patients, level == 10 ? " [Chief]" : "");
    }
}

class EmergencyRoom {
    private final String hospitalName;
    private final Doctor[] doctors;
    private int size = 0;

    public EmergencyRoom(String hospitalName, int capacity) {
        this.hospitalName = hospitalName;
        this.doctors = new Doctor[capacity];
    }

    public boolean treat(Supplier<Doctor> supplier) {
        if (size >= doctors.length) return false;
        doctors[size++] = supplier.get();
        return true;
    }

    public void forEach(Consumer<Doctor> action) {
        for (int i = 0; i < size; i++) action.accept(doctors[i]);
    }

    public int count(Predicate<Doctor> condition) {
        int c = 0;
        for (int i = 0; i < size; i++) if (condition.test(doctors[i])) c++;
        return c;
    }

    public Doctor findFirst(Predicate<Doctor> condition) {
        for (int i = 0; i < size; i++) if (condition.test(doctors[i])) return doctors[i];
        return null;
    }

    public Doctor[] filter(Predicate<Doctor> condition) {
        int matches = count(condition);
        Doctor[] res = new Doctor[matches];
        int j = 0;
        for (int i = 0; i < size; i++)
            if (condition.test(doctors[i])) res[j++] = doctors[i];
        return res;
    }

    public String[] mapToLabels(Function<Doctor, String> mapper) {
        String[] res = new String[size];
        for (int i = 0; i < size; i++)
            res[i] = mapper.apply(doctors[i]);
        return res;
    }

    public void mutate(Consumer<Doctor> mutator) {
        for (int i = 0; i < size; i++) mutator.accept(doctors[i]);
    }

    public void conditionalMutate(Predicate<Doctor> condition, Consumer<Doctor> mutator) {
        for (int i = 0; i < size; i++)
            if (condition.test(doctors[i])) mutator.accept(doctors[i]);
    }

    public int countForEvaluation(DoctorEvaluator evaluator) {
        int c = 0;
        for (int i = 0; i < size; i++) if (evaluator.evaluate(doctors[i])) c++;
        return c;
    }

    public Doctor[] evaluate(DoctorEvaluator evaluator) {
        int cnt = countForEvaluation(evaluator);
        Doctor[] res = new Doctor[cnt];
        int j = 0;
        for (int i = 0; i < size; i++)
            if (evaluator.evaluate(doctors[i])) res[j++] = doctors[i];
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hospital: ").append(hospitalName)
                .append(" (").append(size).append("/").append(doctors.length).append(" doctors)\n");
        for (int i = 0; i < size; i++) {
            sb.append(doctors[i].toString()).append("\n");
        }
        return sb.toString();
    }
}

class NO_PUBLIC_CLASS_FOUND {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmergencyRoom er = new EmergencyRoom("University Clinic", 10);

        int n = sc.nextInt();
        Supplier<Doctor> supplier = () -> {
            int license = sc.nextInt();
            String name = sc.next();
            int level = sc.nextInt();
            int patients = sc.nextInt();
            return new Doctor(license, name, level, patients);
        };

        for (int i = 0; i < n; i++) er.treat(supplier);

        System.out.println("Doctors that are treating:");
        er.forEach(d -> System.out.println(d));
        System.out.println();

        System.out.println("=== All Doctors ===");
        er.forEach(d -> System.out.println(d));
        System.out.println();

        Predicate<Doctor> manyPatients = d -> d.getPatients() > 20;
        DoctorEvaluator highExpertise = new HighExpertiseEvaluator();

        Doctor[] selected = er.evaluate(d -> highExpertise.evaluate(d) && manyPatients.test(d));

        System.out.println("=== Doctors with higher number of patients and a higher level of expertise ===");
        for (Doctor d : selected) System.out.println(d);
        System.out.println();

        Doctor chief = er.findFirst(d -> d.getLevel() == 10);
        System.out.println("=== Chief doctor (level = 10) ===");
        System.out.println(chief != null ? chief : "No chief found");
        System.out.println();

        er.mutate(d -> d.setLevel(d.getLevel() + 1));
        System.out.println("=== Increase all expertise levels by 1 (max 10) ===");
        er.forEach(d -> System.out.println(d));
        System.out.println();

        er.conditionalMutate(d -> d.getPatients() >= 30, d -> d.setLevel(d.getLevel() + 1));
        System.out.println("=== Increase the level of expertise of every doctor by 1 ===");

        System.out.println();

        System.out.println("=== Map doctors to labels ===");
        String[] labels = er.mapToLabels(d -> "Name: " + d.getName() + ", Level: " + d.getLevel());
        for (String label : labels) System.out.println(label);
    }
}
//Predicate<T> е функционален интерфејс што прима еден објект од тип T и враќа boolean (true или false).
//Кога сакаш да провериш некој услов — дали нешто исполнува некој критериум.

//Supplier<T> ништо не прима, ама враќа нешто (објект од тип T).
// Кога се користи:
//Кога сакаш да создадеш/обезбедиш вредност, на пример нов објект, случаен број, тековен датум итн.


//Consumer<T> прима еден објект од тип T и ништо не враќа.
//Кога сакаш да примениш некакво дејство (акција) врз објект — на пример да му ја зголемиш вредноста, да го испечатиш итн.
