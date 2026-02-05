package poly.edu.models.repositories;


import java.util.List;

import org.springframework.stereotype.Repository;

import poly.edu.models.dao.AbstractDAO;
import poly.edu.models.dao.ProductDAO;
import poly.edu.models.entities.Product;

@Repository
public class ProductRepository extends AbstractDAO<Product, Integer> implements ProductDAO {

	protected ProductRepository() {
		super(Product.class);
	}

	@Override
	public List<Product> findPage(int page, int size) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT p FROM Product p ORDER BY p.id", Product.class)
                .setFirstResult(page * size)   // page starts from 0
                .setMaxResults(size)
                .getResultList();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                .getSingleResult();
	}

}
