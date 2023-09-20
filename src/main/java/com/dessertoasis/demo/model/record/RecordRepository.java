package com.dessertoasis.demo.model.record;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer> {
	
	Integer countByCategoryId(Integer categoryId);

}
