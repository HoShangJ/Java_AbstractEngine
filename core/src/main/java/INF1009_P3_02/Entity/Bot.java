package INF1009_P3_02.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Bot extends Entity {
    private float worldW, worldH;
    private float width, height;
    private float dirX, dirY;

    public Bot(float x, float y, float speed,
               float width, float height,
               float worldW, float worldH) {
        super(x, y, Color.GREEN, speed);
        this.width = width;
        this.height = height;
        this.worldW = worldW;
        this.worldH = worldH;
        updateBounds();
    }

    // Strategy can set direction
    public void setDirection(float dx, float dy) {
        this.dirX = dx;
        this.dirY = dy;
    }

    public float getDirX() { return dirX; }
    public float getDirY() { return dirY; }

    public float getWorldW() { return worldW; }
    public float getWorldH() { return worldH; }
    public float getBotWidth() { return width; }
    public float getBotHeight() { return height; }


    @Override
    protected void updateBounds() {
        bounds.set(getX(), getY(), width, height);
    }

    @Override
    public void update(float dt) {
        updateBounds();
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(getColor());
        renderer.triangle(
            getX(), getY(),
            getX() + width, getY(),
            getX() + width / 2f, getY() + height
        );
    }
}
