package io.github.some_example_name.lwjgl3;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final List<AbstractEntity> entities;

    public EntityManager(){
        entities = new ArrayList<>();
    }
    public void addEntities(Entity e){
        entities.add(e);
    }

    public List<AbstractEntity> getEntities(){
        return entities;
    }
}
