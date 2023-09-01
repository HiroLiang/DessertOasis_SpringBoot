package com.dessertoasis.demo.service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.sort.DateRules;
import com.dessertoasis.demo.model.sort.SearchRules;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.model.sort.SortCondition.SortWay;

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

	@PersistenceContext
	private EntityManager em;

	public Optional<Teacher> getCourseByTeacherId(Integer id) {
		Optional<Teacher> data = tRepo.findById(id);
		return data;
	}

	public String getTeacherPage(SortCondition sortCod) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		// 決定select table
		CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
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
				if (hasProperty(teacher, rule.getKey())) {
					predicates[counter] = cb.between(root.get(rule.getKey()), rule.getStart(), rule.getEnd());
					counter++;
				} else if (hasProperty(member, rule.getKey())) {
					predicates[counter] = cb.between(memberJoin.get(rule.getKey()), rule.getStart(), rule.getEnd());
					counter++;
				}
			}
		}
		// 模糊搜索條件
		if (searchRules != null) {
			for (SearchRules rule : searchRules) {
				if (hasProperty(teacher, rule.getKey())) {
					predicates[counter] = cb.like(root.get(rule.getKey()), rule.getInput());
					counter++;
				} else if (hasProperty(member, rule.getKey())) {
					predicates[counter] = cb.like(memberJoin.get(rule.getKey()), rule.getInput());
					counter++;
				}
			}
		}
		// 數字範圍搜索
		if (numKey != null) {
			if (hasProperty(teacher, numKey)) {
				predicates[counter] = cb.between(root.get(numKey), sortCod.getNumStart(), sortCod.getNumEnd());
				counter++;
			} else if (hasProperty(member, numKey)) {
				predicates[counter] = cb.between(memberJoin.get(numKey), sortCod.getNumStart(), sortCod.getNumEnd());
				counter++;
			}
		}
		if(sortCod.getSortBy()!=null) {
			if(hasProperty(teacher, sortCod.getSortBy())) {
				if(sortCod.getSortWay().equals(SortWay.ASC)) {
					cq.where(predicates).orderBy(cb.asc(root.get(sortCod.getSortBy())));
				}else {
					cq.where(predicates).orderBy(cb.desc(root.get(sortCod.getSortBy())));
				}
			}else if(hasProperty(member, sortCod.getSortBy())) {
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
		TypedQuery<Teacher> typedQuery = em.createQuery(cq);
		typedQuery.setFirstResult((sortCod.getPage() - 1) * sortCod.getPageSize());
		typedQuery.setMaxResults(sortCod.getPageSize());
		List<Teacher> result = typedQuery.getResultList();
		System.out.println(result);
		return null;
	}

	public static boolean hasProperty(Object bean, String propertyName) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (propertyDescriptor.getName().equals(propertyName)) {
					return true;
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
