package poly.edu.models.repositories;

import org.springframework.stereotype.Repository;

import poly.edu.models.dao.AbstractDAO;
import poly.edu.models.dao.OrderDetailDAO;
import poly.edu.models.entities.OrderDetail;

@Repository
public class OrderDetailRepository extends AbstractDAO<OrderDetail, Long> implements OrderDetailDAO {

	protected OrderDetailRepository() {
		super(OrderDetail.class);
	}

}
