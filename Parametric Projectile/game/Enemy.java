package game;

public class Enemy extends Entity {

    public Enemy(ParametricProjectile game, int shotType) {
        super(game, shotType == 0 ? Projectile.shotType.unarmed : Projectile.shotType.linear);
    }
}