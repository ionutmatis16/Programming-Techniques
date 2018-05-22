package BusinessLogicLayer;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Class used for creating a JTable based on a list of objects. The process of creating uses reflection
 * techniques
 *
 * @author Ionut Matis, 30421
 */
public class Reflection {
    /**
     * Creates a JTables from an array of objects. It performs the job by using reflection techniques
     * @param objects the list of objects to extract data from
     * @return  a JTable with all the extracted values
     */
    public static JTable createTable(List<Object> objects) {
        try {
            Class c = objects.get(0).getClass();                //get the class of the first object
            Field[] fields = c.getDeclaredFields();             //get the fields of it
            int nrCol = fields.length;
            String[] colName = new String[nrCol];               //create a String array to hold the names of the fields
            Object[][] data = new Object[objects.size()][nrCol];//Matrix of objects that will hold the actual data

            for (int i = 0; i < fields.length; i++) {
                colName[i] = fields[i].getName();               //get the name of the fields
            }

            for (int i = 0; i < objects.size(); i++) {          //loop through all objects
                Field[] fields1 = objects.get(i).getClass().getDeclaredFields();
                for (int j = 0; j < nrCol; j++) {               //loop through each field of each object
                    fields1[j].setAccessible(true);             //set the access accordingly
                    try {
                        data[i][j] = fields1[j].get(objects.get(i));    //get the value of the object and store it
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return new JTable(data, colName);                   //table with all the elements
        } catch (IndexOutOfBoundsException e) {
            return new JTable(0, 0);        //empty table if there are no items
        }
    }
}


