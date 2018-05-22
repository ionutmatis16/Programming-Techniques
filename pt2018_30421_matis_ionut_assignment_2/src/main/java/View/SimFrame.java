package View;

import Model.Server;
import Model.SimulationManager;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class that displays the queues in real time (the timer, the queues and the text area with the log).
 *
 * @author Ionut Matis, 30421
 */

public class SimFrame extends JFrame {
    private final static String TITLE = "Queue simulation";
    private final static int WIDTH = 1300;
    private final static int HEIGHT = 980;
    private final static int locX = 637;
    private final static int locY = 0;
    private final static Font TNR_PLAIN30 = new Font("Times New Roman", Font.PLAIN, 30);
    private final static Font TNR_BOLD40 = new Font("Times New Roman", Font.BOLD, 40);
    private final static Font TNR_PLAIN40 = new Font("Times New Roman", Font.PLAIN, 40);
    private JTextField timerTF = new JTextField();
    private int firstTimer;
    private JPanel contentPanel = new JPanel();
    private JPanel qMainPanel; //panel that holds the qs
    private ArrayList<SPanel> qPanels;
    private JTextArea log;

    /**
     * Constructor that initializes the panels and components, sets the timer to 0
     *
     * @param nrOfPanels number of panels to be added
     */
    public SimFrame(int nrOfPanels) {
        qPanels = new ArrayList<>();
        qMainPanel = new JPanel();
        timerTF.setText("0");
        log = new JTextArea();
        initContentPanel();
        initStyles();
        initPanels(nrOfPanels);
        initComp();
    }

    /**
     * Creates nrOfPanels panels and places them on the qMainPanel
     *
     * @param nrOfPanels number of panels to be added
     */
    private void initPanels(int nrOfPanels) {
        for (int i = 0; i < nrOfPanels; i++) {
            SPanel qp = new SPanel(i + 1, WIDTH / (nrOfPanels + 10));
            qPanels.add(qp);
            qMainPanel.add(qp);
        }
    }

    /**
     * Initializes the component (setting the title, location, width, height etc)
     */
    private void initComp() {
        this.setTitle(TITLE);
        this.setLocation(locX, locY);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        //this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Initializes the styles (fonts, sizes etc)
     */
    private void initStyles() {
        timerTF.setFont(new Font("Times New Roman", Font.PLAIN, 70));
        timerTF.setPreferredSize(new Dimension(150, 100));
        timerTF.setEditable(false);
        timerTF.setBackground(Color.white);

        qMainPanel.setPreferredSize(new Dimension(WIDTH, 700));
        qMainPanel.setBorder(BorderFactory.createEtchedBorder());
        qMainPanel.setLayout(new BoxLayout(qMainPanel, BoxLayout.X_AXIS));
        //qMainPanel.setPreferredSize(new Dimension(WIDTH, 300));

        //log.setPreferredSize(new Dimension(WIDTH - 50, 200));
        log.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        log.setEditable(false);
        DefaultCaret caret = (DefaultCaret) log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    /**
     * This method sets the texts on each panel in real time (it is called every second in Simulation
     * manager). For each server it gets the tasks as a string and updates the text field accordingly
     *
     * @param servers
     */
    public void displayData(ArrayList<Server> servers) {
        for (int i = 0; i < servers.size(); i++)
            qPanels.get(i).getTextArea().setText(servers.get(i).tasksToString());
    }

    /**
     * This method arranges the elements on the frame, placing each component one by one. It also
     * adds a scrollbar to the log
     */
    private void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel timerP = new JPanel();
        JLabel timerLabel = new JLabel("Timer");
        timerLabel.setFont(TNR_BOLD40);
        timerP.add(timerLabel);

        JPanel timerTFP = new JPanel();
        timerTFP.add(timerTF);

        JPanel logP = new JPanel();
        logP.setSize(new Dimension(WIDTH, 500));

        JScrollPane scroll = new JScrollPane(log);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(WIDTH, 500));

        contentPanel.add(Box.createRigidArea(new Dimension(50, 20)));
        contentPanel.add(timerP);
        contentPanel.add(timerTFP);
        contentPanel.add(Box.createRigidArea(new Dimension(50, 50)));
        contentPanel.add(qMainPanel);
        contentPanel.add(scroll);
        contentPanel.add(Box.createRigidArea(new Dimension(50, 10)));

        this.add(contentPanel);
    }

    public JTextField getTimerTF() {
        return timerTF;
    }

    public void setTimerTF(JTextField timerTF) {
        this.timerTF = timerTF;
    }

    public ArrayList<SPanel> getqPanels() {
        return qPanels;
    }

    public int getFirstTimer() {
        return firstTimer;
    }

    public JTextArea getLog() {
        return log;
    }
}
