package com.dessertoasis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.classroom.Reservation;
import com.dessertoasis.demo.service.ReservationService;

@RestController
public class ReservationController {
	
	@Autowired
	private ReservationService rService;
	
	@GetMapping("/reservation/getinmonth")
	public List<Reservation> getReservationForRoomInMonth(
			@RequestParam("roomId") Integer roomId,
			@RequestParam("year") Integer year,
			@RequestParam("month") Integer month) {
		
		return rService.getReservationForClassroomInMonth(roomId, year, month);
	}
}
