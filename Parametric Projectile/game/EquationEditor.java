package game;

import game.evaluator.Term;

import static game.evaluator.Assembler.assemble;

public class EquationEditor {
  private final ParametricProjectile game;

  enum Type {
    YEQUALS("y ="),
    PARAMETRIC("parametric"),
    POLAR("polar");

    private final String text;
    Type(String text) {
      this.text = text;
    }
    @Override
    public String toString() {
      return text;
    }
  }

  private Term equation;
  private Type type;
  public EquationEditor(ParametricProjectile game) {
    this.game = game;
    equation = null;
  }

  public Term getEquation() {
    return equation;
  }

  public void setEquation(Type type, String input) {
    this.type = type;
    equation = assemble(input, this);
  }

  public Type getType() {
    return type;
  }

  public Collider getCollider() {
    return game.player; //TODO customize based on status
  }
}