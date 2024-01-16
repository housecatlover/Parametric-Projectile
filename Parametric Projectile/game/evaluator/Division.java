package game.evaluator;

public class Division extends Equation {
  Division(Term first, Term second) {
    super(first, second);
  }

  @Override
  public double evaluate() {
    return first.evaluate() / second.evaluate();
  }
}