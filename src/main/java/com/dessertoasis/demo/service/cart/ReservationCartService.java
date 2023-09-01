package com.dessertoasis.demo.service.cart;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.cart.ReservationCart;
import com.dessertoasis.demo.model.cart.ReservationCartRepository;

@Service
public class ReservationCartService {
	
	@Autowired
	private ReservationCartRepository rcRepository;
	
	public ReservationCart findById(Integer id) {
		
		Optional<ReservationCart> optional = rcRepository.findById(id);
		if (optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
}
