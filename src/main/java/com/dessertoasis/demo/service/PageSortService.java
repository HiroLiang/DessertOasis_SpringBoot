package com.dessertoasis.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.recipe.Ingredient;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.dessertoasis.demo.model.sort.SortCondition;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class PageSortService {

	@PersistenceContext
	private EntityManager em;
	
	public void GetRecipe() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Recipes> query = builder.createQuery(Recipes.class);
		Root<Recipes> root = query.from(Recipes.class);
		Join<Recipes, Ingredient> join = root.join("ingredient");
		
		List<Predicate> pre = new ArrayList<>();
		
		Predicate[] predicate = new Predicate[pre.size()];
		
		query.multiselect(root.get("id"),root.get("recipeAuthor"),join.get(""));
		
		Predicate a = builder.gt(root.get("price"), 20);
		Predicate b = builder.between(root.get("age"), 20,40);
		query.where(builder.or(a,b));
		
		TypedQuery<Recipes> typedQuery = em.createQuery(query);
		typedQuery.setFirstResult(0);
		typedQuery.setMaxResults(10);
		List<Recipes> resultList = typedQuery.getResultList();
	}

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
