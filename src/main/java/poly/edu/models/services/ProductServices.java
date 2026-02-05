package poly.edu.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import poly.edu.models.dao.ProductDAO;
import poly.edu.models.entities.Product;

@Service
@Transactional
public class ProductServices implements ServiceInterface<Product, Integer> {
	
	@Autowired
	private ProductDAO productDAO;
	
	public List<Product> filter(String keyword, String categoryId) {
		
		List<Product> products = productDAO.findAll();
		
		return products.stream().filter(p -> {
			
			if(keyword != null && !keyword.isBlank()) {
				boolean productFound = p.getName() != null && p.getName().toLowerCase().contains(keyword.toLowerCase());
				if (!productFound)
					return false;
			}
			
			if (categoryId != null && !categoryId.isBlank()) {
				boolean categoryIdMatched= p.getCategory().getId() != null && p.getCategory().getId().equals(categoryId);
				if (!categoryIdMatched) {
					return false;
				}
			}
			
			return true;
		}).toList();
	}
	
	public List<Product> filterAndSort(String keyword, String categoryId, String sortBy) {
		List<Product> filtered = filter(keyword, categoryId);
		
		if (sortBy != null && !sortBy.isBlank()) {
			return switch (sortBy) {
				case "price-asc" -> filtered.stream()
					.sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
					.toList();
				case "price-desc" -> filtered.stream()
					.sorted((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()))
					.toList();
				default -> filtered;
			};
		}
		
		return filtered;
	}


	@Override
	public void save(Product entity) {
		// TODO Auto-generated method stub
		if (entity.getId() == null) {
			productDAO.create(entity); 
			return;
		}
		  
		 productDAO.update(entity);
	}


	@Override
	public Product delete(Integer id) {
		// TODO Auto-generated method stub
		return productDAO.delete(id);
	}


	@Override
	public Product findById(Integer id) {
		// TODO Auto-generated method stub
		Product product = productDAO.findById(id);
		  
		if (product == null) 
			throw new RuntimeException("Product not found");
  
		return product;
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return productDAO.findAll();
	}
	
	public List<Product> findPage(int page, int size) {
	    return productDAO.findPage(page, size);
	}

	/*
	 * To calculate how many pages when given size
	 */
	public int getTotalPages(int size) {
	    long totalItems = productDAO.count(); //calculate how many items
	    //then divide it with how many product you want to have in one page and rounded it up(làm tròn đến số lớn)
	    return (int) Math.ceil((double) totalItems / size); 
	}
}
