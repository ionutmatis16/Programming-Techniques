package BusinessLogicLayer;

import DataAccessLayer.OrderDAO;
import Model.Order;
import java.util.List;

/**
 * Business class that accesses the methods of OrderDAO class
 */
public class OrderBLL {
    private OrderDAO oDAO;


    public OrderBLL(OrderDAO oDAO) {
        this.oDAO = oDAO;
    }

    /**
     * Adds an order to the database
     * @param o order to be added
     */
    public void addNewOrder(Order o) {
        oDAO.insert(o);
    }

    /**
     * Updates a product from the database
     * @param o the new product
     * @param id id of the product to be updated
     */
    public void updateOrder(Order o, int id) {
        oDAO.updateById(o, id);
    }

    /**
     * Deletes an order from the database
     * @param id id of the order to be deleted
     */
    public void deleteOrder(int id) {
        oDAO.deleteById(id);
    }

    /**
     * Method that finds all the orders from a table
     * @return a list of all orders from the database
     */
    public List<Order> viewAllOrders() {
        return oDAO.findAll();
    }

    /**
     * Finds an order in the database
     * @param id id of the order to be found
     * @return the order with the specified id
     */
    public Order findById(int id) {
        return oDAO.findById(id);
    }
}
