package com.dessertoasis.demo.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository oRepo;
	
	public Order insert(Order order) {
		return oRepo.save(order);
	}
	
}
