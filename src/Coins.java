import bagel.*;

public class Coins extends GameEntities {
    String image = ShadowMario.game_props.getProperty("gameObjects.coin.image");
    private final double RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.coin.radius"));
    private final int SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.coin.speed"));
    private boolean collision = false;
    private int x, y;

    // Constructor method for the class
    // sets the initial x and y values for the coin
    public Coins(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws the image
    public void draw() {
        new Image(image).draw(this.x, this.y);
    }

    // Getter methods for the x and y attributes of the class
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    // Setter methods for the x and y attributes of the class
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }

    // Boolean setter methods for if the player has collided with the coin
    public void setCollisionTrue() {
        this.collision = true;
    }

    // Retrieves the boolean collision value
    public boolean getCollision() {
        return this.collision;
    }

    // Retrieves the coin radius
    public double getRadius() {
        return this.RADIUS;
    }

    public void moveLeft() {
        this.x += SPEED;
    }

    public void moveRight() {
        this.x -= SPEED;
    }

    public void collision() {
        this.y -= 10;

        if (this.y == -RADIUS) {
            collision = false;
        }
    }

    /*
     * Creates an update method for the coins class and carries out
     * any sort of update required for the class
     */
    public void update(Input input) {

        if ((input.isDown(Keys.LEFT) || input.isDown(Keys.A)) && !ShadowMario.isEdge) {
            moveLeft();
        }

        if (input.isDown(Keys.RIGHT) || input.isDown(Keys.D)) {
            moveRight();
        }

        if (collision) {
            collision();
        }
    }
}
