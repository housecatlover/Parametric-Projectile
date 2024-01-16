package game;

public class Collider { //TODO make abstract, extend button & have it see mouse input (execute inserted method?)
    protected double x, y, r, Xr, Yr;
    protected shape s;

    protected Collider(double x, double y, double r, shape s){
        this.x = x;
        this.y = y;
        this.r = r;
        this.s = s;
    }

    public Collider(double x, double y, double Xr, double Yr){
        this.x = x;
        this.y = y;
        this.Xr = Xr;
        this.Yr = Yr;
        s = shape.rectangle;
    }

    protected static enum shape {
        circle,
        square,
        rectangle,
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return r;
    }

    public boolean collide(Collider o) { //Some pristine object-oriented design right here
        switch (s) {
            case circle: //considers o a circle
                return Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2)) < o.getRadius() + r;
            case square: //considers o a square, I don't care yet
                return Math.abs(x - o.getX()) < o.getRadius() + r && Math.abs(y - o.getY()) < o.getRadius() + r;
            case rectangle: //considers o a square, I don't care yet
                return Math.abs(x - o.getX()) < o.getRadius() + Xr && Math.abs(y - o.getY()) < o.getRadius() + Yr;
            default:
                return false;
        }
    }

    public void draw(boolean filled) {
        if (filled) {
            switch (s) {
                case circle: //considers o a circle
                    StdDraw.filledCircle(x, y, r);
                    return;
                case square:

                    return;
                case rectangle:
                    StdDraw.filledRectangle(x, y, Xr, Yr);
                    return;
            }
        } else {
            switch (s) {
                case circle:
                    StdDraw.circle(x, y, r);
                    return;
                case square:
                    StdDraw.square(x, y, r);
                    return;
                case rectangle:
                    StdDraw.rectangle(x, y, Xr, Yr);
                    return;
            }


        }
    }
}