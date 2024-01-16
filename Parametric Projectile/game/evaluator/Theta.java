package game.evaluator;

import game.Collider;

public class Theta implements Term {
  private final Collider source;
  private final Angle units;

  enum Angle {
    DEGREES,
    RADIANS
  }

  Theta(Collider source, Angle units) {
    this.source = source;
    this.units = units;
  }

  @Override
  public double evaluate() {
    double radians = Math.atan(source.getY()/source.getX());
    if (units == Angle.DEGREES) {
      return radians * 180 / Math.PI;
    }
    return radians;
  }
}