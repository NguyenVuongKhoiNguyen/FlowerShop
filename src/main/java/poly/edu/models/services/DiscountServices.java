package poly.edu.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import poly.edu.models.dao.DiscountDAO;
import poly.edu.models.entities.Discount;

@Service
@Transactional
public class DiscountServices implements ServiceInterface<Discount, Integer>{
	
	@Autowired
	private DiscountDAO discountDAO;

	@Override
	public void save(Discount entity) {
		// TODO Auto-generated method stub
		if (entity.getId() == null) {
			discountDAO.create(entity); 
			return;
		}
		  
		 discountDAO.update(entity);
	}

	@Override
	public Discount delete(Integer id) {
		// TODO Auto-generated method stub
		return discountDAO.delete(id);
	}

	@Override
	public Discount findById(Integer id) {
		// TODO Auto-generated method stub
		Discount discount = discountDAO.findById(id);

		if (discount == null) {
			throw new RuntimeException("Category not found");
		}

		return discount;
	}

	@Override
	public List<Discount> findAll() {
		// TODO Auto-generated method stub
		return discountDAO.findAll();
	}
	
	public List<Discount> filter(String keyword, String discountType, Boolean active) {
		
		List<Discount> discounts = discountDAO.findAll();
		
		return discounts.stream().filter(d -> {
			
			if (keyword != null && !keyword.isBlank()) {
				boolean productMatched = d.getProduct() != null 
						&& d.getProduct().getName() != null 
						&& d.getProduct().getName().toLowerCase().contains(keyword.toLowerCase());
				if (!productMatched)
					return false;
			}
			
			if (discountType != null && !discountType.isBlank()) {
				boolean discountTypematched = d.getProduct() != null 
						&& d.getDiscountType() != null && d.getDiscountType().toLowerCase().equals(discountType.toLowerCase());
				if (!discountTypematched) 
					return false;
			}
			
			if(active != null) {
				if(!active.equals(d.getActive()))
					return false;
			}
			
			return true;
		}).toList();
	}
	
}
