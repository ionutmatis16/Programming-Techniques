import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Class with 3 fields that is used to store the read data from the Activity.txt file.
 *
 * @author Ionut Matis
 */
public class MonitoredData {
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String activity;

    /**
     * Constructor that takes a line of information from the file, splits it and builds the object
     *
     * @param readLine read line
     */
    public MonitoredData(String readLine) {
        String[] toSplit = readLine.split("\t\t");  //each important information is separated by 2 tabs
        this.startTime = LocalDateTime.parse(toSplit[0], dateFormat);    //use the formatter for start time
        this.endTime = LocalDateTime.parse(toSplit[1], dateFormat);      //use the formatter for end time
        this.activity = toSplit[2];                       //activity is the last element of the split data

    }

    @Override
    public String toString() {
        return "MonitoredData{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", activity='" + activity + '\'' +
                '}';
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getActivity() {
        return activity;
    }

    /**
     * @return start time in a format of the file (apply the inverse formatter)
     */
    public String getStringStartTime() {
        return dateFormat.format(startTime);
    }

    /**
     * @return end time in a format of the file (apply the inverse formatter)
     */
    public String getStringEndTime() {
        return dateFormat.format(endTime);
    }
}
