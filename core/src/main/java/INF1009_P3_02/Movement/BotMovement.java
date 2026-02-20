// BotMovement.java
package INF1009_P3_02.Movement;

import com.badlogic.gdx.math.MathUtils;

public class BotMovement {

    private final MovementManager movementManager;

    private float currentX;
    private float currentY;
    private float dirX;
    private float dirY;

    public BotMovement(MovementManager movementManager) {
        this.movementManager = movementManager;
    }

    private void setRandomDirection() {
        float angle = MathUtils.random(0f, 360f);
        dirX = MathUtils.cosDeg(angle);
        dirY = MathUtils.sinDeg(angle);
    }

    public void updateBot(float dt,
                          float startX, float startY,
                          float startDirX, float startDirY,
                          float speed,
                          float worldW, float worldH,
                          float botWidth, float botHeight) {

        currentX = startX;
        currentY = startY;

        if (dirX == 0 && dirY == 0) {
            // Use existing direction from entity if not random
            if (startDirX != 0 || startDirY != 0) {
                dirX = startDirX;
                dirY = startDirY;
            } else {
                setRandomDirection();
            }
        }

        //Calculate new XY position
        currentX += dirX * speed * dt;
        currentY += dirY * speed * dt;

        boolean hitEdge = false;

        //Keeps bot within screen bounds
        if (currentX < 0) {
            currentX = 0;
            hitEdge = true;
        }
        if (currentX + botWidth > worldW) {
            currentX = worldW - botWidth;
            hitEdge = true;
        }
        if (currentY < 0) {
            currentY = 0;
            hitEdge = true;
        }
        if (currentY + botHeight > worldH) {
            currentY = worldH - botHeight;
            hitEdge = true;
        }

        if (hitEdge) {
            setRandomDirection();
        }

        // Send position back to MovementManager -> EntityManager -> Scene
        movementManager.onBotPositionCalculated(currentX, currentY);
    }

    // Triggers when collision is detected
    public void onCollision() {
        setRandomDirection();
    }
}
