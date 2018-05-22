package View;

import Model.Bank;
import Model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that models the person panel the user sees. It has a JTable and 3 buttons (insert, update
 * and delete person)
 *
 * @author Ionut Matis, 30421
 */
public class PersonView extends JFrame {
    private final static String TITLE = "Person Panel";
    private final static int WIDTH = 973;
    private final static int HEIGHT = 450;
    private final static int locX = 950;
    private final static int locY = 0;
    private final static Font TNR_PLAIN30 = new Font("Times New Roman", Font.PLAIN, 30);
    private final static Font TNR_BOLD40 = new Font("Times New Roman", Font.BOLD, 40);
    private final static Font TNR_BOLD35 = new Font("Times New Roman", Font.BOLD, 35);
    private final static Font TNR_PLAIN40 = new Font("Times New Roman", Font.PLAIN, 40);
    private JPanel contentPanel = new JPanel();     //the first panel of the tabbed panel
    private JTable jTable;
    private JTextField fullNameTF = new JTextField();
    private JTextField emailTF = new JTextField();
    private JTextField dateOfBirthTF = new JTextField();


    private JButton insertB = new JButton("Insert");
    private JButton updateB = new JButton("Update(email only)");
    private JButton deleteB = new JButton("Delete");


    /**
     * Constructor that creates the JTable from the bank and calls the initialization methods
     *
     * @param b bank from which to extract the data
     */
    public PersonView(Bank b) {
        jTable = createTable(b);
        initContentPanel();
        initStyles();
        initComp();
    }

    /**
     * Method that creates a JTable for the accounts from the data extracted from the bank.
     *
     * @param b bank from which to extract the data
     * @return the corresponding JTable
     */
    private JTable createTable(Bank b) {
        String[] colName = {"Full Name", "Email", "Date of Birth", "Nr of accounts"};
        try {
            ArrayList<Person> persons = new ArrayList<>(b.viewAllPersons());
            Object[][] data = new Object[persons.size()][4];
            for (int i = 0; i < persons.size(); i++) {
                data[i][0] = persons.get(i).getFullName();
                data[i][1] = persons.get(i).getEmail();
                data[i][2] = persons.get(i).getDateOfBirth();
                data[i][3] = b.getBankHashMap().get(persons.get(i)).size(); //number of accounts
            }

            return new JTable(data, colName);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return new JTable(new Object[0][3], colName);
        }
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
        jTable.setDefaultEditor(Object.class, null);


        fullNameTF.setFont(TNR_PLAIN30);
        fullNameTF.setPreferredSize(new Dimension(200, 50));
        emailTF.setFont(TNR_PLAIN30);
        emailTF.setPreferredSize(new Dimension(200, 50));
        dateOfBirthTF.setFont(TNR_PLAIN30);
        dateOfBirthTF.setPreferredSize(new Dimension(200, 50));

        insertB.setFont(TNR_PLAIN30);
        insertB.setBackground(Color.GREEN);
        updateB.setFont(TNR_PLAIN30);
        updateB.setBackground(Color.yellow);
        deleteB.setFont(TNR_PLAIN30);
        deleteB.setBackground(Color.red);
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
        JLabel prIDL = new JLabel("Full Name");
        prIDL.setFont(TNR_BOLD35);
        prIDL.setPreferredSize(new Dimension(200, 50));
        JLabel prNameL = new JLabel("Email");
        prNameL.setFont(TNR_BOLD35);
        prNameL.setPreferredSize(new Dimension(200, 50));
        JLabel prAmountL = new JLabel("Date Of Birth");
        prAmountL.setFont(TNR_BOLD35);
        prAmountL.setPreferredSize(new Dimension(210, 50));

        tHeadersP.add(Box.createRigidArea(new Dimension(5, 20)));
        tHeadersP.add(prIDL);
        tHeadersP.add(Box.createRigidArea(new Dimension(70, 20)));
        tHeadersP.add(prNameL);
        tHeadersP.add(Box.createRigidArea(new Dimension(70, 20)));
        tHeadersP.add(prAmountL);
        //tHeadersP.add(Box.createRigidArea(new Dimension(80,20)));

        JPanel tValuesP = new JPanel();         //Panel with text fields in which the user will insert values
        tValuesP.add(fullNameTF);
        tValuesP.add(Box.createRigidArea(new Dimension(70, 20)));
        tValuesP.add(emailTF);
        tValuesP.add(Box.createRigidArea(new Dimension(70, 20)));
        tValuesP.add(dateOfBirthTF);

        JPanel buttonP = new JPanel();
        buttonP.add(insertB);
        buttonP.add(updateB);
        buttonP.add(deleteB);

        contentPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        contentPanel.add(tableP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        contentPanel.add(tHeadersP);
        contentPanel.add(tValuesP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        contentPanel.add(buttonP);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 20)));

        this.add(contentPanel);
    }

    /**
     * Method that destroys the components so that they can be repainted
     *
     * @param b bank to build the JTable
     */
    @SuppressWarnings("Duplicates")
    public void updateFrame(Bank b) {
        jTable = createTable(b);
        contentPanel.removeAll();
        initContentPanel();
        initStyles();
    }

    public JTextField getPrIDTF() {
        return fullNameTF;
    }

    public JTextField getEmailTF() {
        return emailTF;
    }

    public JTextField getPrAmountTF() {
        return dateOfBirthTF;
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

    public JTable getjTable() {
        return jTable;
    }

    public JTextField getFullNameTF() {
        return fullNameTF;
    }

    public JTextField getDateOfBirthTF() {
        return dateOfBirthTF;
    }
}

