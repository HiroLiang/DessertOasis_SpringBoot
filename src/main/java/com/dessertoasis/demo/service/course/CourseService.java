package com.dessertoasis.demo.service.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseCmsTable;
import com.dessertoasis.demo.model.course.CourseDTO;
import com.dessertoasis.demo.model.course.CourseRepository;
import com.dessertoasis.demo.model.course.CourseSearchDTO;
import com.dessertoasis.demo.model.course.CourseTeacherDTO;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderCmsTable;
import com.dessertoasis.demo.model.product.ProdSearchDTO;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;
import com.dessertoasis.demo.service.PageSortService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class CourseService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CourseRepository cRepo;
	
	@Autowired
	private TeacherRepository tRepo;
	
	@Autowired
	private MemberRepository mRepo;
	
	@Autowired
	private PageSortService pService;
	
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
	//新增課程
//	public Boolean addCourse(Integer id,CourseCreateDTO cDTO) {
//		Optional<Member> optional = mRepo.findById(id);
//		if(optional.isPresent()) {
//			
//		}
//	}
	
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
	
	// Order table範例
		public List<CourseCmsTable> getCoursePagenation(SortCondition sortCon) {
			CriteriaBuilder cb = em.getCriteriaBuilder();

			// 決定輸出表格型態
			CriteriaQuery<CourseCmsTable> cq = cb.createQuery(CourseCmsTable.class);

			// 決定select.join表格
			Root<Course> root = cq.from(Course.class);
			Join<Course, Teacher> join = root.join("teacher");

			// 決定查詢 column
			cq.multiselect(root.get("id"), join.get("teacherName"),root.get("courseName"),root.get("courseDate"),root.get("closeDate"),root.get("coursePlace"),root.get("remainPlaces"),root.get("coursePrice"),root.get("courseStatus")
					);

			// 加入查詢條件
			Predicate predicate = cb.conjunction();
			Course course = new Course();
			Predicate pre = pService.checkCourseCondition(root, join, predicate, sortCon, cb, course);
			
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

		public Integer getPages(SortCondition sortCon) {
			CriteriaBuilder cb = em.getCriteriaBuilder();

			// 決定輸出表格型態
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			// 決定select.join表格
			Root<Course> root = cq.from(Course.class);
			Join<Course, Teacher> join = root.join("teacher");

			// 決定查詢 column
			cq.select(cb.count(root));

			// 加入查詢條件
			Predicate predicate = cb.conjunction();
			Course course = new Course();
			Predicate pre = pService.checkCourseCondition(root, join, predicate, sortCon, cb, course);
			cq.where(pre);
			
			//查詢傯頁數
			Long totalRecords = em.createQuery(cq).getSingleResult();
			Integer totalPages = (int) Math.ceil((double) totalRecords / sortCon.getPageSize());
			
			return totalPages;
		}
}
