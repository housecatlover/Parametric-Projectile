package game.evaluator;

public class Pow extends Equation {

  Pow(Term first, Term second) {
    super(first, second);
  }

  @Override
  public double evaluate() {
    return Math.pow(first.evaluate(), second.evaluate());
  }
}