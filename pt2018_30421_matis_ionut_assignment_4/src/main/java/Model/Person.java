package Model;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Class that models the concept of a person
 *
 * @author Ionut Matis, 30421
 */
public class Person implements Observer, Serializable {
    private final String fullName;
    private final String dateOfBirth;
    private String email;

    /**
     * Constructor which initializes the fields
     *
     * @param fullName    name of the person
     * @param email       email of the person
     * @param dateOfBirth date of birth of the person
     */
    public Person(String fullName, String email, String dateOfBirth) {
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Override the hashcode. This is based only on the full name and the date of birth. This is
     * because the email can be modified, so that would change the hashcode and lose the values in the
     * table
     *
     * @return the hash code of the object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + fullName.hashCode();
        hash = 29 * hash + dateOfBirth.hashCode();
        return hash;
    }

    /**
     * Override equals. It compares only the name and the date of birth and decides if they are equal
     *
     * @param obj object to which we compare
     * @return true if the object are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Person p = (Person) obj;
        return fullName.equals(p.getFullName()) &&
                dateOfBirth.equals(p.getDateOfBirth());
    }

    /**
     * Implement the update method of the Observer class. This method is called whenever an observed
     * element changes its state
     *
     * @param o the observed element
     * @param arg argument of the method
     */
    public void update(Observable o, Object arg) {
        System.out.println("Hello " + fullName + ". " +
                ((Account) arg).getAccountName() + " was updated. Current balance: " +
                ((Account) arg).getAvailableSum() + ".\n");
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "Person: " +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' + ".";
    }
}
