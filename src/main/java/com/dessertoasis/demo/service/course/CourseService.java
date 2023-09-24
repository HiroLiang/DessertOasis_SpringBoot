package com.dessertoasis.demo.service.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.ImageUploadUtil;
import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.course.CTag;
import com.dessertoasis.demo.model.course.CTagRepository;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseCmsTable;
import com.dessertoasis.demo.model.course.CourseCtag;
import com.dessertoasis.demo.model.course.CourseCtagRepository;
import com.dessertoasis.demo.model.course.CourseDTO;
import com.dessertoasis.demo.model.course.CoursePicture;
import com.dessertoasis.demo.model.course.CoursePictureRepository;
import com.dessertoasis.demo.model.course.CourseRepository;
import com.dessertoasis.demo.model.course.CourseSearchDTO;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;
import com.dessertoasis.demo.service.PageSortService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Service
public class CourseService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private CourseRepository cRepo;

	@Autowired
	private CTagRepository cTagRepo;

	@Autowired
	private CoursePictureRepository cpRepo;

	@Autowired
	private CourseCtagRepository ccRepo;

	@Autowired
	private ImageUploadUtil imgUtil;

	@Autowired
	private TeacherRepository tRepo;

	@Autowired
	private MemberRepository mRepo;

	@Autowired
	private PageSortService pService;

	// 利用 id 查詢課程
	public Course findById(Integer id) {
		Optional<Course> optional = cRepo.findById(id);

		if (optional.isPresent()) {
			Course course = optional.get();
			return course;
		}
		return null;
	}

	// 查詢全部課程
	public List<Course> findAll() {
		List<Course> result = cRepo.findAll();
		return result;
	}

	// 新增單筆課程
	public Course insert(Course course) {
		return cRepo.save(course);
	}

	// 新增課程
//	public Boolean addCourse(Integer id,CourseCreateDTO cDTO) {
//		Optional<Member> optional = mRepo.findById(id);
//		if(optional.isPresent()) {
//			
//		}
//	}

	// 更新課程
	@Transactional
	public Course updateCourse(Course course) {
		Optional<Course> byId = cRepo.findById(course.getId());
		Course old = byId.get();
		List<CourseCtag> oldList = old.getCourseList();

		for (int i = 0; i < course.getCourseList().size(); i++) {
			CourseCtag cctag = course.getCourseList().get(i);
			if (cctag.getId() == null) {
				CTag tag = cTagRepo.findByCourseTagName(cctag.getCtag().getCourseTagName());
				if (tag != null) {
					course.getCourseList().get(i).setCtag(tag);
					course.getCourseList().get(i).setCourse(course);
					CourseCtag courseCtag = course.getCourseList().get(i);
					ccRepo.save(courseCtag);
				} else {
					CTag cTag = cTagRepo.save(cctag.getCtag());
					course.getCourseList().get(i).setCtag(cTag);
					course.getCourseList().get(i).setCourse(course);
					CourseCtag courseCtag = course.getCourseList().get(i);
					ccRepo.save(courseCtag);
				}
			}
		}
		for (int i = 0; i < oldList.size(); i++) {
			boolean dele = true;
			for (int j = 0; j < course.getCourseList().size(); j++) {
				if (oldList.get(i).getId() == course.getCourseList().get(j).getId()) {
					dele = false;
				}
			}
			if (dele) {
				ccRepo.deleteById(oldList.get(i).getId());
			}
		}

		List<CoursePicture> pictureList = course.getCoursePictureList();
		for (int i = 0; i < pictureList.size(); i++) {
			CoursePicture coursePicture = pictureList.get(i);
			if (coursePicture.getId() == null) {
				Date date = new Date();
				long time = date.getTime();
//				String path = "/Users/apple/Documents/PDF";
				String path = "C:/DessertOasis_Img/Course";
				String img = coursePicture.getCourseImgURL();
				String[] split = img.split(",");
				System.out.println("save picture :" + img);
				// 取得副檔名
				String extension = "";
				int indexOfSemicolon = img.indexOf(";");
				int indexOfSlash = img.indexOf("/");
				if (indexOfSemicolon != -1 && indexOfSlash != -1 && indexOfSlash < indexOfSemicolon) {
					extension = time + "." + img.substring(indexOfSlash + 1, indexOfSemicolon);
				}
				String saveName = imgUtil.saveImageToFolder(path, split[1], extension);
				coursePicture.setCourseImgURL(saveName);
				coursePicture.setCourse(course);
				cpRepo.save(coursePicture);
				pictureList.set(i, coursePicture);
			} else {
				Optional<CoursePicture> oldP = cpRepo.findById(coursePicture.getId());
				CoursePicture oldPic = oldP.get();
				coursePicture.setCourseImgURL(oldPic.getCourseImgURL());
			}
		}
		List<CoursePicture> oldPictures = old.getCoursePictureList();

		for (int i = 0; i < oldPictures.size(); i++) {
			boolean dele = true;
			for (int j = 0; j < course.getCoursePictureList().size(); j++) {
				if (oldPictures.get(i).getId() == course.getCoursePictureList().get(j).getId()) {
					dele = false;
				}
			}
			if (dele) {
				cpRepo.deleteById(oldPictures.get(i).getId());
			}
		}

		Course save = cRepo.save(course);
		if (save != null)
			return save;
		return null;
	}

	// 修改單筆課程
	public Course updateById(Integer id, Course course) {
		Optional<Course> optional = cRepo.findById(id);

		if (optional.isPresent()) {
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

	// 刪除單筆課程
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
		if (optional.isPresent()) {
			cRepo.deleteById(courseId);
			return true;
		}
		return false;
	}

	// 列出該老師所有課程
	public List<CourseDTO> getCoursesByTeacherId(Integer teacherId) {
		List<Course> courses = cRepo.findByTeacherId(teacherId);
		List<CourseDTO> cDTOs = new ArrayList<>();
		for (Course course : courses) {
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
			cDTO.setCoursePictureList(course.getCoursePictureList());
			cDTOs.add(cDTO);
		}
		return cDTOs;
	}

	public Page<Course> searchCourses(CourseSearchDTO criteria, Pageable pageable) {
		return cRepo.findAll((root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (criteria.getCourseName() != null) {
				predicates.add(builder.like(root.get("courseName"), "%" + criteria.getCourseName() + "%"));
			}

			if (criteria.getCategory() != null) {
				predicates.add(builder.like(root.get("category"), "%" + criteria.getCategory() + "%"));
			}

			if (criteria.getCourseStatus() != null) {
				predicates.add(builder.like(root.get("courseStatus"), "%" + criteria.getCourseStatus() + "%"));
			}

			if (criteria.getMinCoursePrice() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("coursePrice"), criteria.getMinCoursePrice()));
			}

			if (criteria.getMaxCoursePrice() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("coursePrice"), criteria.getMaxCoursePrice()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		}, pageable);
	}

	// course table範例
	public List<CourseCmsTable> getCoursePagenation(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定輸出表格型態
		CriteriaQuery<CourseCmsTable> cq = cb.createQuery(CourseCmsTable.class);

		// 決定select.join表格
		Root<Course> root = cq.from(Course.class);
		Join<Course, Teacher> join = root.join("teacher");
		Join<Course, Category> catJoin = root.join("category");

		// 決定查詢 column
		cq.multiselect(root.get("id"), join.get("teacherName"), root.get("courseName"), root.get("courseDate"),
				root.get("closeDate"), root.get("coursePlace"), root.get("remainPlaces"), root.get("coursePrice"),
				root.get("courseStatus"), root.get("courseIntroduction"), catJoin.get("id").alias("categoryId"));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Course course = new Course();
		Predicate pre = pService.checkCourseCondition(root, join, catJoin, predicate, sortCon, cb, course);

		// 填入 where 條件
		cq.where(pre);

		// 排序條件
		if (sortCon.getSortBy() != null) {
			System.out.println("sort");
			if (pService.hasProperty(course, sortCon.getSortBy())) {
				if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
					cq.orderBy(cb.asc(root.get(sortCon.getSortBy())));
				} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
					cq.orderBy(cb.desc(root.get(sortCon.getSortBy())));
				}
			} else {
				if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
					cq.orderBy(cb.asc(join.get(sortCon.getSortBy())));
				} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
					cq.orderBy(cb.desc(join.get(sortCon.getSortBy())));
				}
			}
		}

		// 分頁
		TypedQuery<CourseCmsTable> query = em.createQuery(cq);
		query.setFirstResult((sortCon.getPage() - 1) * sortCon.getPageSize());
		query.setMaxResults(sortCon.getPageSize());

		// 送出請求
		List<CourseCmsTable> list = query.getResultList();
		if (list != null)
			return list;
		return null;
	}

	// course 分頁頁數
	public Integer getPages(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定輸出表格型態
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		// 決定select.join表格
		Root<Course> root = cq.from(Course.class);
		Join<Course, Teacher> join = root.join("teacher");
		Join<Course, Category> catJoin = root.join("category");

		// 決定查詢 column
		cq.select(cb.count(root));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Course course = new Course();
		Predicate pre = pService.checkCourseCondition(root, join, catJoin, predicate, sortCon, cb, course);
		cq.where(pre);

		// 查詢傯頁數
		Long totalRecords = em.createQuery(cq).getSingleResult();
		Integer totalPages = (int) Math.ceil((double) totalRecords / sortCon.getPageSize());

		return totalPages;
	}

	public List<Integer> getNumberRange(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定輸出表格型態
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();

		// 決定select.join表格
		Root<Course> root = cq.from(Course.class);
		Join<Course, Teacher> join = root.join("teacher");
		Join<Course, Category> catJoin = root.join("category");

		// 決定查詢 column
		cq.multiselect(cb.max(root.get("coursePrice")), cb.min(root.get("coursePrice")));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Course course = new Course();
		Predicate pre = pService.checkCourseCondition(root, join, catJoin, predicate, sortCon, cb, course);
		cq.where(pre);

		// 查詢傯頁數
		List<Tuple> range = em.createQuery(cq).getResultList();
		if (range != null && !range.isEmpty()) {
			Tuple result = range.get(0);
			Integer maxPrice = (Integer) result.get(0);
			Integer minPrice = (Integer) result.get(1);
			List<Integer> ranges = new ArrayList<>();
			ranges.add(maxPrice);
			ranges.add(minPrice);
			return ranges;
		}
		return null;
	}

}
