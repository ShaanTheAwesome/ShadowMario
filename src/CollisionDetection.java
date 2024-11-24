public class CollisionDetection<T extends GameEntities, U extends GameEntities> {

    // Allows code reusability for collision detections
    // between the player and the respective entity
    public boolean entityCollision(T entity_one, U entity_two) {
        double distance = Math.sqrt(Math.pow((entity_one.getX() - entity_two.getX()), 2) + Math.pow((entity_one.getY() - entity_two.getY()), 2));
        double range = entity_one.getRadius() + entity_two.getRadius();

        if (distance <= range) {
            return true;
        } else {
            return false;
        }
    }

    // Specifically checks for flying platform collision with the player
    // since the requirements for this collision are more specific
    public boolean flyingPlatformCollision(Player player, FlyingPlatform platform) {
        int distanceX = Math.abs((player.getX() - platform.getX()));
        int distanceY = Math.abs((player.getY() - platform.getY()));

        if (distanceX < platform.getHalfLength() && distanceY <= platform.getHalfHeight()) {
            if (distanceY >= (platform.getHalfHeight() - 1)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
}
