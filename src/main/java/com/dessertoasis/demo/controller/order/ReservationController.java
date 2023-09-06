package com.dessertoasis.demo.controller.order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.order.Reservation;
import com.dessertoasis.demo.service.order.ReservationService;

@RestController
public class ReservationController {
	
	@Autowired
	private ReservationService rService;
	
	@GetMapping("/reservations/{roomId}")
	public List<Reservation> getReservationForRoomInMonth(
			@PathVariable("roomId") Integer roomId,
			@RequestParam("start") String startStr,
			@RequestParam("end") String endStr) {
		
		LocalDate startDate = LocalDate.parse(startStr, DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(endStr, DateTimeFormatter.ISO_DATE);
		
		return rService.getByRoomIdBetweenDates(roomId, startDate, endDate);
	}
}
