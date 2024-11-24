import bagel.*;
import java.util.Random;

public class FlyingPlatform extends GameEntities {

    private int x, y, count = 0;
    private final String IMAGE = ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.image");
    private final int HALFLENGTH = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.halfLength"));
    private final int HALFHEIGHT = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.halfHeight"));
    private final int RANDOM_SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.randomSpeed"));
    private final int MAX_DISPLACEMENT = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
    private final int SPEED = Integer.parseInt(ShadowMario.game_props.getProperty("gameObjects.flyingPlatform.speed"));
    private boolean direction;

    public FlyingPlatform(int x, int y) {
        this.x = x;
        this.y = y;

        count = 0;
        direction = ShadowMario.RANDOM.nextBoolean();
    }

    // draws the image onto the screen
    public void draw() {
        new Image(this.IMAGE).draw(this.x, this.y);
    }

    // getter and setter methods for the x, y and count attributes
    public int getX() {
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }


    // returns 0 for the radius of the flying platform
    // as there is no real radius for it
    public double getRadius() {
        return 0;
    }

    // returns the halfLength and halfWidth of the platform
    public int getHalfLength() {
        return this.HALFLENGTH;
    }
    public int getHalfHeight() {
        return this.HALFHEIGHT;
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
     * Creates an update method for the FlyingPlatform class and carries out
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
