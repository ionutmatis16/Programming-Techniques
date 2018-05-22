package Model;

/**
 * Order class that matches perfectly the table order in the database
 *
 * @author Ionut Matis, 30421
 */
public class Order {
    private int ordID;
    private int cuID;
    private int prID;
    private int ordAmount;

    /**
     * Empty constructor used in AbstractDAO class
     */
    public Order() {

    }

    public Order(int ordID, int cuID, int prID, int ordAmount) {
        this.ordID = ordID;
        this.cuID = cuID;
        this.prID = prID;
        this.ordAmount = ordAmount;
    }

    public int getOrdID() {
        return ordID;
    }

    public int getCuID() {
        return cuID;
    }

    public int getPrID() {
        return prID;
    }

    public int getOrdAmount() {
        return ordAmount;
    }

    public void setOrdID(int ordID) {
        this.ordID = ordID;
    }

    public void setCuID(int cuID) {
        this.cuID = cuID;
    }

    public void setPrID(int prID) {
        this.prID = prID;
    }

    public void setOrdAmount(int ordAmount) {
        this.ordAmount = ordAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ordID=" + ordID +
                ", cuID=" + cuID +
                ", prID=" + prID +
                ", ordAmount=" + ordAmount +
                '}';
    }
}
