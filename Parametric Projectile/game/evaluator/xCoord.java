package game.evaluator;

import game.Collider;

public class xCoord implements Term {

  private final Collider source;

  xCoord(Collider source) {
    this.source = source;
  }

  @Override
  public double evaluate() {
    return source.getX();
  }
}