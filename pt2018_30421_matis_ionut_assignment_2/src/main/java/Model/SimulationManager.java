package Model;

import View.SimFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * Models the Simulation manager of the whole project, based on the data the user introduced in UI.
 * It also contains the Random Client Generator and the global timer.
 *
 * @author Ionut Matis, 30421
 */
public class SimulationManager implements Runnable {
    private int timeLimit;                      //the simulation time
    private int minArrivalTime;                 //minimum arrival time of the client
    private int maxArrivalTime;                 //maximum arrival time of the client
    private int minProcessingTime;              //minimum processing time of the client
    private int maxProcessingTime;              //maximum processing time of the client
    private int nrOfServers;                    //nr of servers to work with
    private int nrOfTasks;                      //total number of tasks
    private int currentTime;                    //actual time of the simulation
    private Scheduler scheduler;                //scheduler for the servers
    private SimFrame simulationFrame;           //frame on which I displayed the details
    private ArrayList<Task> generatedTasks;     //random generated tasks

    /**
     * Constructor which initializes the elements, adds threads to each server, starts them and
     * calls the method which generates the random tasks
     *
     * @param minArrivalTime    minimum arrival time of the client
     * @param maxArrivalTime    maximum arrival time of the client
     * @param minProcessingTime minimum processing time of the client
     * @param maxProcessingTime maximum processing time of the client
     * @param nrOfServers       nr of servers to work with
     * @param nrOfTasks         total number of tasks
     * @param timeLimit         the simulation time
     * @param sf                frame on which I displayed the details
     */
    public SimulationManager(int minArrivalTime, int maxArrivalTime, int minProcessingTime,
                             int maxProcessingTime, int nrOfServers,
                             int nrOfTasks, int timeLimit, SimFrame sf) {
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.nrOfServers = nrOfServers;
        this.nrOfTasks = nrOfTasks;
        this.timeLimit = timeLimit;
        simulationFrame = sf;

        this.currentTime = 0;
        generatedTasks = new ArrayList<>();

        scheduler = new Scheduler(nrOfServers, 50, sf);
        for (Server s : scheduler.getServers()) {
            Thread t = new Thread(s);
            t.start();
        }
        generateNRandomTasks();
    }

    /**
     * This is the Random Client(Task) generator. Generates nrOfTasks tasks with a random arrival
     * and service time and adds them to the list of generated tasks. In the end, I sort the tasks
     * based on their arrival time, using the sort method of Collection framework
     */
    private void generateNRandomTasks() {
        Random rand = new Random();
        for (int i = 0; i < nrOfTasks; i++) {
            int arrTime = rand.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            int serTime = rand.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            generatedTasks.add(new Task(arrTime, serTime));
        }
        Collections.sort(generatedTasks);
    }

    /**
     * This is the run method of the class. It runs until the current timer (initialized with 0) gets
     * to the value of timeLimit (read from the UI). It takes a task from the generated ones and
     * dispatches it to the server with the minimum amount of people. After this, it updates the UI
     * increases the currentTime and waits for a second.
     */
    @Override
    public void run() {
        int currentTime = 0;
        int sumArr = 0;
        int finT = 0;
        int peakTask = 0, peakAtSec = 0;
        while (currentTime <= timeLimit) {
            Iterator<Task> it = generatedTasks.iterator();
            while (it.hasNext()) {
                Task t = it.next();
                if (t.getArrivalTime() == currentTime) {
                    sumArr += currentTime;
                    scheduler.dispatchTask(t);
                    if (scheduler.nrOfTasks() > peakTask) {
                        peakTask = scheduler.nrOfTasks();
                        peakAtSec = currentTime;
                    }
                    finT += t.getFinishTime();
                    //simulationFrame.displayData(getScheduler().getServers());
                    it.remove();
                }
            }
            simulationFrame.getTimerTF().setText(currentTime + "");
            simulationFrame.displayData(getScheduler().getServers());
            currentTime++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Run in Simulation manager interrupted.");
            }
        }
        simulationFrame.getLog().append(" ====== Average waiting time: " +
                (float)(finT - sumArr) / nrOfTasks + " seconds.\n");
        simulationFrame.getLog().append(" ====== Peak time: " + peakTask + " tasks at second " +
                peakAtSec + ".\n");
    }

    private Scheduler getScheduler() {
        return scheduler;
    }

    public int getCurrentTime() {
        return currentTime;
    }
}
