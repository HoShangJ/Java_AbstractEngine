package io.github.some_example_name.lwjgl3;

import java.util.List;

public class CollisionManager {
    private boolean playerBotWasColliding = false;

    public boolean intersects(Entity a, Entity b) {
        return a.getBounds().overlaps(b.getBounds());
    }

    public int resolve(
        Player player,
        Bot bot,
        List<Obstacle> obstacles,
        float pOldX, float pOldY,
        float bOldX, float bOldY,
        int collisionCount
    ) {
        // 1) Player vs Obstacles -> stop player
        for (Obstacle o : obstacles) {
            if (intersects(player, o)) {
                player.setX(pOldX);
                player.setY(pOldY);
                player.update(0);     // refresh bounds
                break;
            }
        }

        // 2) Bot vs Obstacles -> revert bot + random direction
        for (Obstacle o : obstacles) {
            if (intersects(bot, o)) {
                bot.setX(bOldX);
                bot.setY(bOldY);
                bot.onCollision();    // random direction
                bot.update(0);
                break;
            }
        }

        // 3) Player vs Bot -> prevent overlap + count once per touch
        boolean collidingNow = intersects(player, bot);

        // Count once per touch (edge-triggered)
        if (collidingNow && !playerBotWasColliding) {
            collisionCount++;
        }

        // Prevent pass-through (always resolve while overlapping)
        if (collidingNow) {
            player.setX(pOldX);
            player.setY(pOldY);
            player.update(0);

            bot.setX(bOldX);
            bot.setY(bOldY);
            bot.onCollision();
            bot.update(0);
        }

        playerBotWasColliding = collidingNow;
        return collisionCount;
    }
}
