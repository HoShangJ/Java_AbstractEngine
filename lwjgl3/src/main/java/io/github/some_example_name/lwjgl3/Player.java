package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity implements PlayerMovable {

    public enum ShapeType { CIRCLE, SQUARE }

    private ShapeType shape;
    private float size;
    private float worldW, worldH;

    public Player(float x, float y, float speed, float size,
                  ShapeType shape, float worldW, float worldH) {
        super(x, y, Color.RED, speed);
        this.size = size;
        this.shape = shape;
        this.worldW = worldW;
        this.worldH = worldH;
        updateBounds();
    }

    @Override
    public void movementPlayer(float dt) {
        if (Gdx.input.isKeyPressed(Keys.LEFT))  setX(getX() - getSpeed() * dt);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) setX(getX() + getSpeed() * dt);
        if (Gdx.input.isKeyPressed(Keys.UP))    setY(getY() + getSpeed() * dt);
        if (Gdx.input.isKeyPressed(Keys.DOWN))  setY(getY() - getSpeed() * dt);

        // Keep inside screen
        float half = size / 2f;
        setX(MathUtils.clamp(getX(), half, worldW - half));
        setY(MathUtils.clamp(getY(), half, worldH - half));
    }

    public void setWorldSize(float w, float h) {
        this.worldW = w;
        this.worldH = h;
    }

    @Override
    protected void updateBounds() {
        bounds.set(getX() - size / 2f, getY() - size / 2f, size, size);
    }

    @Override
    public void update(float dt) {

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

    @Override
    public void draw(SpriteBatch batch) {

    }
}

