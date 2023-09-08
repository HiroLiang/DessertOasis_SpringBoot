package com.dessertoasis.demo.service.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseDTO;
import com.dessertoasis.demo.model.course.CourseRepository;
import com.dessertoasis.demo.model.course.CourseTeacherDTO;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository cRepo;
	
	@Autowired
	private TeacherRepository tRepo;
	
	@Autowired
	private MemberRepository mRepo;
	
	//利用 id 查詢課程
	public Course findById(Integer id) {
		Optional<Course> optional = cRepo.findById(id);
		
		if(optional.isPresent()) {
			Course course = optional.get();
			return course;
		}
		return null;
	}
	
	//查詢全部課程
	public List<Course> findAll(){
		List<Course> result = cRepo.findAll();
		return result; 
	}
	
	//新增單筆課程
	public Course insert(Course course) {
		return cRepo.save(course);
	}
	
	//修改單筆課程
	public Course updateById(Integer id,Course course) {
		Optional<Course> optional = cRepo.findById(id);
		
		if(optional.isPresent()) {
			Course result = optional.get();
			result.setId(id);
			result.setCourseName(course.getCourseName());
			result.setCourseStatus(course.getCourseStatus());
			result.setCourseDate(course.getCourseDate());
			result.setCloseDate(course.getCloseDate());
			result.setCourseIntroduction(course.getCourseIntroduction());
			result.setCoursePlace(course.getCoursePlace());
		}
		return null;
	}

	//刪除單筆課程
//	public Boolean deleteById(Integer courseId,Integer memberId) {
//		Optional<Member> member = mRepo.findById(memberId);
//		Optional<Course> course = cRepo.findById(courseId);
//		
//		if(course.isPresent()) {
//			 Course courseData = course.get();
//			 if(courseData.getTeacher().getMember().getId().equals(member.get().getId())) {
//			 cRepo.deleteById(courseId);
//			return true;
//			}
//			 
//		}
//		return false;
//	}
	
	public Boolean deleteById(Integer courseId) {
		Optional<Course> optional = cRepo.findById(courseId);
		if(optional.isPresent()) {
			cRepo.deleteById(courseId);
			return true;
		}
		return false;
	}
	
	//列出該老師所有課程
	public List<CourseDTO> getCoursesByTeacherId(Integer teacherId){
		List<Course> courses = cRepo.findByTeacherId(teacherId);
		List<CourseDTO> cDTOs = new ArrayList<>();
		for(Course course:courses) {
			CourseDTO cDTO = new CourseDTO();
			cDTO.setCourseName(course.getCourseName());
			cDTO.setCourseDate(course.getCourseDate());
			cDTO.setCloseDate(course.getCloseDate());
			cDTO.setUpdateDate(course.getUpdateDate());
			cDTO.setCourseStatus(course.getCourseStatus());
			cDTO.setCourseIntroduction(course.getCourseIntroduction());
			cDTO.setCourseFeature(course.getCourseFeature());
			cDTO.setCourseDestination(course.getCourseDestination());
			cDTO.setServiceTarget(course.getServiceTarget());
			cDTO.setRemainPlaces(course.getRemainPlaces());
			cDTO.setCoursePlace(course.getCoursePlace());
			cDTO.setRecipeIntroduction(course.getRecipes().getRecipeIntroduction());
			cDTO.setCoursePrice(course.getCoursePrice());
//			cDTO.setCoursePictureList(course.getCoursePictureList());
			cDTOs.add(cDTO);
		}
		return cDTOs;
	}

}
