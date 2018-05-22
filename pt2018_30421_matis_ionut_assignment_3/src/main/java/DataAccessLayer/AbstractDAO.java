package DataAccessLayer;

import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import Connection.ConnectionFactory;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic class that models the Data Access Object of the application. This class creates queries
 * for a generic table from a database and provides methods to use them. It can be used for any type of
 * table from a database.
 *
 * @param <T> generic parameter to work on
 * @author Ionut Matis, 30421
 */
public class AbstractDAO<T> {
    protected final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type; //type will contain the class of the generic type T

    /**
     * Constructor that sets the type from the generic T
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        //for each AbstractDAO object obtain the class of the generic type T
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates a query for a statement that selects a product based on their id.
     *
     * @param field selection id
     * @return a string with the actual query
     */
    private String createFindIDQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM `");
        sb.append(type.getSimpleName());
        sb.append("` WHERE " + field + " = ?");
        return sb.toString();
    }

    /**
     * Method that uses the createFindIdQuery to search through the database for a specific id.
     *
     * @param id value to be searched
     * @return an object T with its id equal to the one searched
     */
    public T findById(int id) {
        Connection con = null;
        PreparedStatement findIDStatement = null;
        ResultSet resultSet = null;
        Field[] fields = type.getDeclaredFields();                  //get the fields of the object
        String query = createFindIDQuery(fields[0].getName());      //query with the id extracted from the first field
        try {
            con = ConnectionFactory.getConnection();
            findIDStatement = con.prepareStatement(query);
            findIDStatement.setInt(1, id);              //changes the "?" into an actual value
            resultSet = findIDStatement.executeQuery();             //executes the query

            return createObjects(resultSet).get(0);                 //returns the first object found
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(findIDStatement);
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(con);
        }
        return null;
    }

    /**
     * Creates a query for a statement that inserts a T object into a database. An example of query looks like:
     * //INSERT INTO `tableName` (`valueAsID`, `value1`, `value2`, `value3`)
     * // VALUES ('actualID', 'actualValue1', 'actualValue2', 'actualValue3')
     *
     * @param t Object to be inserted in the database
     * @return a string as the query to be executed
     */
    private String createInsertQuery(T t) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = t.getClass().getDeclaredFields();
        sb.append("INSERT INTO `");
        sb.append(type.getSimpleName());                    //get the class name (same as the one in DB)
        sb.append("` (");                                   //build the first parenthesis
        sb.append(fields[0].getName());                     //add the first field
        for (int i = 1; i < fields.length; i++) {
            sb.append(", " + fields[i].getName());          //add the commas and the next fields
        }
        sb.append(") ");                                    //close the parenthesis
        sb.append("VALUES (?");                             //build "?" in the same manner as above
        for (int i = 1; i < fields.length; i++) {
            sb.append(", ?");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Method that uses the createInsertQuery to insert in the database a specific object T
     *
     * @param t object to be inserted
     */
    @SuppressWarnings("Duplicates")
    public void insert(T t) {
        Connection con = null;
        PreparedStatement insertStatement = null;
        String query = createInsertQuery(t);                            //query based on the generic type T
        try {
            con = ConnectionFactory.getConnection();
            insertStatement = con.prepareStatement(query);              //prepare the statement
            Field[] fields = t.getClass().getDeclaredFields();          //get the fields of t

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);                          //set them to be accessible
                if (fields[i].getType().isAssignableFrom(String.class)) {       //if the type of the field is String
                    insertStatement.setString(i + 1, fields[i].get(t).toString());  //get its value
                } else {                                                        //if the type of the field is Integer
                    insertStatement.setInt(i + 1, Integer.parseInt(fields[i].get(t).toString()));
                }
            }
            insertStatement.executeUpdate();                 //do the insertion
        } catch (SQLException | IllegalAccessException e) {
            //LOGGER.log(Level.WARNING, type.getName() + " DAO:insert " + e.getMessage());
            JOptionPane.showMessageDialog(new Panel(), "Cannot insert", "Insertion error",
                    JOptionPane.WARNING_MESSAGE);
            //return -1;
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(con);
        }
        //return inserted;
    }

    /**
     * Creates a query for a statement that updates a T object from a specific id. The query looks like:
     * //UPDATE `tableName` SET `valueAsID`='actualValueForID', `value1`='actualValue1',
     * // `value2`='actualValue2', `value3`='actualValue13' WHERE `valueAsID`='field';
     *
     * @param t     updated object
     * @param field id of the updated object
     * @return string representing the query to be executed
     */
    private String createUpdateQuery(T t, String field) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = t.getClass().getDeclaredFields();
        sb.append("UPDATE `");
        sb.append(type.getSimpleName());
        sb.append("` SET ");
        sb.append(fields[0].getName() + "=?");
        for (int i = 1; i < fields.length; i++) {
            sb.append(", " + fields[i].getName() + "= ?");
        }
        sb.append(" WHERE " + fields[0].getName() + "=" + field);
        return sb.toString();
    }

    /**
     * Method that uses the createUpdateQuery to update in the database a specific object T.
     *
     * @param t  updated object
     * @param id id of the object to be updated
     */
    @SuppressWarnings("Duplicates")
    public void updateById(T t, int id) {
        Connection con = null;
        PreparedStatement updateStatement = null;
        String query = createUpdateQuery(t, Integer.toString(id));      //query on T and the id we want to update
        try {
            con = ConnectionFactory.getConnection();
            updateStatement = con.prepareStatement(query);              //prepare the statement
            Field[] fields = t.getClass().getDeclaredFields();          //get the fields of t

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);                          //set them to be accessible
                if (fields[i].getType().isAssignableFrom(String.class)) {       //if the type of the field is String
                    updateStatement.setString(i + 1, fields[i].get(t).toString());  //get its value from t
                } else {                                                        //if the type of the field is Integer
                    updateStatement.setInt(i + 1, Integer.parseInt(fields[i].get(t).toString()));
                }
            }
            updateStatement.executeUpdate();                 //do the update
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:insert " + e.getMessage());
            JOptionPane.showMessageDialog(new Panel(), "Cannot update", "Update error",
                    JOptionPane.WARNING_MESSAGE);
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(con);
        }
    }

    /**
     * Creates a query for a statement that deletes a row from a specific id.
     *
     * @param field id of the object to be deleted
     * @return string representing the query to be executed
     */
    private String createDeleteIDQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE");
        sb.append(" FROM `");
        sb.append(type.getSimpleName());
        sb.append("` WHERE " + field + " = ?");
        return sb.toString();
    }

    /**
     * Method that uses the createDeleteIDQuery to delete from the database an object with a specific id
     *
     * @param id value to be deleted
     */
    public void deleteById(int id) {
        Connection con = null;
        PreparedStatement deleteIDStatement = null;
        String query = createDeleteIDQuery(type.getDeclaredFields()[0].getName());
        try {
            con = ConnectionFactory.getConnection();
            deleteIDStatement = con.prepareStatement(query);
            deleteIDStatement.setInt(1, id);        //sets the id to be deleted
            deleteIDStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteIDStatement);
            ConnectionFactory.close(con);
        }
    }

    /**
     * Creates a query for a statement that selects all the elements from the table
     *
     * @return string representing the query to be executed
     */
    private String createFindAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `");
        sb.append(type.getSimpleName() + "`");
        return sb.toString();
    }

    /**
     * Method that uses the createFindAllQuery to selects all the elements from the database
     *
     * @return a list of all elements
     */
    public List<T> findAll() {
        Connection con = null;
        PreparedStatement insertStatement = null;
        ResultSet resultSet = null;
        String query = createFindAllQuery();
        try {
            con = ConnectionFactory.getConnection();
            insertStatement = con.prepareStatement(query);
            resultSet = insertStatement.executeQuery();
            return createObjects(resultSet);            //returns the list of all objects
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(con);
        }
        return null;
    }

    /**
     * Method that builds a list of all elements from a result set.
     *
     * @param resultSet result set from where the elements are extracted
     * @return the list of all elements
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {                                   //for each result from ResultSet
                T instance = type.newInstance();                         //create a new Instance
                for (Field f : type.getDeclaredFields()) {               //for each field
                    Object value = resultSet.getObject(f.getName());     //retrieve from the current result the value of the field
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod(); //obtain the method for setting the value
                    method.invoke(instance, value);     //sets the current field a specific value of the instance
                }
                list.add(instance);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException |
                IntrospectionException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, "Error in AbstractDAO");
            e.printStackTrace();
        }
        return list;
    }
}
