package game;


import java.lang.reflect.Executable;
import java.util.function.Function;

public class LevelLoader {

    private final ParametricProjectile game;

    LevelLoader(ParametricProjectile game) {
        this.game = game;
    }

    enum Level {
        test (LevelLoader::test),
        testParametric (LevelLoader::testParametric),
        one (LevelLoader::one);

        final Function<ParametricProjectile, Boolean> method;
        Level(Function<ParametricProjectile, Boolean> method) {
            this.method = method;
        }
    }

    public static void load(ParametricProjectile game, Level selected){
        selected.method.apply(game);
    }
    private static boolean test(ParametricProjectile game) {
        LevelLoader loader = new LevelLoader(game);
        loader.addEnemy(-0.25, 0.5); //TODO it's only updating when I die
        loader.addTarget( 0.25, 0.5);
        loader.addTarget( 0.0, 0.75);
        loader.addWall(0.0, -0.25, 0.1);
        loader.addPlayer(0, 0);
        loader.addType(EquationEditor.Type.YEQUALS);
        loader.addType(EquationEditor.Type.POLAR);
        return false;
    }

    private static boolean testParametric(ParametricProjectile game) {
        LevelLoader loader = new LevelLoader(game);
        loader.addTarget(-0.25, 0.5);
        loader.addEnemy(0.25, 0.5);
        loader.addEnemy(0.0, 0.75);
        loader.addWall(0.0, -0.25, 0.1);
        loader.addPlayer(0, 0);
        loader.addType(EquationEditor.Type.YEQUALS);
        return false;
    }

    private static boolean one(ParametricProjectile game) {
        LevelLoader loader = new LevelLoader(game);
        loader.addTarget(.5, .5, 10);
        loader.addTarget(1, 1, 10); //TODO scale board in level loader
        loader.addTarget(1.5, 1.5, 10);
        loader.addPlayer(0, 0);
        loader.addType(EquationEditor.Type.YEQUALS);
        return false;
    }

    private void addPlayer(double x, double y) {
        game.player.setLocation(x, y);
        game.player.setAlignment(false);
    }

    private void addType(EquationEditor.Type type) {
        game.getPopup().addType(type);

    }
    private void addEnemy(Projectile.shotType shot, double x, double y) {
        game.enemies.add(new Entity(game, shot, x, y));
    }

    private void addEnemy(double x, double y) {
        addEnemy(Projectile.shotType.linear, x, y);
    }

    private void addTarget(double x, double y) {
        game.enemies.add(new Entity(game, Projectile.shotType.unarmed, x, y));
    }

    private void addTarget(double x, double y, int hp) {
        Entity enemy = new Entity(game, Projectile.shotType.unarmed, x, y);
        enemy.setHP(hp);
        game.enemies.add(enemy);
    }

    private void addWall(double x, double y, double radius) {
        game.walls.add(new Obstacle(x, y, radius));
    }

}