package com.dessertoasis.demo.controller.course;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dessertoasis.demo.ImageUploadUtil;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.category.CategoryRepository;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseCmsTable;
import com.dessertoasis.demo.model.course.CourseDTO;
import com.dessertoasis.demo.model.course.CoursePicture;
import com.dessertoasis.demo.model.course.CoursePictureRepository;
import com.dessertoasis.demo.model.course.CourseSearchDTO;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.recipe.RecipeRepository;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.course.CourseService;
import com.dessertoasis.demo.service.course.TeacherService;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/course")
@MultipartConfig
public class CourseController {

	@Autowired
	private CourseService cService;

	@Autowired
	private TeacherService tService;

	@Autowired
	private RecipeRepository rRepo;

	@Autowired
	private CategoryRepository cRepo;

	@Autowired
	private ImageUploadUtil imgUtil;
	
	@Autowired
	private CoursePictureRepository cpRepo;

	// 查詢單筆課程(用課程id)
//	@GetMapping("/{id}")
//	public Course findCourseById(@PathVariable Integer id) {
//		Course course = cService.findById(id);
//		return course;
//	}
//	

	// 列出所有課程
	@GetMapping("/all")
	public ResponseEntity<List<CourseDTO>> findAllCoursesAsDTO() {
		List<Course> courseList = cService.findAll();
		List<CourseDTO> courseDTOList = new ArrayList<>();
		for (Course course : courseList) {
			CourseDTO courseDTOItem = new CourseDTO(course);
			System.out.println(course.getCoursePictureList().get(0).getCourseImgURL()
					+ "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			courseDTOList.add(courseDTOItem);
		}
		return ResponseEntity.ok(courseDTOList);
//		return courseDTOList;
	}

	
	
	@GetMapping("/{courseId}")
	public ResponseEntity<CourseDTO> getCourseDetails(@PathVariable Integer courseId) {
		Course course = cService.findById(courseId);
//	    List<CourseCtag> courseCtags = courseCtagService.findByCourseId(courseId);
//	    List<CoursePicture> coursePictures = coursePictureService.findByCourseId(courseId);

		if (course == null) {
			return ResponseEntity.notFound().build();
		}

		CourseDTO courseDTO = new CourseDTO(course);

////	    courseDTO.setTag(course.)
//	    courseDTO.setCoursePictureList(course.getCoursePictureList());

		return ResponseEntity.ok(courseDTO);
	}

	// 新增單筆課程
	@PostMapping("/add")
	public ResponseEntity<Integer> addCourse(@RequestBody Course course, HttpSession session) {

		Member member = (Member)session.getAttribute("loggedInMember");
//		member.getTeacher().getId();
//		Teacher teacher = tService.getTeacherById(course.getTeacher().getId()); 
		Teacher teacher = tService.getTeacherById(1);
//		Optional<Recipes> recipes = rRepo.findById(course.getRecipes().getId());
		Optional<Recipes> recipes = rRepo.findById(1);
		Recipes recipe = recipes.get();
//		Optional<Category> categorys = cRepo.findById(course.getCategory().getId());
		Optional<Category> categorys = cRepo.findById(2);
		Category category = categorys.get();
//
   if (teacher != null) {

		course.setTeacher(teacher); // 将教师关联到课程中
		course.setRecipes(recipe);
		course.setCategory(category);

		// 將課程資料存到資料庫
		Course savedCourse = cService.insert(course);
		Integer courseId = savedCourse.getId();
		System.out.println(courseId);
		return ResponseEntity.ok(courseId);
   }return ResponseEntity.ok(0);
	}


	// 修改課程
	@PostMapping("/updateCourse")
	public Course updateCourse(@RequestBody Course courseData, HttpSession session) {
		System.out.println(courseData.getId());
		// 身份判斷
//		Member user = (Member) session.getAttribute("loggedInMember");
//		if (user == null || !user.getAccess().equals(MemberAccess.TEACHER) || !user.getId().equals(courseData.getTeacher().getMemberId())) {
//			return null;
//		}

		Course course = cService.updateCourse(courseData);
		if (course != null)
			return course;

		return null;

	}

	@PostMapping("/uploadImage")
	public ResponseEntity<String> uploadImage(@RequestParam("courseId") Integer courseId,
			@RequestParam("image") MultipartFile image) {
		System.err.println("start");
		Course course = cService.findById(courseId);
		try {
			String baseDir = "C:/dessertoasis-vue/public/images/course/";
			String courseDir = baseDir + courseId;
			String thumbnailDir = courseDir + "/thumbnail";

			File courseFolder = new File(courseDir);
			if (!courseFolder.exists()) {
				courseFolder.mkdirs();
			}

			File thumbnailFolder = new File(thumbnailDir);
			if (!thumbnailFolder.exists()) {
				thumbnailFolder.mkdirs();
			}

			String imagePath = courseDir + "/" + image.getOriginalFilename();
			String thumbnailPath = thumbnailDir + "/" + "thumbnail_" + image.getOriginalFilename(); // 修改縮圖路径

			File destination = new File(imagePath);
			image.transferTo(destination);

			// 处理縮圖逻辑，将縮圖存储在thumbnailPath中

			// pService.addImageToProduct(productId, imagePath);
		 	 CoursePicture coursePicture = new CoursePicture();
		 	 coursePicture.setCourse(course);
		 	 coursePicture.setCourseImgURL(imagePath);
		 	 cpRepo.save(coursePicture);
		 	 System.err.println("end");

			return ResponseEntity.ok("Image uploaded successfully.");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
		}
	}

	// 修改單筆課程
	@Transactional
	@PutMapping("/update/{id}")
	public String updateCourse(@PathVariable Integer id, @RequestBody Course course) {
		Course result = cService.updateById(id, course);
		if (result == null) {
			return "修改課程失敗";
		}
		return "修改課程成功";
	}

	// 刪除單筆課程
//	@DeleteMapping("/{courseId}")
//	public String deleteCourseById(@PathVariable Integer courseId,HttpSession session) {
//		System.out.println("session" + session.getAttribute("loggedMamber"));
//		Member member = (Member)session.getAttribute("loggedMember");
//		System.out.println("memberId" +member.getId());
//		if(member != null) {
//		Boolean isDeleted = cService.deleteById(courseId,member.getId());
//		System.out.println(member.getId());
//		if(isDeleted) {
//			return "刪除成功";
//		}
//		return "刪除失敗";}
//		return "不是會員，請登入";
//	}

	// 刪除圖片
	@DeleteMapping("/{courseId}")
	public String deleteCourseById(@PathVariable Integer courseId) {

		Boolean isDeleted = cService.deleteById(courseId);

		if (isDeleted) {
			return "刪除成功";
		}
		return "刪除失敗";
	}

	// 依照老師id列出該教師所有課程
	@GetMapping("/teacher/{teacherId}")
	public ResponseEntity<List<CourseDTO>> getCourseByTeacherId(@PathVariable Integer teacherId) {
//		Member member = (Member) session.getAttribute("loggedInMember");
		List<CourseDTO> courseDTOs = cService.getCoursesByTeacherId(teacherId);
		if (courseDTOs.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(courseDTOs);
		}
	}

	// 新增課程
//	@PostMapping("/addcourse")
//	@ResponseBody
//	public String addCourse (@RequestBody CourseCreateDTO createDTO,HttpSession session) {
//		Member member = (Member) session.getAttribute("loggedInMember");
//		Boolean add = cService.addCourse(4, createDTO);
//		if(add) {
//			return "OK";//新增成功
//		}
//		return "Fail";//新增失敗
//	}

	@PostMapping("/criteria")
	public ResponseEntity<Page<Course>> searchProducts(@RequestBody CourseSearchDTO criteria, Pageable pageable) {

		Page<Course> products = cService.searchCourses(criteria, pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<Course>> searchCourses(CourseSearchDTO criteria,
			@PageableDefault(size = 20) Pageable pageable,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		Sort sort = Sort.unsorted();

		if (sortBy != null && !sortBy.isEmpty()) {
			String[] sortParams = sortBy.split("&");
			for (String param : sortParams) {
				String[] sortField = param.split(",");
				if (sortField.length == 2) {
					String field = sortField[0];
					String direction = sortField[1].toUpperCase(); // Ensure direction is uppercase
					Sort.Order order = "ASC".equals(direction) ? Sort.Order.asc(field) : Sort.Order.desc(field);
					sort = sort.and(Sort.by(order));
				}
			}
		}

		int adjustedPage = pageable.getPageNumber() - 1;
		int effectivePageSize = pageSize != null ? pageSize : 20;

		Page<Course> courses = cService.searchCourses(criteria, PageRequest.of(adjustedPage, effectivePageSize, sort));
		System.out.println(courses.getContent().get(0).getCoursePictureList().get(0).getCourseImgURL());
		return ResponseEntity.ok(courses);
	}

	// ID查詢課程,並輸出圖片
	@GetMapping("/course-desplay")
	public Course getCourseDetail(@RequestParam Integer id) {
		// 取得課程資料
		Course course = cService.findById(id);
		if (course != null) {
			// 處理圖片路徑
			for (int i = 0; i < course.getCoursePictureList().size(); i++) {
				CoursePicture picture = course.getCoursePictureList().get(i);
				String pictureUrl = picture.getCourseImgURL();
				String picUrl = imgUtil.writeImageToString(pictureUrl);
				course.getCoursePictureList().get(i).setCourseImgURL(picUrl);
			}
			return course;
		}
		return null;
	}

	@GetMapping("/base64/image")
	public String getPictureUrlByPath(@RequestParam("path") String path) {
		System.out.println(path);
		if(path!=null) {
			String picUrl = imgUtil.writeImageToString(path);
			if (picUrl != null)
				return picUrl;
			
		}
		return null;
	}

	// 課程分頁查詢
	@PostMapping("/pagenation")
	public List<CourseCmsTable> getCoursePage(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
//		Member user = (Member) session.getAttribute("loggedInMember");
//		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
//			return null;
//		}
		// 送出查詢條件給service，若有結果則回傳list
		List<CourseCmsTable> result = cService.getCoursePagenation(sortCon);
		if (result != null) {
			for (int j = 0; j < result.size(); j++) {
				CourseCmsTable table = result.get(j);
				Course course = cService.findById(table.getId());
				if (course.getCoursePictureList() != null && course.getCoursePictureList().size() > 0) {
					String url = course.getCoursePictureList().get(0).getCourseImgURL();
					table.setCourseImgURL(url);
				}
			}
			System.out.println(result);
			return result;
		}
		return null;
	}

	@PostMapping("/pages")
	public Integer getPages(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
//		Member user = (Member) session.getAttribute("loggedInMember");
//		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
//			return null;
//		}
		// 送出條件查詢總頁數
		Integer pages = cService.getPages(sortCon);
		return pages;
	}

	@PostMapping("/number-range")
	public List<Integer> getNumberRange(@RequestBody SortCondition sortCon, HttpSession session) {
		List<Integer> numberRange = cService.getNumberRange(sortCon);
		if (numberRange != null) {
			return numberRange;
		}
		return null;
	}
}

