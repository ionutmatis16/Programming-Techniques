package Model;

import View.SimFrame;

import java.util.ArrayList;

/**
 * Class that models the scheduler and distributes the tasks between the servers. The task will be
 * distributed to the server with minimum waiting clients.
 *
 * @author Ionut Matis, 30421
 */
public class Scheduler {
    private ArrayList<Server> servers;  //the list of all servers
    private int nrOfServers;            //maximum number of servers
    private int maxTasksPerServer;      //maximum number of tasks per server
    private SimFrame sf;                //a simulation frame to update the UI

    /**
     * Constructor which initializes the variables and adds maxNrOfServers of servers to the list.
     * Also, creates a thread for each one of the added servers
     *
     * @param maxNrOfServers    maximum number of servers
     * @param maxTasksPerServer maximum number of tasks per server
     * @param sf                the simulation frame on which we update the data
     */
    Scheduler(int maxNrOfServers, int maxTasksPerServer, SimFrame sf) {
        this.sf = sf;
        this.nrOfServers = maxNrOfServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<>();
        for (int i = 0; i < maxNrOfServers; i++) {
            Server s = new Server(sf, i);
            servers.add(s);
            Thread t = new Thread(s);
        }
    }

    /**
     * This method is used in the Simulation Manager to get the peak time. It calculates the total
     * number of tasks at a certain moment.
     *
     * @return total number of tasks in all servers
     */
    public int nrOfTasks() {
        int nrT = 0;
        for (Server s: servers) {
            nrT += s.getTasks().size();
        }
        return nrT;
    }

    /**
     * Distributes the task t to the server which has the smallest number of waiting clients, similar to
     * the SHORTEST_QUEUE policy. It does this by searching through the servers and returns the index of
     * the server which has the minimum number of tasks.
     * It also updates the log of the UI
     *
     * @param t task to be added to the list of servers
     */
    public void dispatchTask(Task t) {
        int minQSize = 100;
        int qNr = 0;
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).getTasks().size() < minQSize) {
                minQSize = servers.get(i).getTasks().size();
                qNr = i;
            }
        }
        sf.getLog().append(" " + t.toStringMin() + " entered at server: " + (qNr + 1) + "\n");
        servers.get(qNr).addTask(t);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
}
