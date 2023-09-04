package com.dessertoasis.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.sort.SortCondition;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class PageSortService {

	@PersistenceContext
	private EntityManager em;
	

	public String getPageJson(SortCondition sortCod) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		

			CriteriaQuery<Member> cq = cb.createQuery(Member.class);
			Root<Member> root = cq.from(Member.class);
			Predicate[] predicates = new Predicate[20];
			int counter = 0;
			if(sortCod.getSortBy() != null) {
				predicates[counter] = cb.like(root.get("itemName"), "%"+sortCod.getSortBy()+"%");
				counter++;
			}
			CriteriaQuery<Member> select = cq.select(root).where(predicates);
			TypedQuery<Member> query = em.createQuery(select);
			query.setFirstResult((sortCod.getPage()-1)*sortCod.getPageSize());
			query.setMaxResults(sortCod.getPageSize());
			List<Member> list = query.getResultList();
			System.out.println(list);

		return null;
	}

}
