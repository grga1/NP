import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}
class Podatoci{
    int P;
    int W;
    int D;
    int L;
    int GS;  // goals scored
    int GC;

    public Podatoci() {
        this.P=0;
        this.W=0;
        this.D=0;
        this.L=0;
        this.GS=0;
        this.GC=0;
    }

    public void setP(int p) {
        P = p;
    }

    public void setW(int w) {
        W = w;
    }

    public void setD(int d) {
        D = d;
    }

    public void setL(int l) {
        L = l;
    }


    public int getP() {
        return P;
    }

    public int getW() {
        return W;
    }

    public int getD() {
        return D;
    }

    public int getL() {
        return L;
    }

    public int getPTS() {
        return getW()*3+getD()*1;
    }
    public void addGoalsScored(int g) {
        GS += g;
    }

    public void addGoalsConceded(int g) {
        GC += g;
    }

    public int getGoalDifference() {
        return GS - GC;
    }
    
    @Override
    public String toString() {
        return String.format("%-15d5 %d %d %d %d",getP(),getW(),getD(),getL(),getPTS());
    }
}
class FootballTable{
   HashMap<String,Podatoci> mapa;

    public FootballTable() {
        this.mapa = new HashMap<>();
    }

    private Podatoci getTeam(String name) {
        return mapa.computeIfAbsent(name, t -> new Podatoci());
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        Podatoci home = getTeam(homeTeam);
        Podatoci away = getTeam(awayTeam);

        // одигран натпревар за двата тима
        home.setP(home.getP() + 1);
        away.setP(away.getP() + 1);

        // голови
        home.addGoalsScored(homeGoals);
        home.addGoalsConceded(awayGoals);

        away.addGoalsScored(awayGoals);
        away.addGoalsConceded(homeGoals);

        // резултат
        if (homeGoals > awayGoals) {
            home.setW(home.getW() + 1);
            away.setL(away.getL() + 1);
        } else if (homeGoals < awayGoals) {
            away.setW(away.getW() + 1);
            home.setL(home.getL() + 1);
        } else { // нерешено
            home.setD(home.getD() + 1);
            away.setD(away.getD() + 1);
        }
    }

    public void printTable(){
        int i=1;
        List<Map.Entry<String, Podatoci>> lista = new ArrayList<>(mapa.entrySet());

        lista.sort(
                Comparator
                        .comparing((Map.Entry<String, Podatoci> e )-> e.getValue().getPTS())
                        .reversed()
                        .thenComparing(
                                (Map.Entry<String, Podatoci> e) -> e.getValue().getGoalDifference(),
                                Comparator.reverseOrder()
                        )
                        .thenComparing(Map.Entry::getKey)
        );
        for (Map.Entry<String,Podatoci> entry: lista){

            System.out.printf("%2d. %-15s%5d%5d%5d%5d%5d\n",i++,entry.getKey(),entry.getValue().getP(),entry.getValue().getW(),entry.getValue().getD(),entry.getValue().getL(),entry.getValue().getPTS());
        }
    }
}

