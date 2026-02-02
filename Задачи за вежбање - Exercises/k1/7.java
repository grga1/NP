import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class UnsupportedFormatException extends  Exception{
    public UnsupportedFormatException(String message) {
        super(message);
    }
}
class InvalidTimeException extends  Exception{
    public InvalidTimeException(String message) {
        super(message);
    }
}
class Time{
    int hour;
    int minutes;

    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }
    public String getFORMAT_24(){
        if(hour<10){
            return String.format("%2d:%02d", hour, minutes);

        }
        return String.format("%d:%02d", hour, minutes);
        }



        public String getFORMAT_AMPM(){
        if (hour==0){
            hour+=12;
            return String.format("%d:%02d AM", hour, minutes);
        }
        else if (hour==12){
            return String.format("%d:%02d PM", hour, minutes);

        }else if (1<=hour&&hour<12){
            if(hour<10){
                return String.format("%2d:%02d AM", hour, minutes);

            }
            return String.format("%d:%02d AM", hour, minutes);
        }else{
            hour-=12;
            if(hour<10){
                return String.format("%2d:%02d PM", hour, minutes);

            }
            return String.format("%d:%02d PM", hour, minutes);
        }
     }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }
}
class TimeTable{
    List<Time> times;
    public TimeTable() {
     this.times = new ArrayList<>();
    }
    void readTimes(InputStream inputStream) throws UnsupportedFormatException, InvalidTimeException {
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNext()){
            String token = sc.next();
            String parts[];
            if (token.contains(":")){
                    parts=token.split(":");
            }else if (token.contains(".")){
                parts=token.split("\\.");
            }else{
                throw  new UnsupportedFormatException(
                        String.format("%s",token)
                );
            }

                Integer hour = Integer.parseInt(parts[0].toString());
                Integer minutes = Integer.parseInt(parts[1].toString());
             if (hour<0||hour>23||minutes>59||minutes<0){
                 throw  new InvalidTimeException(
                   String.format("%s",token)
                 );
            }

           times.add( new Time(hour,minutes));
        }
    }
    void writeTimes(OutputStream outputStream, TimeFormat format){
        PrintWriter pw = new PrintWriter(outputStream);
        times.sort(Comparator.comparing(Time::getHour).thenComparing(Time::getMinutes));
        for (Time t : times) {
            if (format == TimeFormat.FORMAT_24) {
                pw.println(t.getFORMAT_24());

            } else if (format == TimeFormat.FORMAT_AMPM) {
                pw.println(t.getFORMAT_AMPM());
            }
        }
        pw.flush();
    }
}
