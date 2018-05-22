package Model;

import View.SimFrame;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that models the concept of a Server, having an ArrayBlockingQueue of tasks and an AtomicInteger
 * as the total waiting period of the server. This class will hold the tasks to be processed
 *
 * @author Ionut Matis, 30421
 */

public class Server implements Runnable {
    private ArrayBlockingQueue<Task> tasks;     //the tasks of the current server
    private AtomicInteger waitingPeriod;        //the waiting time of the server
    private int addedTasks;                     //number of tasks
    private SimFrame sf;                        //simulation frame in order to update the UI
    private int serverNr;                       //the number of this server

    /**
     * Constructor which initializes the frame annd the server number
     *
     * @param sf       the frame in which we update the UI
     * @param serverNr the number of this server
     */
    Server(SimFrame sf, int serverNr) {
        this.sf = sf;
        this.serverNr = serverNr;
        tasks = new ArrayBlockingQueue<>(50);
        waitingPeriod = new AtomicInteger(0);
        addedTasks = 0;
    }

    /**
     * Adds a new task to the ArrayBlockingQueue and updates the waiting period and the number of tasks
     *
     * @param newTask the task to be added
     */
    public void addTask(Task newTask) {
        newTask.setFinishTime(waitingPeriod.get());
        tasks.add(newTask);
        addedTasks++;
        waitingPeriod.getAndAdd(newTask.getServiceTime());
    }

    /**
     * Overridden run method that takes a server from a task, updates the waiting period and puts the
     * server on sleep for a specific amount of time. It also updates the UI with the specific task
     * to be processed and appends text to the logger area.
     */
    public void run() {
        while (true) {
            try {
                Task t = tasks.take();
                if (addedTasks > 0)
                    sf.getqPanels().get(serverNr).getServedNow().setText("Serving: " + t.toStringMin());
                Thread.sleep(t.getServiceTime() * 1000);
                waitingPeriod.getAndAdd(t.getServiceTime() * -1);
                sf.getqPanels().get(serverNr).getServedNow().setText("Serving:         ");
                addedTasks--;
                sf.getLog().append(" " + t.toString() + " exited at second " + t.getFinishTime() + ".\n");
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
    }

    public ArrayBlockingQueue<Task> getTasks() {
        return tasks;
    }

    /**
     * Creates a string of all tasks of the current server so that they can be displayed in the UI
     *
     * @return text which represents all the tasks of the current server
     */
    public String tasksToString() {
        String s = "";
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext())
            s = s + it.next().toStringMin() + "\n";
        return s;
    }
}
