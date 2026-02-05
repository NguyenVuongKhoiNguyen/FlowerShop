package poly.edu.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import poly.edu.models.dao.OrderDAO;
import poly.edu.models.dao.ProductDAO;
import poly.edu.models.entities.Order;
import poly.edu.models.entities.OrderDetail;
import poly.edu.models.entities.Product;

@Service
@Transactional
public class OrderServices {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	public void save(Order entity) {
		orderDAO.create(entity);
	}
	
	public Order addOrderDetailToOrder(Order order, Integer productId, Integer quantity) {
		
		Product product = productDAO.findById(productId);
		
		// Check if product has enough stock
		if (product.getAmount() < quantity) {
			throw new RuntimeException("Insufficient stock for product: " + product.getName());
		}

        // Check if product already in cart
        for (OrderDetail item : order.getOrderDetails()) {
            if (item.getProduct().getId().equals(productId)) {
            	int newQuantity = item.getQuantity() + quantity;
            	// Check if total quantity exceeds stock
            	if (product.getAmount() < newQuantity) {
            		throw new RuntimeException("Insufficient stock for product: " + product.getName());
            	}
                item.setQuantity(newQuantity);
                return order;
            }
        }
        
        // New cart
        Order newOrder = new Order();
        
        // New item
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setQuantity(quantity);
        
        //Add cart into item
        orderDetail.setOrder(newOrder);
        
        //Add item into cart
        order.getOrderDetails().add(orderDetail);
        		
		return order;
	}
	
	/**
	 * Validate if all products in the order have sufficient stock
	 * @param order The order to validate
	 * @return true if all products have sufficient stock, false otherwise
	 */
	public boolean validateStock(Order order) {
		for (OrderDetail od : order.getOrderDetails()) {
			Product product = productDAO.findById(od.getProduct().getId());
			if (product.getAmount() < od.getQuantity()) {
				return false;
			}
		}
		return true;
	}
}
