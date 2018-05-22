package Model;

/**
 * This class models the concept of a mathematical monomial. It has specific parameters (degree, coefficient
 * and denominator) and methods which will be of great help for the future implementation.
 * <p>
 * The class implements Comparable interface, because I will sort the monomials by their degree in the Polynomial class.
 *
 * @author Ionut Matis, group 30421
 */
public class Monomial implements Comparable<Monomial> {

    /**
     * degree represents the degree of the monomial.
     * denominator represents the denominator of the monomial.
     * coefficient represents the coefficient of the monomial.
     */
    private int degree;
    private int coefficient;
    private int denominator;

    /**
     * This constructor creates an instance a monomial of a specific degree and coefficient. The denominator will be set
     * to 1 as default.
     */
    public Monomial(int degree, int coefficient) {
        this.degree = degree;
        this.coefficient = coefficient;
        this.denominator = 1;
    }

    /**
     * This constructor creates an instance of a monomial of a specific degree, coefficient and denominator.
     */
    public Monomial(int degree, int coefficient, int denominator) {
        this.degree = degree;
        this.coefficient = coefficient;
        this.denominator = denominator;
    }

    public int getDegree() {
        return degree;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    /**
     * This method compares two monomials by their degree.
     *
     * @return an int representing the result of the comparison of the degrees.
     */
    @Override
    public int compareTo(Monomial o) {
        if (this.degree > o.degree) {
            return -1;
        } else {
            if (this.degree < o.degree) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * This method builds a string as a representation of a monomial so that the user can operate with it. There are
     * special cases when the parameters are not displayed (ex: when the degree is 0 or denominator is 1.)
     *
     * @return a string representing the monomial in a human readable format
     */
    @Override
    public String toString() {
        switch (degree) {
            case 0:
                if (coefficient != 0) {
                    if (denominator == 1) {
                        return coefficient + "";
                    } else {
                        return coefficient + "/" + denominator;
                    }
                } else {
                    return "";
                }
            case 1:
                if (coefficient != 0) {
                    if (denominator == 1) {
                        return coefficient + "*x";
                    } else {
                        return coefficient + "/" + denominator + "*x";
                    }
                } else {
                    return "";
                }

            default:
                if (coefficient != 0) {
                    if (denominator == 1) {
                        return coefficient + "*x^" + degree;
                    } else {
                        return coefficient + "/" + denominator + "*x^" + degree;
                    }
                } else {
                    return "";
                }
        }
    }
}
