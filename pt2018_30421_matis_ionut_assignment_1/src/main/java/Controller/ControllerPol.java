package Controller;

import Model.*;
import View.ViewPol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the controller of the project. It interacts both with the view and model so that the classes
 * of the project have well defined purpose. In this class we call methods from Model package by defining action listeners
 * to the components of the View package.
 *
 * @author Ionut Matis, group 30421
 */
public class ControllerPol {
    private Polynomial pol1;
    private Polynomial pol2;
    private ViewPol view;

    /**
     * This constructor initialises the elements before any action is made.
     *
     * @param view represents the GUI
     */
    public ControllerPol(ViewPol view) {
        this.pol1 = new Polynomial();
        pol1.addTerm(new Monomial(0, 0));
        this.pol2 = new Polynomial();
        pol2.addTerm(new Monomial(0, 0));
        this.view = view;
        addListeners();
        view.getFirstPolLabelOp().setText(0 + "");
        view.getSecondPolLabelOp().setText(0 + "");
        view.getResultField().setText(0 + "");
    }

    public static void main(String[] args) {
        ViewPol viewPol = new ViewPol();
        new ControllerPol(viewPol);
    }

    /**
     * This method adds listeners to all buttons from the view.
     */
    private void addListeners() {
        view.getLoad1().addActionListener(new SingleOpListener1());
        view.getDiff1().addActionListener(new SingleOpListener1());
        view.getIntegr1().addActionListener(new SingleOpListener1());
        view.getReset1().addActionListener(new SingleOpListener1());

        view.getLoad2().addActionListener(new SingleOpListener2());
        view.getDiff2().addActionListener(new SingleOpListener2());
        view.getIntegr2().addActionListener(new SingleOpListener2());
        view.getReset2().addActionListener(new SingleOpListener2());

        view.getEqualsButton().addActionListener(new ResultListener());
    }

    /**
     * Create an inner class that implements the interface ActionListener. This class controls the operation done on
     * the first polynomial (Loading, Differentiation, Integration, Reset.)
     */
    private class SingleOpListener1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                switch (command) {
                    case "Load":
                        pol1.addTerm(new Monomial(Integer.parseInt(view.getDegreeTextField1().getText()),
                                Integer.parseInt(view.getCoeffTextField1().getText())));
                        view.getFirstPolLabelOp().setText(pol1.toString());
                        view.getResultField().setText(pol1.toString());
                        break;
                    case "Differentiate":
                        view.getResultField().setText(pol1.diffPolynomial().toString());
                        break;
                    case "Integrate":
                        view.getResultField().setText(pol1.integratePolynomial().toString());
                        break;
                    default:
                        pol1.getTerms().clear();
                        pol1.addTerm(new Monomial(0, 0));
                        view.getFirstPolLabelOp().setText("0");
                        view.getResultField().setText(0 + "");
                }
            } catch (NumberFormatException ne) {
                view.getResultField().setText("Invalid input");
            }
        }
    }

    /**
     * Create an inner class that implements the interface ActionListener. This class controls the operation done on
     * the second polynomial (Loading, Differentiation, Integration, Reset.)
     */
    private class SingleOpListener2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            try {
                switch (command) {
                    case "Load":
                        pol2.addTerm(new Monomial(Integer.parseInt(view.getDegreeTextField2().getText()),
                                Integer.parseInt(view.getCoeffTextField2().getText())));
                        view.getSecondPolLabelOp().setText(pol2.toString());
                        view.getResultField().setText(pol2.toString());
                        break;
                    case "Differentiate":
                        view.getResultField().setText(pol2.diffPolynomial().toString());
                        break;
                    case "Integrate":
                        view.getResultField().setText(pol2.integratePolynomial().toString());
                        break;
                    default:
                        pol2.getTerms().clear();
                        pol2.addTerm(new Monomial(0, 0));
                        view.getSecondPolLabelOp().setText("0");
                        view.getResultField().setText(0 + "");
                }
            } catch (NumberFormatException ne) {
                view.getResultField().setText("Invalid input");
            }
        }
    }

    /**
     * Create an inner class that implements the interface ActionListener. This class controls the operation done on
     * the both polynomials (addition, subtraction, multiplication, division.)
     */
    private class ResultListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Character sign = (Character) view.getOperationCombo().getSelectedItem();
            switch (sign) {
                case '+':
                    view.getResultField().setText(pol1.addSubPolynomial(pol2, '+').toString());
                    break;
                case '-':
                    view.getResultField().setText(pol1.addSubPolynomial(pol2, '-').toString());
                    break;
                case '*':
                    view.getResultField().setText(pol1.multiplyPolynomial(pol2).toString());
                    break;
                case '/':
                    try {
                        String q = pol1.dividePolynomial(pol2)[0].toString();
                        String r = pol1.dividePolynomial(pol2)[1].toString();
                        view.getResultField().setText("Q: " + q + "\t" + "R: " + r);
                    } catch (NullPointerException ex) {
                        view.getResultField().setText("Cannot divide by 0.");
                    }
            }
        }
    }
}
