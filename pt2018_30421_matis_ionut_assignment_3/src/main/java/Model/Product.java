package Model;

/**
 * Product class that matches perfectly the table product in the database
 *
 * @author Ionut Matis, 30421
 */
public class Product {
    private int prID;
    private String prName;
    private int prAmount;
    private int prPrice;

    /**
     * Empty constructor used in AbstractDAO class
     */
    public Product() {

    }

    public Product(int prID, String prName, int prAmount, int prPrice) {
        this.prID = prID;
        this.prName = prName;
        this.prAmount = prAmount;
        this.prPrice = prPrice;
    }

    public int getPrID() {
        return prID;
    }

    public void setPrID(int prID) {
        this.prID = prID;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public int getPrAmount() {
        return prAmount;
    }

    public void setPrAmount(int prAmount) {
        this.prAmount = prAmount;
    }

    public int getPrPrice() {
        return prPrice;
    }

    public void setPrPrice(int prPrice) {
        this.prPrice = prPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "prID=" + prID +
                ", prName='" + prName + '\'' +
                ", prAmount=" + prAmount +
                ", prPrice=" + prPrice +
                '}';
    }
}
