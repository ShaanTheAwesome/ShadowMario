// Abstract class to predefine all the common Game Entity attributes
public abstract class GameEntities {

    private int x, y, radius;
    private String IMAGE;

    public abstract void draw();
    public abstract int getX();
    public abstract int getY();
    public abstract void setX(int x);
    public abstract void setY(int y);
    public abstract double getRadius();

}
