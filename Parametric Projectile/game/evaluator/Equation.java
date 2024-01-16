package game.evaluator;

public abstract class Equation implements Term {
  protected final Term first;
  protected final Term second;

  Equation (Term first, Term second) {
    this.first = first;
    this.second = second;
  }
}