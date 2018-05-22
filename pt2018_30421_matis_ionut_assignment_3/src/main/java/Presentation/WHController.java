package Presentation;

import BusinessLogicLayer.CustomerBLL;
import BusinessLogicLayer.OrderBLL;
import BusinessLogicLayer.ProductBLL;
import DataAccessLayer.CustomerDAO;
import DataAccessLayer.OrderDAO;
import DataAccessLayer.ProductDAO;
import Model.Customer;
import Model.Order;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Class that represents the controller of the project. It has both instances of BLL classes and
 * view classes
 *
 * @author Ionut Matis, 30421
 */
public class WHController {
    private StartView startView;

    private CustomerBLL customerBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;

    private CustomerView customerView;
    private ProductView productView;
    private OrderView orderView;


    public WHController(StartView startView, CustomerBLL customerBLL,
                        ProductBLL productBLL, OrderBLL orderBLL,
                        CustomerView customerView, ProductView productView, OrderView orderView) {
        this.startView = startView;

        this.customerBLL = customerBLL;
        this.productBLL = productBLL;
        this.orderBLL = orderBLL;

        this.customerView = customerView;
        this.productView = productView;
        this.orderView = orderView;

        addActCustomer();           //action listeners for Customer frame
        addActProduct();            //action listeners for Product frame
        addActOrder();              //action listeners for Order frame
    }

    public static void main(String[] args) {
        StartView sw = new StartView();

        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderDAO orderDAO = new OrderDAO();

        CustomerBLL customerBLL = new CustomerBLL(customerDAO);
        ProductBLL productBLL = new ProductBLL(productDAO);
        OrderBLL orderBLL = new OrderBLL(orderDAO);

        CustomerView customerView = new CustomerView(new ArrayList<>(customerBLL.viewAllCustomers()), customerDAO);
        ProductView productView = new ProductView(new ArrayList<>(productBLL.viewAllProducts()), productDAO);
        OrderView orderView = new OrderView(new ArrayList<>(orderBLL.viewAllOrders()));

        new WHController(sw, customerBLL, productBLL, orderBLL, customerView, productView, orderView);
    }

    /**
     * Method that adds action listeners to the buttons of the customer view
     */
    @SuppressWarnings("Duplicates")
    private void addActCustomer() {
        startView.getSeeCButton().addActionListener((ActionEvent e) -> {
            startView.getSeeCButton().setEnabled(false);
            customerView.setVisible(true);
        });

        //Action listener for delete button
        customerView.getDeleteB().addActionListener((ActionEvent e) -> {
                    try {
                        int idToBeDel = Integer.parseInt(customerView.getjTable().getValueAt(customerView.getjTable().getSelectedRow(),
                                0).toString());                         //gets the id to be deleted from the table
                        customerBLL.deleteCustomer(idToBeDel);

                        customerView.setCustomers(customerBLL.viewAllCustomers());
                        customerView.getContentPane().removeAll();            //removes all components so I can repaint
                        customerView.updateFrame(new ArrayList<>(customerBLL.viewAllCustomers()));
                        customerView.getContentPane().repaint();
                        customerView.getContentPane().revalidate();

                        orderView.getContentPane().removeAll();
                        orderView.updateFrame(new ArrayList<>(orderBLL.viewAllOrders()));
                        orderView.getContentPane().repaint();
                        orderView.getContentPane().revalidate();

                    } catch (ArrayIndexOutOfBoundsException e3) {
                        try {
                            //if you want to delete directly by ID
                            customerBLL.deleteCustomer(Integer.parseInt(customerView.getCuIDTF().getText()));
                            customerView.setCustomers(customerBLL.viewAllCustomers());
                            customerView.getContentPane().removeAll();
                            customerView.updateFrame(new ArrayList<>(customerBLL.viewAllCustomers()));
                            customerView.getContentPane().repaint();
                            customerView.getContentPane().revalidate();
                        } catch (NumberFormatException e4) {
                            JOptionPane.showMessageDialog(new Panel(), "Invalid id for deletion", "Deletion error",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
        );

        //Action listener for insert button
        customerView.getInsertB().addActionListener((ActionEvent e2) -> {
            try {
                //get the values to insert
                int idToInsert = Integer.parseInt(customerView.getCuIDTF().getText());
                String nameToInsert = customerView.getCuNameTF().getText();
                String emailToInsert = customerView.getCuEmailTF().getText();
                int ageToInsert = Integer.parseInt(customerView.getCuAgeTF().getText());

                customerBLL.addNewCustomer(new Customer(idToInsert, nameToInsert, emailToInsert, ageToInsert));

                customerView.setCustomers(customerBLL.viewAllCustomers());
                customerView.getContentPane().removeAll();
                customerView.updateFrame(new ArrayList<>(customerBLL.viewAllCustomers()));
                customerView.getContentPane().repaint();
                customerView.getContentPane().revalidate();

            } catch (Exception e3) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid input for insertion", "Insertion error",
                        JOptionPane.WARNING_MESSAGE);
                //e3.printStackTrace();
            }


        });

        //Action listener for update button
        customerView.getUpdateB().addActionListener((ActionEvent e) -> {
            try {
                int idToUpdate = Integer.parseInt(customerView.getCuIDTF().getText());
                if (customerBLL.findById(idToUpdate) == null)
                    JOptionPane.showMessageDialog(new Panel(), "Invalid values for update", "Update error",
                            JOptionPane.WARNING_MESSAGE);
                else {
                    String nameToInsert = customerView.getCuNameTF().getText();
                    String emailToInsert = customerView.getCuEmailTF().getText();
                    int ageToInsert = Integer.parseInt(customerView.getCuAgeTF().getText());

                    customerBLL.updateCustomer(new Customer(idToUpdate, nameToInsert, emailToInsert, ageToInsert), idToUpdate);

                    customerView.setCustomers(customerBLL.viewAllCustomers());
                    customerView.getContentPane().removeAll();
                    customerView.updateFrame(new ArrayList<>(customerBLL.viewAllCustomers()));
                    customerView.getContentPane().repaint();
                    customerView.getContentPane().revalidate();
                }
            } catch (Exception e4) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid values for update", "Update error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });


        //action listener for filter button
        customerView.getFilterB().addActionListener((ActionEvent e) -> {
            try {
                customerView.getTextArea().setText("");
                String colName = String.valueOf(customerView.getColName().getSelectedItem());
                Character sign = (Character) (customerView.getSign().getSelectedItem());
                switch (colName) {
                    case "cuName":
                        String valueToFilter = customerView.getValTF().getText();
                        switch (sign) {
                            case '=':
                                for (Customer c : customerView.getCustomers()) {
                                    if (c.getCuName().compareTo(valueToFilter) == 0) {
                                        customerView.getTextArea().append(c.toString() + "\n");
                                    }
                                }
                                break;
                            case '<':
                                for (Customer c : customerView.getCustomers()) {
                                    if (c.getCuName().compareTo(valueToFilter) < 0) {
                                        customerView.getTextArea().append(c.toString() + "\n");
                                    }
                                }
                                break;
                            default:
                                for (Customer c : customerView.getCustomers()) {
                                    if (c.getCuName().compareTo(valueToFilter) > 0) {
                                        customerView.getTextArea().append(c.toString() + "\n");
                                    }
                                }
                        }
                        break; //end of cuName case;

                    case "cuID":
                        int idToFiler = Integer.parseInt(customerView.getValTF().getText());
                        switch (sign) {
                            case '=':
                                for (Customer c : customerView.getCustomers()) {
                                    if (c.getCuID() == idToFiler) {
                                        customerView.getTextArea().append(c.toString() + "\n");
                                    }
                                }
                                break;
                            case '<':
                                for (Customer c : customerView.getCustomers()) {
                                    if (c.getCuID() < idToFiler) {
                                        customerView.getTextArea().append(c.toString() + "\n");
                                    }
                                }
                                break;
                            default:
                                for (Customer c : customerView.getCustomers()) {
                                    if (c.getCuID() > idToFiler) {
                                        customerView.getTextArea().append(c.toString() + "\n");
                                    }
                                }
                        }
                        break; //end of cuID case;

                    case "cuAge":
                        int ageToFilter = Integer.parseInt(customerView.getValTF().getText());
                        switch (sign) {
                            case '=':
                                for (Customer c : customerView.getCustomers()) {
                                    if (c.getCuAge() == ageToFilter) {
                                        customerView.getTextArea().append(c.toString() + "\n");
                                    }
                                }
                                break;
                            case '<':
                                for (Customer c : customerView.getCustomers()) {
                                    if (c.getCuAge() < ageToFilter) {
                                        customerView.getTextArea().append(c.toString() + "\n");
                                    }
                                }
                                break;
                            default:
                                for (Customer c : customerView.getCustomers()) {
                                    if (c.getCuAge() > ageToFilter) {
                                        customerView.getTextArea().append(c.toString() + "\n");
                                    }
                                }
                        }
                        break; //end of cuAge case;

                }
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid input for filtering", "Filter error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

    }

    /**
     * Action listener for product view
     */
    @SuppressWarnings("Duplicates")
    private void addActProduct() {
        startView.getSeePButton().addActionListener((ActionEvent e) -> {
            startView.getSeePButton().setEnabled(false);
            productView.setVisible(true);
        });

        //Action listener for delete button
        productView.getDeleteB().addActionListener((ActionEvent e) -> {
            try {
                int idToBeDel = Integer.parseInt(productView.getjTable().getValueAt(productView.getjTable().getSelectedRow(),
                        0).toString());
                productBLL.deleteProduct(idToBeDel);

                orderView.getContentPane().removeAll();
                orderView.updateFrame(new ArrayList<>(orderBLL.viewAllOrders()));
                orderView.getContentPane().repaint();
                orderView.getContentPane().revalidate();

                productView.setProducts(productBLL.viewAllProducts());
                productView.getContentPane().removeAll();
                productView.updateFrame(new ArrayList<>(productBLL.viewAllProducts()));
                productView.getContentPane().repaint();
                productView.getContentPane().revalidate();

            } catch (ArrayIndexOutOfBoundsException e3) {
                try {
                    productBLL.deleteProduct(Integer.parseInt(productView.getPrIDTF().getText()));

                    productView.setProducts(productBLL.viewAllProducts());
                    productView.getContentPane().removeAll();
                    productView.updateFrame(new ArrayList<>(productBLL.viewAllProducts()));
                    productView.getContentPane().repaint();
                    productView.getContentPane().revalidate();
                } catch (NumberFormatException e4) {
                    JOptionPane.showMessageDialog(new Panel(), "Invalid id for deletion", "Deletion error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //Action listener for delete button
        productView.getInsertB().addActionListener((ActionEvent e) -> {
            try {
                int idToInsert = Integer.parseInt(productView.getPrIDTF().getText());
                String nameToInsert = productView.getPrNameTF().getText();
                int prAmountToInsert = Integer.parseInt(productView.getPrAmountTF().getText());
                int priceToInsert = Integer.parseInt(productView.getPrPriceTF().getText());

                productBLL.addNewProduct(new Product(idToInsert, nameToInsert, prAmountToInsert, priceToInsert));

                productView.setProducts(productBLL.viewAllProducts());
                productView.getContentPane().removeAll();
                productView.updateFrame(new ArrayList<>(productBLL.viewAllProducts()));
                productView.getContentPane().repaint();
                productView.getContentPane().revalidate();


            } catch (Exception e3) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid input for insertion", "Insertion error",
                        JOptionPane.WARNING_MESSAGE);
                //e3.printStackTrace();
            }
        });

        //Action listener for delete button
        productView.getUpdateB().addActionListener((ActionEvent e) -> {
            try {
                int idToUpdate = Integer.parseInt(productView.getPrIDTF().getText());
                if (productBLL.findById(idToUpdate) == null)
                    JOptionPane.showMessageDialog(new Panel(), "Invalid values for update", "Update error",
                            JOptionPane.WARNING_MESSAGE);
                else {
                    String nameToInsert = productView.getPrNameTF().getText();
                    int amountToInsert = Integer.parseInt(productView.getPrAmountTF().getText());
                    int priceToInsert = Integer.parseInt(productView.getPrPriceTF().getText());

                    productBLL.updateProduct(new Product(idToUpdate, nameToInsert, amountToInsert, priceToInsert), idToUpdate);

                    productView.setProducts(productBLL.viewAllProducts());
                    productView.getContentPane().removeAll();
                    productView.updateFrame(new ArrayList<>(productBLL.viewAllProducts()));
                    productView.getContentPane().repaint();
                    productView.getContentPane().revalidate();
                }
            } catch (Exception e4) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid values for update", "Update error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        //Action listener for filter button
        productView.getFilterB().addActionListener((ActionEvent e) -> {
            try {
                productView.getTextArea().setText("");
                String colName = String.valueOf(productView.getColName().getSelectedItem());
                Character sign = (Character) (productView.getSign().getSelectedItem());
                switch (colName) {
                    case "prName":
                        String valueToFilter = productView.getValTF().getText();
                        switch (sign) {
                            case '=':
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrName().compareTo(valueToFilter) == 0) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                            case '<':
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrName().compareTo(valueToFilter) < 0) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                            default:
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrName().compareTo(valueToFilter) > 0) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                        }
                        break; //end of cuName case;

                    case "prID":
                        int idToFilter = Integer.parseInt(productView.getValTF().getText());
                        switch (sign) {
                            case '=':
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrID() == idToFilter) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                            case '<':
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrID() < idToFilter) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                            default:
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrID() > idToFilter) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                        }
                        break; //end of cuID case;

                    case "prAmount":
                        int amountToFilter = Integer.parseInt(productView.getValTF().getText());
                        switch (sign) {
                            case '=':
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrAmount() == amountToFilter) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                            case '<':
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrAmount() < amountToFilter) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                            default:
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrAmount() > amountToFilter) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                        }
                        break; //end of cuAge case;
                    default:
                        int priceToFilter = Integer.parseInt(productView.getValTF().getText());
                        switch (sign) {
                            case '=':
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrPrice() == priceToFilter) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                            case '<':
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrPrice() < priceToFilter) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break;
                            default:
                                for (Product p : productView.getProducts()) {
                                    if (p.getPrPrice() > priceToFilter) {
                                        productView.getTextArea().append(p.toString() + "\n");
                                    }
                                }
                                break; //end price filter
                        }

                }
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid input for filtering", "Filter error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Method that adds action listeners to the order view
     */
    @SuppressWarnings("Duplicates")
    private void addActOrder() {
        startView.getPlaceButton().addActionListener((ActionEvent e) -> {
            startView.getPlaceButton().setEnabled(false);
            orderView.setVisible(true);
        });

        //Action listener for delete button
        orderView.getPlaceB().addActionListener((ActionEvent e) -> {
            try {
                int idToInsert = Integer.parseInt(orderView.getIdTF().getText());

                //gets the id of the customer
                int cuSelRow = customerView.getjTable().getSelectedRow();
                int cuID = Integer.parseInt(customerView.getjTable().getValueAt
                        (cuSelRow, 0).toString());

                //gets the id of the product
                int prSelRow = productView.getjTable().getSelectedRow();
                int prID = Integer.parseInt(productView.getjTable().getValueAt
                        (prSelRow, 0).toString());
                String prName = productView.getjTable().getValueAt(prSelRow, 1).toString();
                int prPrice = Integer.parseInt(productView.getjTable().getValueAt(prSelRow, 3).toString());
                int amountToPlace = Integer.parseInt(orderView.getAmountTF().getText());
                int amountAvail = Integer.parseInt(productView.getjTable().getValueAt(prSelRow, 2).toString());

                if (amountAvail < amountToPlace || amountToPlace <= 0) {
                    JOptionPane.showMessageDialog(new Panel(), "Invalid amount", "Order error",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    orderBLL.addNewOrder(new Order(idToInsert, cuID, prID, amountToPlace));

                    productBLL.updateProduct(new Product(prID, prName, amountAvail - amountToPlace, prPrice), prID);

                    productView.setProducts(productBLL.viewAllProducts());
                    productView.getContentPane().removeAll();
                    productView.updateFrame(new ArrayList<>(productBLL.viewAllProducts()));
                    productView.getContentPane().repaint();
                    productView.getContentPane().revalidate();

                    orderView.getContentPane().removeAll();
                    orderView.updateFrame(new ArrayList<>(orderBLL.viewAllOrders()));
                    orderView.getContentPane().repaint();
                    orderView.getContentPane().revalidate();
                }

            } catch (Exception e3) {
                JOptionPane.showMessageDialog(new Panel(), "Invalid input for insertion", "Insertion error",
                        JOptionPane.WARNING_MESSAGE);
                e3.printStackTrace();
            }
        });

        //action listener to the place button so that it creates a bill for each order
        orderView.getCreateB().addActionListener((ActionEvent e) -> {
            int nrOfRows = orderView.getjTable().getRowCount();
            for (int i = 0; i < nrOfRows; i++) {
                try {
                    int orderID = Integer.parseInt(orderView.getjTable().getValueAt(i, 0).toString());
                    int cuID = Integer.parseInt(orderView.getjTable().getValueAt(i, 1).toString());
                    int prID = Integer.parseInt(orderView.getjTable().getValueAt(i, 2).toString());
                    int prAmount = Integer.parseInt(orderView.getjTable().getValueAt(i, 3).toString());

                    PrintWriter writer = new PrintWriter("order" + (i + 1) + ".txt", "UTF-8");
                    writer.println("Order id: " + orderID);
                    writer.println("Customer: " + (customerBLL.findById(cuID).getCuName()));
                    writer.println("Product: " + productBLL.findById(prID).getPrName());
                    writer.println("Amount: " + prAmount);
                    writer.println("Paid: " + prAmount * productBLL.findById(prID).getPrPrice());
                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException e2) {
                    System.out.println("ERROR at writing.");
                }
            }

        });
    }
}
