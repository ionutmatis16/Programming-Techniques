package Model;

/**
 * Customer class that matches perfectly the table customer in the database
 *
 * @author Ionut Matis, 30421
 */
public class Customer {
    private int cuID;
    private String cuName;
    private String cuEmail;
    private int cuAge;

    /**
     * Empty constructor used in AbstractDAO class
     */
    public Customer() {

    }

    public Customer(int cuID, String cuName, String cuEmail, int cuAge) {
        this.cuID = cuID;
        this.cuName = cuName;
        this.cuEmail = cuEmail;
        this.cuAge = cuAge;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cuID=" + cuID +
                ", cuName='" + cuName + '\'' +
                ", cuEmail='" + cuEmail + '\'' +
                ", cuAge=" + cuAge +
                '}';
    }

    public int getCuID() {
        return cuID;
    }

    public void setCuID(int cuID) {
        this.cuID = cuID;
    }

    public String getCuName() {
        return cuName;
    }

    public void setCuName(String cuName) {
        this.cuName = cuName;
    }

    public String getCuEmail() {
        return cuEmail;
    }

    public void setCuEmail(String cuEmail) {
        this.cuEmail = cuEmail;
    }

    public int getCuAge() {
        return cuAge;
    }

    public void setCuAge(int cuAge) {
        this.cuAge = cuAge;
    }
}
