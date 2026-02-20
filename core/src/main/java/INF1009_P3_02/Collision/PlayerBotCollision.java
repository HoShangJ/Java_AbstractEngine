package INF1009_P3_02.Collision;


import INF1009_P3_02.Entity.Bot;
import INF1009_P3_02.Entity.Entity;
import INF1009_P3_02.Entity.Player;
import INF1009_P3_02.InputOutput.Speaker;


public class PlayerBotCollision implements CollisionHandler {

    @Override
    public void handle(Entity a, Entity b, CollisionContext ctx, Speaker speaker) {
        if (!(a instanceof Player) || !(b instanceof Bot)) return;

        Player player = (Player) a;
        Bot bot = (Bot) b;

        boolean overlapping = CollisionUtil.intersects(player, bot);
        ctx.playerBotOverlappingNow = overlapping;

        if (!overlapping) return;

        // Block player from passing through the bot
        CollisionUtil.revert(player, ctx.pOldX, ctx.pOldY);

        // Nudge player away to avoid sticking on the bot edge
        float dx = player.getX() - bot.getX();
        float dy = player.getY() - bot.getY();
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len > 0f) {
            dx /= len;
            dy /= len;
            float push = Math.max(2f, player.getSpeed() * ctx.dt);
            player.setX(player.getX() + dx * push);
            player.setY(player.getY() + dy * push);
            player.clampToWorld();
            player.update(0);
        }

        // Block bot: revert bot so it bounces like hitting an obstacle
        CollisionUtil.revert(bot, ctx.bOldX, ctx.bOldY);

        // Mark reaction so movement manager flips direction
        ctx.botCollidedWithPlayer = true;
        speaker.playCollisionSound(); //Play collision sound
        System.out.println("CollisionManager: Player collided with bot!");
    }
}
