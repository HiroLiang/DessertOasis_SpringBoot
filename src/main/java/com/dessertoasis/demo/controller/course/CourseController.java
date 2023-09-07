package com.dessertoasis.demo.controller.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.category.CategoryRepository;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseDTO;
import com.dessertoasis.demo.model.course.CourseTeacherDTO;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.recipe.RecipeDTO;
import com.dessertoasis.demo.model.recipe.RecipeRepository;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.service.course.CourseService;
import com.dessertoasis.demo.service.course.TeacherService;
import com.dessertoasis.demo.service.recipe.RecipeService;

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
	
	@Autowired
	private RecipeRepository rRepo;
	
	@Autowired
	private CategoryRepository cRepo;
	
	//查詢單筆課程(用課程id)
//	@GetMapping("/{id}")
//	public Course findCourseById(@PathVariable Integer id) {
//		Course course = cService.findById(id);
//		return course;
//	}
//	

	
	//列出所有課程
	@GetMapping("/all")
	public ResponseEntity<List<CourseDTO>> findAllCoursesAsDTO(){
		List<Course> courseList = cService.findAll();
		List<CourseDTO> courseDTOList = new ArrayList<>();
		for(Course course: courseList) {
			CourseDTO courseDTOItem = new CourseDTO(course);
			courseDTOList.add(courseDTOItem);
		}
		return ResponseEntity.ok(courseDTOList);
//		return courseDTOList;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CourseDTO> getCourseDetails(@PathVariable Integer id) {
	    // Logic to retrieve Course and related data
	    Course course = cService.findById(id);
//	    List<CourseCtag> courseCtags = courseCtagService.findByCourseId(courseId);
//	    List<CoursePicture> coursePictures = coursePictureService.findByCourseId(courseId);

	    if (course == null) {
	        return ResponseEntity.notFound().build();
	    }

	    // Create a CourseDTO object and set its properties
	    CourseDTO courseDTO = new CourseDTO(course);

////	    courseDTO.setTag(course.)
//	    courseDTO.setCoursePictureList(course.getCoursePictureList());
	    
	    // Set other properties based on your data


	    // Return ResponseEntity<CourseDTO> with HTTP status and CourseDTO object
	    return ResponseEntity.ok(courseDTO);
	}
	
	//新增單筆課程
	@PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course, HttpServletRequest request) {
        // 從請求的Cookie中獲得user 是老師的身分
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
            	// 老師身分，允許添加課程
                if ("userType".equals(cookie.getName()) && "teacher".equals(cookie.getValue())) {
                	// 假设你使用Spring Data JPA进行数据访问
                	Teacher teacher = tService.getTeacherById(2); // 获取ID为3的教师
                	 Optional<Recipes> recipes = rRepo.findById(1);
                	  Recipes recipe = recipes.get();
                	  Optional<Category> categorys = cRepo.findById(2);
                	  Category category = categorys.get();

                	if (teacher != null) {
                	    
                	    course.setTeacher(teacher); // 将教师关联到课程中
                	    course.setRecipes(recipe);
                	    course.setCategory(category);
                	
                	// 將課程資料存到資料庫
                	    Course savedCourse = cService.insert(course);
                	System.out.println("新增課程成功");
                    return ResponseEntity.ok("課程已添加");}
                	else {
                		// 处理教师不存在的情况	
                	}
                }
            }
        }
        // 如果不是老師身份，返回錯誤訊息
        System.out.println("新增失敗");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("您没有權限執行此操作");
    }

	
	//修改單筆課程
	@Transactional
	@PutMapping("/update/{id}")
	public String updateCourse(@PathVariable Integer id, @RequestBody Course course) {
		 Course result = cService.updateById(id, course);
		 if(result == null) {
			 return "修改課程失敗";
		 }
		return "修改課程成功";
	}
	
	//刪除單筆課程
	@DeleteMapping("/{id}")
	public String deleteCourseById(@PathVariable Integer id) {
		boolean isDeleted = cService.deleteById(id);
		
		if(isDeleted) {
			return "刪除成功";
		}
		return "刪除失敗";
	}
	
	

}
