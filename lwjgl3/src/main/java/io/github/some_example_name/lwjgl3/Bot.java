package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bot extends Entity implements AIMovable {
    private float worldW, worldH;
    private float width, height;
    private float dirX, dirY;

    public Bot(float x, float y, float speed, float width, float height,
               float worldW, float worldH) {
        super(x, y, Color.GREEN, speed);
        this.width = width;
        this.height = height;
        this.worldW = worldW;
        this.worldH = worldH;
        randomizeDirection();
        updateBounds();
    }

    public void setWorldSize(float w, float h) {
        this.worldW = w;
        this.worldH = h;
    }

    public void randomizeDirection() {
        float angle = MathUtils.random(0f, 360f);
        dirX = MathUtils.cosDeg(angle);
        dirY = MathUtils.sinDeg(angle);
    }

    @Override
    public void movementAI(float dt) {
        setX(getX() + dirX * getSpeed() * dt);
        setY(getY() + dirY * getSpeed() * dt);

        boolean hitEdge = false;

        if (getX() < 0) { setX(0); hitEdge = true; }
        if (getX() + width > worldW) { setX(worldW - width); hitEdge = true; }
        if (getY() < 0) { setY(0); hitEdge = true; }
        if (getY() + height > worldH) { setY(worldH - height); hitEdge = true; }

        if (hitEdge) {
            randomizeDirection();
            // ensure it points inward
            if (getX() == 0 && dirX < 0) dirX *= -1;
            if (getX() + width == worldW && dirX > 0) dirX *= -1;
            if (getY() == 0 && dirY < 0) dirY *= -1;
            if (getY() + height == worldH && dirY > 0) dirY *= -1;
        }
    }

    @Override
    protected void updateBounds() {
        bounds.set(getX(), getY(), width, height);
    }

    @Override
    public void update(float dt) {

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

    @Override
    public void draw(SpriteBatch batch) {

    }
}
