package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller of the project. It also has the main method which stars all the processes.
 *
 * @author Ionut Matis, 30421
 */
public class SController {
    private InfoView infoView;      //instance of infoView so that I can get the input
    private SimulationManager sm;   //manager to simulate the data

    /**
     * Constructor of the class, calls the method which adds action listener to the single button of
     * the UI
     *
     * @param infoView InfoView from which we read the input
     */
    public SController(InfoView infoView) {
        this.infoView = infoView;
        addAct();
    }

    public static void main(String[] args) {
        InfoView infoView = new InfoView();
        SController q = new SController(infoView);
    }

    /**
     * Adds action listener to the button. This will fetch the data from infoview and create
     * a SimFrame that will display in real time the data entered. It also creates a simulation frame
     * based on the input and starts its process.
     */
    private void addAct() {
        infoView.getStart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int minInt = Integer.parseInt(infoView.getMinIntervalTF().getText());
                    int maxInt = Integer.parseInt(infoView.getMaxIntervalTF().getText());
                    int minServ = Integer.parseInt(infoView.getMinServiceTF().getText());
                    int maxServ = Integer.parseInt(infoView.getMaxServiceTF().getText());
                    if (minInt > maxInt || minServ > maxServ)
                        JOptionPane.showMessageDialog(new JPanel(), "Minimum is greater than maximum!",
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                    else {
                        int nrOfServ = Integer.parseInt(infoView.getNrQTextField().getText());
                        int nrOfTasks = Integer.parseInt(infoView.getNrOfTasks().getText());
                        int timer = Integer.parseInt(infoView.getSimTimeTF().getText());
                        SimFrame sf = new SimFrame(nrOfServ);
                        sm = new SimulationManager(minInt, maxInt, minServ, maxServ, nrOfServ, nrOfTasks,
                                timer, sf);
                        Thread t = new Thread(sm);
                        t.start();
                    }
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(new JPanel(), "You entered an invalid input!",
                            "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
