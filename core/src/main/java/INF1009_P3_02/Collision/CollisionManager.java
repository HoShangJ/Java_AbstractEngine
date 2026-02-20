package INF1009_P3_02.Collision;

import INF1009_P3_02.Entity.Bot;
import INF1009_P3_02.Entity.Obstacle;
import INF1009_P3_02.Entity.Player;
import INF1009_P3_02.InputOutput.Speaker;

import java.util.List;

public class CollisionManager {
    // Strategy objects responsible for handling specific collision types.
    private final CollisionHandler playerObstacle = new PlayerObstacleCollision();
    private final CollisionHandler botObstacle    = new BotObstacleCollision();
    private final CollisionHandler playerBot      = new PlayerBotCollision();

    // Tracks the number of valid player–bot collision events.
    private final CollisionCounter collisionCounter = new CollisionCounter();
    // Handles sound effects when collisions occur.
    private final Speaker speaker;

    public CollisionManager(Speaker speaker) {
        this.speaker = speaker;
    }

    public CollisionContext resolve(
        Player player,
        Bot bot,
        List<Obstacle> obstacles,
        float pOldX, float pOldY,
        float bOldX, float bOldY,
        float dt
    ) {
        CollisionContext ctx = new CollisionContext(pOldX, pOldY, bOldX, bOldY, dt); //store old positions and collision flags.

        // Player vs obstacles
        for (Obstacle o : obstacles) {
            playerObstacle.handle(player, o, ctx, speaker);
        }

        // Bot vs obstacles
        for (Obstacle o : obstacles) {
            botObstacle.handle(bot, o, ctx, speaker);
        }

        // Player vs bot
        playerBot.handle(player, bot, ctx, speaker);

        // Count Collision
        collisionCounter.update(ctx, dt);

        return ctx; //return collision results for this frame.
    }

    public int getPlayerBotCollisionCount() {
        return collisionCounter.getCount();
    }
}
