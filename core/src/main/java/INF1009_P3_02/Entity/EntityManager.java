package INF1009_P3_02.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityManager {
    private Player player;
    private Bot bot;

    private final List<Obstacle> obstacles = new ArrayList<>();
    private final List<Entity> entities = new ArrayList<>();

    public void addEntity(Entity e) {
        entities.add(e);

        if (e instanceof Player) player = (Player) e;
        if (e instanceof Bot) bot = (Bot) e;
        if (e instanceof Obstacle) obstacles.add((Obstacle) e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);

        if (e == player) player = null;
        if (e == bot) bot = null;

        if (e instanceof Obstacle) obstacles.remove(e);
    }

    public List<Entity> getAll() {
        return Collections.unmodifiableList(entities);
    }

    public void updateAll(float dt) {
        for (Entity e : entities) {
            e.update(dt);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Bot getBot() {
        return bot;
    }

    public void updatePlayerPosition(float newX, float newY) {
        if (player != null) {
            player.setX(newX);
            player.setY(newY);
            player.clampToWorld();
        }
    }

    // NEW: mirror of player update, but using Bot bounds/clamp
    public void updateBotPosition(float newX, float newY) {
        if (bot != null) {
            bot.setX(newX);
            bot.setY(newY);
        }
    }

    public List<Obstacle> getObstacles() {
        return Collections.unmodifiableList(obstacles);
    }
}
