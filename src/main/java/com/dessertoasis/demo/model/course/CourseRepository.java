package com.dessertoasis.demo.model.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	List<Course> findByTeacher(Teacher teacher);
	
	@Query("SELECT NEW com.dessertoasis.demo.model.course.CourseTeacherDTO(c.courseName, t.teacherName) FROM Course c JOIN c.teacher t")
	List<CourseTeacherDTO> findAllCoursesWithTeacherNames();
}
