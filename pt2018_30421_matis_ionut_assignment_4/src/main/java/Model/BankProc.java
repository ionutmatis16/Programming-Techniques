package Model;

import java.util.List;


/**
 * Interface which proposes methods to be implemented by a Bank class. There are also specified
 * the pre and post conditions for the interface methods.
 *
 * @author Ionut Matis, 30421
 */
public interface BankProc {
    /**
     * Adds a person to the bank
     *
     * @pre p != null
     * @post getNrOfPers() == getNrOfPers()@pre + 1
     * @param p person to be added
     * @throws Exception exception if a problem is encountered (ex: Duplicate keys)
     */
    void addPerson(Person p) throws Exception;

    /**
     * Removes a person from the bank
     *
     * @pre p != null && getNrOfPers() > 0
     * @post getNrOfPers() == getNrOfPers()@pre - 1
     * @param p person to be removed
     * @throws Exception exception if a problem is encountered (ex: person does not exist)
     */
    void removePerson(Person p) throws Exception;

    /**
     * Returns a list of all persons from the bank
     *
     * @pre true
     * @post @nochange
     * @return list of all person from the bank
     */
    List<Person> viewAllPersons();

    /**
     * Associates an account to the person p
     *
     * @pre p != null && a != null
     * @post totalNrOfAcc() == totalNrOfAcc()@pre + 1 && personHoldsAcc(p, a)
     * @param p person to which we assign the account
     * @param a account to be assigned
     */
    void addAccount(Person p, Account a);

    /**
     * Removes an account from the person p
     *
     * @pre p != null && a != null && totalNrOfAcc() > 0 && personHoldsAcc(a) == true
     * @post totalNrOfAcc() == totalNrOfAcc()@pre - 1
     * @param p person to which we assign the account
     * @param a account to be assigned
     */
    void removeAccount(Person p, Account a);

    /**
     * @pre true
     * @post @nochange
     * @return list of all accounts from the bank
     */
    List<Account> viewAllAccounts();

    /**
     * Deposits a sum to the account a
     *
     * @pre a != null && sum > 0
     * @post a.getSum() == a.getSum()@pre + sum
     * @param a account in which the sum is deposited
     * @param sum sum to be deposited
     * @throws Exception exception is the transaction fails (ex: invalid sum)
     */
    void deposit(Account a, int sum) throws Exception;

    /**
     * Withdraws a sum from the account a
     *
     * @pre a != null && sum > 0 && a.getSum() - sum >= 0
     * @post a.getSum() == a.getSum@pre - sum
     * @param a account from which the sum is withdrawn
     * @param sum sum to be withdrawn
     * @throws Exception exception is the transaction fails (ex: invalid sum)
     */
    void withdraw(Account a, int sum) throws Exception;
}
