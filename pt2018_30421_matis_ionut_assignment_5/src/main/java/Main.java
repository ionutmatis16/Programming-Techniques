import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<MonitoredData> list = PerformTasks.readActivityData("Activities.txt");
        System.out.println("TASK 0: LIST OF ALL MONITORED DATA ELEMENTS:");
        list.forEach(s -> System.out.println(s.getStringStartTime() + "\t\t" +
                                             s.getStringEndTime() + "\t\t" + s.getActivity()));
        System.out.println("\nTASK 1: LIST OF ALL DISTINCT DATES:");
        PerformTasks.countDistinctDays(list).forEach(System.out::println);
        System.out.println("Total: " + PerformTasks.countDistinctDays(list).size());

        try {
            PrintWriter writer = new PrintWriter("Task2-totalNrOfOcc.txt", "UTF-8");
            PerformTasks.totalActivitiesCount(list).forEach((k, v) ->
                writer.println("Activity: " + k + "\t\t Count: " + v));
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("ERROR at writing.");
        }

        try {
            PrintWriter writer = new PrintWriter("Task3-countEachActEachDay.txt", "UTF-8");
            PerformTasks.activityCountEachDay(list).forEach((k, v) -> {
                writer.println("Day: " + k);
                v.forEach((k1, v1) -> {
                    writer.println("\tActivity: " + k1 + "\t\t" + "Count: " + v1);
                });
                writer.println();
            });
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("ERROR at writing.");
        }

        try{
            PrintWriter writer = new PrintWriter("Task4-actGreaterThan10.txt", "UTF-8");
            System.out.println("\nTASK 4: TOTAL DURATION IN HOURS");
            PerformTasks.totalDuration(list).forEach((s, d) -> {
                writer.println(s + "\t\t" + " Duration: " +d.getTime() / 1000L / 3600 + " hours.");
            });
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("ERROR at writing.");
        }

        try{
            PrintWriter writer = new PrintWriter("Task5-90lessThan5.txt", "UTF-8");
            System.out.println("\nTASK 5: 90% OF RECORDS WITH DURATION LESS THAN 5 MIN");
            PerformTasks.lessThan5Min(list).forEach(act -> writer.println(act));
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("ERROR at writing.");
        }
    }

}
