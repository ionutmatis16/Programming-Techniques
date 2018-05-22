package Model;

/**
 * This class models the Task (Client) concept that will be used by all other classes. Its parameters
 * will be generated randomly.
 *
 * @author Ionut Matis, 30421
 */
public class Task implements Comparable<Task> {
    private static int taskCounter = 1; //current counter of the Task objects created
    private String ID;                  //name of the Task
    private int arrivalTime;            //the time the task arrived at the Server
    private int serviceTime;            //the time needed for the task to finish its work
    private int finishTime;             //the time the task will finish its work

    /**
     * Constructor of the Class which initializes the parameters
     *
     * @param arrivalTime the time the task arrives at the server
     * @param serviceTime the time needed for the task to finish its work
     */
    Task(int arrivalTime, int serviceTime) {
        ID = "Task: " + taskCounter;
        taskCounter++;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    /**
     * Method used to compare 2 tasks based on their arrival time
     *
     * @param o the task to which we compare this object
     * @return 1 if this is smaller than o, -1 if not and 0 if the arrival times are equal
     */

    public int compareTo(Task o) {
        if (this.getArrivalTime() < o.getArrivalTime()) {
            return 1;
        } else {
            if (this.getArrivalTime() > o.getArrivalTime()) {
                return -1;
            }
            return 0;
        }
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int serverPeriod) {
        finishTime = arrivalTime + serviceTime + serverPeriod;
    }

    /**
     * Overridden method so that it displays the important parameters of the task
     *
     * @return readable format of a task
     */
    @Override
    public String toString() {
        return ID + " with arrival time: " + arrivalTime + ", service time: " + serviceTime;
    }

    public String toStringMin() {
        return ID + "";
    }
}
