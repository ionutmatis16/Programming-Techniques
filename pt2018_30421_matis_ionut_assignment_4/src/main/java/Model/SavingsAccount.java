package Model;

/**
 * Class that extends the abstract class Account. It provides methods for deposit and withdraw. It
 * has a unique feature - allows the user to perform only one deposit and only one withdrawal. This is
 * done through the boolean variables madeDeposit and madeWithdraw
 *
 * @author Ionut Matis, 30421
 */
public class SavingsAccount extends Account {
    private boolean madeDeposit;
    private boolean madeWithdraw;
    private int interest;

    /**
     * Constructor which sets the boolean values to false and interest to 0
     *
     * @param accNr the number of the account
     */
    public SavingsAccount(int accNr) {
        super(accNr);
        this.madeDeposit = false;
        this.madeWithdraw = false;
        interest = 0;
    }

    /**
     * Constructor which sets the boolean values to false and interest to 0. It also adds an initial
     * sum
     *
     * @param accNr the number of the account
     * @param availableSum sum to be deposited initially
     */
    public SavingsAccount(int accNr, int availableSum) {
        super(accNr, availableSum);
        this.madeDeposit = false;
        this.madeWithdraw = false;
        interest = (int) (2.0 / 100 * availableSum);
    }

    /**
     * Implemented method which adds a sum to the current one. It also notifies the observers
     *
     * @param sumToDeposit sum to be deposited into the account
     * @throws Exception exception if the user tried to make multiple deposits
     */
    @Override
    public void deposit(int sumToDeposit) throws Exception {
        if (!madeDeposit) {
            if (sumToDeposit > 0) {
                availableSum += sumToDeposit;
                madeDeposit = true;
                interest = (int) (2.0 / 100 * availableSum);
                notifyObservers();
            }
        } else {
            throw new Exception("Spending accounts cannot do multiple deposits");
        }
    }

    /**
     * Implemented method which subtracts a sum to the current one. It also notifies the observers
     *
     * @param sumToWithdraw sum to be withdrawn from the account
     * @throws Exception exception if the user tried to make multiple withdrawals or tries to
     *                   withdraw a larger sum than the available one
     */
    @Override
    public void withdraw(int sumToWithdraw) throws Exception {
        if (!madeWithdraw) {
            if (sumToWithdraw > 0 && (availableSum - sumToWithdraw) >= 0) {
                availableSum -= sumToWithdraw;
                madeWithdraw = true;
                interest = (int) (2.0 / 100 * availableSum);
                notifyObservers();
            } else {
                throw new Exception("Invalid sum to withdraw.");
            }
        } else {
            throw new Exception("Spending accounts cannot do multiple withdrawals.");
        }
    }

    public int getAvailableSum() {
        return availableSum;
    }

    public int getInterest() {
        return interest;
    }
}
