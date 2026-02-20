package INF1009_P3_02.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Obstacle extends Entity {

    private float width, height;

    public Obstacle(float x, float y, float width, float height) {
        super(x, y, Color.GRAY, 0);
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public static Obstacle spawnRandom(
        float worldW, float worldH,
        float width, float height
    ) {
        float x = MathUtils.random(0f, worldW - width);
        float y = MathUtils.random(0f, worldH - height);
        return new Obstacle(x, y, width, height);
    }

    @Override
    public void update(float dt) {
        updateBounds();
    }

    @Override
    protected void updateBounds() {
        bounds.set(getX(), getY(), width, height);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(getColor());
        renderer.rect(getX(), getY(), width, height);
    }
}
