package com.dessertoasis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService oService;
	
	@GetMapping("/odermanagement/findall")
	public List<Order> findAllOrder() {
		return oService.findAll(); 
	}
}
