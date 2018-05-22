package Presentation;

import BusinessLogicLayer.Reflection;
import Model.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Class that models the order panel the user sees. It has a JTable and 2 buttons from which the user
 * can perform various actions
 *
 * @author Ionut Matis, 30421
 */
public class OrderView extends JFrame{
    private final static String TITLE = "Order Panel";
    private final static int WIDTH = 1200;
    private final static int HEIGHT = 490;
    private final static int locX = 628;
    private final static int locY = 503;
    private final static Font TNR_PLAIN30 = new Font("Times New Roman", Font.PLAIN, 30);
    private final static Font TNR_BOLD40 = new Font("Times New Roman", Font.BOLD, 40);
    private JPanel contentPanel = new JPanel();
    private JTable jTable;

    private JTextField amountTF = new JTextField();
    private JTextField idTF = new JTextField();

    private JButton placeB = new JButton("Place order");
    private JButton createB = new JButton("Create bill");

    private List<Object> orders;

    /**
     * Constructor that sets the values of variables and calls the initialization methods
     * @param orders list of object to extract data from
     */
    public OrderView(List<Object> orders) {
        jTable = Reflection.createTable(orders);
        this.orders = orders;
        initContentPanel();
        initStyles();
        initComp();
    }

    /**
     * Method that prepares the frame by setting the title, location, width, height etc
     */
    @SuppressWarnings("Duplicates")
    private void initComp() {
        this.setTitle(TITLE);
        this.setLocation(locX, locY);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        //this.setResizable(false);
        this.setVisible(false);
    }

    /**
     * Method that initializes the style of the components (fonts, sizes, colors)
     */
    @SuppressWarnings("Duplicates")
    private void initStyles() {
        jTable.setRowHeight(30);
        jTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 30));
        jTable.setFont(TNR_PLAIN30);

        amountTF.setPreferredSize(new Dimension(150, 50));
        amountTF.setFont(TNR_PLAIN30);
        idTF.setPreferredSize(new Dimension(150, 50));
        idTF.setFont(TNR_PLAIN30);

        placeB.setFont(TNR_PLAIN30);
        placeB.setBackground(Color.GREEN);
        createB.setFont(TNR_PLAIN30);
    }

    /**
     * Method that adds to the frame the necessary components
     */
    @SuppressWarnings("Duplicates")
    private void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel tableP = new JPanel();
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(WIDTH - 75, 190));
        tableP.add(jScrollPane);

        JPanel placeP = new JPanel();               //panel which has 2 buttons and 2 textfields
        JLabel idL = new JLabel("ordID: ");
        idL.setFont(TNR_BOLD40);
        JLabel ordAmountL = new JLabel("Amount: ");
        ordAmountL.setFont(TNR_BOLD40);
        placeP.add(idL);
        placeP.add(idTF);
        placeP.add(Box.createRigidArea(new Dimension(20, 20)));
        placeP.add(ordAmountL);
        placeP.add(amountTF);
        placeP.add(Box.createRigidArea(new Dimension(20, 20)));
        placeP.add(placeB);

        JPanel billP = new JPanel();                //panel for button which creates the bill
        billP.add(createB);

        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(tableP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(placeP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(billP);


        this.add(contentPanel);
    }

    /**
     * Method that destroys the components so that they can be repainted
     * @param orders list of objects to build the JTable
     */
    public void updateFrame(List<Object> orders) {
        jTable = Reflection.createTable(orders);
        this.orders = orders;

        contentPanel.removeAll();
        initContentPanel();
        initStyles();
    }

    public JButton getPlaceB() {
        return placeB;
    }

    public JButton getCreateB() {
        return createB;
    }

    public JTextField getIdTF() {
        return idTF;
    }

    public JTextField getAmountTF() {
        return amountTF;
    }

    public JTable getjTable() {
        return jTable;
    }
}
