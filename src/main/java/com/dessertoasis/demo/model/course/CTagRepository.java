package com.dessertoasis.demo.model.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CTagRepository extends JpaRepository<CTag,Integer> {
	
	CTag findByCourseTagName(String courseTagName);
}
