package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Class that models the concept of an account. It has a name, a sum associated with it and a list
 * of observers.
 *
 * @author Ionut Matis, 30421
 */
public abstract class Account extends Observable implements Serializable {
    protected String accountName;
    protected int availableSum;
    protected ArrayList<Observer> observers;

    /**
     * Constructor which creates the object with a number (sort of id) and the sum equal to 0.
     */
    public Account(int accNr) {
        this.accountName = "Account " + accNr;
        this.availableSum = 0;
        observers = new ArrayList<>();
    }

    /**
     * Constructor which creates the object with a number and a sum equal to a specific parameter.
     *
     * @param availableSum sum of the account
     */
    public Account(int accNr, int availableSum) {
        this.accountName = "Account " + accNr;
        this.availableSum = availableSum;
        observers = new ArrayList<>();
    }

    /**
     * Method to be implemented by the classes that extend this class
     *
     * @param sumToDeposit sum to be deposited into the account
     * @throws Exception exception is thrown if the transaction cannot be done
     */
    public abstract void deposit(int sumToDeposit) throws Exception;

    /**
     * Method to be implemented by the classes that extend this class
     *
     * @param sumToWithdraw sum to be withdrawn from the account
     * @throws Exception exception is thrown if the transaction cannot be done
     */
    public abstract void withdraw(int sumToWithdraw) throws Exception;

    /**
     * Adds an observer to the current list of observers
     *
     * @param o observer to be added
     */
    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Deletes an observer from the current list of observers
     *
     * @param o observer to be deleted
     */
    @Override
    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Displays a message and notifies all observers that a change has been made onto the observed
     * object
     */
    @Override
    public void notifyObservers() {
        System.out.println("Notifying all observers when a change is made.");
        for (Observer o : observers) {
            o.update(this, this);
        }
    }

    public String getAccountName() {
        return accountName;
    }

    public int getAvailableSum() {
        return availableSum;
    }

    @Override
    public String toString() {
        return "Account: " +
                "accountName='" + accountName + '\'' +
                ", availableSum=" + availableSum + '.';
    }
}
