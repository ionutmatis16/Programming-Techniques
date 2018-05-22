package BusinessLogicLayer;

import DataAccessLayer.ProductDAO;
import Model.Product;

import java.util.List;

/**
 * Business class that accesses the methods of ProductDAO class
 */
public class ProductBLL {
    private ProductDAO pDAO;

    public ProductBLL(ProductDAO pDAO) {
        this.pDAO = pDAO;
    }

    /**
     * Adds a product to the database
     * @param p product to be added
     */
    public void addNewProduct(Product p) {
        pDAO.insert(p);
    }

    /**
     * Updates a product to the database
     * @param p the new product
     * @param id the id of the product to be updated
     */
    public void updateProduct(Product p, int id) {
        pDAO.updateById(p, id);
    }

    /**
     * Deletes a product to the database
     * @param id if of the product to be deleted
     */
    public void deleteProduct(int id) {
        pDAO.deleteById(id);
    }

    /**
     * Method that finds all the products from a table
     * @return a list of all products from the database
     */
    public List<Product> viewAllProducts() {
        return pDAO.findAll();
    }

    /**
     * Finds a product in the database
     * @param id id of the object to be found
     * @return the product with the specified id
     */
    public Product findById(int id) {
        return pDAO.findById(id);
    }
}
