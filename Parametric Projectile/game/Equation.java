/*
package game;

public class Equation {
    private double[] coefficients;
    final public type equT; //equation type

    public enum type{
        linear,
        quadratic,
        parametric,
    }

    public Equation(type equationType, double... coefficients){
        equT = equationType;
        this.coefficients = coefficients;
    }

    public double tickY(int t){
        double x = tickX(t);
        switch(equT){
            case linear:
                return coefficients[0] * x + coefficients[1];
            case quadratic:
                return coefficients[0] * x * x + coefficients[1] * x + coefficients[2];
        }
        return 0;
    }

    public double tickX(int t){
        return t;
    }
}
*/