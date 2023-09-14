package com.dessertoasis.demo.service.course;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseCmsTable;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherCmsTable;
import com.dessertoasis.demo.model.course.TeacherDemo;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.sort.DateRules;
import com.dessertoasis.demo.model.sort.SearchRules;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;
import com.dessertoasis.demo.service.PageSortService;
import com.dessertoasis.demo.service.member.MemberService;

import jakarta.persistence.EntityManager;
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

//	public Optional<Teacher> getCourseByTeacherId(Integer id) {
//		Optional<Teacher> optional = tRepo.findById(id);
//		if(optional.isPresent()) {
//			Teacher teacher = optional.get();
//		return teacher;}
//	}
//	

	public List<Teacher> findAllTeachers(){
		return tRepo.findAll();
	}
	
	
	//成為教師
	public Teacher becomeTeacher(Integer memberId) {
		// 根據memberId查詢是否已經存在關聯的Teacher
        Teacher existingTeacher = tRepo.findByMemberId(memberId);	

        if (existingTeacher == null) {
            // 不存在關聯的Teacher，可以執行新增操作
            Member existingMember = mService.findByMemberId(memberId);

            if (existingMember != null) {
                // 創建一個新的Teacher實體
                Teacher newTeacher = new Teacher();
                newTeacher.setMember(existingMember); // 設定member屬性

                // 保存新創建的Teacher實體
                  Teacher result = tRepo.save(newTeacher);
                  System.out.println("新增成功");
                  return result;
                
            } else {
                // 處理Member不存在的情況
                // 可以拋出異常或執行其他操作
            	System.out.println("member不存在");
            	return null;
            }
        } else {
            // 已經存在關聯的Teacher，不執行新增操作
            // 可以拋出異常或執行其他操作
        	System.out.println("已經存在關聯的Teacher，不執行新增操作");
        	return null;
        }
    }
    
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
	
	public void update(Teacher teacher) {
		tRepo.save(teacher);
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
		cq.multiselect(root.get("id"), memberJoin.get("fullName"), root.get("teacherTel"), root.get("teacherMail"));
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

	// Order table範例
			public List<TeacherCmsTable> getTeacherPagenation(SortCondition sortCon) {
				CriteriaBuilder cb = em.getCriteriaBuilder();

				// 決定輸出表格型態
				CriteriaQuery<TeacherCmsTable> cq = cb.createQuery(TeacherCmsTable.class);

				// 決定select.join表格
				Root<Teacher> root = cq.from(Teacher.class);
				Join<Teacher, Member> join = root.join("member");

				// 決定查詢 column
//				cq.multiselect(root.get("id"), join.get("teacherName"),root.get("courseName"),root.get("courseDate"),root.get("closeDate"),root.get("coursePlace"),root.get("remainPlaces"),root.get("coursePrice"),root.get("courseStatus"),root.get("courseIntroduction")
//						);
				cq.multiselect(root.get("id"), join.get("fullName"),root.get("teacherName"),root.get("teacherProfilePic"),root.get("teacherTel"),root.get("teacherMail"),root.get("teacherProfile")
						);


				// 加入查詢條件
				Predicate predicate = cb.conjunction();
				Teacher teacher = new Teacher();
//				Predicate pre = pService.checkCourseCondition(root, join, predicate, sortCon, cb, course);
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
	
	
}
