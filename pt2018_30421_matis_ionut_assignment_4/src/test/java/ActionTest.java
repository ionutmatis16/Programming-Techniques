import Model.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for performing various action on accounts (deposit, withdraw)
 *
 * @author Ionut Matis, 30421
 */
public class ActionTest {
    private Bank bank = new Bank();
    private Person p1 = new Person("Person 1", "p1@email.com", "22/10/1990");
    private Person p2 = new Person("Person 2", "p2@email.com", "22/10/1990");

    private Account a1 = new SpendingAccount(1, 200);
    private Account a2 = new SavingsAccount(2, 100);

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

    }

    /**
     * Method that performs a deposit
     *
     * @throws Exception if the sum is less than 0
     */
    @Test
    public void depositTest1() throws Exception {
        a1.deposit(200);
        assertEquals(400, a1.getAvailableSum());
    }

    /**
     * Method that performs a deposit
     *
     * @throws Exception if the sum is less than 0
     */
    @Test
    public void depositTest2() throws Exception {
        a2.deposit(500);
        assertEquals(600, a2.getAvailableSum());
    }

    /**
     * Method that performs a withdraw
     *
     * @throws Exception if the sum is less than 0
     */
    @Test
    public void withdrawTest1() throws Exception {
        a1.withdraw(100);
        assertEquals(100, a1.getAvailableSum());
    }

    /**
     * Method that performs a withdraw
     *
     * @throws Exception if the sum is less than 0
     */
    @Test
    public void withdrawTest2() throws Exception {
        a2.withdraw(50);
        assertEquals(50, a2.getAvailableSum());
    }
}
