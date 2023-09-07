package com.dessertoasis.demo.service.order;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.order.Reservation;
import com.dessertoasis.demo.model.order.ReservationRepository;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepository rRepo;
	
	public List<Reservation> getByRoomIdBetweenDates(Integer roomId, LocalDate startDate, LocalDate endDate) {
		return rRepo.findBetweenDates(roomId, startDate, endDate);
	}
	
	//查詢指定教室某天某時段的預約
	public Reservation getByRoomIdWithDateAndTime(Integer roomId, LocalDate date, String time) {
		return rRepo.findByDateAndTime(roomId, date, time);
	}
	
	public String timeMap(String reservationTime) {
		Map<String, String> map = new HashMap<>();
		map.put("A", "早上");
		map.put("B", "下午");
		map.put("C", "晚上");
		return map.get(reservationTime);
	}
}
