package View;

import Model.Polynomial;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the Graphical User Interface of the project. It is built on a single frame which holds a panel
 * with BoxLayout.Y_AXIS (aligns elements vertically). All the other elements are placed inside this panel on different
 * panels with layout of type Flow.
 * <p>
 * On the first panel there is a label with a text. On the second there are 2 labels, 2 text fields and 4 buttons
 * (for single operations on polynomials). The next 2 panels are similar to the first 2. The fifth panel holds a label
 * with a message "Do some math". The sixth panel has 2 labels, 2 text fields, one JComboBox and one button. The last
 * panel displays the result and has one label and one non-editable text field.
 * <p>
 * The GUI uses a font (Times New Roman) of different sizes and weights.
 *
 * @author Ionut Matis, group 30421
 */
public class ViewPol {
    private final static String TITLE = "Polynomial Application";
    private final static int WIDTH = 1600;
    private final static int HEIGHT = 820;
    private final static Font TNR_PLAIN30 = new Font("Times New Roman", Font.PLAIN, 30);
    private final static Font TNR_BOLD40 = new Font("Times New Roman", Font.BOLD, 40);
    private final static Font TNR_PLAIN40 = new Font("Times New Roman", Font.PLAIN, 40);
    private JFrame frame = new JFrame();
    private JPanel mainPanel = new JPanel();
    private JPanel contentPanel = new JPanel();
    private Polynomial pol1;
    private Polynomial pol2;
    private JTextField coeffTextField1;
    private JTextField degreeTextField1;
    private JButton load1;
    private JButton diff1;
    private JButton integr1;
    private JButton reset1;

    private JTextField coeffTextField2;
    private JTextField degreeTextField2;
    private JButton load2;
    private JButton diff2;
    private JButton integr2;
    private JButton reset2;

    private JTextField firstPolLabelOp;
    private JComboBox<Character> operationCombo;
    private JTextField secondPolLabelOp;
    private JButton equalsButton;

    private JTextField resultField;

    /**
     * Constructor which initialises all elements (JButtons, Labels, JTextFields etc) and loads 3 methods
     * initContentPanel(), initStyles() and initComp().
     */
    public ViewPol() {
        coeffTextField1 = new JTextField();
        degreeTextField1 = new JTextField();
        load1 = new JButton("Load");
        diff1 = new JButton("Differentiate");
        integr1 = new JButton("Integrate");
        reset1 = new JButton("Reset");

        coeffTextField2 = new JTextField();
        degreeTextField2 = new JTextField();
        load2 = new JButton("Load");
        diff2 = new JButton("Differentiate");
        integr2 = new JButton("Integrate");
        reset2 = new JButton("Reset");


        operationCombo = new JComboBox<>();
        firstPolLabelOp = new JTextField();
        equalsButton = new JButton("=");
        secondPolLabelOp = new JTextField();
        resultField = new JTextField();

        this.pol1 = new Polynomial();
        this.pol2 = new Polynomial();
        initContentPanel();
        initStyles();
        initComp();
    }

    /**
     * This method sets the attributes of the frame.
     */
    private void initComp() {
        frame.setTitle(TITLE);
        frame.setLocation(150, 120);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * This method sets the fonts, sizes, borders and colors of the elements.
     */
    private void initStyles() {
        coeffTextField1.setPreferredSize(new Dimension(70, 50));
        coeffTextField1.setFont(TNR_PLAIN40);
        degreeTextField1.setPreferredSize(new Dimension(70, 50));
        degreeTextField1.setFont(TNR_PLAIN40);
        load1.setBackground(Color.GREEN);
        load1.setFont(TNR_PLAIN30);
        diff1.setFont(TNR_PLAIN30);
        integr1.setFont(TNR_PLAIN30);
        reset1.setFont(TNR_PLAIN30);
        reset1.setBackground(Color.red);

        coeffTextField2.setFont(TNR_PLAIN40);
        coeffTextField2.setPreferredSize(new Dimension(70, 50));
        degreeTextField2.setPreferredSize(new Dimension(70, 50));
        degreeTextField2.setFont(TNR_PLAIN40);
        load2.setBackground(Color.GREEN);
        load2.setFont(TNR_PLAIN30);
        diff2.setFont(TNR_PLAIN30);
        integr2.setFont(TNR_PLAIN30);
        reset2.setFont(TNR_PLAIN30);
        reset2.setBackground(Color.red);

        firstPolLabelOp.setFont(TNR_PLAIN40);
        firstPolLabelOp.setPreferredSize(new Dimension(570, 50));
        firstPolLabelOp.setBorder(BorderFactory.createEtchedBorder());
        firstPolLabelOp.setEditable(false);
        operationCombo.setFont(TNR_BOLD40);
        secondPolLabelOp.setFont(TNR_PLAIN40);
        secondPolLabelOp.setPreferredSize(new Dimension(570, 50));
        secondPolLabelOp.setBorder(BorderFactory.createEtchedBorder());
        secondPolLabelOp.setEditable(false);
        equalsButton.setFont(TNR_BOLD40);

        resultField.setFont(TNR_PLAIN40);
        resultField.setPreferredSize(new Dimension(1100, 50));
        resultField.setEditable(false);
        resultField.setBackground(Color.WHITE);
    }

    /**
     * Adds, aligns the components on the various panels and then adds all the panels on the main panel. Lastly, adds
     * this last panel on the frame.
     */
    private void initContentPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel firstPolLabelPanel = new JPanel(); //panel for  the first message
        JLabel firstPolLabel = new JLabel();
        firstPolLabel.setFont(TNR_BOLD40);
        firstPolLabel.setText("LOAD the coefficients and degrees for the first polynomial (one by one)");
        firstPolLabelPanel.add(firstPolLabel);

        JPanel firstPolActionPanel = new JPanel(); //create a panel to hold the first text field and the buttons
        JLabel coeffLabel1 = new JLabel("Coeff: ");
        JLabel degreeLabel1 = new JLabel("Degree: ");
        coeffLabel1.setFont(TNR_PLAIN40);
        degreeLabel1.setFont(TNR_PLAIN40);

        // arrange the components of the panel
        firstPolActionPanel.add(coeffLabel1);
        firstPolActionPanel.add(coeffTextField1);
        firstPolActionPanel.add(Box.createRigidArea(new Dimension(50, 50))); // add distance
        firstPolActionPanel.add(degreeLabel1);
        firstPolActionPanel.add(degreeTextField1);
        firstPolActionPanel.add(Box.createRigidArea(new Dimension(100, 50))); // add distance
        firstPolActionPanel.add(load1);
        firstPolActionPanel.add(Box.createRigidArea(new Dimension(25, 50))); // add distance
        firstPolActionPanel.add(diff1);
        firstPolActionPanel.add(Box.createRigidArea(new Dimension(25, 50))); // add distance
        firstPolActionPanel.add(integr1);
        firstPolActionPanel.add(Box.createRigidArea(new Dimension(25, 50))); // add distance
        firstPolActionPanel.add(reset1);

        //=============================================================================================

        JPanel secondPolLabelPanel = new JPanel(); //panel for  the second message
        JLabel secondPolLabel = new JLabel();
        secondPolLabel.setFont(TNR_BOLD40);
        secondPolLabel.setText("LOAD the coefficients and degrees for the second polynomial (one by one)");
        secondPolLabelPanel.add(secondPolLabel);

        JPanel secondPolActionPanel = new JPanel(); //create a panel to hold the second text field and the buttons
        JLabel coeffLabel2 = new JLabel("Coeff: ");
        JLabel degreeLabel2 = new JLabel("Degree: ");
        coeffLabel2.setFont(TNR_PLAIN40);
        degreeLabel2.setFont(TNR_PLAIN40);

        // arrange the components of the panel
        secondPolActionPanel.add(coeffLabel2);
        secondPolActionPanel.add(coeffTextField2);
        secondPolActionPanel.add(Box.createRigidArea(new Dimension(50, 50))); // add distance
        secondPolActionPanel.add(degreeLabel2);
        secondPolActionPanel.add(degreeTextField2);
        secondPolActionPanel.add(Box.createRigidArea(new Dimension(100, 50))); // add distance
        secondPolActionPanel.add(load2);
        secondPolActionPanel.add(Box.createRigidArea(new Dimension(25, 50))); // add distance
        secondPolActionPanel.add(diff2);
        secondPolActionPanel.add(Box.createRigidArea(new Dimension(25, 50))); // add distance
        secondPolActionPanel.add(integr2);
        secondPolActionPanel.add(Box.createRigidArea(new Dimension(25, 50))); // add distance
        secondPolActionPanel.add(reset2);

        //=============================================================================================

        JPanel doMathPanel = new JPanel(); // panel that will hold the label "Do some math"
        JLabel doMathLabel = new JLabel("Do some math:");
        doMathLabel.setFont(TNR_BOLD40);
        doMathPanel.add(doMathLabel);
        doMathPanel.add(Box.createRigidArea(new Dimension(980, 50)));

        JPanel operationPanel = new JPanel(); // panel that will hold 4 components
        operationPanel.setPreferredSize(new Dimension(1500, 150));
        JLabel pol1L = new JLabel("Pol1: ");
        pol1L.setFont(TNR_BOLD40);
        JLabel pol2L = new JLabel("Pol2: ");
        pol2L.setFont(TNR_BOLD40);
        operationCombo.addItem('+');
        operationCombo.addItem('-');
        operationCombo.addItem('*');
        operationCombo.addItem('/');


        operationPanel.add(pol1L);
        operationPanel.add(firstPolLabelOp);
        operationPanel.add(Box.createRigidArea(new Dimension(10, 50)));
        operationPanel.add(operationCombo);
        operationPanel.add(Box.createRigidArea(new Dimension(10, 50)));
        operationPanel.add(pol2L);
        operationPanel.add(secondPolLabelOp);
        operationPanel.add(equalsButton);

        JPanel resultPanel = new JPanel();
        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setFont(TNR_BOLD40);

        resultPanel.add(resultLabel);
        resultPanel.add(Box.createRigidArea(new Dimension(20, 50)));
        resultPanel.add(resultField);

        //=============================================================================================

        //add each panel to the main one
        contentPanel.add(Box.createRigidArea(new Dimension(100, 20))); // add space from the top
        contentPanel.add(firstPolLabelPanel); // "Insert the first..."
        contentPanel.add(Box.createRigidArea(new Dimension(100, 5))); // add space after label
        contentPanel.add(firstPolActionPanel); // add the panel with the textfield and 3 buttons

        contentPanel.add(Box.createRigidArea(new Dimension(100, 80))); // add space between components
        contentPanel.add(secondPolLabelPanel); // "Insert the second..."
        contentPanel.add(Box.createRigidArea(new Dimension(100, 5))); // add space after label
        contentPanel.add(secondPolActionPanel); // add the panel with textfield and 3 buttons

        contentPanel.add(Box.createRigidArea(new Dimension(100, 80))); // add space
        contentPanel.add(doMathPanel); // "Do some math" panel
        contentPanel.add(Box.createRigidArea(new Dimension(100, 5))); // space
        contentPanel.add(operationPanel); // panel with the operation options
        contentPanel.add(Box.createRigidArea(new Dimension(100, 20))); // space
        contentPanel.add(resultPanel); // result label and the textfield displaying the result

        mainPanel.add(contentPanel); // add the special layout panel to the main panel
        frame.add(mainPanel); // add all to the frame
    }

    public JTextField getResultField() {
        return resultField;
    }

    public JButton getLoad1() {
        return load1;
    }

    public JButton getDiff1() {
        return diff1;
    }

    public JButton getIntegr1() {
        return integr1;
    }

    public JButton getReset1() {
        return reset1;
    }

    public JButton getLoad2() {
        return load2;
    }

    public JButton getDiff2() {
        return diff2;
    }

    public JButton getIntegr2() {
        return integr2;
    }

    public JButton getReset2() {
        return reset2;
    }

    public JTextField getCoeffTextField1() {
        return coeffTextField1;
    }

    public JTextField getDegreeTextField1() {
        return degreeTextField1;
    }

    public JTextField getCoeffTextField2() {
        return coeffTextField2;
    }

    public JTextField getDegreeTextField2() {
        return degreeTextField2;
    }

    public JComboBox<Character> getOperationCombo() {
        return operationCombo;
    }

    public JTextField getFirstPolLabelOp() {
        return firstPolLabelOp;
    }

    public JTextField getSecondPolLabelOp() {
        return secondPolLabelOp;
    }

    public JButton getEqualsButton() {
        return equalsButton;
    }
}

