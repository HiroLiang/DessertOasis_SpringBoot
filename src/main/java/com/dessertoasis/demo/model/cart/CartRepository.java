package com.dessertoasis.demo.model.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	public Cart findByCategoryIdAndInterestedId(Integer categoryId, Integer interestedId);
}
