package INF1009_P3_02.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends Entity {
    private final float worldW, worldH;
    private final ShapeType shape;
    private final float size;

    public Player(float x, float y, float speed, float size,
                  ShapeType shape, float worldW, float worldH) {
        super(x, y, Color.RED, speed);
        this.size = size;
        this.shape = shape;
        this.worldW = worldW;
        this.worldH = worldH;
        updateBounds();
    }

    public float getWorldW() { return worldW; }
    public float getWorldH() { return worldH; }

    // Keep clamp in Player (it's a Player/world rule)
    public void clampToWorld() {
        float half = size / 2f;
        setX(Math.max(half, Math.min(getX(), worldW - half)));
        setY(Math.max(half, Math.min(getY(), worldH - half)));
    }

    public float getSize() { return size; }

    @Override
    protected void updateBounds() {
        bounds.set(getX() - size / 2f, getY() - size / 2f, size, size);
    }

    @Override
    public void update(float dt) {
        updateBounds();
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(getColor());
        if (shape == ShapeType.CIRCLE) {
            renderer.circle(getX(), getY(), size / 2f);
        } else {
            renderer.rect(getX() - size / 2f, getY() - size / 2f, size, size);
        }
    }
}
