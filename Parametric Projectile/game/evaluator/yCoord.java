package game.evaluator;

import game.Collider;

public class yCoord implements Term {

  private final Collider source;

  yCoord(Collider source) {
    this.source = source;
  }

  @Override
  public double evaluate() {
    return source.getY();
  }
}