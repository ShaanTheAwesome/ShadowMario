import bagel.*;
import bagel.util.Colour;
import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2024
 * Please enter your name below
 * Pirdnani Shaan Satoshi Lalit
 */

public class ShadowMario extends AbstractGame {

    public static final Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    public static boolean isEdge, canMove, playerUseFireball, bossCanFire, bossFired, fireballDirection = true, playerFired = false;
    public static final Random RANDOM = new Random();
    private final Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");

    // initializing general values required for the game
    private final Image BACKGROUND_IMAGE;
    private final Font TITLE, INSTRUCTIONS, SCORE, HEALTH;
    private Platform platform;
    private Player player;
    private Boss boss;
    private Fireball playerFireball, bossFireball;
    private ArrayList<Coins> coins;
    private ArrayList<Enemy> enemys;
    private ArrayList<FlyingPlatform> flyingPlatforms;
    private ArrayList<DoubleScore> doubleScores;
    private ArrayList<Invincible> invincibles;
    private Flag endFlag;
    private final CollisionDetection<GameEntities, GameEntities> COLLISION = new CollisionDetection<>();
    private boolean invinciblePower = false, doubleScore = false, gameStart = false, isJumping = false, gameWon = false, gameLost = false, isInAir = false;
    private ArrayList<ArrayList<String>> levelWorld;
    private final int COIN_VALUE = Integer.parseInt(game_props.getProperty("gameObjects.coin.value"));
    private final int DOUBLE_SCORE_MAX_FRAMES = Integer.parseInt(game_props.getProperty("gameObjects.doubleScore.maxFrames"));
    private final int INVINCIBLE_MAX_FRAMES = Integer.parseInt(game_props.getProperty("gameObjects.invinciblePower.maxFrames"));
    private final int JUMP_SPEED = -20;
    private int doubleScoreTimer = 0, invincibleTimer = 0, randomBossCounter = 0;
    private String scoreNum, playerHP, bossHP;

    /**
     * The constructor
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
                Integer.parseInt(game_props.getProperty("windowHeight")),
                message_props.getProperty("title"));

        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));

        // you can initialise other values from the property files here

        // initialising the texts as well as the text size
        TITLE = new Font (game_props.getProperty("font"), Integer.parseInt(game_props.getProperty("title.fontSize")));
        INSTRUCTIONS = new Font (game_props.getProperty("font"), Integer.parseInt(game_props.getProperty("instruction.fontSize")));
        SCORE = new Font (game_props.getProperty("font"), Integer.parseInt(game_props.getProperty("score.fontSize")));
        HEALTH = new Font (game_props.getProperty("font"), Integer.parseInt(game_props.getProperty("playerHealth.fontSize")));

        scoreNum = "0";
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");

        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }


    // initialises the main title screen of the game
    public void titleScreen() {

        // imports start screen text
        String titleName = message_props.getProperty("title");
        int titleX = Integer.parseInt(game_props.getProperty("title.x"));
        int titleY = Integer.parseInt(game_props.getProperty("title.y"));

        // initialises the title screen instructions
        String titleInstructions = message_props.getProperty("instruction");
        int instructionsY = Integer.parseInt(game_props.getProperty("instruction.y"));

        // draws the start screen game and waits for
        // the player to press the "SPACE" key
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        TITLE.drawString(titleName, titleX, titleY);
        INSTRUCTIONS.drawString(titleInstructions, Window.getWidth()/2.0 - INSTRUCTIONS.getWidth(titleInstructions)/2.0, instructionsY);

    }

    // Imports all the level components as well as
    // resets all the boolean attributes and arrayLists
    public void loadLevel() {

        invinciblePower = false;
        isJumping = false;
        isInAir = false;
        gameWon = false;
        gameLost = false;
        canMove = true;
        doubleScore = false;
        fireballDirection = true;
        bossCanFire = false;
        bossFired = false;

        doubleScoreTimer = 0;
        randomBossCounter = 0;

        this.player = null;
        this.boss = null;
        this.coins = new ArrayList<>();
        this.enemys = new ArrayList<>();
        this.flyingPlatforms = new ArrayList<>();
        this.doubleScores = new ArrayList<>();
        this.invincibles = new ArrayList<>();

        for (ArrayList<String> row: levelWorld) {
            switch(row.get(0)){
                case "PLATFORM":
                    int platformX = Integer.parseInt(row.get(1));
                    int platformY = Integer.parseInt(row.get(2));
                    platform = new Platform(platformX, platformY);
                    platform.setInitialX(platformX);
                    platform.setInitialY(platformY);
                    continue;
                case "PLAYER":
                    int playerX = Integer.parseInt(row.get(1));
                    int playerY = Integer.parseInt(row.get(2));
                    player = new Player(playerX, playerY);
                    player.setInitialX(playerX);
                    player.setInitialY(playerY);
                    playerHP = player.getHealth();
                    playerFireball = new Fireball(playerX, playerY);
                    continue;
                case "COIN":
                    int coinX = Integer.parseInt(row.get(1));
                    int coinY = Integer.parseInt(row.get(2));
                    Coins coin = new Coins(coinX, coinY);
                    coins.add(coin);
                    continue;
                case "ENEMY":
                    int enemyX = Integer.parseInt(row.get(1));
                    int enemyY = Integer.parseInt(row.get(2));
                    Enemy enemy = new Enemy(enemyX, enemyY);
                    enemys.add(enemy);
                    continue;
                case "FLYING_PLATFORM":
                    int flyingX = Integer.parseInt(row.get(1));
                    int flyingY = Integer.parseInt(row.get(2));
                    FlyingPlatform flying = new FlyingPlatform(flyingX, flyingY);
                    flyingPlatforms.add(flying);
                case "END_FLAG":
                    int flagX = Integer.parseInt(row.get(1));
                    int flagY = Integer.parseInt(row.get(2));
                    endFlag = new Flag(flagX, flagY);
                    continue;
                case "ENEMY_BOSS":
                    int bossX = Integer.parseInt(row.get(1));
                    int bossY = Integer.parseInt(row.get(2));
                    boss = new Boss(bossX, bossY);
                    bossHP = boss.getHealth();
                    bossFireball = new Fireball(bossX, bossY);
                    continue;
                case "DOUBLE_SCORE":
                    int doubleX = Integer.parseInt(row.get(1));
                    int doubleY = Integer.parseInt(row.get(2));
                    DoubleScore score = new DoubleScore(doubleX, doubleY);
                    doubleScores.add(score);
                    continue;
                case "INVINCIBLE_POWER":
                    int invincibleX = Integer.parseInt(row.get(1));
                    int invincibleY = Integer.parseInt(row.get(2));
                    Invincible invincible = new Invincible(invincibleX, invincibleY);
                    invincibles.add(invincible);
            }
        }
    }

    // Renders all the entities onto the screen
    public void loadWorld() {

        // Initialises the Score of the game
        String scoreText = message_props.getProperty("score");
        String scoreX = game_props.getProperty("score.x");
        String scoreY = game_props.getProperty("score.y");

        // Initialises the health section of the game
        String healthBar = message_props.getProperty("health");
        String playerHealthX = game_props.getProperty("playerHealth.x");
        String playerHealthY = game_props.getProperty("playerHealth.y");

        // draws out the background, health and score onto the game screen
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        SCORE.drawString(scoreText, Integer.parseInt(scoreX), Integer.parseInt(scoreY));
        SCORE.drawString(scoreNum, Integer.parseInt(scoreX) + 150, Integer.parseInt(scoreY));
        HEALTH.drawString(healthBar, Integer.parseInt(playerHealthX), Integer.parseInt(playerHealthY));
        HEALTH.drawString(playerHP, Integer.parseInt(playerHealthX) + 160, Integer.parseInt(playerHealthY));

        /*
         * Draws out every game entity onto the screen
         * if the game entity exists within the level
         */
        platform.draw();
        endFlag.draw();

        if (boss != null) {
            boss.draw();

            // Obtains the x and y coordinates of the boss health bar
            String bossX = game_props.getProperty("enemyBossHealth.x");
            String bossY = game_props.getProperty("enemyBossHealth.y");

            // Draws out the boss's health onto the screen as well as it's colour
            DrawOptions options = new DrawOptions();
            HEALTH.drawString(healthBar, Integer.parseInt(bossX), Integer.parseInt(bossY), options.setBlendColour(Colour.RED));
            HEALTH.drawString(bossHP, Integer.parseInt(bossX) + 160, Integer.parseInt(bossY), options.setBlendColour(Colour.RED));
        }

        if (flyingPlatforms != null) {
            for (FlyingPlatform platforms : flyingPlatforms) {
                platforms.draw();
            }
        }

        if (invincibles != null) {
            for (Invincible invincible: invincibles) {
                invincible.draw();
            }
        }

        if (coins != null) {
            for (Coins coin: coins) {
                coin.draw();
            }
        }

        if (enemys != null) {
            for (Enemy enemy : enemys) {
                enemy.draw();
            }
        }

        if (doubleScores != null) {
            for (DoubleScore score: doubleScores) {
                score.draw();
            }
        }

        player.draw();

    }

    // initialises the losing screen of the game
    public void losingScreen() {

        // initialises the game losing message and position
        String gameLoss = message_props.getProperty("gameOver");
        String gameY = game_props.getProperty("message.y");

        // Draws the losing screen text onto the window
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        INSTRUCTIONS.drawString(gameLoss, Window.getWidth()/2.0 - INSTRUCTIONS.getWidth(gameLoss)/2.0,Integer.parseInt(gameY));

    }

    // initialises the winning screen
    public void winningScreen() {

        // initialises the game winning message and position
        String gameWin = message_props.getProperty("gameWon");
        String gameY = game_props.getProperty("message.y");

        // draws the winning screen text onto the window
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        INSTRUCTIONS.drawString(gameWin, Window.getWidth()/2.0 - INSTRUCTIONS.getWidth(gameWin)/2.0,Integer.parseInt(gameY));
    }


    // Entire jumping mechanic for the player
    public void jump() {

        // Obtains the players vertical speed and increments it by 1
        // for every frame the player is in the air
        int vertSpeed = player.getVerticalSpeed();
        int y = player.getY() + vertSpeed;
        player.setY(y);
        player.setVerticalSpeed(vertSpeed += 1);

        /*
         * Allows the player to jump up, either onto any platform or
         * from 1 platform to another, as long as the flying platform
         * it's trying to jump to is higher than the current one
         */
        if (isJumping) {
            if (flyingPlatformCollision()) {
                player.setVerticalSpeed(0);
                isJumping = false;
            } else if (player.getY() >= player.getInitialY()) {
                player.setY(player.getInitialY());
                player.setVerticalSpeed(0);
                isJumping = false;
            }
        } else if (isInAir) {
            if (player.getY() >= player.getInitialY()) {
                player.setY(player.getInitialY());
                player.setVerticalSpeed(0);
                isJumping = false;
                isInAir = false;
            }
        }
    }

    // Continuously checks whether the player and
    // boss are within the 500 pixel range
    public void playerBossRange() {

        if (Math.abs(player.getX() - boss.getX()) <= boss.getActivationRadius()) {
            fireballDirection = player.getX() <= boss.getX();

            boss.setInRangeTrue();
        } else {
            boss.setInRangeFalse();
        }
    }

    // Starts the DoubleScore timer once the
    // player collides with the power up
    public void doubleScoreActivate() {

        doubleScoreTimer += 1;

        if (doubleScoreTimer == DOUBLE_SCORE_MAX_FRAMES) {
            doubleScore = false;
        }
    }

    // Starts the Invincible timer once the
    // player collides with the power up
    public void invincibleActivate() {

        invincibleTimer += 1;

        if (invincibleTimer == INVINCIBLE_MAX_FRAMES) {
            invinciblePower = false;
        }
    }

    // method that checks for the player and coin collision
    // and acts if there is a collision
    public void coinCollision() {

        for (Coins coin: coins) {

            // Checks for coin collision and if the coin has
            // already collided with the player or not
            if (COLLISION.entityCollision(player, coin) && !coin.getCollision()) {
                int num = Integer.parseInt(scoreNum);
                if (doubleScore) {
                    num += (COIN_VALUE * 2);
                } else {
                    num += COIN_VALUE;
                }

                scoreNum = String.valueOf(num);
                coin.setCollisionTrue();
            }
        }
    }

    // Method that returns true if the player and flying platform
    // have had some sort of collision and returns false if not
    public boolean flyingPlatformCollision() {

        for (FlyingPlatform platform: flyingPlatforms) {
            if (COLLISION.flyingPlatformCollision(player, platform)) {
                return true;
            }
        }

        return false;
    }

    // Method for checking if player has collided with end flag
    public void flagCollision() {

        // checks for player and flag collision
        if (COLLISION.entityCollision(player, endFlag)) {
            gameWon = true;
        }
    }

    // Checks if player has collided with an enemy
    public void enemyCollision() {

        for (Enemy enemy: enemys) {

            // Checks for enemy collision and if the enemy has
            // already collided with the player or not
            if (COLLISION.entityCollision(player, enemy) && !enemy.getDamageDone() && !invinciblePower) {
                int health = Integer.parseInt(playerHP);
                health = health - enemy.getDamage();
                playerHP = String.valueOf(health);
                enemy.setDamageDoneTrue();
            }
        }
    }

    // Method that checks for player and double score power up collision
    public void doubleScoreCollision() {

        for (DoubleScore score: doubleScores) {

            if (COLLISION.entityCollision(player, score) && !score.getCollision()) {

                // Resets the power up timer if the player has already
                // collected the power up before, else it activates the power up
                if (doubleScore) {
                    doubleScoreTimer = 0;
                } else {
                    doubleScoreTimer = 0;
                    doubleScore = true;
                }

                score.setCollisionTrue();
            }
        }
    }

    // Method that checks for player and invincible power up collision
    public void invincibleCollision() {

        // iterates through each invincible power up entity
        for (Invincible invincible: invincibles) {

            if (COLLISION.entityCollision(player, invincible) && !invincible.getCollision()) {

                // Resets the power up timer if the player has already
                // collected the power up before, else it activates the power up
                if (invinciblePower) {
                    invincibleTimer = 0;
                } else {
                    invincibleTimer = 0;
                    invinciblePower = true;
                }

                invincible.setCollisionTrue();
            }
        }
    }

    // Randomises the boss boolean attribute that
    // allows the boss to shoot a fireball if true
    public void bossRandomizer() {

        randomBossCounter += 1;

        if (randomBossCounter == 100) {
            bossCanFire = RANDOM.nextBoolean();
            randomBossCounter = 0;
        }
    }

    // Detects whether the player's fireball has collided with the boss
    public void playerFireballCollision() {

        if (COLLISION.entityCollision(playerFireball, boss) && playerFired) {

            int health = Integer.parseInt(bossHP);
            health = health - playerFireball.getDamage();
            bossHP = String.valueOf(health);
            playerFired = false;
        } else if (playerFireball.getX() >= Window.getWidth() || playerFireball.getX() <= 0) {

            playerFired = false;
        }

    }

    // Detects if the boss's fireball collides with the player or not
    public void bossFireballCollision() {

        // Reduces the player's health if fireball collides with player
        // else stops fireball altogether if the fireball touches the edge
        if (COLLISION.entityCollision(bossFireball, player)) {

            int health = Integer.parseInt(playerHP);
            health = health - bossFireball.getDamage();
            playerHP = String.valueOf(health);
            bossFired = false;

        } else if (bossFireball.getX() >= Window.getWidth() || bossFireball.getX() <= 0) {

            bossFired = false;
        }
    }

    // Contains the losing mechanisms for when the game is lost
    public void gameLosingMechanism() {

        int y = player.getY();
        player.setY(y += 2);

        if (y >= Window.getHeight()) {
            scoreNum = "0";
            gameLost = true;
        }
    }

    // Contains the losing mechanism for when the boss dies
    public void bossLosing() {
        if (Integer.parseInt(bossHP) <= 0) {
            int y = boss.getY();
            boss.setY(y += 2);

            if (y >= platform.getY()) {
                boss.setIsAliveFalse();
            }
        }
    }


    /*
    * Checks whether the player is at the edge of
    * screen and prevents them from moving
    */
    public void checkEdge() {
        isEdge = platform.getX() == platform.getInitialX();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        // close window
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // goes to title screen when game hasn't started and loads
        // level once player has imported the specific level they want
        if (!gameStart) {

            gameWon = false;
            gameLost = false;
            titleScreen();

            // loads level 1
            if (input.wasPressed(Keys.NUM_1)) {
                levelWorld = new Level().getLevelOne();
                playerUseFireball = false;
                bossCanFire = false;
                loadLevel();
                gameStart = true;
            }

            // loads level 2
            if (input.wasPressed(Keys.NUM_2)) {
                levelWorld = new Level().getLevelTwo();
                playerUseFireball = true;
                bossCanFire = false;
                loadLevel();
                gameStart = true;
            }

            // loads level 3
            if (input.wasPressed(Keys.NUM_3)) {
                levelWorld = new Level().getLevelThree();
                playerUseFireball = true;
                bossCanFire = false;
                loadLevel();
                gameStart = true;
            }

        } else {

            loadWorld();
            checkEdge();

            // Allows the player to move
            if (canMove) {

                // Makes the player jump if key is pressed
                if (input.wasPressed(Keys.UP) || input.wasPressed(Keys.W)) {
                    if (player.getY() == player.getInitialY() || flyingPlatformCollision()) {
                        player.setVerticalSpeed(JUMP_SPEED);
                    }
                    isJumping = true;
                }

                // Applies the jumping mechanism
                if (isJumping) {
                    jump();
                }

                // Allows the player to fall off flying platforms
                if ((!flyingPlatformCollision() || isInAir) && !isJumping) {
                    isInAir = true;
                    jump();
                }

                // Allows the player to shoot fireballs
                if (input.wasPressed(Keys.S) && playerUseFireball && !playerFired) {
                    playerFireball.setX(player.getX());
                    playerFireball.setY(player.getY());
                    playerFired = true;
                }

                // Applies the fireball shooting mechanism
                if (playerFired) {
                    playerFireball.shootFireball(fireballDirection);
                }

            }

            // Ensures that nothing happens outside each level
            if (!gameLost || !gameWon) {

                // stops the level if the player dies
                if (Integer.parseInt(playerHP) <= 0) {
                    canMove = false;
                    gameLosingMechanism();
                }

                // Detects collisions for each entity
                coinCollision();
                enemyCollision();
                flagCollision();

                // Ensures there is a boss in the level before applying methods
                if (boss != null) {
                    boss.update(input);
                    playerBossRange();
                    playerFireballCollision();
                    bossRandomizer();
                    bossFireballCollision();
                    bossLosing();

                    // Carries out the random boss Fireball mechanism if in range of player
                    if (boss.getInRange() && boss.getIsAlive()) {
                        if (bossCanFire && !bossFired) {
                            bossFireball.setX(boss.getX());
                            bossFireball.setY(boss.getY());
                            bossFired = true;

                        }

                        // Applies the fireball shooting mechanism for the boss
                        if (bossFired) {
                            bossFireball.shootFireball(!fireballDirection);
                        }
                    } else {
                        bossFired = false;
                        randomBossCounter = 0;

                    }

                }

                /*
                 * Applies the update methods from each entity class
                 * if the class exists in the level
                 */
                if (platform != null) {
                    for (FlyingPlatform platform : flyingPlatforms) {
                        platform.update(input);
                    }
                    platform.update(input);
                }

                if (enemys != null) {
                    for (Enemy enemy : enemys) {
                        enemy.update(input);
                    }
                }

                if (coins != null) {
                    for (Coins coin : coins) {
                        coin.update(input);
                    }
                }

                if (doubleScores != null) {
                    for (DoubleScore score : doubleScores) {
                        score.update(input);
                    }
                }

                if (invincibles != null) {
                    for (Invincible invincible : invincibles) {
                        invincible.update(input);
                    }
                }

                if (player != null) {
                    player.update(input);
                }

                if (endFlag != null) {
                    endFlag.update(input);
                }

                if (playerUseFireball) {
                    playerFireball.update(input);
                }

                // Applies the doubleScore mechanisms
                doubleScoreCollision();
                doubleScoreActivate();

                // Applies the Invincible power up mechanism
                invincibleCollision();
                invincibleActivate();
            }

            // Game winning mechanic
            if (gameWon) {
                canMove = false;
                winningScreen();

                if (input.wasPressed(Keys.SPACE)) {
                    gameStart = false;
                }
            }

            // Goes to the losingScreen method if the losing conditions are met
            if (gameLost && gameStart) {
                losingScreen();

                if (input.wasPressed(Keys.SPACE)) {
                    gameStart = false;
                }
            }

        }


    }
}
