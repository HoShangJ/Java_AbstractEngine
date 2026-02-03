package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
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

    public Color getColor() {
        return color;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public void draw(SpriteBatch batch) {

    }
    public abstract void draw(ShapeRenderer renderer);
    public Rectangle getBounds() {
        return bounds;
    }
    protected abstract void updateBounds();
    public abstract void update(float dt);
}
