package poly.edu.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poly.edu.models.entities.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

}
