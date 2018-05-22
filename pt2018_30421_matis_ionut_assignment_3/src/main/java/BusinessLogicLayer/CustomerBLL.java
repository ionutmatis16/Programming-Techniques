package BusinessLogicLayer;

import DataAccessLayer.CustomerDAO;
import Model.Customer;
import java.util.List;

/**
 * Business class that accesses the methods of CustomerDAO class
 */
public class CustomerBLL {
    private CustomerDAO cDAO;

    public CustomerBLL(CustomerDAO cDAO) {
        this.cDAO = cDAO;
    }

    /**
     * Adds a customer to the database
     * @param c customer to be added
     */
    public void addNewCustomer(Customer c) {
        cDAO.insert(c);
    }

    /**
     * Updates a customer from the database
     * @param c the new customer
     * @param id id of the customer to be updated
     */
    public void updateCustomer(Customer c, int id) {
        cDAO.updateById(c, id);
    }

    /**
     * Deletes a customer to the database
     * @param id if of the customer customer to be deleted
     */
    public void deleteCustomer(int id) {
        cDAO.deleteById(id);
    }

    /**
     * Method that finds all the customers from a table
     * @return a list of all customer from the database
     */
    public List<Customer> viewAllCustomers() {
        return cDAO.findAll();
    }

    /**
     * Finds an customer in the database
     * @param id id of the customer to be found
     * @return the customer with the specified id
     */
    public Customer findById(int id) {
        return cDAO.findById(id);
    }
}
