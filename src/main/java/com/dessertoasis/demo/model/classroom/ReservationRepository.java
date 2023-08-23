package com.dessertoasis.demo.model.classroom;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
	List<Reservation> findByRoomIdAndReservationDateBetween(Integer roomId, LocalDate startDate, LocalDate endDate);
	
}