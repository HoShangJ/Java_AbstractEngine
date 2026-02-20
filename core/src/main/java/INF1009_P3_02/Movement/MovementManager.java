// MovementManager.java
package INF1009_P3_02.Movement;

import INF1009_P3_02.Entity.Bot;
import INF1009_P3_02.Entity.EntityManager;
import INF1009_P3_02.Entity.Player;

public class MovementManager {

    private final PlayerMovement playerMovement;
    private final BotMovement botMovement;
    private final EntityManager entityManager;

    public MovementManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.playerMovement = new PlayerMovement(this);
        this.botMovement = new BotMovement(this);
    }

    public void update(float dt) {
        Player player = entityManager.getPlayer();
        if (player != null) {
            playerMovement.setWorldBounds(player.getWorldW(), player.getWorldH(), player.getSize());
            playerMovement.update(dt);
        }

        Bot bot = entityManager.getBot();
        if (bot != null) {
            botMovement.updateBot(dt, bot.getX(), bot.getY(),
                bot.getDirX(), bot.getDirY(),
                bot.getSpeed(),
                bot.getWorldW(), bot.getWorldH(),
                bot.getBotWidth(), bot.getBotHeight());
        }
    }


    //Sends received key input to playerMovement to calculate
    public void onKeyInput(char key, float dt) {
        playerMovement.onKeyInput(key, dt);
    }

    //Sends the new XY player position to entityManager
    public void onPositionCalculated(float newX, float newY) {
        // System.out.println("MovementManager: Received player position (" + newX + ", " + newY + ")");
        if (entityManager != null) {
            entityManager.updatePlayerPosition(newX, newY);
        }
    }

    //Sends the new XY bot position to entityManager
    public void onBotPositionCalculated(float newX, float newY) {
        // System.out.println("MovementManager: Received bot position (" + newX + ", " + newY + ")");
        if (entityManager != null) {
            entityManager.updateBotPosition(newX, newY);
        }
    }

    //Calls botMovement to set random direction when collision is detected
    public void handleCollision(EntityManager em) {
        Bot bot = em.getBot();
        if (bot != null) {
            botMovement.onCollision();
        }
    }

    //Synchronises X Y position of player
    public void syncPlayerMovementFromEntity() {
        Player p = entityManager.getPlayer();
        if (p != null) {
            playerMovement.syncFromEntity(p.getX(), p.getY());
        }
    }
}
