package Presentation;

import BusinessLogicLayer.Reflection;
import DataAccessLayer.ProductDAO;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that models the product panel the user sees. It has a JTable and 4 buttons from which the user
 * can perform various actions
 *
 * @author Ionut Matis, 30421
 */
public class ProductView extends JFrame {
    private final static String TITLE = "Product Panel";
    private final static int WIDTH = 973;
    private final static int HEIGHT = 505;
    private final static int locX = 950;
    private final static int locY = 0;
    private final static Font TNR_PLAIN30 = new Font("Times New Roman", Font.PLAIN, 30);
    private final static Font TNR_BOLD40 = new Font("Times New Roman", Font.BOLD, 40);
    private final static Font TNR_PLAIN40 = new Font("Times New Roman", Font.PLAIN, 40);
    private JPanel contentPanel = new JPanel();     //the first panel of the tabbed panel
    private JPanel filterPanel = new JPanel();      //the second panel of the tabbed panel
    private JTable jTable;
    private JTextField prIDTF = new JTextField();
    private JTextField prNameTF = new JTextField();
    private JTextField prAmountTF = new JTextField();
    private JTextField prPriceTF = new JTextField();

    private JButton insertB = new JButton("Insert");
    private JButton updateB = new JButton("Update");
    private JButton deleteB = new JButton("Delete");

    private JComboBox<String> colName = new JComboBox<>();
    private JComboBox<Character> sign = new JComboBox<>();
    private JTextField valTF = new JTextField();
    private JButton filterB = new JButton("Filter");
    private JTextArea textArea = new JTextArea();

    private JTabbedPane jTabbedPane = new JTabbedPane();

    private List<Product> products;
    //private ProductDAO productDAO;

    /**
     * Constructor that sets the values of variables and calls the initialization methods
     * @param products list of objects to extract data from
     * @param productDAO DAO class to have access to the table
     */
    public ProductView(List<Object> products, ProductDAO productDAO) {
        jTable = Reflection.createTable(products);
        //this.productDAO = productDAO;
        this.products = new ArrayList<>(productDAO.findAll());
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
        this.setResizable(false);
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

        prIDTF.setFont(TNR_PLAIN30);
        prIDTF.setPreferredSize(new Dimension(150,50));
        prNameTF.setFont(TNR_PLAIN30);
        prNameTF.setPreferredSize(new Dimension(150,50));
        prAmountTF.setFont(TNR_PLAIN30);
        prAmountTF.setPreferredSize(new Dimension(150,50));
        prPriceTF.setFont(TNR_PLAIN30);
        prPriceTF.setPreferredSize(new Dimension(150,50));

        insertB.setFont(TNR_PLAIN30);
        insertB.setBackground(Color.GREEN);
        updateB.setFont(TNR_PLAIN30);
        updateB.setBackground(Color.yellow);
        deleteB.setFont(TNR_PLAIN30);
        deleteB.setBackground(Color.red);

        sign.setFont(TNR_PLAIN40);
        sign.addItem('>');
        sign.addItem('<');
        sign.addItem('=');

        colName.setFont(TNR_PLAIN40);
        colName.addItem("prID");
        colName.addItem("prName");
        colName.addItem("prAmount");
        colName.addItem("prPrice");

        valTF.setFont(TNR_PLAIN40);
        valTF.setPreferredSize(new Dimension(200, 60));

        filterB.setFont(TNR_PLAIN40);
        textArea.setFont(TNR_PLAIN30);
    }

    /**
     * Method that adds to the frame the necessary components
     */
    private void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel tableP = new JPanel();               //panel of the JTable
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(875, 170));
        tableP.add(jScrollPane);

        JPanel tHeadersP = new JPanel();            //Panel of the labels corresponding to the fields of the db table
        JLabel prIDL = new JLabel("prID");
        prIDL.setFont(TNR_BOLD40);
        JLabel prNameL = new JLabel("prName");
        prNameL.setFont(TNR_BOLD40);
        JLabel prAmountL = new JLabel("prAmount");
        prAmountL.setFont(TNR_BOLD40);
        JLabel prPriceL = new JLabel("prPrice");
        prPriceL.setFont(TNR_BOLD40);

        tHeadersP.add(Box.createRigidArea(new Dimension(50,20)));
        tHeadersP.add(prIDL);
        tHeadersP.add(Box.createRigidArea(new Dimension(80,20)));
        tHeadersP.add(prNameL);
        tHeadersP.add(Box.createRigidArea(new Dimension(100,20)));
        tHeadersP.add(prAmountL);
        tHeadersP.add(Box.createRigidArea(new Dimension(80,20)));
        tHeadersP.add(prPriceL);
        tHeadersP.add(Box.createRigidArea(new Dimension(20,20)));

        JPanel tValuesP = new JPanel();         //Panel with text fields in which the user will insert values
        tValuesP.add(prIDTF);
        tValuesP.add(Box.createRigidArea(new Dimension(70,20)));
        tValuesP.add(prNameTF);
        tValuesP.add(Box.createRigidArea(new Dimension(70,20)));
        tValuesP.add(prAmountTF);
        tValuesP.add(Box.createRigidArea(new Dimension(70,20)));
        tValuesP.add(prPriceTF);

        JPanel buttonP = new JPanel();
        buttonP.add(insertB);
        buttonP.add(updateB);
        buttonP.add(deleteB);

        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(tableP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(tHeadersP);
        contentPanel.add(tValuesP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        contentPanel.add(buttonP);

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
     * @param products list of objects to build the JTable
     */
    @SuppressWarnings("Duplicates")
    public void updateFrame(List<Object> products) {
        jTable = Reflection.createTable(products);

        contentPanel.removeAll();
        jTabbedPane.removeAll();
        filterPanel.removeAll();
        colName.removeAllItems();
        sign.removeAllItems();
        initContentPanel();
        initStyles();
    }

    public JTextField getPrIDTF() {
        return prIDTF;
    }

    public JTextField getPrNameTF() {
        return prNameTF;
    }

    public JTextField getPrAmountTF() {
        return prAmountTF;
    }

    public JTextField getPrPriceTF() {
        return prPriceTF;
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

    public JTextField getValTF() {
        return valTF;
    }

    public JButton getFilterB() {
        return filterB;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public JTable getjTable() {
        return jTable;
    }
}
