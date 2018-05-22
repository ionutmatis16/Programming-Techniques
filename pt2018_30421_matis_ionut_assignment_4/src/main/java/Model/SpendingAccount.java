package Model;

/**
 * Class that extends the abstract class Account. It provides methods for deposit and withdraw. It
 * allows the user to perform multiple deposits and withdrawals.
 *
 * @author Ionut Matis, 30421
 */
public class SpendingAccount extends Account {
    public SpendingAccount(int accNr) {
        super(accNr);
    }

    public SpendingAccount(int accNr, int availableSum) {
        super(accNr, availableSum);
    }

    /**
     * Implements the deposit method from the superclass. It also notifies the observers
     *
     * @param sumToDeposit sum to be deposited into the account
     */
    @Override
    public void deposit(int sumToDeposit) {
        if (sumToDeposit > 0) {
            availableSum += sumToDeposit;
            notifyObservers();
        }
    }

    /**
     * Implements the withdraw method from the superclass. It also notifies the observers
     *
     * @param sumToWithdraw sum to be withdrawn from the account
     * @throws Exception exception if the user tries to withdraw a larger sum than the available one
     */
    @Override
    public void withdraw(int sumToWithdraw) throws Exception {
        if (sumToWithdraw > 0 && (availableSum - sumToWithdraw) >= 0) {
            availableSum -= sumToWithdraw;
            notifyObservers();
        } else
            throw new Exception("Invalid sum to withdraw.");
    }

    public int getAvailableSum() {
        return availableSum;
    }
}
