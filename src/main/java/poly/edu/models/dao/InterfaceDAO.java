package poly.edu.models.dao;

import java.util.List;

public interface InterfaceDAO<Entity, Key> {

    void create(Entity entity);

    void update(Entity entity);

    Entity delete(Key id);

    Entity findById(Key id);

    List<Entity> findAll();
}
