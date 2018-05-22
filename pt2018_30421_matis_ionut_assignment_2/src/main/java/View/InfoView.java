package View;

import javax.swing.*;
import java.awt.*;

/**
 * This class constructs the first frame, in which we insert the data for the simulation.
 *
 * @author Ionut Matis, 30421
 */
public class InfoView {
    private final static String TITLE = "Queue Info";
    private final static int WIDTH = 500;
    private final static int HEIGHT = 700;
    private final static int locX = 150;
    private final static int locY = 200;
    private final static Font TNR_BOLD40 = new Font("Times New Roman", Font.BOLD, 40);
    private final static Font TNR_PLAIN40 = new Font("Times New Roman", Font.PLAIN, 40);
    private JFrame frame = new JFrame();
    private JPanel contentPanel = new JPanel();
    private JTextField minIntervalTF = new JTextField("2");
    private JTextField maxIntervalTF = new JTextField("10");
    private JTextField minServiceTF = new JTextField("2");
    private JTextField maxServiceTF = new JTextField("7");
    private JTextField simTimeTF = new JTextField("25");

    private JTextField nrQTextField = new JTextField("4");
    private JTextField nrOfTasks = new JTextField("15");
    private JButton start = new JButton("Start");

    /**
     * Constructor which initializes all the contents of the class
     */
    public InfoView() {
        initContentPanel();
        initStyles();
        initComp();
    }

    /**
     * This method sets the attributes of the frame
     */
    private void initComp() {
        frame.setTitle(TITLE);
        frame.setLocation(locX, locY);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Initializes the styles of the contents, such as Fonts, Sizes or Colors
     */
    private void initStyles() {
        minIntervalTF.setFont(TNR_PLAIN40);
        minIntervalTF.setPreferredSize(new Dimension(60, 50));
        maxIntervalTF.setFont(TNR_PLAIN40);
        maxIntervalTF.setPreferredSize(new Dimension(60, 50));

        minServiceTF.setFont(TNR_PLAIN40);
        minServiceTF.setPreferredSize(new Dimension(60, 50));
        maxServiceTF.setFont(TNR_PLAIN40);
        maxServiceTF.setPreferredSize(new Dimension(60, 50));

        simTimeTF.setFont(TNR_PLAIN40);
        simTimeTF.setPreferredSize(new Dimension(70, 50));

        nrQTextField.setPreferredSize(new Dimension(55, 50));
        nrQTextField.setFont(TNR_PLAIN40);

        nrOfTasks.setPreferredSize(new Dimension(55, 50));
        nrOfTasks.setFont(TNR_PLAIN40);

        start.setFont(TNR_PLAIN40);
        start.setPreferredSize(new Dimension(150, 50));
        start.setBackground(Color.green);
    }

    /**
     * Initializes the content panel and adds all components to the main frame in a specific order
     * and compounded panels
     */
    private void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel intervalT = new JLabel("Arrival time");
        JLabel minIntervalL = new JLabel("Min: ");
        JLabel maxIntervalL = new JLabel("Max: ");
        JLabel serviceT = new JLabel("Service time");
        JLabel minServiceL = new JLabel("Min: ");
        JLabel maxServiceL = new JLabel("Max: ");
        JLabel nrQLabel = new JLabel("Nr of servers: ");
        JLabel simTime = new JLabel("Simulation time: ");
        JLabel nrOfTasksL = new JLabel("Nr of tasks: ");
        intervalT.setFont(TNR_BOLD40);
        minIntervalL.setFont(TNR_PLAIN40);
        maxIntervalL.setFont(TNR_PLAIN40);
        serviceT.setFont(TNR_BOLD40);
        minServiceL.setFont(TNR_PLAIN40);
        maxServiceL.setFont(TNR_PLAIN40);
        nrQLabel.setFont(TNR_BOLD40);
        simTime.setFont(TNR_BOLD40);
        nrOfTasksL.setFont(TNR_BOLD40);

        JPanel intP = new JPanel();
        intP.add(intervalT);

        JPanel intMinMax = new JPanel();
        intMinMax.add(minIntervalL);
        intMinMax.add(minIntervalTF);
        intMinMax.add(Box.createRigidArea(new Dimension(50, 10)));
        intMinMax.add(maxIntervalL);
        intMinMax.add(maxIntervalTF);

        JPanel serP = new JPanel();
        serP.add(serviceT);

        JPanel serMinMax = new JPanel();
        serMinMax.add(minServiceL);
        serMinMax.add(minServiceTF);
        serMinMax.add(Box.createRigidArea(new Dimension(50, 10)));
        serMinMax.add(maxServiceL);
        serMinMax.add(maxServiceTF);

        JPanel nrQP = new JPanel();
        nrQP.add(nrQLabel);
        nrQP.add(nrQTextField);

        JPanel nrT = new JPanel();
        nrT.add(nrOfTasksL);
        nrT.add(nrOfTasks);

        JPanel simIntP = new JPanel();
        simIntP.add(simTime);
        simIntP.add(simTimeTF);

        JPanel startP = new JPanel();
        startP.add(start);

        contentPanel.add(Box.createRigidArea(new Dimension(50, 20)));
        contentPanel.add(intP);
        contentPanel.add(intMinMax);
        contentPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        contentPanel.add(serP);
        contentPanel.add(serMinMax);
        contentPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        contentPanel.add(nrQP);
        contentPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        contentPanel.add(nrT);
        contentPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        contentPanel.add(simIntP);
        contentPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        contentPanel.add(startP);
        contentPanel.add(Box.createRigidArea(new Dimension(100, 20)));
        frame.add(contentPanel);
    }

    public JTextField getMinIntervalTF() {
        return minIntervalTF;
    }

    public JTextField getMaxIntervalTF() {
        return maxIntervalTF;
    }

    public JTextField getMinServiceTF() {
        return minServiceTF;
    }

    public JTextField getMaxServiceTF() {
        return maxServiceTF;
    }

    public JTextField getSimTimeTF() {
        return simTimeTF;
    }

    public JButton getStart() {
        return start;
    }

    public JTextField getNrQTextField() {
        return nrQTextField;
    }

    public JTextField getNrOfTasks() {
        return nrOfTasks;
    }
}
