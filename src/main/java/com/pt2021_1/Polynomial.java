package com.pt2021_1;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Polynomial {
    private ArrayList<Monomial> monomials;
    private int degree;

    public Polynomial() {
        monomials = new ArrayList<>();
    }

    public Polynomial(Polynomial newPol) {
        monomials = new ArrayList<>();
        for (Monomial i : newPol.getMonomials()) {
            addMonomial(new Monomial(i.getExponent(), i.getCoefficient()));
        }
        degree = newPol.getDegree();
    }

    public int updateDegree() {
        degree = 0;
        for (Monomial i : monomials) {
            if (i.getExponent() > degree)
                degree = i.getExponent();

        }
        return degree;
    }

    public void reduce() {
        this.monomials.sort(Monomial.exponentComparator);
        ArrayList<Monomial> newMonomials = new ArrayList<>();
        double currentCoefficient = 0;
        if (!monomials.isEmpty()) {
            int currentExponent = this.monomials.get(0).getExponent();
            for (Monomial i : this.monomials) {
                if (i.getExponent() == currentExponent)
                    currentCoefficient += i.getCoefficient();
                else {
                    newMonomials.add(new Monomial(currentExponent, currentCoefficient));
                    currentCoefficient = i.getCoefficient();
                    currentExponent = i.getExponent();
                }
            }
            monomials.removeIf(current -> current.getCoefficient() == 0.0);
            newMonomials.add(new Monomial(currentExponent, currentCoefficient));
            setMonomials(newMonomials);
        }
    }

    public Polynomial add(Polynomial b) {
        Polynomial result = new Polynomial();
        int i = 0, j = 0;
        while (i < monomials.size() && j < b.getMonomials().size()) {
            if (monomials.get(i).getExponent() == b.getMonomials().get(j).getExponent()) {
                result.getMonomials().add(new Monomial(monomials.get(i).getExponent(),
                        monomials.get(i++).getCoefficient() + b.getMonomials().get(j++).getCoefficient()));
            } else if (monomials.get(i).getExponent() > b.getMonomials().get(j).getExponent()) {
                result.getMonomials().add(monomials.get(i++));
            } else {
                result.getMonomials().add(b.getMonomials().get(j++));
            }
        }
        while (i < monomials.size()) {
            result.getMonomials().add(monomials.get(i++));
        }
        while (j < b.getMonomials().size()) {
            result.getMonomials().add(b.getMonomials().get(j++));
        }
        return result;
    }

    public Polynomial subtract(Polynomial b) {
        for (Monomial i : b.getMonomials()) {
            i.setCoefficient(-i.getCoefficient());
        }
        return add(b);
    }

    public Polynomial multiply(Polynomial b) {
        Polynomial result = new Polynomial();
        for (Monomial i : monomials) {
            for (Monomial j : b.getMonomials()) {
                result.addMonomial(new Monomial(i.getExponent() + j.getExponent(), i.getCoefficient() * j.getCoefficient()));
            }
        }
        result.reduce();
        return result;
    }

    public void removeZeroes() {
        monomials.removeIf(current -> current.getCoefficient() == 0.0);
    }

    public Polynomial divide(Polynomial b) {
        Polynomial q = new Polynomial();
        Polynomial r = new Polynomial(this);
        while (!r.getMonomials().isEmpty() && (r.updateDegree() >= b.updateDegree())) {
            r.monomials.sort(Monomial.exponentComparator);
            b.monomials.sort(Monomial.exponentComparator);
            double co = r.getMonomials().get(0).getCoefficient() / b.getMonomials().get(0).getCoefficient();
            int ex = r.getMonomials().get(0).getExponent() - b.getMonomials().get(0).getExponent();
            Polynomial t = new Polynomial();
            t.addMonomial(new Monomial(ex, co));
            q = q.add(t);
            Polynomial a = t.multiply(b);
            r = r.subtract(a);
            r.removeZeroes();
            r.reduce();
        }
        return q;
    }

    public Polynomial modulo(Polynomial b) {
        Polynomial q = new Polynomial();
        Polynomial r = new Polynomial(this);
        while (!r.getMonomials().isEmpty() && (r.updateDegree() >= b.updateDegree())) {
            r.monomials.sort(Monomial.exponentComparator);
            b.monomials.sort(Monomial.exponentComparator);
            double co = r.getMonomials().get(0).getCoefficient() / b.getMonomials().get(0).getCoefficient();
            int ex = r.getMonomials().get(0).getExponent() - b.getMonomials().get(0).getExponent();
            Polynomial t = new Polynomial();
            t.addMonomial(new Monomial(ex, co));
            q = q.add(t);
            r = r.subtract(t.multiply(b));
            r.removeZeroes();
        }
        return r;
    }

    public Polynomial differentiate() {
        Polynomial result = new Polynomial();
        for (Monomial i : monomials) {
            result.getMonomials().add(i.differentiate());
        }
        return result;
    }

    public Polynomial integrate() {
        Polynomial result = new Polynomial();
        for (Monomial i : monomials) {
            result.getMonomials().add(i.integrate());
        }
        return result;
    }


    public String printPolynomial() {
        StringBuilder poly = new StringBuilder();
        System.out.println("\n");
        for (Monomial i : monomials) {
            poly.append(i.printMonomial());
        }
        if (poly.toString().startsWith("+")) {
            return poly.substring(1);
        }
        return poly.toString();
    }

    public void addMonomial(Monomial m) {
        monomials.add(m);
    }

    public boolean isZero() {
        return monomials.size() > 0 && monomials.get(0).getCoefficient() == 0.0;
    }
}
