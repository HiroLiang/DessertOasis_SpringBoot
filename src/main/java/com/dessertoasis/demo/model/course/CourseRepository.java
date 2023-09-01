package com.dessertoasis.demo.model.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {

//	public List<Course> findByTeacher(Teacher teacher);
	
//	@Query("SELECT NEW com.dessertoasis.demo.model.course.CourseTeacherDTO(c.courseName, t.teacherName) FROM Course c JOIN c.teacher t")
//	List<CourseTeacherDTO> findAllCoursesWithTeacherNames();
	
//	 @Query("SELECT NEW com.dessertoasis.demo.model.course.CourseTeacherDTO(c.courseId,c.courseName, t.teacherName, c.courseStatus, c.courseDate, c.regDeadline, c.courseDescription, c.courseLocation, c.courseSortId, c.remainingPlaces, c.coursePrice, c.coursePictureUrl, c.courseVideoId, c.recipeId, c.tagId) FROM Course c JOIN c.teacher t")
//	    List<CourseTeacherDTO> findAllCoursesWithTeacherInfo();
}
