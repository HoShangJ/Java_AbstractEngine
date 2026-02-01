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

    public void addEntities(Entity e){
        entities.add(e);
    }

    public List<Entity> getEntities(){
        return entities;
    }

    public void updateAll() {
        for (Entity e : entities) {
            e.movementPlayer();
            e.update();
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

    public void movementPlayer(){}
    public void update(){}

}
