package poly.edu.models.repositories;

import org.springframework.stereotype.Repository;

import poly.edu.models.dao.AbstractDAO;
import poly.edu.models.dao.CategoryDAO;
import poly.edu.models.entities.Category;

@Repository
public class CategoryRepository extends AbstractDAO<Category, String> implements CategoryDAO {

	public CategoryRepository() {
		super(Category.class);
	}
}
