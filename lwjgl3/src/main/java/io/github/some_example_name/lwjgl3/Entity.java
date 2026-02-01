package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity implements iMovable {
    private float x;
    private float y;
    private float speed;
    private Color color;
    protected Rectangle bounds = new Rectangle();

    public Entity(){
        this.x = 0;
        this.y = 0;
        this.speed = 0;
        this.color = null;
    }

    public Entity(float x, float y, Color color, float speed){
        this.x = x;
        this.y = y;
        this.color = color;
        this.speed = speed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    protected abstract void updateBounds();
    public abstract void update();
    public abstract void draw(ShapeRenderer renderer);
    public abstract void draw(SpriteBatch batch);
    public abstract void movementAI();
    public abstract void movementPlayer();
}

class Obstacle extends Entity{
    private float width;
    private float height;

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

    protected void updateBounds() {
        bounds.set(getX(), getY(), width, height);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(getColor());
        renderer.rect(getX(), getY(), width, height);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void movementAI() {

    }

    @Override
    public void movementPlayer() {

    }
}

class Player extends Entity{
    public enum ShapeType { CIRCLE, SQUARE }
    private float worldW, worldH;
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

    @Override
    protected void updateBounds() {
        bounds.set(getX() - size / 2f, getY() - size / 2f, size, size);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(ShapeRenderer renderer) {

    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void movementAI() {

    }

    @Override
    public void movementPlayer() {

    }
}
