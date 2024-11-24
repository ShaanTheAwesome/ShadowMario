import bagel.*;

public class Platform extends GameEntities {
    private final String IMAGE = ShadowMario.game_props.getProperty("gameObjects.platform.image");
    private final int SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.platform.speed"));
    private int x, y, initialX, initialY;

    // Constructor method for the class
    // sets the initial x and y values for the platform
    public Platform(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Draws the platform onto the screen
    public void draw() {
        new Image(IMAGE).draw(x, y);
    }

    // Getter methods for the x and y attributes of the class
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    // Setter methods for the x, y, initial x, initial y and radius attributes of the class
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }
    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }

    public int getInitialX() {
        return this.initialX;
    }
    public int getInitialY() {
        return this.initialY;
    }
    public double getRadius() {
        return 0;
    }

    public void moveLeft() {
        this.x += SPEED;
    }

    public void moveRight() {
        this.x -= SPEED;
    }

    /*
     * Creates an update method for the platform class and carries out
     * any sort of update required for the class
     */
    public void update(Input input) {

        if ((input.isDown(Keys.LEFT) || input.isDown(Keys.A)) && !ShadowMario.isEdge) {
            moveLeft();
        }

        if (input.isDown(Keys.RIGHT) || input.isDown(Keys.D)) {
            moveRight();
        }
    }
}
