import Model.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class represents a Test Class for all polynomial operations implemented in this project: Addition, Subtraction,
 * Multiplication, Division, Differentiation and Integration.
 *
 * @author Ionut Matis, 30421
 */
public class PolTest {
    private Polynomial firstPol = new Polynomial();
    private Polynomial secondPol = new Polynomial();
    private Polynomial resultAdd = new Polynomial();
    private Polynomial resultSub = new Polynomial();
    private Polynomial resultMul = new Polynomial();
    private Polynomial[] resultDiv = new Polynomial[]{new Polynomial(), new Polynomial()};
    private Polynomial resultDiff = new Polynomial();
    private Polynomial resultIntegr = new Polynomial();

    /**
     * This method sets up the values for the first and second polynomial together with the result of the operations
     * computed on them.
     *
     * The first polynomial:        2*x^3 + x + 4
     * The second polynomial:       3*x^4 - 2*x + 5
     * Addition result:             3*x^4 + 2*x^3 - 1*x + 9
     * Subtraction result:         -3*x^4 + 2*x^3 + 3*x - 1
     * Multiplication result:       6*x^7 + 3*x^5 + 8*x^4 + 10*x^3 - 2*x^2 - 3*x + 20
     * Division result:             Q: 0	R: 2*x^3 + 1*x + 4
     * Differentiate first:         6*x^2 + 1
     * Integrate first:             2/4*x^4 + 1/2*x^2 + 4*x
     */
    @Before
    public void setUp() {
        firstPol.addTerm(new Monomial(3, 2));
        firstPol.addTerm(new Monomial(1, 1));
        firstPol.addTerm(new Monomial(0, 4));

        secondPol.addTerm(new Monomial(4, 3));
        secondPol.addTerm(new Monomial(1, -2));
        secondPol.addTerm(new Monomial(0, 5));

        resultAdd.addTerm(new Monomial(4,3));
        resultAdd.addTerm(new Monomial(3,2));
        resultAdd.addTerm(new Monomial(1,-1));
        resultAdd.addTerm(new Monomial(0,9));

        resultSub.addTerm(new Monomial(4,-3));
        resultSub.addTerm(new Monomial(3,2));
        resultSub.addTerm(new Monomial(1,3));
        resultSub.addTerm(new Monomial(0,-1));

        resultMul.addTerm(new Monomial(7,6));
        resultMul.addTerm(new Monomial(5,3));
        resultMul.addTerm(new Monomial(4,8));
        resultMul.addTerm(new Monomial(3,10));
        resultMul.addTerm(new Monomial(2,-2));
        resultMul.addTerm(new Monomial(1,-3));
        resultMul.addTerm(new Monomial(0,20));

        resultDiv[0].addTerm(new Monomial(0,0));
        resultDiv[1].addTerm(new Monomial(3,2));
        resultDiv[1].addTerm(new Monomial(1,1));
        resultDiv[1].addTerm(new Monomial(0,4));

        resultDiff.addTerm(new Monomial(2,6));
        resultDiff.addTerm(new Monomial(0,1));

        resultIntegr.addTerm(new Monomial(4,2,4));
        resultIntegr.addTerm(new Monomial(2,1,2));
        resultIntegr.addTerm(new Monomial(1,4));
    }

    /**
     * Tests whether the Addition was performed correctly, by comparing the expected result of the operation with the
     * actual one created by addSubPolynomial() method.
     */
    @Test
    public void testAddition() {
        assertEquals(resultAdd.toString(), firstPol.addSubPolynomial(secondPol, '+').toString());
    }

    /**
     * Tests whether the Subtraction was performed correctly, by comparing the expected result of the operation with the
     * actual one created by addSubPolynomial() method.
     */
    @Test
    public void testSubtraction() {
        assertEquals(resultSub.toString(), firstPol.addSubPolynomial(secondPol, '-').toString());
    }

    /**
     * Tests whether the Multiplication was performed correctly, by comparing the expected result of the operation with
     * the actual one created by multiplyPolynomial() method.
     */
    @Test
    public void testMultiplication() {
        assertEquals(resultMul.toString(), firstPol.multiplyPolynomial(secondPol).toString());
    }

    /**
     * Tests whether the Division was performed correctly, by comparing the expected result of the operation with the
     * actual one created by dividePolynomial() method.
     */
    @Test
    public void testDivision() {
        assertEquals(resultDiv[0].toString(), firstPol.dividePolynomial(secondPol)[0].toString());
        assertEquals(resultDiv[1].toString(), firstPol.dividePolynomial(secondPol)[1].toString());
    }

    /**
     * Tests whether the Differentiation was performed correctly, by comparing the expected result of the operation with
     * the actual one created by diffPolynomial() method.
     */
    @Test
    public void testDifferentiation() {
        assertEquals(resultDiff.toString(), firstPol.diffPolynomial().toString());
    }

    /**
     * Tests whether the Integration was performed correctly, by comparing the expected result of the operation with the
     * actual one created by integratePolynomial() method.
     */
    @Test
    public void testIntegration() {
        assertEquals(resultIntegr.toString(), firstPol.integratePolynomial().toString());
    }

    public void tearDown() {
    }
}
