import bagel.*;

public class Fireball extends GameEntities {

    String image = ShadowMario.game_props.getProperty("gameObjects.fireball.image");
    private final double RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.fireball.radius"));
    private final int SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.fireball.speed"));
    private final int DAMAGE = (int) (Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.fireball.damageSize")) * 100);
    private int x, y;

    // Constructor method for the class
    // sets the initial x and y values for the flag
    public Fireball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Draws the flag image onto the screen
    public void draw() {
        new Image(image).draw(x, y);
    }

    // Creates getter methods for the x and y attributes of the class
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getDamage() {
        return this.DAMAGE;
    }

    // Creates setter methods for the x and y attributes of the class
    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    // Returns the radius of the flag
    public double getRadius() {
        return this.RADIUS;
    }
    public void moveLeft() {
        this.x += SPEED;
    }

    public void moveRight() {
        this.x -= SPEED;
    }

    public void shootFireball(boolean direction) {
        draw();
        autoMove(direction);
    }

    public void autoMove(boolean direction) {
        if (direction) {
            this.x += SPEED;
        } else {
            this.x -= SPEED;
        }
    }


    /*
     * Creates an update method for the flag class and carries out
     * any sort of update required for the class
     */
    public void update(Input input) {

        if (input.isDown(Keys.LEFT) && !ShadowMario.isEdge) {
            moveLeft();
        }

        if (input.isDown(Keys.RIGHT)) {
            moveRight();
        }

    }
}
