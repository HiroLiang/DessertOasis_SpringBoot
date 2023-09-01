package com.dessertoasis.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.sort.DateRules;
import com.dessertoasis.demo.model.sort.SearchRules;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
	
	@GetMapping("/teacher/{id}/course")
	public Optional<Teacher> getCourseByTeacherId(Integer id) {
        Optional<Teacher> data = tRepo.findById(id);
        return data;
    }
	
	public String getTeacherPage(Integer page, Integer pageSize , String[] sortRule,List<DateRules> dateRules,List<SearchRules> searchRules,String[] numberRange) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
		Root<Teacher> root = cq.from(Teacher.class);
		Join<Teacher, Member> memberJoin = root.join("member");
		cq.multiselect(root.get("id"),memberJoin.get("fullName"),root.get("teacherTel"),root.get("teacherMail"));
		int conditionLength = dateRules.size()+searchRules.size()+numberRange.length;
		Predicate[] predicates = new Predicate[conditionLength];
		int counter = 0;
		for (DateRules dateRule : dateRules) {
			
		}
		
		
		return null;
	}
}
