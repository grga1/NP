// Влезна задача: 
// текстот беше нешто да се најде максималниот број на појавувања на даден карактер во дадена линија и да се испечати тој број
// нешто отприлика вака е влезот:

// input
// -------
// zzzdravoo na site
// prijaaatno
// naprrredno programiranje

// output
// ----------
// 3 (бидејќи 'z' 3 пати се појавува)
// 3 (бидејќи 'a' 3 пати се појавува)
// 6 (бидејќи 'r' 6 пати се појавува)


import java.io.BufferedReader;
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String input = "";
        List<String> lista = new ArrayList<>();

          while (sc.hasNextLine()){
              String line = sc.nextLine();
              if (line.isEmpty()) break;
              lista.add(line);}

        for (int i = 0; i < lista.size(); i++) {
            HashMap<Character,Integer> mapa = new HashMap<>();
            char[] parts = lista.get(i).toCharArray();
            for (int j = 0; j < lista.get(i).length(); j++) {
                   if (Character.isAlphabetic(parts[j])) {
                       if (!mapa.containsKey(Character.toLowerCase(parts[j]))) {
                           mapa.put(Character.toLowerCase(parts[j]), 1);
                       } else {
                           mapa.put(Character.toLowerCase(parts[j]), mapa.get(Character.toLowerCase(parts[j])) + 1);
                       }
                   }
            }

            int max=-100;
            Character karakter='-';
            for (Map.Entry<Character,Integer> entry: mapa.entrySet()){
                if (entry.getValue()>max){
                    max=entry.getValue();
                    karakter=entry.getKey();
                }
            }
            System.out.println(max +"(бидејќи '"+karakter+"' "+max+" пати се појавува");
        }
    }

}
