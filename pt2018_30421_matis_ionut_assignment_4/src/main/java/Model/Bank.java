package Model;

import java.io.Serializable;
import java.util.*;

/**
 * Class that implements the BankProc and Serializable interface. It provides methods for the
 * interface, has an invariant and uses assertions
 *
 * @author Ionut Matis, 30421
 */
public class Bank implements BankProc, Serializable {
    private HashMap<Person, ArrayList<Account>> bankHashMap;
    private int idOfAcc;

    /**
     * Constructor of the class, creates an empty HashMap
     */
    public Bank() {
        this.bankHashMap = new HashMap<>();
        idOfAcc = 1;
    }

    /**
     * Adds a person to the bank
     *
     * @pre p != null
     * @post getNrOfPers() == getNrOfPers()@pre + 1
     * @param p person to be added
     * @throws Exception exception if a problem is encountered (ex: Duplicate keys)
     */
    public void addPerson(Person p) throws Exception {
        assert isWellFormed();
        assert p != null;
        int nrOfPersPre = getNrOfPers();

        if (!bankHashMap.containsKey(p))
            bankHashMap.put(p, new ArrayList<>());
        else
            throw new Exception("Duplicate keys");

        assert getNrOfPers() == nrOfPersPre + 1;
        assert isWellFormed();
    }

    /**
     * Removes a person from the bank
     *
     * @pre p != null && getNrOfPers() > 0
     * @post getNrOfPers() == getNrOfPers()@pre - 1
     * @param p person to be removed
     * @throws Exception exception if a problem is encountered (ex: person does not exist)
     */
    public void removePerson(Person p) throws Exception {
        assert isWellFormed();
        assert p != null;
        assert getNrOfPers() > 0;
        int nrOfPersPre = getNrOfPers();

        if (bankHashMap.containsKey(p))
            bankHashMap.remove(p);
        else
            throw new Exception("Person does not exist in the table");

        assert getNrOfPers() == nrOfPersPre - 1;
        assert isWellFormed();
    }

    /**
     * Returns a list of all persons from the bank
     *
     * @pre true
     * @post @nochange
     * @return list of all person from the bank
     */
    @Override
    public List<Person> viewAllPersons() {
        if (!bankHashMap.isEmpty())
            return new ArrayList<>(bankHashMap.keySet());
        return new ArrayList<>();
    }

    /**
     * Associates an account to the person p
     *
     * @pre p != null && a != null
     * @post totalNrOfAcc() == totalNrOfAcc()@pre + 1 && personHoldsAcc(p, a)
     * @param p person to which we assign the account
     * @param a account to be assigned
     */
    public void addAccount(Person p, Account a) {
        assert isWellFormed();
        assert p != null && a != null;
        int nrOfAccPre = getNrOfAcc();

        bankHashMap.get(p).add(a);
        a.addObserver(p);
        idOfAcc++;

        assert getNrOfAcc() == nrOfAccPre + 1;
        assert personHoldsAcc(p, a);
        assert isWellFormed();
    }

    /**
     * Removes an account from the person p
     *
     * @pre p != null && a != null && totalNrOfAcc() > 0 && personHoldsAcc(a) == true
     * @post totalNrOfAcc() == totalNrOfAcc()@pre - 1
     * @param p person to which we assign the account
     * @param a account to be assigned
     */
    public void removeAccount(Person p, Account a) {
        assert isWellFormed();
        assert p != null && a != null && personHoldsAcc(p, a);
        int nrOfAcc = getNrOfAcc();

        a.deleteObserver(p);
        bankHashMap.get(p).remove(a);

        assert getNrOfAcc() == nrOfAcc - 1;
        assert isWellFormed();
    }

    /**
     * Searches through the bank to find the owner of a specific account
     *
     * @param account account to which we want to find the owner
     * @return string with the name of the holder
     */
    public String searchAccountOwner(Account account) {
        for (Person p : bankHashMap.keySet()) {
            for (Account a : bankHashMap.get(p)) {
                if (a.getAccountName().equals(account.getAccountName()))
                    return p.getFullName();
            }
        }
        return null;
    }

    /**
     * Searches a person based on its name. It loops through the list of persons and compares their
     * unique names
     *
     * @param name name of the person
     * @return a person with a specific name
     */
    public Person searchPerson(String name) {
        for (Person p : bankHashMap.keySet()) {
            if (p.getFullName().equals(name))
                return p;
        }
        return null;
    }

    /**
     * Searches an account based on its name. It loops through the list of accounts and compares their
     * unique names
     *
     * @param name name of the account
     * @return account with a specific name
     */
    public Account searchAccount(String name) {
        for (Account a : viewAllAccounts()) {
            if (a.getAccountName().equals(name))
                return a;
        }
        return null;
    }

    /**
     * @pre true
     * @post @nochange
     *
     * @return list of all accounts from the bank
     */
    @Override
    public List<Account> viewAllAccounts() {
        if (!bankHashMap.isEmpty()) {
            ArrayList<Account> accounts = new ArrayList<>();
            for (ArrayList<Account> ac : bankHashMap.values()) {
                accounts.addAll(ac);
            }
            return accounts;
        }
        return new ArrayList<>();
    }

    /**
     * Deposits a sum to the account a
     *
     * @pre a != null && sum > 0
     * @post a.getSum() == a.getSum()@pre + sum
     * @param a   account in which the sum is deposited
     * @param sum sum to be deposited
     * @throws Exception exception is the transaction fails (ex: invalid sum)
     */
    @Override
    public void deposit(Account a, int sum) throws Exception {
        assert isWellFormed();
        assert a != null && sum >= 0;
        int sumPre = a.getAvailableSum();

        a.deposit(sum);

        assert a.getAvailableSum() == sumPre + sum;
        assert isWellFormed();
    }

    /**
     * Withdraws a sum from the account a
     *
     * @pre a != null && sum > 0 && a.getSum() - sum >= 0
     * @post a.getSum() == a.getSum@pre - sum
     * @param a   account from which the sum is withdrawn
     * @param sum sum to be withdrawn
     * @throws Exception exception is the transaction fails (ex: invalid sum)
     */
    @Override
    public void withdraw(Account a, int sum) throws Exception {
        assert isWellFormed();
        assert a != null && sum > 0 && (a.getAvailableSum() - sum) >= 0;
        int sumPre = a.getAvailableSum();

        a.withdraw(sum);

        assert a.getAvailableSum() == sumPre - sum;
        assert isWellFormed();
    }

    /**
     * Represents the invariant of the class. It iterates through the HashMap and checks whether
     * the number of persons / accounts correspond to the actual number of person / accounts. It
     * also checks if every account has an available sum greater than 0 (cannot have negative balance)
     *
     * @return true if the class meets the requirements, false otherwise
     */
    private boolean isWellFormed() {
        int nrOfPers = 0;
        int nrOfAcc = 0;
        int nrOfAccPerPers;
        for (Person p : viewAllPersons()) {
            nrOfPers++;
            nrOfAccPerPers = 0;
            for (Account a : bankHashMap.get(p)) {
                nrOfAccPerPers++;
                nrOfAcc++;
                if (a.getAvailableSum() < 0)
                    return false;
            }
            if (nrOfAccPerPers != bankHashMap.get(p).size())
                return false;
        }
        if (nrOfPers != viewAllPersons().size())
            return false;
        if (nrOfAcc != viewAllAccounts().size())
            return false;
        return true;
    }

    /**
     * Checks whether the Person p has an Account a
     *
     * @param p person to be searched if it holds the account
     * @param a account to be searched
     * @return true if the p has an account a
     */
    private boolean personHoldsAcc(Person p, Account a) {
        for (Account a1 : bankHashMap.get(p)) {
            if (a1.equals(a))
                return true;
        }
        return false;
    }

    /**
     * @return nr of persons from the bank
     */
    private int getNrOfPers() {
        return viewAllPersons().size();
    }

    /**
     * @return nr of accounts from a bank
     */
    private int getNrOfAcc() {
        return viewAllAccounts().size();
    }

    public HashMap<Person, ArrayList<Account>> getBankHashMap() {
        return bankHashMap;
    }

    public int getIdOfAcc() {
        return idOfAcc;
    }
}
