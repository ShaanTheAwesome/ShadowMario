import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class Invincible extends GameEntities {

    private int x, y;
    private final double RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.invinciblePower.radius"));
    private final String IMAGE = ShadowMario.game_props.getProperty("gameObjects.invinciblePower.image");
    private final int SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.invinciblePower.speed"));
    private boolean collision = false;

    // Constructor for intialising the x and y attributes of the class
    public Invincible(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Draws the image
    public void draw() {
        new Image(this.IMAGE).draw(this.x, this.y);
    }

    // Getters and setter methods for both the x and y attributes
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    // gets teh radius of the coin
    public double getRadius() {
        return this.RADIUS;
    }

    // Getters and setter methods for the collision attribute
    public void setCollisionTrue() {
        this.collision = true;
    }

    public boolean getCollision() {
        return this.collision;
    }

    // Moves the power up image off the screen
    public void collision() {

        if (this.collision) {
            this.y -= 10;

            if (this.y == -RADIUS) {
                this.collision = false;
            }
        }
    }


    public void moveLeft() {
        this.x += SPEED;
    }

    public void moveRight() {
        this.x -= SPEED;
    }

    /*
     * Creates an update method for the invinible class and carries out
     * any sort of update required for the class
     */
    public void update(Input input) {

        if ((input.isDown(Keys.LEFT) || input.isDown(Keys.A)) && !ShadowMario.isEdge) {
            moveLeft();
        }

        if (input.isDown(Keys.RIGHT) || input.isDown(Keys.D)) {
            moveRight();
        }

        collision();

    }
}