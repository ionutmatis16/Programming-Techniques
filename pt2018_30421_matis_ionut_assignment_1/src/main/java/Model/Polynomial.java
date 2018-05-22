package Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class models the concept of a mathematical polynomial. It contains an ArrayList of monomials and various
 * methods specific to polynomials (ex: addition, division.)
 *
 * @author Ionut Matis, group 30421
 */
public class Polynomial {

    /**
     * terms represent an ArrayList of monomials
     */
    private ArrayList<Monomial> terms;

    /**
     * This constructor creates an instance a new Polynomial by creating a new ArrayList of Monomials
     */
    public Polynomial() {
        terms = new ArrayList<>();
    }

    /**
     * Adds a monomial to the list of Monomials using the Collection method "add".
     * Sorts the list so that the higher degrees are the first using the Collection method "sort".
     * Adds the terms with the same degree using the method "addSameDegree()";
     *
     * @param monomial represents the monomial to be added to the list of monomials
     */
    public void addTerm(Monomial monomial) {
        terms.add(monomial);
        Collections.sort(terms);
        addSameDegree();
    }

    /**
     * This method computes the Greatest Common Divisor of a two numbers using the subtraction method.
     *
     * @param a represents the first number
     * @param b represents the second number
     * @return a number representing the gcd of number a and number b
     */
    private int gcd(int a, int b) {
        while (a != b) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }
        return a;
    }

    /**
     * This method adds the terms of the polynomial with the same degree and works even though the denominator is not 1.
     * Before entering this method, the terms are sorted. The algorithm checks whether the consecutive terms have the
     * same degree. If they do, the Smallest Common Multiple of the denominators is computed. After this, we apply
     * the mathematical addition of the two fraction and remove the second consecutive element added.
     */
    private void addSameDegree() {
        int cmm;
        for (int i = 0; i < terms.size() - 1; i++) {
            if (terms.get(i).getDegree() == terms.get(i + 1).getDegree()) {
                cmm = terms.get(i).getDenominator() * terms.get(i + 1).getDenominator() /
                        gcd(terms.get(i).getDenominator(), terms.get(i + 1).getDenominator());
                terms.get(i).setCoefficient(cmm / terms.get(i).getDenominator() * terms.get(i).getCoefficient() +
                        cmm / terms.get(i + 1).getDenominator() * terms.get(i + 1).getCoefficient());
                terms.get(i).setDenominator(cmm);
                terms.remove(i + 1);
            }
        }
    }

    /**
     * This method returns the coefficient of a specific degree.
     *
     * @param degree represents the degree of which we want to find the coefficient
     * @return the coefficient of the degree or 0 (when there is no term of that degree)
     */
    private int coeffOfDegree(int degree) {
        for (Monomial mon : terms) {
            if (mon.getDegree() == degree) {
                return mon.getCoefficient();
            }
        }
        return 0;
    }

    /**
     * This method returns the denominator of a specific degree.
     *
     * @param degree represents the degree of which we want to find the denominator
     * @return the denominator of the degree or 1 (when there is no term of that degree)
     */

    private int denOfDegree(int degree) { //returns the denominator of a specific monomial(of degree "degree")
        for (Monomial mon : terms) {
            if (mon.getDegree() == degree) {
                return mon.getDenominator();
            }
        }
        return 1;
    }

    /**
     * This method is used to differentiate the Polynomial. For each term of the polynomial it multiplies the
     * coefficient with the degree and then decrements the degree.
     *
     * @return a Polynomial as the result of the differentiation
     */
    public Polynomial diffPolynomial() {
        Polynomial result = new Polynomial();
        int degree, coeff;
        for (Monomial mon : this.getTerms()) {
            coeff = mon.getCoefficient() * mon.getDegree();
            degree = mon.getDegree() - 1;
            result.addTerm(new Monomial(degree, coeff));
        }
        return result;
    }

    /**
     * This method is used to integrate the Polynomial. For each term of the polynomial it increments the degree,
     * keeps the same coefficient and sets the denominator to the value of the degree.
     *
     * @return a Polynomial as the result of the integration.
     */
    public Polynomial integratePolynomial() {
        Polynomial result = new Polynomial();
        int degree, coeff;
        for (Monomial mon : terms) {
            degree = mon.getDegree() + 1;
            coeff = mon.getCoefficient();
            result.addTerm(new Monomial(degree, coeff, degree));
        }

        return result;
    }

    /**
     * This method adds or subtracts two polynomials based on the operator value (either '+' or '-'). It iterates
     * through all degrees and checks whether the polynomials have coefficients of that specific degree. If yes, it
     * adds or subtracts them using fraction addition or subtraction.
     *
     * @param pol2     represents the polynomial to be added or subtracted from the current polynomial.
     * @param operator represents the operation to be performed between polynomials (either '+' or '-'.)
     * @return a polynomial
     */
    public Polynomial addSubPolynomial(Polynomial pol2, char operator) {
        Polynomial result = new Polynomial();
        int cmm, coeffRes;
        int maxDegree = this.getTerms().get(0).getDegree() > pol2.getTerms().get(0).getDegree() ?
                this.getTerms().get(0).getDegree() : pol2.getTerms().get(0).getDegree();
        for (int i = maxDegree; i >= 0; i--) {
            if (this.coeffOfDegree(i) == 0) {
                if (operator == '+') {
                    result.addTerm(new Monomial(i, pol2.coeffOfDegree(i), pol2.denOfDegree(i)));
                } else {
                    result.addTerm(new Monomial(i, -pol2.coeffOfDegree(i), pol2.denOfDegree(i)));
                }
            } else {
                if (pol2.coeffOfDegree(i) == 0) {
                    result.addTerm(new Monomial(i, this.coeffOfDegree(i), this.denOfDegree(i)));
                } else {
                    cmm = this.denOfDegree(i) * pol2.denOfDegree(i) / gcd(this.denOfDegree(i), pol2.denOfDegree(i));
                    if (operator == '+') {
                        coeffRes = cmm / this.denOfDegree(i) * this.coeffOfDegree(i) +
                                cmm / pol2.denOfDegree(i) * pol2.coeffOfDegree(i);
                    } else {
                        coeffRes = cmm / this.denOfDegree(i) * this.coeffOfDegree(i) -
                                cmm / pol2.denOfDegree(i) * pol2.coeffOfDegree(i);
                    }
                    result.addTerm(new Monomial(i, coeffRes, cmm));
                }
            }
        }
        return result;
    }

    /**
     * This methods checks whether the polynomial represents the 0 polynomial.
     *
     * @return true if the polynomial equals 0, false otherwise
     */
    private boolean equalsZero() {
        for (int i = 0; i < this.getTerms().size(); i++) {
            if (this.getTerms().get(i).getCoefficient() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method multiplies every term of the current polynomial with very of the two polynomials by adding the
     * degrees, multiplying the coefficients and multiplying the denominators. After this, adds the coefficients with
     * the same degree.
     *
     * @param pol2 represents the polynomial used for multiplying the current polynomial
     * @return a polynomial representing the result of the multiplication.
     */
    public Polynomial multiplyPolynomial(Polynomial pol2) {
        Polynomial result = new Polynomial();
        for (Monomial mon1 : this.getTerms()) {
            for (Monomial mon2 : pol2.getTerms()) {
                result.addTerm(new Monomial(mon1.getDegree() + mon2.getDegree(),
                        mon1.getCoefficient() * mon2.getCoefficient(),
                        mon1.getDenominator() * mon2.getDenominator()));
            }
        }
        result.addSameDegree();
        return result;
    }

    /**
     * This method divides the current polynomial (this reference) to the polynomial d using the Horner mathematical
     * method. See the method <a href="https://en.wikipedia.org/wiki/Horner%27s_method">here</a>.
     *
     * @param d represents the polynomial used for dividing the current polynomial
     * @return an array of 2 polynomials (quotient and rest) or array of nulls if the division is not possible
     */
    public Polynomial[] dividePolynomial(Polynomial d) {
        Polynomial aux = new Polynomial();
        Polynomial cop = this;
        Polynomial q = new Polynomial();
        Monomial t;
        if (d.equalsZero()) {
            return new Polynomial[]{null, null};
        } else {
            while (cop.getTerms().get(0).getDegree() >= d.getTerms().get(0).getDegree() && !cop.equalsZero()) {
                if (d.getTerms().get(0).getCoefficient() > 0) {
                    t = new Monomial(cop.getTerms().get(0).getDegree() - d.getTerms().get(0).getDegree(),
                            cop.getTerms().get(0).getCoefficient(), d.getTerms().get(0).getCoefficient());
                } else {
                    t = new Monomial(cop.getTerms().get(0).getDegree() - d.getTerms().get(0).getDegree(),
                            -cop.getTerms().get(0).getCoefficient(), -d.getTerms().get(0).getCoefficient());
                }
                q.addTerm(t);
                aux.addTerm(t);
                cop = cop.addSubPolynomial(aux.multiplyPolynomial(d), '-');
                cop.getTerms().remove(0);
                aux = new Polynomial();
            }
        }
        return new Polynomial[]{q, cop};
    }

    public ArrayList<Monomial> getTerms() {
        return terms;
    }

    /**
     * This method builds a string as a representation of a polynomial so that the user can operate with it. It
     * concatenates each monomial string representation in the ArrayList
     *
     * @return a string representing the polynomial in a human readable format
     */
    @Override
    public String toString() {
        String s = "";
        for (Monomial mon : terms) {
            s = s + (mon.getCoefficient() > 0 ? "+" : "") + mon.toString();
        }
        if (s.length() != 0) {
            return s;
        } else {
            return 0 + "";
        }

    }
}
