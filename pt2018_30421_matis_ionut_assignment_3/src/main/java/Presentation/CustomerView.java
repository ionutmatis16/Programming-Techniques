package Presentation;

import BusinessLogicLayer.Reflection;
import DataAccessLayer.CustomerDAO;
import Model.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that models the customer panel the user sees. It has a JTable and 4 buttons from which the user
 * can perform various actions
 *
 * @author Ionut Matis, 30421
 */
public class CustomerView extends JFrame{
    private final static String TITLE = "Customer Panel";
    private final static int WIDTH = 973;
    private final static int HEIGHT = 515;
    private final static int locX = -10;
    private final static int locY = 0;
    private final static Font TNR_PLAIN30 = new Font("Times New Roman", Font.PLAIN, 30);
    private final static Font TNR_BOLD40 = new Font("Times New Roman", Font.BOLD, 40);
    private final static Font TNR_PLAIN40 = new Font("Times New Roman", Font.PLAIN, 40);
    private JPanel contentPanel = new JPanel();
    private JPanel filterPanel = new JPanel();
    private JTable jTable;
    private JTextField cuIDTF = new JTextField();
    private JTextField cuNameTF = new JTextField();
    private JTextField cuEmailTF = new JTextField();
    private JTextField cuAgeTF = new JTextField();

    private JButton insertB = new JButton("Insert");
    private JButton updateB = new JButton("Update");
    private JButton deleteB = new JButton("Delete");
    private JButton viewAllB = new JButton("View all");

    private JComboBox<String> colName = new JComboBox<>();
    private JComboBox<Character> sign = new JComboBox<>();
    private JTextField valTF = new JTextField();
    private JButton filterB = new JButton("Filter");
    private JTextArea textArea = new JTextArea();

    private JTabbedPane jTabbedPane = new JTabbedPane();

    private List<Customer> customers;
    //private CustomerDAO customerDAO;

    /**
     * Constructor that sets the values of variables and calls the initialization methods
     * @param customers list of objects to extract data from
     * @param customerDAO DAO class to have access to the table
     */
    public CustomerView(List<Object> customers, CustomerDAO customerDAO) {
        jTable = Reflection.createTable(customers);
        //this.customerDAO = customerDAO;
        this.customers = new ArrayList<>(customerDAO.findAll());
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

        cuIDTF.setFont(TNR_PLAIN30);
        cuIDTF.setPreferredSize(new Dimension(150,50));
        cuNameTF.setFont(TNR_PLAIN30);
        cuNameTF.setPreferredSize(new Dimension(150,50));
        cuEmailTF.setFont(TNR_PLAIN30);
        cuEmailTF.setPreferredSize(new Dimension(150,50));
        cuAgeTF.setFont(TNR_PLAIN30);
        cuAgeTF.setPreferredSize(new Dimension(150,50));

        insertB.setFont(TNR_PLAIN30);
        insertB.setBackground(Color.GREEN);
        updateB.setFont(TNR_PLAIN30);
        updateB.setBackground(Color.yellow);
        deleteB.setFont(TNR_PLAIN30);
        deleteB.setBackground(Color.red);
        viewAllB.setFont(TNR_PLAIN30);

        sign.setFont(TNR_PLAIN40);
        sign.addItem('>');
        sign.addItem('<');
        sign.addItem('=');

        colName.setFont(TNR_PLAIN40);
        colName.addItem("cuID");
        colName.addItem("cuName");
        colName.addItem("cuEmail");
        colName.addItem("cuAge");

        valTF.setFont(TNR_PLAIN40);
        valTF.setPreferredSize(new Dimension(200, 60));

        filterB.setFont(TNR_PLAIN40);
        textArea.setFont(TNR_PLAIN30);

    }

    /**
     * Method that adds to the frame the necessary components
     */
    @SuppressWarnings("Duplicates")
    private void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel tableP = new JPanel();
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(WIDTH - 75, 170));
        tableP.add(jScrollPane);

        JPanel tHeadersC = new JPanel();
        JLabel cuIDL = new JLabel("cuID");
        cuIDL.setFont(TNR_BOLD40);
        JLabel cuNameL = new JLabel("cuName");
        cuNameL.setFont(TNR_BOLD40);
        JLabel cuEmailL = new JLabel("cuEmail");
        cuEmailL.setFont(TNR_BOLD40);
        JLabel cuAgeL = new JLabel("cuAge");
        cuAgeL.setFont(TNR_BOLD40);

        tHeadersC.add(Box.createRigidArea(new Dimension(20,20)));
        tHeadersC.add(cuIDL);
        tHeadersC.add(Box.createRigidArea(new Dimension(80,20)));
        tHeadersC.add(cuNameL);
        tHeadersC.add(Box.createRigidArea(new Dimension(90,20)));
        tHeadersC.add(cuEmailL);
        tHeadersC.add(Box.createRigidArea(new Dimension(70,20)));
        tHeadersC.add(cuAgeL);
        tHeadersC.add(Box.createRigidArea(new Dimension(20,20)));


        JPanel tValuesC = new JPanel();
        tValuesC.add(cuIDTF);
        tValuesC.add(Box.createRigidArea(new Dimension(70,20)));
        tValuesC.add(cuNameTF);
        tValuesC.add(Box.createRigidArea(new Dimension(70,20)));
        tValuesC.add(cuEmailTF);
        tValuesC.add(Box.createRigidArea(new Dimension(70,20)));
        tValuesC.add(cuAgeTF);

        JPanel buttonP = new JPanel();
        buttonP.add(insertB);
        //buttonP.add(Box.createRigidArea(new Dimension(10, 20)));
        buttonP.add(updateB);
        //buttonP.add(Box.createRigidArea(new Dimension(10, 20)));
        buttonP.add(deleteB);
        //buttonP.add(Box.createRigidArea(new Dimension(10, 20)));
        //buttonP.add(viewAllB);


        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(tableP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(tHeadersC);
        contentPanel.add(tValuesC);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(buttonP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));

        ///////////////////////////////////// Second tab /////////////////////////////////////////////////

        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        JPanel doSomeF = new JPanel();
        JLabel doSomeL = new JLabel("Do some filtering");
        doSomeL.setFont(TNR_BOLD40);
        doSomeF.add(doSomeL);

        JPanel selPanel = new JPanel();

        selPanel.add(colName);
        selPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        selPanel.add(sign);
        selPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        selPanel.add(valTF);
        selPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        selPanel.add(filterB);

        JPanel textPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 100, 200));
        textPanel.add(scrollPane);

        filterPanel.add(doSomeF);
        filterPanel.add(selPanel);
        filterPanel.add(textPanel);

        jTabbedPane.addTab("Table and actions", contentPanel);
        jTabbedPane.addTab("Filters", filterPanel);

        this.add(jTabbedPane);
    }


    /**
     * Method that destroys the components so that they can be repainted
     * @param customers list of objects to build the JTable
     */
    @SuppressWarnings("Duplicates")
    public void updateFrame(List<Object> customers) {
        jTable = Reflection.createTable(customers);
        //this.customers = customers;

        contentPanel.removeAll();
        jTabbedPane.removeAll();
        filterPanel.removeAll();
        colName.removeAllItems();
        sign.removeAllItems();
        initContentPanel();
        initStyles();
    }

    public JTextField getCuIDTF() {
        return cuIDTF;
    }

    public JTextField getCuNameTF() {
        return cuNameTF;
    }

    public JTextField getCuEmailTF() {
        return cuEmailTF;
    }

    public JTextField getCuAgeTF() {
        return cuAgeTF;
    }

    public JButton getInsertB() {
        return insertB;
    }

    public JButton getUpdateB() {
        return updateB;
    }

    public JButton getDeleteB() {
        return deleteB;
    }

    public JComboBox<String> getColName() {
        return colName;
    }

    public JComboBox<Character> getSign() {
        return sign;
    }

    public JButton getFilterB() {
        return filterB;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public JTextField getValTF() {
        return valTF;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public JTable getjTable() {
        return jTable;
    }
}
