package poly.edu.models.repositories;

import org.springframework.stereotype.Repository;

import poly.edu.models.dao.AbstractDAO;
import poly.edu.models.dao.OrderDAO;
import poly.edu.models.entities.Order;

@Repository
public class OrderRepository extends AbstractDAO<Order, Long> implements OrderDAO{

	protected OrderRepository() {
		super(Order.class);
	}
	
 }
