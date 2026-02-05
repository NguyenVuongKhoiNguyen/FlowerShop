package poly.edu.models.services;

import java.util.List;

public interface ServiceInterface<Entity, Key>  {
	void save(Entity entity);
	
	Entity delete(Key id);
	
	Entity findById(Key id);
	
	List<Entity> findAll();
}
