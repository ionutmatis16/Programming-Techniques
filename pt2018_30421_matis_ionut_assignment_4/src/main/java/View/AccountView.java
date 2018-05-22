package View;

import Model.Account;
import Model.Bank;
import Model.SavingsAccount;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class that models the account panel the user sees. It has a JTable, a radio button from which the user can select
 * the type of account they want to add and a field for inserting a sum and 4 buttons (insert, deposit, withdraw,
 * delete)
 *
 * @author Ionut Matis, 30421
 */
public class AccountView extends JFrame {
    private final static String TITLE = "Account Panel";
    private final static int WIDTH = 973;
    private final static int HEIGHT = 450;
    private final static int locX = 950;
    private final static int locY = 500;
    private final static Font TNR_PLAIN30 = new Font("Times New Roman", Font.PLAIN, 30);
    private final static Font TNR_BOLD40 = new Font("Times New Roman", Font.BOLD, 40);
    private final static Font TNR_BOLD35 = new Font("Times New Roman", Font.BOLD, 35);
    private final static Font TNR_PLAIN40 = new Font("Times New Roman", Font.PLAIN, 40);
    private JPanel contentPanel = new JPanel();     //the first panel of the tabbed panel
    private JTable jTable;

    private JRadioButton spendingRB = new JRadioButton("Spending", true);
    private JRadioButton savingsRB = new JRadioButton("Savings");
    private JTextField sum = new JTextField();


    private JButton insertB = new JButton("Insert");
    private JButton depositB = new JButton("Deposit");
    private JButton withdrawB = new JButton("Withdraw");
    private JButton deleteB = new JButton("Delete");


    /**
     * Constructor that creates the JTable from the bank and calls the initialization methods
     *
     * @param b bank from which to extract the data
     */
    public AccountView(Bank b) {
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
        String[] colName = {"Account ID", "Sum", "Interest", "Type", "Owner"};
        try {
            ArrayList<Account> accounts = new ArrayList<>(b.viewAllAccounts());
            Object[][] data = new Object[accounts.size()][5];
            for (int i = 0; i < accounts.size(); i++) {
                data[i][0] = accounts.get(i).getAccountName();
                data[i][1] = accounts.get(i).getAvailableSum();
                data[i][3] = accounts.get(i).getClass().getName().equals("Model.SavingsAccount") ?
                        "Savings" : "Spending";                     //get the account type
                data[i][2] = data[i][3].toString().equals("Savings") ?
                        ((SavingsAccount) accounts.get(i)).getInterest() : 0;

                data[i][4] = b.searchAccountOwner(accounts.get(i)); //use function from bank
            }

            return new JTable(data, colName);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return new JTable(new Object[0][4], colName);
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

        sum.setFont(TNR_PLAIN30);
        sum.setPreferredSize(new Dimension(200, 50));

        spendingRB.setFont(TNR_PLAIN30);
        savingsRB.setFont(TNR_PLAIN30);

        insertB.setFont(TNR_PLAIN30);
        insertB.setBackground(Color.GREEN);
        depositB.setFont(TNR_PLAIN30);
        depositB.setBackground(Color.yellow);
        withdrawB.setFont(TNR_PLAIN30);
        withdrawB.setBackground(Color.yellow);
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
        JLabel typeL = new JLabel("Type");
        typeL.setFont(TNR_BOLD35);
        typeL.setPreferredSize(new Dimension(200, 50));
        JLabel sumL = new JLabel("Sum");
        sumL.setFont(TNR_BOLD35);
        sumL.setPreferredSize(new Dimension(200, 50));

        tHeadersP.add(Box.createRigidArea(new Dimension(100, 20)));
        tHeadersP.add(typeL);
        tHeadersP.add(Box.createRigidArea(new Dimension(100, 20)));
        tHeadersP.add(sumL);
        //tHeadersP.add(Box.createRigidArea(new Dimension(80,20)));

        JPanel tValuesP = new JPanel();         //Panel with text fields in which the user will insert values
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(spendingRB);
        buttonGroup.add(savingsRB);

        tValuesP.add(spendingRB);
        tValuesP.add(savingsRB);
        tValuesP.add(Box.createRigidArea(new Dimension(70, 20)));
        tValuesP.add(Box.createRigidArea(new Dimension(70, 20)));
        tValuesP.add(sum);

        JPanel buttonP = new JPanel();
        buttonP.add(insertB);
        buttonP.add(depositB);
        buttonP.add(withdrawB);
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


    public JTextField getPrAmountTF() {
        return sum;
    }

    public JButton getInsertB() {
        return insertB;
    }


    public JButton getDeleteB() {
        return deleteB;
    }

    public JTable getjTable() {
        return jTable;
    }

    public JRadioButton getSpendingRB() {
        return spendingRB;
    }

    public JRadioButton getSavingsRB() {
        return savingsRB;
    }

    public JTextField getSum() {
        return sum;
    }

    public JButton getDepositB() {
        return depositB;
    }

    public JButton getWithdrawB() {
        return withdrawB;
    }
}

