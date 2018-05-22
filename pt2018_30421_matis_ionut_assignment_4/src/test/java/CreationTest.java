import Model.Account;
import Model.Bank;
import Model.Person;
import Model.SpendingAccount;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for performing various action on the bank (insert persons, accounts, delete persons)
 *
 * @author Ionut Matis, 30421
 */
public class CreationTest {
    private Bank bank = new Bank();
    private Person p1 = new Person("Person 1", "p1@email.com", "22/10/1990");
    private Person p2 = new Person("Person 2", "p2@email.com", "22/10/1990");

    private Account a1 = new SpendingAccount(1);
    private Account a2 = new SpendingAccount(2);
    private Account a3 = new SpendingAccount(3);

    /**
     * Set values up before testing the methods
     *
     * @throws Exception exception if we try to insert duplicate values
     */
    @Before
    public void setUp() throws Exception {
        bank.addPerson(p1);
        bank.addPerson(p2);

        bank.addAccount(p1, a1);
        bank.addAccount(p2, a2);
        bank.addAccount(p2, a3);
    }

    /**
     * Compares the person p1 with the one inserted into the bank
     */
    @Test
    public void testAddPers1() {
        assertEquals(p1.toString(), bank.searchPerson(p1.getFullName()).toString());
    }

    /**
     * Compares the person p2 with the one inserted into the bank
     */
    @Test
    public void testAddPers2() {
        assertEquals(p2.toString(), bank.searchPerson(p2.getFullName()).toString());
    }

    /**
     * Searches for an account holder and compares it with the one inserted in setUp method
     */
    @Test
    public void testAddAcc1() {
        assertEquals(bank.searchAccountOwner(a1), p1.getFullName());
    }

    /**
     * Searches for an account holder and compares it with the one inserted in setUp method
     */
    @Test
    public void testAddAcc2() {
        assertEquals(bank.searchAccountOwner(a2), p2.getFullName());
    }

    /**
     * Searches for an account holder and compares it with the one inserted in setUp method
     */
    @Test
    public void testAddAcc3() {
        assertEquals(bank.searchAccountOwner(a3), p2.getFullName());
    }

    /**
     * Deletes a person from the bank and verifies if the total number of persons and accounts have
     * changed
     */
    @Test
    public void testDelete() throws Exception {
        bank.removePerson(p2);
        assertEquals(bank.viewAllPersons().size(), 1);
        assertEquals(bank.viewAllAccounts().size(), 1);
    }
}
