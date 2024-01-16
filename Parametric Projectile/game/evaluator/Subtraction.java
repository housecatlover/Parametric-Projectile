package game.evaluator;

public class Subtraction extends Equation {

  Subtraction(Term first, Term second) {
    super(first, second);
  }

  @Override
  public double evaluate() {
    return first.evaluate() - second.evaluate();
  }
}