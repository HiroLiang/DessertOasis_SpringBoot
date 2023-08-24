package com.dessertoasis.demo.model.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	List<Course> findByTeacher(Teacher teacher);
}
