package com.dessertoasis.demo.model.course;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

	Optional<Teacher> findById(Integer teacherId);
	
	Teacher findByMemberId(Integer memberId);
}
