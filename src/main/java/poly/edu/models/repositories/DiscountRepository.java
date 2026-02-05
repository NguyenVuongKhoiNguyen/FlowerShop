package poly.edu.models.repositories;

import org.springframework.stereotype.Repository;

import poly.edu.models.dao.AbstractDAO;
import poly.edu.models.dao.DiscountDAO;
import poly.edu.models.entities.Discount;

@Repository
public class DiscountRepository extends AbstractDAO<Discount, Integer> implements DiscountDAO {

	protected DiscountRepository() {
		super(Discount.class);
	}

}
