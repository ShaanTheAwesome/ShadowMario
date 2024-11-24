import bagel.*;

public class Boss extends GameEntities {

    String image = ShadowMario.game_props.getProperty("gameObjects.enemyBoss.image");
    private final int SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.enemyBoss.speed"));
    private final double RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.enemyBoss.radius"));
    private final String HEALTH = String.valueOf((int) (Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.enemyBoss.health")) * 100));
    private final int ACTIVATION_RADIUS = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.enemyBoss.activationRadius"));
    private int x, y;
    private boolean inRange, isAlive = true;

    /*
    * Constructor method for the class
    * sets the initial x and y values for the platform
    */
    public Boss(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Draws the platform onto the screen
    public void draw() {
        new Image(image).draw(x, y);
    }

    /*
    * Getter methods for the x, y, radius, health, activation
    * radius, canFire and inRange attributes of the class
    */
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public double getRadius() {
        return this.RADIUS;
    }
    public String getHealth() {
        return HEALTH;
    }
    public int getActivationRadius() {
        return this.ACTIVATION_RADIUS;
    }
    public boolean getInRange() {
        return this.inRange;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    // Setter methods for the x, y, radius, inRange attributes of the class
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setInRangeTrue() {
        this.inRange = true;
    }
    public void setInRangeFalse() {
        this.inRange = false;
    }
    public void setIsAliveFalse() {
        this.isAlive = false;
    }

    // Movement methods for the class
    public void moveLeft() {
        this.x += SPEED;
    }
    public void moveRight() {
        this.x -= SPEED;
    }

    /*
     * Creates an update method for the DoubleScore class and carries out
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