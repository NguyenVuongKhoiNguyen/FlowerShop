package poly.edu.models.services;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import poly.edu.models.entities.OrderDetail;

@Service
@Transactional
public class OrderDetailServices implements ServiceInterface<OrderDetail, Long> {

	
	
	@Override
	public void save(OrderDetail entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OrderDetail delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDetail findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDetail> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
