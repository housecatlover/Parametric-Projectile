package game; /**
 * A super basic shooter with customizable Parametric Projectiles.
 *
 * @Alex Schwartz
 * @1.0 5/1/2020
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ParametricProjectile {
  public static void main(String[] args) {
    ParametricProjectile game = new ParametricProjectile();
    game.draw();
    game.pauseScreen(true);
  }
  //Could include a tick to draw the screen, or manually call a draw, &&/^ create a between level check
  //that will include wait to start, as well < make a pause

        /*
         //Title screen
         game.StdDraw.show();
         game.StdDraw.clear();
         */

  //while (!game.StdDraw.hasNextKeyTyped());//wait before starting the game
  //tick()

  private static final int FRAME_BUFFER = 25; //ms paused between frames without calculations
  //    private static final int RESOLUTION_HORIZONTAL = 1280;
//    private static final int RESOLUTION_VERTICAL = 720;
  private static final int RESOLUTION_HORIZONTAL = 800;
  private static final int RESOLUTION_VERTICAL = 800;

  protected Entity player;
  protected Set<Entity> enemies;
  private Set<Entity> corpses;
  private final EquationEditor editor;
  private final EquationScreen popup;

  protected Set<Obstacle> walls;

  private double scaleX; //can change scale on levels for player understanding (pi for example)
  private double scaleY;
  private double centerX; //can change the center of the screen
  private double centerY;  // like to move it to the 1st quadrant for some reason

  public ParametricProjectile() {
    StdDraw.enableDoubleBuffering();
    System.out.println('\u000C');
    StdDraw.setCanvasSize(RESOLUTION_HORIZONTAL, RESOLUTION_VERTICAL);

    centerX = 0;
    centerY = 0;
    scaleX = ((double) RESOLUTION_HORIZONTAL / (double) RESOLUTION_VERTICAL) * (double) 2;
    scaleY = ((double) RESOLUTION_VERTICAL / (double) RESOLUTION_HORIZONTAL) * (double) 2;

    setScale(centerX - scaleX / 2, centerX + scaleX / 2,
            centerY - scaleY / 2, centerY + scaleY / 2);

    StdDraw.setPenRadius(0.05);
    StdDraw.setTitle("Parametric Projectile");
    StdDraw.setFont(new Font("Arial", Font.PLAIN, 28));

    corpses = new HashSet<>();
    walls = new HashSet<>();

    enemies = new HashSet<>(); //Create set of enemies to draw from

    editor = new EquationEditor(this);

    popup = new EquationScreen(editor, this);

    player = new Entity(this, null);

    //Adding some enemies manually for testing
    LevelLoader.load(this, LevelLoader.Level.test);
  }

  public void setScale(double xMin, double xMax, double yMin, double yMax) {
    StdDraw.setXscale(xMin, xMax);
    StdDraw.setYscale(yMin, yMax);
  }

  public void run() {
    boolean playing = true;
    while (playing) {
      if (pauseScreen(false)) return; //check if escape is pressed, if yes manages everything

      //moves Entities
      if (tick())
        playing = false;

      StdDraw.show();
      StdDraw.clear();
      StdDraw.pause(FRAME_BUFFER);
    }
  }

  public boolean tick() {
    boolean output = player.tick();
    //System.out.println(player.getHP());
    ArrayList<Entity> dead = new ArrayList<>();
    for (Entity enemy : enemies) {
      if (enemy.tick()) {//moves enemies towards player
        dead.add(enemy);
      }
    }
    dead.forEach(enemies::remove); //don't need to check the corpses

    corpses.addAll(dead);
    for (Entity corpse : corpses) {
      corpse.draw(); //can change this if they should decay
    }

    //runs all the projectiles
    player.projectileTick();
    for (Entity enemy : enemies) {
      enemy.projectileTick();//needs to know when to stop checking enemies
    }
    for (Obstacle wall : walls) {
      wall.draw();
    }

    return output;
  }

  public void draw() {
    player.draw();
    for (game.Entity enemy : enemies) {
      enemy.draw();
    }
    StdDraw.show();
    StdDraw.clear();
  }

  EquationEditor getEditor() {
    return editor;
  }

  EquationScreen getPopup() {
    return popup;
  }

  /**
   * Opens the popup pause menu
   *
   * @param paused
   * @return
   */
  public boolean pauseScreen(boolean paused) {
    if (!paused && !StdDraw.isKeyPressed(69)) { //p I think
      return false;
    }

    popup.setVisible(true);
    return true;
  }

  /**
   * Deprecated, brings up a pause menu using STDDraw and colliders as buttons and the mouse.
   */
  public void manualPauseScreen(boolean paused) {
    if (!paused) {
      if (StdDraw.isKeyPressed(69)) { //p I think
        paused = true;//becomes paused
      }
    } else return;

    /*
    Popup popup = null; //todo remove this implementation of buttons
    Collider linear = new Collider(-.33, .455, .2, .1); //these probably have to change with
    Collider button = new Collider(.75, 0, .2, .2);


        EquationEditor.type asked = null;
        EquationEditor.type answered = null;
        double[] input = {0, 1, 0, 0}; //numbers for testing
        while (paused) {
            StdDraw.picture(0.0, 0.0, "Parametric Projectile/resources/PauseScreen.png", scaleX, scaleY);

//            StdDraw.textRight(-.38, .45, entry);//y1, m, x1);

            linear.draw(false);
            button.draw(false);
            if (StdDraw.isMousePressed()) {
                Collider mouse = new Collider(StdDraw.mouseX(), StdDraw.mouseY(), 0, Collider.shape.square);
                if (linear.collide(mouse) && asked != EquationEditor.type.YEQUALS) {
                     popup = new Popup(new String[] {"Y1", "m", "X1"}, new double[] {0, 1, 0});
                     asked = EquationEditor.type.YEQUALS;
                }
                if (button.collide(mouse) && asked != EquationEditor.type.parametric) {
                    popup = new Popup(new String[] {"x", "y", "Xr", "Yr"}, new double[] {0,0,.2,.2});
                    asked = EquationEditor.type.parametric;
                }
            }

            if (popup != null && asked != answered) {
                popup.ask();
                answered = asked;
            }
            if (popup != null) {
                input = popup.getValues();
            }
            StdDraw.textRight(-.38, .45, "" + input[1]);
            StdDraw.rectangle(input[0], input[1], input[2], input[3]); //TODO temp
            */
    //StdDraw.show();
    //StdDraw.clear();

    if (StdDraw.isKeyPressed(27)) { //esc
      for (Entity enemy : enemies) {//Draws the game screen in back
        enemy.draw();
      }
      player.draw();
      StdDraw.show();
      StdDraw.clear();
      StdDraw.pause(200);
      return; //ends pause
    }
    //stays paused
  }

}