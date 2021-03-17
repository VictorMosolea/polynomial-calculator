package com.pt2021_1;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.Comparator;

@Setter
@Getter
public class Monomial {
    private int exponent;
    private double coefficient;

    public Monomial() {
        exponent = 0;
        coefficient = 0.0;
    }

    public Monomial(int exponent, double coefficient) {
        this.exponent = exponent;
        this.coefficient = coefficient;
    }

    public String printMonomial() {
        String exp = "";
        if (exponent != 0) {
            if (exponent == 1) {
                exp = "x";
            } else
                exp = "x" + Print.superscript(String.valueOf(exponent));
        }
        DecimalFormat df2 = new DecimalFormat("#.##");
        String coef;
        if (coefficient == 1.0 && exponent == 0) {
            coef = "1";
        } else if (coefficient == 1.0)
            coef = "";
        else if (coefficient == -1.0 & exponent == 0) {
            coef = "-1";
        } else if (coefficient == -1.0)
            coef = "-";
        else coef = df2.format(coefficient);
        if (coefficient >= 0)
            return "+" + coef + exp;
        else
            return coef + exp;
    }

    public Monomial differentiate() {
        Monomial m = new Monomial();
        m.setCoefficient(coefficient * exponent);
        m.setExponent(exponent - 1);
        return m;
    }

    public Monomial integrate() {
        Monomial m = new Monomial();
        m.setCoefficient(coefficient / (exponent + 1));
        m.setExponent(exponent + 1);
        return m;
    }

    public static Comparator<Monomial> exponentComparator = (o1, o2) -> o2.exponent - o1.exponent;
}
