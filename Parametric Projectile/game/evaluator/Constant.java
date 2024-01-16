package game.evaluator;

public class Constant implements Term {
  final double value;

  Constant (double value) {
    this.value = value;
  }

  Constant (String value) {
    this.value = Double.parseDouble(value);
  }

  @Override
  public double evaluate() {
    return value;
  }
}