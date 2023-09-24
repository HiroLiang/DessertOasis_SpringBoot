package com.dessertoasis.demo.controller.course;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dessertoasis.demo.ImageUploadUtil;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseDTO;
import com.dessertoasis.demo.model.course.CourseRepository;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherCmsTable;
import com.dessertoasis.demo.model.course.TeacherDTO;
import com.dessertoasis.demo.model.course.TeacherDemo;

import com.dessertoasis.demo.model.course.TeacherFrontDTO;
import com.dessertoasis.demo.model.course.TeacherPicture;

import com.dessertoasis.demo.model.course.TeacherPictureRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductPicture;
import com.dessertoasis.demo.model.recipe.RecipeFrontDTO;

import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.course.CourseService;
import com.dessertoasis.demo.service.course.TeacherPictureService;
import com.dessertoasis.demo.service.course.TeacherService;
import com.dessertoasis.demo.service.member.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherService tService;
	
	@Autowired
	private TeacherPictureService tpService;
	
	@Autowired
    private TeacherPictureRepository tpRepo;
	
	

	@Autowired
	private MemberService mService;
	
	@Autowired
	private MemberRepository mRepo;

	@Autowired
	private CourseService cService;

	@Autowired
	private CourseRepository cRepo;
	
	@Autowired
	private ImageUploadUtil imgUtil;

	@PostMapping("/getTeacherPage")
	public List<TeacherDemo> getTeacherDatas(@RequestBody SortCondition sortCod) {
		List<TeacherDemo> teacherPage = tService.getTeacherPage(sortCod);

		return teacherPage;
	}

	@GetMapping("/teacher-display")
	public Teacher getTeacherDetail(@RequestParam Integer id)  {
		//取得課程資料
		Teacher teacher = tService.getTeacherById(id);
		return teacher;}


	// 查詢老師個人資料by id
	@GetMapping("/{id}")
	public ResponseEntity<Teacher> getTeacherById(@PathVariable Integer id, HttpSession session) {

		// 判斷 user 存在且為 TEACHER
				Member user = (Member) session.getAttribute("loggedInMember");
//				if (user == null || !user.getAccess().equals(MemberAccess.TEACHER)) {
//					return null;
//				}
				
//				Optional<Member> optional = mRepo.findById(user.getId());
//				Member member = optional.get();
				

		Teacher teacher = tService.getTeacherById(id);
		return ResponseEntity.ok(teacher);
	}
	


	// 列出所有老師
	@GetMapping("/all")
	public ResponseEntity<List<Teacher>> findAllTeachers() {
		List<Teacher> teachers = tService.findAllTeachers();
		return ResponseEntity.ok(teachers);
	}

	// 依照老師編號列出該教師所有課程
	@GetMapping("/{id}/courses")
	public List<CourseDTO> getTeacherCourses(@PathVariable Integer id) {
		Teacher teacher = tService.getTeacherById(id);
		if (teacher != null) {
			return cService.getCoursesByTeacherId(id);
		}
		return Collections.emptyList();
	}

	// admin才可刪除老師
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteTeacher(@PathVariable Integer id, HttpSession session) {
		// 判斷 user 存在且為 ADMIN
//		Member user = (Member) session.getAttribute("loggedInMember");
//		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
//			return null;
//		}
		tService.deleteTeacherById(id);
		return ResponseEntity.ok("已成功刪除");
	}
	
	@PostMapping("/editTeacher")
	public ResponseEntity<Teacher> editTeacher(
		@RequestParam("id")Integer id,@RequestParam("teacherName") String teacherName,
		@RequestParam("teacherTel") Integer teacherTel,
		@RequestParam("email") String email, 
		@RequestParam("teacherProfile") String teacherProfile,
		@RequestParam("teacherContract") String teacherContract,
		@RequestParam("teacherAccountStatus") String teacherAccountStatus){
		
		//判斷會員有無登入) {
//				Member user = (Member)session.getAttribute("loggedInMember");
//				if (user == null || user.getAccess().equals(MemberAccess.ADMIN) || user.getAccess().equals(MemberAccess.ADMIN)) {
//					return null;
//				}
//				System.out.println(user.getId()+"-------------------------");
//				//拿到會員id
//				Member result = mService.findByMemberId(user.getId());
//				System.out.println(result.getAccess()+"------------------");
				Teacher old = tService.getTeacherById(id);
//				Teacher teacher = new Teacher();
				old.setTeacherName(teacherName);
				old.setTeacherTel(teacherTel);
				old.setTeacherMail(email);
				old.setTeacherProfile(teacherProfile);
				old.setTeacherContract(teacherContract);
				old.setTeacherAccountStatus(teacherAccountStatus);

				
//				teacher.setMember(result);
//				 result.setAccess(MemberAccess.TEACHER);
//				 System.out.println(result.getAccess()+"---------------------");

				  // 将更新后的老师对象保存到数据库
				Teacher updatedTeacher = tService.updateTeacher(old);
				return ResponseEntity.ok(updatedTeacher);
	}
	
	
	
	
	
	
	
	
	
	
	// 修改老師
		@PostMapping("/updateTeacher")
		public Teacher updateTeacher(@RequestBody Teacher teacherData, HttpSession session) {

			// 身份判斷
//			Member user = (Member) session.getAttribute("loggedInMember");
//			if (user == null || !user.getAccess().equals(MemberAccess.TEACHER) || !user.getId().equals(courseData.getTeacher().getMemberId())) {
//				return null;
//			}

			Teacher teacher = tService.updateTeacher(teacherData);
			if(teacher!=null)
				return teacher;

			return null;

		}

	@PutMapping("/edit")
	public ResponseEntity<Teacher> editTeacher( @RequestBody Teacher teacher,HttpSession session) {
		
		//判斷會員有無登入
				Member user = (Member)session.getAttribute("loggedInMember");
				if (user == null || user.getAccess().equals(MemberAccess.ADMIN) || user.getAccess().equals(MemberAccess.ADMIN)) {
					return null;
				}
				System.out.println(user.getId()+"-------------------------");
				//拿到會員id
				Member existingMember = mService.findByMemberId(user.getId());
				System.out.println(existingMember.getAccess()+"------------------");
		
		
		Teacher existingTeacher = tService.getTeacherById(existingMember.getId());
		if (existingTeacher  != null) {
			 tService.updateTeacher(existingTeacher);
			return ResponseEntity.ok(teacher);
		} else {
			return ResponseEntity.notFound().build();
		}

	}


//	// 課程分頁查詢
	@PostMapping("/pagenation")
	public List<TeacherCmsTable> getTeacherPage(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
			Member user = (Member) session.getAttribute("loggedInMember");
			if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
				return null;
			}
		// 送出查詢條件給service，若有結果則回傳list
		List<TeacherCmsTable> result = tService.getTeacherPagenation(sortCon);
		if (result != null) {
			System.out.println(result);
			return result;
		}
		return null;
	}

	@PostMapping("/pages")
	public Integer getPages(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
		Member user = (Member) session.getAttribute("loggedInMember");
		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
			return null;
		}
		// 送出條件查詢總頁數
		Integer pages = tService.getPages(sortCon);
		return pages;
	}

	/*----------------------﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀發送base64給前端範例﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀--------------------------*/
	@PostMapping("/getImage")
	@ResponseBody
	public List<String> getPicByGetPicture(@RequestBody Integer id) {
		System.out.println(id);
		Teacher teacherPictures = tService.getTeacherById(id);
		System.out.println(teacherPictures.getPictures().get(0).getPictureURL());
		System.out.println("start");
		if (!teacherPictures.getPictures().isEmpty()) {
			System.out.println("if");
			String userPath = "C:/dessertoasis-vue/public";
//			        String userPath = "C:\\workspace\\dessertoasis-vue\\public";
			// String userPath = "C:\\Users\\iSpan\\Documents\\dessertoasis-vue\\public\\";

			for (TeacherPicture teacherPicture : teacherPictures.getPictures()) {
				String pictureURL = teacherPicture.getPictureURL();

				/*-------------------getPicture方法   第一個參數接收自己的儲存路徑， 第二個參數接收存於資料庫的路徑(範例: images/recipe/1/3584160_20230914005256937.jpg  等等)---------------------*/
				List<String> picture = imgUtil.getPicture(userPath, pictureURL);
				System.out.println("userPath:"+userPath);
				System.out.println("pictureURL:"+pictureURL);
				HttpHeaders headers = new HttpHeaders();
				/*-------------------getPicture回傳值[0]為檔案MIME字串(如image/png 等)  將其設定到 headers中----------------------------*/
				headers.setContentType(MediaType.parseMediaType(picture.get(0)));

				System.out.println("headers"+headers);
				/*-------------------getPicture回傳值[1]為檔案base64字串  將其設定到body中----------------------------*/
				return picture;
			}
		}

		return null;
	}
	/*----------------------﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀發送base64給前端範例﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀--------------------------*/

	//成為老師
	@PostMapping("/uploadImage")
	public ResponseEntity<String> uploadImage(@RequestParam("teacherName") String teacherName,
			@RequestParam("pictureURL") MultipartFile image, @RequestParam("teacherTel") Integer teacherTel,
			@RequestParam("email") String email, @RequestParam("teacherProfile") String teacherProfile,
			@RequestParam("teacherContract")String teacherContract,
			@RequestParam("teacherAccountStatus")String teacherAccountStatus,HttpSession session) {
		
		//判斷會員有無登入
		Member user = (Member)session.getAttribute("loggedInMember");
		if (user == null || user.getAccess().equals(MemberAccess.ADMIN) || user.getAccess().equals(MemberAccess.ADMIN)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("未登入或非教師身分");
		}
		System.out.println(user.getId()+"-------------------------");
		//拿到會員id
		Member result = mService.findByMemberId(user.getId());
		System.out.println(result.getAccess()+"------------------");
		
		Teacher teacher = new Teacher();
		teacher.setTeacherName(teacherName);
		teacher.setTeacherTel(teacherTel);
		teacher.setTeacherMail(email);
		teacher.setTeacherProfile(teacherProfile);
		teacher.setTeacherContract(teacherContract);
		teacher.setTeacherAccountStatus(teacherAccountStatus);

		
		teacher.setMember(result);
		 result.setAccess(MemberAccess.TEACHER);
		 System.out.println(result.getAccess()+"---------------------");

		Teacher save = tService.addTeacher(teacher);
		Integer teacherId = save.getId();

		try {
			String uploadDir = "C:/dessertoasis-vue/public/images/teacher/" + teacherId;
			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String imagePath = uploadDir + "/" + image.getOriginalFilename();
			String sqlPath =  "/" + "images/teacher/" + teacherId + "/" + image.getOriginalFilename();
			File destination = new File(imagePath);
			image.transferTo(destination);
			System.out.println(imagePath);
			TeacherPicture teacherPicture = new TeacherPicture();
			teacherPicture.setTeacher(save);
			teacherPicture.setPictureURL(sqlPath);
			tpRepo.save(teacherPicture);
//			tService.addImageToTeacher(1, sqlPath);

			return ResponseEntity.ok("Image uploaded successfully.");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
		}
	}
	
	/*----------------------------------------------前台資料查詢Controller------------------------------------------------------------------*/

	@PostMapping("/teacherFrontPagenation")
	public List<TeacherFrontDTO> getFrontTeacherPage(@RequestBody SortCondition sortCon, HttpSession session) {
		System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
		Member user = (Member) session.getAttribute("loggedInMember");
//		if (user == null || (!user.getAccess().equals(MemberAccess.ADMIN) && !user.getAccess().equals(MemberAccess.TEACHER))) {
//			return null;
//		}
		
	
		Integer memberId = user.getId();
		System.out.println("memberId:" + memberId);
		
//		
//		Teacher teacher = tService.getTeacherById(memberId);
//		System.out.println("teacherId:" + teacher.getId());
		
//		List<CourseDTO> courseDTOList = cService.getCoursesByTeacherId(teacher.getId());
		
		
//		List<Course> courses = cRepo.findByTeacherId(memberId);
		
		// 送出查詢條件給service，若有結果則回傳list
		List<TeacherFrontDTO> result = tService.getFrontTeacherPagenation(sortCon);
		if (result != null) {
			System.err.println(result.size());
			return result;
		}
		return null;
	}

	 public List<Course> getCoursesByTeacherId(Integer teacherId) {
	        // 使用數據訪問層（Repository）查詢該教師開設的所有課程
	        return cRepo.findByTeacherId(teacherId);
	    }
}
