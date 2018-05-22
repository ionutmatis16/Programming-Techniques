package View;

import javax.swing.*;
import java.awt.*;

/**
 * Class that models the first panel the user sees. It is used to manage the whole project by providing
 * buttons for each window (person, account) and a button to generate reports.
 *
 * @author Ionut Matis, 30421
 */
public class StartView {
    private final static String TITLE = "Bank Application";
    private final static int WIDTH = 542;
    private final static int HEIGHT = 310;
    private final static int locX = 100;
    private final static int locY = 350;
    private final static Font TNR_PLAIN40 = new Font("Times New Roman", Font.PLAIN, 40);
    private JFrame frame = new JFrame();
    private JButton pOPButton = new JButton("Person operations");
    private JButton aOPButton = new JButton("Account operations");
    private JButton genRepButton = new JButton("Generate reports");

    private JPanel contentPanel = new JPanel();

    /**
     * Constructor that calls the initialization methods
     */
    public StartView() {
        initContentPanel();
        initStyles();
        initComp();
    }

    /**
     * Method that prepares the frame by setting the title, location, width, height etc
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
     * Method that initializes the style of the components
     */
    private void initStyles() {
        pOPButton.setFont(TNR_PLAIN40);
        aOPButton.setFont(TNR_PLAIN40);
        genRepButton.setFont(TNR_PLAIN40);
    }

    /**
     * Method that adds to the frame the necessary components
     */
    private void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel placeP = new JPanel();
        placeP.add(pOPButton);

        JPanel seeCP = new JPanel();
        seeCP.add(aOPButton);

        JPanel genP = new JPanel();
        genP.add(genRepButton);

        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(placeP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        contentPanel.add(seeCP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        contentPanel.add(genP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));

        frame.add(contentPanel);
    }

    public JButton getpOPButton() {
        return pOPButton;
    }

    public JButton getaOPButton() {
        return aOPButton;
    }

    public JButton getGenRepButton() {
        return genRepButton;
    }

    public JFrame getFrame() {
        return frame;
    }
}
