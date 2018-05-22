package Controller;

import Model.*;
import View.AccountView;
import View.PersonView;
import View.StartView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * This class represents the controller of the project. The serialization and deserialization processes
 * are done in this class.
 *
 * @author Ionut Matis, 30421
 */
public class BController {
    private Bank bank;
    private StartView startView;
    private PersonView personView;
    private AccountView accountView;

    public BController(Bank bank, StartView startView,
                       PersonView personView, AccountView accountView) {
        this.bank = bank;
        this.startView = startView;
        this.personView = personView;
        this.accountView = accountView;
        addActPerson();
        addActAccount();
        addActGen();
    }

    public static void main(String[] args) throws Exception {
        /*Bank bank = new Bank();
        try {
            FileOutputStream fileOut = new FileOutputStream("bank1.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(bank);
            out.close();
            fileOut.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }*/

        Bank bank2 = null;

        /*
         * Read the object from the .ser file
         */
        try {
            FileInputStream fileIn = new FileInputStream("bank1.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            bank2 = (Bank) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Bank class not found");
            e.printStackTrace();
        }

        new BController(bank2, new StartView(), new PersonView(bank2), new AccountView(bank2));
    }

    /**
     * Add action listeners to the person panel.
     */
    private void addActPerson() {
        startView.getpOPButton().addActionListener((ActionEvent e) -> {
            personView.setVisible(true);
        });

        /*
         * After the client closes the application (presses x button), the changes made in the bank
         * are stored in a file, so the user does not lose any data
         */
        startView.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileOutputStream fileOut = new FileOutputStream("bank1.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(bank);
                    out.close();
                    fileOut.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });

        personView.getInsertB().addActionListener((ActionEvent e) -> {
            try {
                String fullName = personView.getFullNameTF().getText();
                String email = personView.getEmailTF().getText();
                String dateOfBirth = personView.getDateOfBirthTF().getText();

                if (email.equals(""))
                    throw new Exception("Empty field");

                bank.addPerson(new Person(fullName, email, dateOfBirth));

                personView.getContentPane().removeAll();
                personView.updateFrame(bank);
                personView.getContentPane().repaint();
                personView.getContentPane().revalidate();
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid input for insertion. \n" +
                                e2.getMessage() + ".", "Insertion error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        personView.getUpdateB().addActionListener((ActionEvent e) -> {
            try {
                String emailToBeUpdated = personView.getEmailTF().getText();    //get the new email
                if (emailToBeUpdated.equals(""))                                //check for empty field
                    throw new Exception("Empty field");

                String nameToBeUpdated = personView.getjTable().getValueAt(personView.getjTable().getSelectedRow(),
                        0).toString();                                    //try to get the name from the table
                Person toBeUpdated = bank.searchPerson(nameToBeUpdated);        //get the person by its name
                toBeUpdated.setEmail(emailToBeUpdated);                         //set the new value

                personView.getContentPane().removeAll();
                personView.updateFrame(bank);                                   //update gui
                personView.getContentPane().repaint();
                personView.getContentPane().revalidate();
            } catch (IndexOutOfBoundsException e3) {                            //there was no selection on the table
                try {
                    String emailToBeUpdated = personView.getEmailTF().getText();    //get the new email
                    String fullName = personView.getFullNameTF().getText(); //get the name of the person
                    Person toBeUpdated = bank.searchPerson(fullName);       //find the actual person
                    toBeUpdated.setEmail(emailToBeUpdated);

                    personView.getContentPane().removeAll();
                    personView.updateFrame(bank);
                    personView.getContentPane().repaint();
                    personView.getContentPane().revalidate();

                } catch (NullPointerException e4) {                         //invalid name
                    JOptionPane.showMessageDialog(new Panel(), "Invalid name to update", "Update error",
                            JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e3) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid input for update. \n" +
                                e3.getMessage() + ".", "Update error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        personView.getDeleteB().addActionListener((ActionEvent e) -> {
            try {
                String nameToBeDeleted = personView.getjTable().getValueAt(personView.getjTable().getSelectedRow(),
                        0).toString();                                    //try to get the name from the table
                Person toBeDeleted = bank.searchPerson(nameToBeDeleted);        //get the person by its name

                bank.removePerson(toBeDeleted);

                personView.getContentPane().removeAll();
                personView.updateFrame(bank);                                   //update gui
                personView.getContentPane().repaint();
                personView.getContentPane().revalidate();

                accountView.getContentPane().removeAll();
                accountView.updateFrame(bank);                                   //update gui
                accountView.getContentPane().repaint();
                accountView.getContentPane().revalidate();
            } catch (IndexOutOfBoundsException e2) {
                try {
                    String fullName = personView.getFullNameTF().getText(); //get the name of the person
                    Person toBeDeleted = bank.searchPerson(fullName);        //get the person by its name
                    bank.removePerson(toBeDeleted);

                    personView.getContentPane().removeAll();
                    personView.updateFrame(bank);                                   //update gui
                    personView.getContentPane().repaint();
                    personView.getContentPane().revalidate();
                } catch (Exception e3) {
                    JOptionPane.showMessageDialog(new Panel(), "Invalid input for deletion. \n" +
                                    e3.getMessage() + ".", "Delete error",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e3) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid input for deletion. \n" +
                                e3.getMessage(), "Delete error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Add action listeners to the account panel.
     */
    private void addActAccount() {
        startView.getaOPButton().addActionListener((ActionEvent e) -> {
            accountView.setVisible(true);
        });

        accountView.getInsertB().addActionListener((ActionEvent e) -> {
            try {
                String nameOfHolder = personView.getjTable().getValueAt(personView.getjTable().getSelectedRow(),
                        0).toString();                                    //try to get the name from the table
                Person holder = bank.searchPerson(nameOfHolder);
                if (accountView.getSum().getText().equals("")) {
                    if (accountView.getSavingsRB().isSelected()) {
                        bank.addAccount(holder, new SavingsAccount(bank.getIdOfAcc()));
                    } else {
                        bank.addAccount(holder, new SpendingAccount(bank.getIdOfAcc()));
                    }
                } else {
                    int sum = Integer.parseInt(accountView.getSum().getText());
                    if (accountView.getSavingsRB().isSelected()) {
                        bank.addAccount(holder, new SavingsAccount(bank.getIdOfAcc(),sum));
                    } else {
                        bank.addAccount(holder, new SpendingAccount(bank.getIdOfAcc(),sum));
                    }
                }

                personView.getContentPane().removeAll();
                personView.updateFrame(bank);                                   //update gui
                personView.getContentPane().repaint();
                personView.getContentPane().revalidate();

                accountView.getContentPane().removeAll();
                accountView.updateFrame(bank);                                  //update gui
                accountView.getContentPane().repaint();
                accountView.getContentPane().revalidate();

            } catch (IndexOutOfBoundsException e2) {
                JOptionPane.showMessageDialog(new Panel(), "Please select a person from the persons table. \n",
                        "Insertion error",
                        JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException e3) {
                JOptionPane.showMessageDialog(new Panel(), "Please insert a valid sum. \n",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        accountView.getDepositB().addActionListener((ActionEvent e) -> {
            try {
                String nameOfAccount = accountView.getjTable().getValueAt(accountView.getjTable().getSelectedRow(),
                        0).toString();                                    //try to get the name from the table
                Account toDeposit = bank.searchAccount(nameOfAccount);
                int sum = Integer.parseInt(accountView.getSum().getText());
                if (sum < 0)
                    throw new NumberFormatException("Invalid sum.");

                bank.deposit(toDeposit, sum);

                accountView.getContentPane().removeAll();
                accountView.updateFrame(bank);                                  //update gui
                accountView.getContentPane().repaint();
                accountView.getContentPane().revalidate();

            } catch (NumberFormatException e3) {
                JOptionPane.showMessageDialog(new Panel(), "Please insert a valid sum. \n",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            } catch (ArrayIndexOutOfBoundsException e3) {
                JOptionPane.showMessageDialog(new Panel(), "Please select a row from the account table. \n",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            } catch (Exception e3) {
                JOptionPane.showMessageDialog(new Panel(), "Savings accounts cannot " +
                                "perform multiple deposits",
                        "Deposit error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        accountView.getWithdrawB().addActionListener((ActionEvent e) -> {
            try {
                String nameOfAccount = accountView.getjTable().getValueAt(accountView.getjTable().getSelectedRow(),
                        0).toString();                                    //try to get the name from the table
                Account toWithdraw = bank.searchAccount(nameOfAccount);
                int sum = Integer.parseInt(accountView.getSum().getText());
                if (sum < 0)
                    throw new NumberFormatException("Invalid sum.");

                bank.withdraw(toWithdraw, sum);

                accountView.getContentPane().removeAll();
                accountView.updateFrame(bank);                                  //update gui
                accountView.getContentPane().repaint();
                accountView.getContentPane().revalidate();

            } catch (NumberFormatException e3) {
                JOptionPane.showMessageDialog(new Panel(), "Please insert a valid sum.",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            } catch (ArrayIndexOutOfBoundsException e3) {
                JOptionPane.showMessageDialog(new Panel(), "Please select a row from the account table.",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            } catch (AssertionError | Exception e3) {
                JOptionPane.showMessageDialog(new Panel(), e3.getMessage(),
                        "Withdrawal error",
                        JOptionPane.WARNING_MESSAGE);
            } /*catch (Exception e3) {
                JOptionPane.showMessageDialog(new Panel(), "Cannot withdraw.",
                        "Withdrawal error",
                        JOptionPane.WARNING_MESSAGE);
            }*/
        });

        accountView.getDeleteB().addActionListener((ActionEvent e) -> {
            try {
                String nameOfAccount = accountView.getjTable().getValueAt(accountView.getjTable().getSelectedRow(),
                        0).toString();                                    //try to get the name from the table
                Account toDelete = bank.searchAccount(nameOfAccount);
                Person delFrom = bank.searchPerson(bank.searchAccountOwner(toDelete));
                bank.removeAccount(delFrom, toDelete);

                personView.getContentPane().removeAll();
                personView.updateFrame(bank);                                   //update gui
                personView.getContentPane().repaint();
                personView.getContentPane().revalidate();

                accountView.getContentPane().removeAll();
                accountView.updateFrame(bank);                                  //update gui
                accountView.getContentPane().repaint();
                accountView.getContentPane().revalidate();

            } catch (ArrayIndexOutOfBoundsException e2) {
                JOptionPane.showMessageDialog(new Panel(), "Please select a row from the account table.",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Add action listeners to the button which generates the reports
     */
    private void addActGen() {
        startView.getGenRepButton().addActionListener((ActionEvent e) -> {
            try {
                if (bank.getBankHashMap().size() == 0)
                    throw new NullPointerException("No data");
                for (Person p : bank.viewAllPersons()) {
                    System.out.println(p.toString());
                    for (Account a : bank.getBankHashMap().get(p)) {
                        System.out.println("\t" + a.toString());
                    }
                }
                System.out.println();
            } catch (NullPointerException e2) {
                System.out.println("There is no data to report.\n");
            }

        });
    }

}















