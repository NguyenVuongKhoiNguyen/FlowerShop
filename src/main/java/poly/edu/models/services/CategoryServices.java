package poly.edu.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import poly.edu.models.dao.CategoryDAO;
import poly.edu.models.entities.Category;

@Service
@Transactional
public class CategoryServices implements ServiceInterface<Category, String> {

	@Autowired
	private CategoryDAO categoryDAO;

	@Override
	public void save(Category entity) {
		// TODO Auto-generated method stub
		Category found = categoryDAO.findById(entity.getId());

		if (found != null) {
			categoryDAO.update(entity);
			return;
		}

		categoryDAO.create(entity);
	}

	@Override
	public Category delete(String id) {
		// TODO Auto-generated method stub
		return categoryDAO.delete(id);
	}

	@Override
	public Category findById(String id) {
		// TODO Auto-generated method stub
		Category category = categoryDAO.findById(id);

		if (category == null) {
			throw new RuntimeException("Category not found");
		}

		return category;
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return categoryDAO.findAll();
	}

	public List<Category> filter(String keyword) {
		// TODO Auto-generated method stub
		List<Category> categories = categoryDAO.findAll();

		return categories.stream().filter(c -> {
			if (keyword != null && !keyword.isBlank()) {
				boolean keywordMatched = c.getName() != null && c.getName().toLowerCase().trim().contains(keyword.toLowerCase().trim());
				if (!keywordMatched) {
					return false;
				}
			}
			return true;
		}).toList();
	}




}
