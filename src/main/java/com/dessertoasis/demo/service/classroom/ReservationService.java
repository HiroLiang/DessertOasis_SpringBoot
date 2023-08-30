package com.dessertoasis.demo.service.classroom;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.order.Reservation;
import com.dessertoasis.demo.model.order.ReservationRepository;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepository rRepo;
	
	public List<Reservation> getByRoomId(Integer roomId, LocalDate startDate, LocalDate endDate) {
		return rRepo.find(roomId, startDate, endDate);
	}
}
