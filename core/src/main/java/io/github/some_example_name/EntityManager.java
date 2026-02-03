package io.github.some_example_name;
import io.github.some_example_name.lwjgl3.PlayerMovable;

import java.util.ArrayList;
import java.util.List;

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
            if (e instanceof io.github.some_example_name.AIMovable) {
                ((io.github.some_example_name.AIMovable) e).movementAI(dt);
            }

            // Update (refresh bounds, internal logic)
            e.update(dt);
        }
    }
}
