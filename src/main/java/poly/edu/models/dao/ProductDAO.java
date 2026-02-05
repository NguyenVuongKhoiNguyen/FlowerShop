package poly.edu.models.dao;

import java.util.List;

import poly.edu.models.entities.Product;

public interface ProductDAO extends InterfaceDAO<Product, Integer> {
	List<Product> findPage(int page, int size);
    long count(); //count() always return long
	
}
