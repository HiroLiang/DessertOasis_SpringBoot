package com.dessertoasis.demo.service.course;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseCmsTable;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherCmsTable;
import com.dessertoasis.demo.model.course.TeacherDemo;
import com.dessertoasis.demo.model.course.TeacherFrontDTO;
import com.dessertoasis.demo.model.course.TeacherPicture;
import com.dessertoasis.demo.model.course.TeacherPictureRepository;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductPicture;
import com.dessertoasis.demo.model.product.ProductPictureRepository;
import com.dessertoasis.demo.model.recipe.RecipeFrontDTO;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.sort.DateRules;
import com.dessertoasis.demo.model.sort.SearchRules;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;
import com.dessertoasis.demo.service.PageSortService;
import com.dessertoasis.demo.service.member.MemberService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository tRepo;
	
	@Autowired
	private MemberRepository mRepo;
	
	@Autowired
	private MemberService mService;

	@Autowired
	private PageSortService pService;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private TeacherPictureRepository TrPicRepo;

	public Teacher getCourseByTeacherId(Integer id) {
		Optional<Teacher> optional = tRepo.findById(id);
		if(optional.isPresent()) {
			Teacher teacher = optional.get();
		return teacher;
		}
		return null;
	}
	

	public List<Teacher> findAllTeachers(){
		return tRepo.findAll();
	}
	
	
//	//成為教師
//	public Teacher becomeTeacher(Integer memberId) {
//		// 根據memberId查詢是否已經存在關聯的Teacher
//        Teacher existingTeacher = tRepo.findByMemberId(memberId);	
//
//        if (existingTeacher == null) {
//            // 不存在關聯的Teacher，可以執行新增操作
//            Member existingMember = mService.findByMemberId(memberId);
//
//            if (existingMember != null) {
//                // 創建一個新的Teacher實體
//                Teacher newTeacher = new Teacher();
//                newTeacher.setMember(existingMember); // 設定member屬性
//
//                // 保存新創建的Teacher實體
//                  Teacher result = tRepo.save(newTeacher);
//                  System.out.println("新增成功");
//                  return result;
//                
//            } else {
//                // 處理Member不存在的情況
//                // 可以拋出異常或執行其他操作
//            	System.out.println("member不存在");
//            	return null;
//            }
//        } else {
//            // 已經存在關聯的Teacher，不執行新增操作
//            // 可以拋出異常或執行其他操作
//        	System.out.println("已經存在關聯的Teacher，不執行新增操作");
//        	return null;
//        }
//    }
    
	//利用teacherId查單筆資料
	public Teacher getTeacherById(Integer id) {
		Optional<Teacher> result = tRepo.findById(id);
		if(result != null) {
			Teacher teacher = result.get();
			return teacher;
		}return null;
	}
	
	
	
	//新增老師資訊
	public Teacher addTeacher(Teacher teacher) {
        // 在此可以进行一些数据验证或逻辑处理
        // 例如，确保传入的teacher对象的各项数据都合法
        // 如果需要，还可以关联Member等其他对象

        // 调用Repository保存老师数据
        return tRepo.save(teacher);
    }	
	
	//刪除老師
	public Boolean deleteTeacherById(Integer id) {
		Optional<Teacher> optional = tRepo.findById(id);
		if(optional.isPresent()) {
			tRepo.deleteById(id);
			return true;
		}
		return false;
	}
	
	// 修改老師
//		public Boolean updateTeacher(Integer id, Teacher teacher) {
//			Optional<Member> member = mRepo.findById(id);
//			Optional<Teacher> optional = tRepo.findById(teacher.getId());
//
//			if (optional.isPresent()) {
//				Teacher teacherData = optional.get();
//				if (teacherData.getId().equals(member.get().getId())) {
//					tRepo.save(teacher);
//				}
//			
//				return true;
//			}
//			return false;
//		}
	
	@Transactional
	public Teacher update(Teacher teacher) {
		Teacher result = tRepo.save(teacher);
		return result;
	}

	//動態條件搜索
	public List<TeacherDemo> getTeacherPage(SortCondition sortCod) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		// 決定select table
		CriteriaQuery<TeacherDemo> cq = cb.createQuery(TeacherDemo.class);
		Root<Teacher> root = cq.from(Teacher.class);
		// 決定join Table
		Join<Teacher, Member> memberJoin = root.join("member");
		// 決定搜索欄位
		cq.multiselect(root.get("id"), memberJoin.get("fullName"),root.get("teacherName"), root.get("teacherTel"), root.get("teacherMail"),root.get("teacherProfile"));
		// 計算條件數量
		List<DateRules> dateRules = sortCod.getDateRules();
		List<SearchRules> searchRules = sortCod.getSearchRules();
		String numKey = sortCod.getNumKey();
		int n = 0;
		if (dateRules != null)
			n += sortCod.getDateRules().size();
		if (searchRules != null)
			n += sortCod.getSearchRules().size();
		if (numKey != null)
			n += 1;
		System.out.println(n);
		int counter = 0;
		Teacher teacher = new Teacher();
		Member member = new Member();

		// 遍歷搜索條件
		Predicate[] predicates = new Predicate[n];
		// 日期範圍搜索
		if (dateRules != null) {
			for (DateRules rule : dateRules) {
				if (pService.hasProperty(teacher, rule.getKey())) {
					predicates[counter] = cb.between(root.get(rule.getKey()), rule.getStart(), rule.getEnd());
					counter++;
				} else if (pService.hasProperty(member, rule.getKey())) {
					predicates[counter] = cb.between(memberJoin.get(rule.getKey()), rule.getStart(), rule.getEnd());
					counter++;
				}
			}
		}
		// 模糊搜索條件
		if (searchRules != null) {
			for (SearchRules rule : searchRules) {
				if (pService.hasProperty(teacher, rule.getKey())) {
					predicates[counter] = cb.like(root.get(rule.getKey()), rule.getInput());
					counter++;
				} else if (pService.hasProperty(member, rule.getKey())) {
					predicates[counter] = cb.like(memberJoin.get(rule.getKey()), rule.getInput());
					counter++;
				}
			}
		}
		// 數字範圍搜索
		if (numKey != null) {
			if (pService.hasProperty(teacher, numKey)) {
				predicates[counter] = cb.between(root.get(numKey), sortCod.getNumStart(), sortCod.getNumEnd());
				counter++;
			} else if (pService.hasProperty(member, numKey)) {
				predicates[counter] = cb.between(memberJoin.get(numKey), sortCod.getNumStart(), sortCod.getNumEnd());
				counter++;
			}
		}
		//取得排序條件
		if(sortCod.getSortBy()!=null) {
			if(pService.hasProperty(teacher, sortCod.getSortBy())) {
				if(sortCod.getSortWay().equals(SortWay.ASC)) {
					cq.where(predicates).orderBy(cb.asc(root.get(sortCod.getSortBy())));
				}else {
					cq.where(predicates).orderBy(cb.desc(root.get(sortCod.getSortBy())));
				}
			}else if(pService.hasProperty(member, sortCod.getSortBy())) {
				if(sortCod.getSortWay().equals(SortWay.ASC)) {
					cq.where(predicates).orderBy(cb.asc(memberJoin.get(sortCod.getSortBy())));
				}else {
					cq.where(predicates).orderBy(cb.desc(memberJoin.get(sortCod.getSortBy())));
				}
			}else {
				cq.where(predicates);
			}
		}else {
			cq.where(predicates);
		}
		TypedQuery<TeacherDemo> typedQuery = em.createQuery(cq);
		typedQuery.setFirstResult((sortCod.getPage() - 1) * sortCod.getPageSize());
		typedQuery.setMaxResults(sortCod.getPageSize());
		List<TeacherDemo> result = typedQuery.getResultList();
		System.out.println(result);
		return result;
	}

	// 老師後臺表格
	public List<TeacherCmsTable> getTeacherPagenation(SortCondition sortCon) {
			CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定輸出表格型態
			CriteriaQuery<TeacherCmsTable> cq = cb.createQuery(TeacherCmsTable.class);

		// 決定select.join表格
			Root<Teacher> root = cq.from(Teacher.class);
			Join<Teacher, Member> join = root.join("member");

		// 決定查詢 column
//			cq.multiselect(root.get("id"), join.get("teacherName"),root.get("courseName"),root.get("courseDate"),root.get("closeDate"),root.get("coursePlace"),root.get("remainPlaces"),root.get("coursePrice"),root.get("courseStatus"),root.get("courseIntroduction")
//					);
			cq.multiselect(root.get("id"),root.get("teacherName"),
				root.get("teacherTel"),root.get("teacherMail"),root.get("teacherProfile"),
				root.get("teacherContract"),root.get("teacherAccountStatus"),join.get("account"));


		// 加入查詢條件
			Predicate predicate = cb.conjunction();
			Teacher teacher = new Teacher();
//			Predicate pre = pService.checkCourseCondition(root, join, predicate, sortCon, cb, course);
			Predicate pre = pService.checkTeacherCondition(root,join, predicate, sortCon, cb, teacher);
				
		// 填入 where 條件
			cq.where(pre);

		// 排序條件
			if (sortCon.getSortBy() != null) {
				System.out.println("sort");
				if (pService.hasProperty(teacher, sortCon.getSortBy())) {
					if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
						cq.orderBy(cb.asc(root.get(sortCon.getSortBy())));
						} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
							cq.orderBy(cb.desc(root.get(sortCon.getSortBy())));
						}
				}
				} else {
					if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.ASC)) {
						cq.orderBy(cb.asc(join.get(sortCon.getSortBy())));
						} else if (sortCon.getSortBy() != null && sortCon.getSortWay().equals(SortWay.DESC)) {
							cq.orderBy(cb.desc(join.get(sortCon.getSortBy())));
						}
					}
				

		// 分頁
			TypedQuery<TeacherCmsTable> query = em.createQuery(cq);
				query.setFirstResult((sortCon.getPage() - 1) * sortCon.getPageSize());
				query.setMaxResults(sortCon.getPageSize());

		// 送出請求
			List<TeacherCmsTable> list = query.getResultList();
				if (list != null) 
					return list;
				return null;
			}

	public Integer getPages(SortCondition sortCon) {
				CriteriaBuilder cb = em.getCriteriaBuilder();

				// 決定輸出表格型態
				CriteriaQuery<Long> cq = cb.createQuery(Long.class);

				// 決定select.join表格
				Root<Teacher> root = cq.from(Teacher.class);
				Join< Teacher,Member> join = root.join("member");

				// 決定查詢 column
				cq.select(cb.count(root));

				// 加入查詢條件
				Predicate predicate = cb.conjunction();
				Teacher teacher = new Teacher();
//				Predicate pre = pService.checkCourseCondition(root, join, predicate, sortCon, cb, course);
				Predicate pre = pService.checkTeacherCondition(root, join, predicate, sortCon, cb, teacher);
				cq.where(pre);
				
				//查詢傯頁數
				Long totalRecords = em.createQuery(cq).getSingleResult();
				Integer totalPages = (int) Math.ceil((double) totalRecords / sortCon.getPageSize());
				
				return totalPages;
			}
			
	public List<TeacherPicture> getTeacherPicturesByTeacherId(Integer id) {
		        Optional<Teacher> optional = tRepo.findById(id);

		        if (optional.isPresent()) {
		            Teacher teacher = optional.get();
		            List<TeacherPicture> teacherPictures = teacher.getPictures();
		            return teacherPictures;
		        } else {
		            throw new EntityNotFoundException("Product with ID " + id + " not found.");
		        }
		    }
	
			
			@Transactional
			public void addImageToTeacher(Integer teacherId, String sqlPath) {
		        Teacher teacher = getTeacherById(teacherId);
		        if (teacher != null) {
		            List<TeacherPicture> pictures = teacher.getPictures();
		            if (pictures == null) {
		                pictures = new ArrayList<>();
		            }

		            TeacherPicture teacherPicture = new TeacherPicture();
		            System.out.println(sqlPath);
		            teacherPicture.setPictureURL(sqlPath); // 设置商品圖片路径
		            //productPicture.setThumbnailURL(thumbnailPath); // 设置縮圖路径
		            System.out.println(teacher);

		            // 正确设置ProductPicture的product属性
		            teacherPicture.setTeacher(teacher);

		            pictures.add(teacherPicture);
		            teacher.setPictures(pictures);

		            // 使用EntityManager将新的ProductPicture对象保存到数据库
		           em.persist(teacherPicture);
		        }
			}
	
	/*--------------------------------------------前台Criteria ------------------------------------------------*/

	public List<TeacherFrontDTO> getFrontTeacherPagenation(SortCondition sortCon) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 決定select table
		CriteriaQuery<TeacherFrontDTO> cq = cb.createQuery(TeacherFrontDTO.class);

		// 決定select.join表格
		Root<Teacher> root = cq.from(Teacher.class);
//		Join<Teacher, Course> join = root.join("courseList");
		Join<Teacher, List<TeacherPicture>> join = root.join("pictures");
		Join<Teacher,List<Course>> join2 = root.join("courseList");
//				

		// 決定查詢 column
//		cq.multiselect(root.get("id"), root.get("teacherName"),join.get("courseList"),join2.get("pictureURL"));
		cq.multiselect(root.get("id"), root.get("teacherName"),join.get("pictureURL"),join2.get("courseName"));

		// 加入查詢條件
		Predicate predicate = cb.conjunction();
		Teacher teacher = new Teacher();
		Predicate pre = pService.checkTeacherCondition2(root, join, predicate, sortCon, cb, teacher);
//		Predicate pre = pService.checkTeacherCondition3(root, predicate, sortCon, cb, teacher);
				
		// 填入 where 條件
		cq.where(pre);

		// 分頁
		TypedQuery<TeacherFrontDTO> query = em.createQuery(cq);
		query.setFirstResult((sortCon.getPage() - 1) * sortCon.getPageSize());
		query.setMaxResults(sortCon.getPageSize());

		// 送出請求
		List<TeacherFrontDTO> list = query.getResultList();
			if (list != null) {
					return list;
				}
				return null;
			}
			
		@Transactional
		public Teacher updateTeacher(Teacher teacher) {
				Optional<Teacher> optionalTeacher = tRepo.findById(teacher.getId());
				if (optionalTeacher.isPresent()) {
					// 获取数据库中的老师对象
			        Teacher oldTeacher = optionalTeacher.get();
				// 更新老师对象的属性
		        oldTeacher.setTeacherName(teacher.getTeacherName());
		        oldTeacher.setTeacherTel(teacher.getTeacherTel());
		        oldTeacher.setTeacherMail(teacher.getTeacherMail());
		        oldTeacher.setTeacherProfile(teacher.getTeacherProfile());
		        oldTeacher.setTeacherContract(teacher.getTeacherContract());
		        oldTeacher.setTeacherAccountStatus(teacher.getTeacherAccountStatus());
		     // 保存更新后的老师对象到数据库
		        Teacher savedTeacher = tRepo.save(oldTeacher);
		        
		        return savedTeacher;
				}return null;
				}
			
}
