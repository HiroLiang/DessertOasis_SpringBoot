package com.dessertoasis.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository oRepo;
	
	public List<Order> findAll() {
		return oRepo.findAll();
	}
	
	public Page<Order> findByPage(Integer pageNum, Integer size, String sort, String props) {
		Sort.Direction dir = (sort == "asc")? Sort.Direction.ASC : Sort.Direction.DESC;
		
		PageRequest request = PageRequest.of(pageNum, size, dir, props);
		Page<Order> page = oRepo.findAll(request);
		
		return page;
	}
}
