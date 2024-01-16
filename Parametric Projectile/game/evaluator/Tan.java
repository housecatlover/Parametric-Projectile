package game.evaluator;

public class Tan implements Term {
  private final Term first;

  Tan(Term first) {
    this.first = first;
  }

  @Override
  public double evaluate() {
    return Math.tan(first.evaluate());
  }
}