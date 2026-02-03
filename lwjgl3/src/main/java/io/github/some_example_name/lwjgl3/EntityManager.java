package io.github.some_example_name.lwjgl3;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
    private final List<Entity> entities;

    public EntityManager(){
        entities = new ArrayList<>();
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public List<Entity> getAll(){
        return entities;
    }

    public void updateAll(float dt) {
        for (Entity e : entities) {

            // Player movement (if entity supports it)
            if (e instanceof PlayerMovable) {
                ((PlayerMovable) e).movementPlayer(dt);
            }

            // AI movement (if entity supports it)
            if (e instanceof AIMovable) {
                ((AIMovable) e).movementAI(dt);
            }

            // Update (refresh bounds, internal logic)
            e.update(dt);
        }
    }

    public void drawAll(ShapeRenderer sr, SpriteBatch batch) {

        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity e : entities) {
            e.draw(sr);
        }
        sr.end();

        batch.begin();
        for (Entity e : entities) {
            e.draw(batch);
        }
        batch.end();
    }
}
