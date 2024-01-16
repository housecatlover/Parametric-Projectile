package game;

import game.evaluator.Term;

import static game.Projectile.shotType.linear;


public class Linear extends Projectile {

  public static final double DEFAULT_SPEED = .025;
  private double angle;
  private double speed; //euclidean speed
  private Term equation;

  public Linear(double x, double y, double radius, ParametricProjectile game, Entity owner) {
    super(x, y, radius, game, owner, linear);
    //default, used only for enemies: from entity to target at DEFAULT_SPEED
    speed = DEFAULT_SPEED;
    double tx, ty;
    tx = game.player.getX();
    ty = game.player.getY();
    angle = (Math.atan2(ty - y, tx - x));

  }


  public boolean tick() {
    x += Math.cos(angle) * speed;
    y += Math.sin(angle) * speed;
    //y = equation.evaluate();
    return super.tick();
  }

}