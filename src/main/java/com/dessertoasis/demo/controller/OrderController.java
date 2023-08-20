package com.dessertoasis.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService oService;
	
	@GetMapping("/order/findall")
	public List<Order> findAllOrder() {
		return oService.findAll(); 
	}
	
	@GetMapping("/order/find")
	public Map<String, Object> find(@RequestParam("page") Integer pageNum,
			@RequestParam("sort") String sort) {
		
		Integer size = 8;
		String porps = "ordDate";
		
		Page<Order> page = oService.findByPage(pageNum, size, sort, porps);
		Map<String, Object> res = new HashMap<>();
		res.put("totalPages", page.getTotalPages());
		res.put("list", page.getContent());
		
		return res;
	}
}
