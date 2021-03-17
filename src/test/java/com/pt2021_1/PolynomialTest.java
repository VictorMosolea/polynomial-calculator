package com.pt2021_1;

import org.junit.Test;

import static org.junit.Assert.*;

public class PolynomialTest {

    @Test
    public void add() {
        Polynomial poly1 = PrimaryController.parsePolynomial("6x^3+20x-10x^2+2");
        Polynomial poly2 = PrimaryController.parsePolynomial("10x^4+10x+15x^2+5");
        Polynomial resultPoly = poly1.add(poly2);
        resultPoly.removeZeroes();
        resultPoly.reduce();
        assertEquals("10x⁴+6x³+5x²+30x+7",resultPoly.printPolynomial());
    }

    @Test
    public void subtract() {
        Polynomial poly1 = PrimaryController.parsePolynomial("20x^6-10x+15x^4+2");
        Polynomial poly2 = PrimaryController.parsePolynomial("-x^5+x^7-10x^6-15x^4+2");
        Polynomial resultPoly = poly1.subtract(poly2);
        resultPoly.removeZeroes();
        resultPoly.reduce();
        assertEquals("-x⁷+30x⁶+x⁵+30x⁴-10x",resultPoly.printPolynomial());
    }

    @Test
    public void multiply() {
        Polynomial poly1 = PrimaryController.parsePolynomial("10x+20x^2-5-2x^3");
        Polynomial poly2 = PrimaryController.parsePolynomial("13x^2-20");
        Polynomial resultPoly = poly1.multiply(poly2);
        resultPoly.removeZeroes();
        resultPoly.reduce();
        assertEquals("-26x⁵+260x⁴+170x³-465x²-200x+100",resultPoly.printPolynomial());
    }

    @Test
    public void divide() {
        Polynomial poly1 = PrimaryController.parsePolynomial("x^2+x+2");
        Polynomial poly2 = PrimaryController.parsePolynomial("x+1");
        Polynomial resultPoly = poly1.divide(poly2);
        resultPoly.removeZeroes();
        resultPoly.reduce();
        assertEquals("x",resultPoly.printPolynomial());
    }

    @Test
    public void modulo() {
        Polynomial poly1 = PrimaryController.parsePolynomial("13x^9+11x^4");
        Polynomial poly2 = PrimaryController.parsePolynomial("x^2+1");
        Polynomial resultPoly = poly1.modulo(poly2);
        resultPoly.removeZeroes();
        resultPoly.reduce();
        assertEquals("13x+11",resultPoly.printPolynomial());
    }

    @Test
    public void differentiate() {
        Polynomial poly1 = PrimaryController.parsePolynomial("13x^9+11x^4");
        Polynomial resultPoly = poly1.differentiate();
        resultPoly.removeZeroes();
        resultPoly.reduce();
        assertEquals("117x⁸+44x³",resultPoly.printPolynomial());
    }

    @Test
    public void integrate() {
        Polynomial poly1 = PrimaryController.parsePolynomial("1+2x+62x^3+10x^2");
        Polynomial resultPoly = poly1.integrate();
        resultPoly.removeZeroes();
        resultPoly.reduce();
        assertEquals("15.5x⁴+3.33x³+x²+x",resultPoly.printPolynomial());
    }
}