package game;

/**
 * A projectile object
 * <p>
 * Alex Schwartz
 * j8 5/1/2020
 */
public abstract class Projectile extends Collider {
  enum shotType {
    unarmed(0, Integer.MAX_VALUE),
    linear(50, 20),
    parametric(75, 35);

    public final int damage;

    public final int RoF;

    private shotType(int damage, int RoF) {
      this.damage = damage;
      this.RoF = RoF;
    }
  }

  private final Entity owner;

  private final shotType shot;
  ParametricProjectile game;

  public static Projectile createProjectile(double x, double y, shotType shot, double radius,
                                            ParametricProjectile game, Entity owner) {
    switch (shot) {
      case unarmed -> {
        return null; //TODO handle null
      }
      case linear -> {
        return new Linear(x, y, radius, game, owner);
        //TODO setup pulling equation parameters from the game
      }
//            case parametric -> {
//                return new Parametric
//            }
    }
    throw new RuntimeException(new IllegalArgumentException("Invalid shot type")); //should be unreachable anyway
  }

  /**
   * Constructor for objects of class game.Projectile
   *
   * @param x      starting x coordinate
   * @param y      starting y coordinate
   * @param radius = visible size
   * @param game   the game containing the projectile to provide protected game.Entity data
   */
  public Projectile(double x, double y, double radius, ParametricProjectile game, Entity owner, shotType shot) {
    super(x, y, radius, shape.circle);
    this.owner = owner;
    this.game = game;
    this.shot = shot;
  }


  public boolean tick() {
    //movement determined in subclass, then this is called from the subclass's tick()

    boolean collided = false;
    draw();

    //for player (owner != player is to disable friendly fire (projectiles start inside Entities))
    if (owner != game.player && game.player.collide(this)) {
      game.player.damage(shot.damage);//does an amount of damage based on the projectile
      collided = true;
    }
    for (Entity enemy : game.enemies) {//owner != enemy is to disable self damage
      if (owner != enemy && enemy.collide(this)) {
        enemy.damage(shot.damage);//does an amount of damage based on the projectile
        collided = true;
      }

    }
    for (Collider wall : game.walls) {
      if (collide(wall)) {
        return true;
      }
    }
    return collided;
  }

  public double getY() {
    return y;
  }

  public double getX() {
    return x;
  }

  public double getRadius() {
    return r;
  }

  public void draw() {
    StdDraw.setPenColor(0, 0, 0);
    super.draw(true); //StdDraw.filledCircle(x, y, r);
  }
}