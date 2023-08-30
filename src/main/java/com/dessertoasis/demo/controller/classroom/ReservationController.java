package com.dessertoasis.demo.controller.classroom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.classroom.Reservation;
import com.dessertoasis.demo.service.classroom.ReservationService;

@RestController
public class ReservationController {
	
	@Autowired
	private ReservationService rService;
	
	@GetMapping("/reservation/getForRoom")
	public List<Reservation> getReservationForRoomInMonth(
			@RequestParam("room") Integer roomId,
			@RequestParam("start") String startStr,
			@RequestParam("end") String endStr) {
		
		LocalDate startDate = LocalDate.parse(startStr, DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(endStr, DateTimeFormatter.ISO_DATE);
		
		return rService.getByRoomId(roomId, startDate, endDate);
	}
}
