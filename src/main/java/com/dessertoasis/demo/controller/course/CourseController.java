package com.dessertoasis.demo.controller.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseDTO;
import com.dessertoasis.demo.model.course.CourseTeacherDTO;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.service.CourseService;
import com.dessertoasis.demo.service.TeacherService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService cService;
	
	@Autowired
	private TeacherService tService;
	
	//查詢單筆課程(用課程id)
//	@GetMapping("/{id}")
//	public Course findCourseById(@PathVariable Integer id) {
//		Course course = cService.findById(id);
//		return course;
//	}
//	
	@GetMapping("/list")
    public ResponseEntity<List<Course>> Courses() {
        List<Course> courses = cService.findAll();
        return ResponseEntity.ok(courses);
    }
	
	//查詢所有課程
//	@GetMapping("/all")
//	public List<Course> findAllCourse(){
//		List<Course> courses = cService.findAll();
//		return courses;
//	} 
	
	@GetMapping("/{courseId}")
	public ResponseEntity<CourseDTO> getCourseDetails(@PathVariable Integer courseId) {
	    // Logic to retrieve Course and related data
	    Course course = cService.findById(courseId);
//	    List<CourseCtag> courseCtags = courseCtagService.findByCourseId(courseId);
//	    List<CoursePicture> coursePictures = coursePictureService.findByCourseId(courseId);

	    if (course == null) {
	        return ResponseEntity.notFound().build();
	    }

	    // Create a CourseDTO object and set its properties
	    CourseDTO courseDTO = new CourseDTO();
	    courseDTO.setCourseId(course.getId());
	    courseDTO.setTeacherName(course.getTeacher().getTeacherName());
	    courseDTO.setRecipeIntroduction(course.getRecipes().getRecipeIntroduction());
	    courseDTO.setCategoryName(course.getCategory().getCategoryName());
	    courseDTO.setCourseName(course.getCourseName());
	    courseDTO.setCourseIntroduction(course.getCourseIntroduction());
	    courseDTO.setCourseFeature(course.getCourseFeature());
	    courseDTO.setCourseDestination(course.getCourseDestination());
	    courseDTO.setServiceTarget(course.getServiceTarget());
	    courseDTO.setCloseDate(course.getCloseDate());
	    courseDTO.setUpdateDate(course.getUpdateDate());
	    courseDTO.setCoursePlace(course.getCoursePlace());
	    courseDTO.setCourseStatus(course.getCourseStatus());
	    courseDTO.setRemainPlaces(course.getRemainPlaces());
	    courseDTO.setCoursePrice(course.getCoursePrice());
//	    courseDTO.setTag(course.)
	    courseDTO.setCoursePictureList(course.getCoursePictureList());
	    
	    // Set other properties based on your data

	    // Set the lists of related data
//	    courseDTO.setCourseList(courseCtags);
//	    courseDTO.setCoursePictureList(coursePictures);

	    // Return ResponseEntity<CourseDTO> with HTTP status and CourseDTO object
	    return ResponseEntity.ok(courseDTO);
	}
	
	//新增單筆課程
//	@PostMapping("/course/add")
//	public String addCourse(@RequestBody Course course) {
//		cService.insert(course);
//		return "新增課程成功";
//	}
	
	@PostMapping("/add-course")
    public ResponseEntity<String> addCourse(@RequestBody Course course, HttpServletRequest request) {
        // 從請求的Cookie中獲得user 是老師的身分
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
            	// 老師身分，允許添加課程
                if ("userType".equals(cookie.getName()) && "teacher".equals(cookie.getValue())) {
                    // 將課程資料存到資料庫
                	cService.insert(course);
                	System.out.println("新增課程成功");
                    return ResponseEntity.ok("課程已添加");
                }
            }
        }
        // 如果不是老師身份，返回錯誤訊息
        System.out.println("新增失敗");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("您没有權限執行此操作");
    }

	
	//修改單筆課程
//	@Transactional
//	@PutMapping("/course/update/{id}")
//	public String updateCourse(@PathVariable Integer id, @RequestBody Course course) {
//		 Course result = cService.updateById(id, course);
//		 if(result == null) {
//			 return "修改課程失敗";
//		 }
//		return "修改課程成功";
//	}
	
	//刪除單筆課程
	@DeleteMapping("/{id}")
	public String deleteCourseById(@PathVariable Integer id) {
		boolean isDeleted = cService.deleteById(id);
		
		if(isDeleted) {
			return "刪除成功";
		}
		return "刪除失敗";
	}
	
//	@PostMapping("/checkTeacher")
//    @ResponseBody
//	public String checkTeacher(HttpServletRequest request,@RequestBody Course course) {
//		// 獲取請求中的所有Cookie
//        Cookie[] cookies = request.getCookies();
//        
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//            	System.out.println(cookie.getName());
//            	if(cookie.getName().equals("login1")&& cookie.getValue().equals("true")) {
//            		System.out.println(course.getTeacherId());
//            		if(course.getTeacherId()== 2) {
////            			System.out.println(course.getTeacherId());
//            			return "這是 Teacher id";
//            		}
//            		return "這不是 Teacher id";
//            	}
//                // 检查Cookie的名称是否为"teacher"，这里假设教师信息存储在名为"teacher"的Cookie中
//                if ("teacherId".equals(cookie.getName())) {
//                    // 如果找到名为"teacher"的Cookie，編寫逻辑判断教师信息是否存在于Cookie中
//                    // 比如，檢查Cookie的值是否符合教師的條件
//                    String teacherInfo = cookie.getValue();
//                    if (isTeacherId(teacherInfo)) {
//                        return "这是一个教师Cookie";
//                    }
//                }
//            }
//        }return "這不是一个教師Cookie";
//	}
	
//	private boolean isTeacherId(String cookieValue) {
//        // 编写逻辑来判断Cookie中的值是否表示教师
//        // 您可以根据您的需求定义教师信息的格式和判断条件
//        // 这里只是一个示例，您可能需要根据实际情况进行修改
//        return cookieValue != null && cookieValue.contains("teacherId");
//    }
	
//	@GetMapping("/course/withTrName")
//	public List<CourseTeacherDTO> getAllCoursesWithTeacherNames() {
//        return cService.getAllCoursesWithTeacherNames();
//    }
	
//	@GetMapping("/course/withTName")
//	public List<CourseTeacherDTO> getAllCoursesWithTeacherInfo() {
//        return cService.getAllCoursesWithTeacherInfo();
//    }
}
