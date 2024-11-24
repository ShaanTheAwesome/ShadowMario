import bagel.*;
import java.util.Random;

public class Enemy extends GameEntities {
    private final String IMAGE = ShadowMario.game_props.getProperty("gameObjects.enemy.image");
    private final double RADIUS = Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.enemy.radius"));
    private final int RANDOM_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.enemy.randomSpeed"));
    private final int SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.enemy.speed"));
    private final int DAMAGE = (int) (Double.parseDouble(ShadowMario.game_props.getProperty("gameObjects.enemy.damageSize")) * 100);
    private final int MAX_DISPLACEMENT = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.enemy.maxRandomDisplacementX"));
    private int x, y, count = 0;
    private boolean damageDone = false, direction;

    // Constructor method for the class
    // sets the initial x and y values for the enemy
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;

        count = 0;
        direction = ShadowMario.RANDOM.nextBoolean();
    }

    // Draws the enemy on the screen
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



    // Setter methods for the x and y attributes of the class
    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }


    // Boolean setter methods for if the enemy has done damage yet
    public void setDamageDoneTrue() {
        this.damageDone = true;
    }

    // Getter method for the damageDone value
    public boolean getDamageDone() {
        return this.damageDone;
    }

    public double getRadius() {
        return this.RADIUS;
    }

    public int getDamage() {
        return this.DAMAGE;
    }

    public void randomMovement() {

        if (this.direction) {
            this.x += RANDOM_SPEED;

            if (this.count == MAX_DISPLACEMENT) {
                this.count = 0;
                this.direction = false;
            } else {
                this.count += 1;
            }
        } else {
            this.x -= RANDOM_SPEED;

            if (this.count == MAX_DISPLACEMENT) {
                this.count = 0;
                this.direction = true;
            } else {
                this.count += 1;
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
    * Creates an update method for the enemy class and carries out
    * any sort of update required for the class
    */
    public void update(Input input) {

        if ((input.isDown(Keys.LEFT) || input.isDown(Keys.A)) && !ShadowMario.isEdge) {
            moveLeft();
        }

        if (input.isDown(Keys.RIGHT) || input.isDown(Keys.D)) {
            moveRight();
        }

        randomMovement();
    }

}
