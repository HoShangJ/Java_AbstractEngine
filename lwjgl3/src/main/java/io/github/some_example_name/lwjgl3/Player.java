package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity implements PlayerMovable {

    private float worldW, worldH;
    private final ShapeType shape;
    private final float size;
    private ControlScheme controlScheme;

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
        switch (controlScheme){
            case WASD:
                if (Gdx.input.isKeyPressed(Input.Keys.A)) setX(getX() - getSpeed() * dt);
                if (Gdx.input.isKeyPressed(Input.Keys.D)) setX(getX() + getSpeed() * dt);
                if (Gdx.input.isKeyPressed(Input.Keys.W)) setY(getY() + getSpeed() * dt);
                if (Gdx.input.isKeyPressed(Input.Keys.S)) setY(getY() - getSpeed() * dt);
                break;

            case ARROW_KEYS:
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  setX(getX() - getSpeed() * dt);
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) setX(getX() + getSpeed() * dt);
                if (Gdx.input.isKeyPressed(Input.Keys.UP))    setY(getY() + getSpeed() * dt);
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  setY(getY() - getSpeed() * dt);
                break;
        }
        // ✅ Clamp using hitbox size
        float half = size / 2f;
        setX(Math.max(half, Math.min(getX(), worldW - half)));
        setY(Math.max(half, Math.min(getY(), worldH - half)));
    }

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

