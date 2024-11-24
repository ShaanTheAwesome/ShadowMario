import bagel.*;

public class Player extends GameEntities {
    private int x, y, initialX, initialY, verticalSpeed = 0;
    private final double RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.player.radius"));
    private final String HEALTH = String.valueOf((int) (Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.player.health")) * 100));
    private final String PLAYER_RIGHT = ShadowMario.game_props.getProperty("gameObjects.player.imageRight");
    private final String PLAYER_LEFT = ShadowMario.game_props.getProperty("gameObjects.player.imageLeft");
    private String image = ShadowMario.game_props.getProperty("gameObjects.player.imageRight");

    // Constructor method for the class
    // sets the initial x and y values for the player
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Draws the player image onto the game
    public void draw() {
        new Image(this.image).draw(this.x, this.y);
    }

    // Sets the player orientation
    public void setImageRight() {
        this.image = PLAYER_RIGHT;
    }
    public void setImageLeft() {
        this.image = PLAYER_LEFT;
    }

    // Setter methods for the x ,y, radius, initial position,
    // inRange and vertical speed attributes of the class
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setVerticalSpeed(int speed) {
        this.verticalSpeed = speed;
    }
    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }
    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }

    // Getter methods for the x ,y, radius, initial position,
    // inRange and vertical speed attributes of the class
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getVerticalSpeed() {
        return this.verticalSpeed;
    }
    public double getRadius() {
        return this.RADIUS;
    }
    public String getHealth() {
        return HEALTH;
    }
    public int getInitialY() {
        return this.initialY;
    }
    public int getInitialX() {
        return this.initialX;
    }

    public void update(Input input) {

        // Rotates the players image to face the direction the player's moving too
        if ((input.isDown(Keys.LEFT) || input.isDown(Keys.A)) && !ShadowMario.isEdge) {
            setImageLeft();
        }

        if (input.isDown(Keys.RIGHT) || input.isDown(Keys.D)) {
            setImageRight();
        }
    }
}
