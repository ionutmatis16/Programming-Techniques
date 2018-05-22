import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PerformTasks {
    /**
     * Method that reads the data from a file and stores it in a list of MonitoredData
     *
     * @param fileName name of the file from which to read the data
     * @return a list of MonitorData representing the read data
     */
    public static List<MonitoredData> readActivityData(String fileName) {
        List<MonitoredData> activities = new ArrayList<>();
        try {
            //read file into stream
            Stream<String> stream = Files.lines(Paths.get(fileName));
            //for each line represented as a String, add it in the list a new Object
            stream.forEach((String s) -> activities.add(new MonitoredData(s)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activities;
    }


    /**
     * Method which takes a list of activities and returns the number of distinct days that appear in the log
     *
     * @param activities list of Objects of type MonitoredData
     * @return the number of distinct days
     */
    public static List<String> countDistinctDays(List<MonitoredData> activities) {
        List<String> stringDates = new ArrayList<>();

        /*
        Store in a list of strings the dates (ex: "2011-11-28")
         */
        activities.forEach((MonitoredData activity) -> {
            String sTime = activity.getStringStartTime().substring(0, 10); //get the date from start time
            String eTime = activity.getStringEndTime().substring(0, 10);   //get the date from end time
            if (sTime.equals(eTime))
                stringDates.add(sTime);     //if equal, add only one
            else {
                stringDates.add(sTime);     //they are not equal
                stringDates.add(eTime);     //add both
            }
        });

        return stringDates.stream()         //return the number of distinct dates
                .distinct()
                .collect(Collectors.toList());
    }


    /**
     * Method which maps a String (activity) to and Integer (its total number of appearances)
     *
     * @param activities list of Objects of type MonitoredData
     * @return a map of activities and their occurrences
     */
    public static Map<String, Integer> totalActivitiesCount(List<MonitoredData> activities) {
        /*
        The below block creates a list of Strings which represent all the distinct activities
         */
        List<String> stringActivities = activities.stream()
                .map(MonitoredData::getActivity)        //map each activity of MonitoredData to its activity name
                .distinct()                             //make sure they are distinct
                .collect(Collectors.toList());          //collect them in the list

        /*
        The below block associates each unique string activity searched above the number of appearances in the whole
        list of activities
         */
        return stringActivities.stream()
                .collect(Collectors.toMap(key -> key,           //the String itself will be the KEY
                        value -> {                              //the VALUE will be the number of appearances
                            return (int) activities.stream()
                                    .filter(fil -> value.equals(fil.getActivity()))  //get only the wanted act
                                    .count();
                        }));
    }


    /**
     * Creates a map with an Integer (the number of the day) as a KEY and a map, with a String (activity) as a key and
     * an Integer (number of occurrences in that day) as a value, as a VALUE.
     *
     * @param activities list of Objects of type MonitoredData
     * @return a structure that counts the occurrences of each activity of each day
     */
    public static Map<Integer, Map<String, Integer>> activityCountEachDay(List<MonitoredData> activities) {
        /*
        List of all dates from the file as integers
         */
        List<Integer> datesAsInt = activities.stream()
                //map each activity to its date nr (ex. from "Nov 12" get 12)
                .map(act -> Integer.parseInt(act.getStartTime().toString().substring(8, 10)))
                .distinct()                                                 //make sure they are distinct
                .collect(Collectors.toList());                              //collect them

        /*
         Function that takes an Integer (date as a number) and a String(activity) and returns the number of activities
         performed in that day.
         */
        BiFunction<Integer, String, Integer> actDay = (nrDay, activity) ->
                (int) activities.stream()
                        //find only the MonitoredData that have the date equal to the one we want
                        .filter(monData -> Integer.parseInt(monData.getStartTime().toString().substring(8, 10)) == nrDay)
                        //find only the MonitoredData that have the activity equal to the one we want
                        .filter(monData -> monData.getActivity().equals(activity))
                        .count();

        return datesAsInt.stream()
                .collect(Collectors.toMap(key -> key,                           //the KEY is the Integer itself
                        value -> {                                              //the value is the result of BiFunction of each activity
                            //build a list of all different activities
                            List<String> stringActivities = activities.stream()
                                    .map(MonitoredData::getActivity)            //map each MonData to its activity name
                                    .distinct()                                 //make sure they are distinct
                                    .collect(Collectors.toList());              //collect them in the list
                            return stringActivities.stream()                    //return a map as a VALUE
                                    .collect(Collectors.toMap(key -> key,       //the key is the String (activity) itself
                                            val -> actDay.apply(value, val)));  //the value is the BiFunction defined above
                        }));
    }


    /**
     * Function that takes a string (name of an activity) and returns its total duration in milliseconds
     *
     * @param activities list of Objects of type MonitoredData
     * @return a function that takes a String and returns a Long
     */
    private static Function<String, Long> totalTime(List<MonitoredData> activities) {
        return actStr -> activities.stream()                          //take each activity in the list
                .filter(fil -> actStr.equals(fil.getActivity()))      //make sure the found activity is the one we want
                .map(act -> Duration.between(act.getStartTime(), act.getEndTime()).toMinutes())  //map each act to its duration
                .reduce((long) 0, (a, b) -> a + b);                  //add all values
    }


    /**
     * Method that creates a map with a String (activity) as a KEY and a Date (total duration) as a VALUE of the activities
     * that have a total duration of more than 10 hours
     *
     * @param activities list of Objects of type MonitoredData
     * @return a map of String, Date
     */
    public static Map<String, Date> totalDuration(List<MonitoredData> activities) {
        /*
        Create a list of all distinct activities
         */
        List<String> stringActivities = activities.stream()
                .map(MonitoredData::getActivity)            //map each activity of MonitoredData to its activity name
                .distinct()                                 //make sure they are distinct
                .collect(Collectors.toList());              //collect them in the list

        /*
        Map each activity that meets the requirements (takes > 10 hours) to a date and collect it in a map which will be
        returned
         */
        return stringActivities.stream()
                .filter(actStr -> {                                   //filter the activities so that only the ones over
                    long res = totalTime(activities).apply(actStr);   //10 hours are accepted
                    //res represents the time in minutes
                    System.out.println(actStr + " = " + res + " minutes ~ " + res / 60 + " hours.");
                    return (res / 60) > 10;
                })
                .collect(Collectors.toMap(key -> key,           //the KEY will be the string
                        //the value will be a new Date. Multiply argument with 60000L so that we get the milliseconds
                        value -> new Date(totalTime(activities).apply(value) * 60000L)));
    }


    /**
     * Method that creates a list of activities that have 90% of the monitoring samples with duration less than 5 minutes
     *
     * @param activities list of Objects of type MonitoredData
     * @return list of activities
     */
    public static List<String> lessThan5Min(List<MonitoredData> activities) {
        List<String> stringActivities = activities.stream()    //creates a list of all distinct activities
                .map(MonitoredData::getActivity)               //map each activity of MonitoredData to its activity name
                .distinct()                                    //make sure they are distinct
                .collect(Collectors.toList());                 //collect them in the list

        /*
        Function that takes a String (name of an activity) and returns the number of actions which appear in the file
         */
        Function<String, Long> totalNrOfAct = actStr -> activities.stream()   //take each activity in the list
                .filter(fil -> actStr.equals(fil.getActivity()))      //make sure the found activity is the one we want
                .count();

        /*
        Function that takes a String and returns how many activities of under 5 min appear in the file
         */
        Function<String, Long> nrOfActLess5 = actStr -> activities.stream()
                .filter(fil -> actStr.equals(fil.getActivity()))       //make sure the found activity is the one we want
                .filter(fil ->                                        //decide whether the activity is under 5 min
                    Duration.between(fil.getStartTime(), fil.getEndTime()).toMinutes() < 5) //result is in minutes
                .count();

        System.out.println("Total number of actions:");
        stringActivities.forEach(st -> System.out.println(st + ": " + totalNrOfAct.apply(st)));
        System.out.println("\nTotal number of actions under 5 min:");
        stringActivities.forEach(st -> System.out.println(st + ": " + nrOfActLess5.apply(st)));
        System.out.println("\nPercentages:");
        return stringActivities.stream()
                .filter(fil -> {
                    System.out.println(fil + ": " + nrOfActLess5.apply(fil) * 100 / totalNrOfAct.apply(fil) + "%");
                    return (float) nrOfActLess5.apply(fil) * 100 / totalNrOfAct.apply(fil) > 90;
                })
                .collect(Collectors.toList());
    }
}
