package com.dessertoasis.demo.model.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

	Teacher findByTeacherId(Integer teacherId);
}
