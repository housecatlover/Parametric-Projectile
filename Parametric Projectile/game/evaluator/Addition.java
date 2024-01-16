package game.evaluator;

public class Addition extends Equation {


  Addition(Term first, Term second) {
    super(first, second);
  }

  @Override
  public double evaluate() {
    return first.evaluate() + second.evaluate();
  }
}