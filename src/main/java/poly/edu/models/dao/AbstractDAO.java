package poly.edu.models.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public abstract class AbstractDAO<Entity, Key> implements InterfaceDAO<Entity, Key> {

    private final Class<Entity> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;
    
    protected AbstractDAO(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }
    
    @Override
    @Transactional
    public void create(Entity entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void update(Entity entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public Entity delete(Key id) {
        Entity entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
            return entity;
        }
        return null;
    }

    @Override
    public Entity findById(Key id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<Entity> findAll() {
        return entityManager
                .createQuery(
                    "FROM " + entityClass.getSimpleName(),
                    entityClass
                )
                .getResultList();
    }
    
    
	/*
	 * AbstractDAO implement InterfaceDAO
	 * AccountDAO implement InterfaceDAO
	 * AccountRepository extents AbstractDAO implement AccountDAO
	 */
    }

