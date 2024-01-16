package game.evaluator;

import game.Collider;

public class Tick implements Term {

  private long tick;

  Tick(Collider source) {
    tick = 0;
  }

  @Override
  public double evaluate() {
    return tick++;
  }
}