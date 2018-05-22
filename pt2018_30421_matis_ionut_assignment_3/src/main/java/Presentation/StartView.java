package Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Class that models the first panel the user sees. It is used to manage the whole project by providing
 * buttons for each window (products, customers, orders)
 *
 * @author Ionut Matis, 30421
 */
public class StartView {
    private final static String TITLE = "Warehouse Application";
    private final static int WIDTH = 542;
    private final static int HEIGHT = 400;
    private final static int locX = 100;
    private final static int locY = 503;
    private final static Font TNR_PLAIN40 = new Font("Times New Roman", Font.PLAIN, 40);
    private JFrame frame = new JFrame();
    private JButton placeButton = new JButton("Order operations");
    private JButton seeCButton = new JButton("Customer operations");
    private JButton seePButton = new JButton("Product operations");

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
        placeButton.setFont(TNR_PLAIN40);
        seeCButton.setFont(TNR_PLAIN40);
        seePButton.setFont(TNR_PLAIN40);
    }

    /**
     * Method that adds to the frame the necessary components
     */
    private void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel placeP = new JPanel();
        placeP.add(placeButton);

        JPanel seeCP = new JPanel();
        seeCP.add(seeCButton);

        JPanel seePP = new JPanel();
        seePP.add(seePButton);

        contentPanel.add(Box.createRigidArea(new Dimension(20,50)));
        contentPanel.add(placeP);
        contentPanel.add(Box.createRigidArea(new Dimension(20,20)));
        contentPanel.add(seeCP);
        contentPanel.add(Box.createRigidArea(new Dimension(20,20)));
        contentPanel.add(seePP);
        contentPanel.add(Box.createRigidArea(new Dimension(20,50)));

        frame.add(contentPanel);
    }

    public JButton getPlaceButton() {
        return placeButton;
    }

    public JButton getSeeCButton() {
        return seeCButton;
    }

    public JButton getSeePButton() {
        return seePButton;
    }
}
