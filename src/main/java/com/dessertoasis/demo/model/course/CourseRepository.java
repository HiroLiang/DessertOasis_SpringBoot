package com.dessertoasis.demo.model.course;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dessertoasis.demo.model.product.Product;

public interface CourseRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

	Page<Course> findAll(Pageable pageable);
	
	public List<Course> findByTeacher(Teacher teacher);
	
	public List<Course> findByTeacherId(Integer teacherid);
	
	Integer countByCourseStatus(String courseStatus);
	
//	@Query("SELECT NEW com.dessertoasis.demo.model.course.CourseTeacherDTO(c.courseName, t.teacherName) FROM Course c JOIN c.teacher t")
//	List<CourseTeacherDTO> findAllCoursesWithTeacherNames();
	
//	 @Query("SELECT NEW com.dessertoasis.demo.model.course.CourseTeacherDTO(c.courseId,c.courseName, t.teacherName, c.courseStatus, c.courseDate, c.regDeadline, c.courseDescription, c.courseLocation, c.courseSortId, c.remainingPlaces, c.coursePrice, c.coursePictureUrl, c.courseVideoId, c.recipeId, c.tagId) FROM Course c JOIN c.teacher t")
//	    List<CourseTeacherDTO> findAllCoursesWithTeacherInfo();
}
