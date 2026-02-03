package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bot extends Entity implements io.github.some_example_name.AIMovable {
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
        setRandomDirection();
        updateBounds();
    }

    private void setRandomDirection() {
        float angle = MathUtils.random(0f, 360f);
        dirX = MathUtils.cosDeg(angle);
        dirY = MathUtils.sinDeg(angle);
    }


    public void onCollision() {
        setRandomDirection();
    }

    @Override
    public void movementAI(float dt) {
        setX(getX() + dirX * getSpeed() * dt);
        setY(getY() + dirY * getSpeed() * dt);

        boolean hitEdge = false;

        // Left edge
        if (getX() < 0) {
            setX(0);
            hitEdge = true;
        }

        // Right edge ✅ (must use + width)
        if (getX() + width > worldW) {
            setX(worldW - width);
            hitEdge = true;
        }

        // Bottom edge
        if (getY() < 0) {
            setY(0);
            hitEdge = true;
        }

        // Top edge ✅ (must use + height)
        if (getY() + height > worldH) {
            setY(worldH - height);
            hitEdge = true;
        }
        if (hitEdge) onCollision();
    }

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
