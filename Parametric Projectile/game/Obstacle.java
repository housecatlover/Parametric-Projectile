package game;

public class Obstacle extends Collider {
    public Obstacle(double x, double y, double radius){
        super(x, y, radius, shape.square);
    }
    public void draw() {
        StdDraw.setPenColor(200,75,25); //Mud red-ish
        super.draw(true); StdDraw.filledSquare(x,y,r);
    }
}