package game.evaluator;

public class Multiplication extends Equation {

  Multiplication(Term first, Term second) {
    super(first, second);
  }

  @Override
  public double evaluate() {
    return first.evaluate() * second.evaluate();
  }
}