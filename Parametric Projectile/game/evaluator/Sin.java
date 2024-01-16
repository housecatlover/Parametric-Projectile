package game.evaluator;

public class Sin implements Term {

  private final Term first;

  Sin(Term first) {
    this.first = first;
  }

  @Override
  public double evaluate() {
    return Math.sin(first.evaluate());
  }
}