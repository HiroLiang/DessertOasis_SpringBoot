package com.dessertoasis.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.classroom.Reservation;
import com.dessertoasis.demo.model.classroom.ReservationRepository;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepository rRepo;
	
	public List<Reservation> getByRoomId(Integer roomId, LocalDate startDate, LocalDate endDate) {
		return rRepo.findByRoomIdAndReservationDateBetween(roomId, startDate, endDate);
	}
}
