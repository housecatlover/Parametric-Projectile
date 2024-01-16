package game.evaluator;

public class Cos implements Term {
  private final Term first;

  Cos(Term first) {
    this.first = first;
  }

  @Override
  public double evaluate() {
    return Math.cos(first.evaluate());
  }
}