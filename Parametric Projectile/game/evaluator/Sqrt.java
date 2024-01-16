package game.evaluator;

public class Sqrt implements Term {
  private final Term first;

  Sqrt(Term first) {
    this.first = first;
  }

  @Override
  public double evaluate() {
    return Math.sqrt(first.evaluate());
  }
}