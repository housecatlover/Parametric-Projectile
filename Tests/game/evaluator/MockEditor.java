package game.evaluator;

import game.Collider;
import game.EquationEditor;

class MockEditor extends EquationEditor {
  final double x, y;
  MockEditor(double x, double y) {
    super(null);
    this.x = x;
    this.y = y;
  }
  @Override
  public Collider getCollider() {
    return new Collider(x, y, 0, 0);
  }

}