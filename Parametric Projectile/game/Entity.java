package game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import game.evaluator.Term;

import static game.Projectile.shotType.linear;

/**
 * Write a description of class game.Entity here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Entity extends Collider {
  private final static double MIN_PROXIMITY = .2;
  private static final double DEFAULT_RADIUS = .04;
  private double vx;
  private double vy;
  private double speed; //euclidean speed
  private int hp;
  private boolean evil; //if this game.Entity is an enemy TODO move to seperate class

  private long t; //How many ticks since firing

  private Projectile.shotType shot; //Numbers will correlate to shot type... 1 = linear, maybe use string parsing for everything else
  private double shotRadius;//radius of this game.Entity's projectiles

  private Term equation;
  private Set<Projectile> shots;

  private final ParametricProjectile game; //has protected data

  private final int INIT_HP = 100;


  /**
   * Constructs an Entity & sets all of its values with default positions.
   *
   * @param game the game that this entity belongs to
   * @param shot the mathematical equation for this shot
   */
  public Entity(ParametricProjectile game, Projectile.shotType shot) {
    this(game, shot, 0, 0);
  }

  /**
   * Constructs an Entity & sets all of its values.
   *
   * @param game the game that this entity belongs to
   * @param shot the mathematical equation for this shot
   * @param x    the starting x coord
   * @param y    the starting y coord
   */
  public Entity(ParametricProjectile game, Projectile.shotType shot, double x, double y) {
    super(x, y, DEFAULT_RADIUS, shape.circle);
    this.game = game;
    this.shot = shot;

    t = 0;
    s = shape.circle;
    speed = .01;
    hp = INIT_HP;
    evil = true; //easier to assume true and correct it for the player
    shotRadius = .01;
    shots = new HashSet<>();
  }

  public void setLocation(double newX, double newY) {
    x = newX;
    y = newY;
  }

//  public void setShotType(Projectile.shotType newShot) {
//    shot = newShot;
//  }

  public int getHP() {
    return hp;
  }

  public void setHP(int hp) {
    this.hp = hp;
  }

  /**
   * Receives damage
   *
   * @param damage the amount of damage taken
   */
  public void damage(int damage) {
    hp -= damage;
  }

  public void setAlignment(boolean evil) {
    this.evil = evil;
  }

  public void draw() {
    if (hp <= 0) StdDraw.setPenColor(0, 0, 0);
    else {
      if (evil) StdDraw.setPenColor(Math.round((long) (255.0 * (hp / INIT_HP * .5 + .5))), 0, 0);
      else StdDraw.setPenColor(0, Math.round((long) (255.0 * (hp / INIT_HP * .5 + .5))), 0);
    }
    super.draw(true); //StdDraw.filledCircle(x, y, r);
  }

  public boolean tick() {
    if (hp <= 0) {
      draw();//still draws the corpse
      return true; //tick returns true if dead
    }
    t += 1;

    move();

    if (Math.abs(x + vx) + r > 1.0) vx = -vx; //if going out of bounds, go backwards
    if (Math.abs(y + vy) + r > 1.0) vy = -vy; // if player gets out of bounds this would break
    x += vx;
    y += vy;

    draw();
    //creates projectiles
    if (shot != null) { //the player hasn't set their shot yet; equivalent to unarmed
      if ((evil ^ StdDraw.isMousePressed()) && t > shot.RoF) {//t % RoF == 0 is rate of fire
        shots.add(Projectile.createProjectile(x, y, shot, shotRadius, game, this));
        t = 0;
      }
    }
    return false;
  }

  private void move() {
    double deltaX = 0;
    double deltaY = 0;
    double distance;
    if (evil) {
      deltaY = game.player.getY() - y;
      deltaX = game.player.getX() - x;
      distance = Math.sqrt(Math.pow(deltaY, 2) + Math.pow(deltaX, 2));
      if (distance < MIN_PROXIMITY) { //close enough
        vx = 0;
        vy = 0;
      }
    } else { //player input
      if (StdDraw.isKeyPressed(68)) {
        deltaX += 1;
      }
      if (StdDraw.isKeyPressed(65)) {
        deltaX -= 1;
      }
      if (StdDraw.isKeyPressed(87)) {
        deltaY += 1;
      }
      if (StdDraw.isKeyPressed(83)) {
        deltaY -= 1;
      }
      distance = Math.sqrt(Math.pow(deltaY, 2) + Math.pow(deltaX, 2));
    }
    if (distance < 0.001) {
      vx = 0.0;
      vy = 0.0;
    } else {
      vx = (deltaX) / distance * speed;
      vy = (deltaY) / distance * speed;
    }
  }

  public void projectileTick() {
    LinkedList<Projectile> dead = new LinkedList<>();
    for (Projectile shot : shots) {
      if (shot.tick()) { //
        dead.add(shot);
      }
    }
    for (Projectile shot : dead) {
      shots.remove(shot);
    }
  }
}