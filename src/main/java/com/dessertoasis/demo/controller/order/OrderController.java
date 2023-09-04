package com.dessertoasis.demo.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.service.order.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService oService;
	
	
}
