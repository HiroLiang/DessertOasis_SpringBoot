package com.dessertoasis.demo.model.classroom;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
	@Query("from Reservation where roomId = :roomId and reservationDate between :startDate and :endDate order by reservationTime")
	List<Reservation> find(@Param("roomId") Integer roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
}